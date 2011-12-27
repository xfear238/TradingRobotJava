package robot;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import records.PricesRecord;
import records.TradesRecord;
import utils.Constants;

import neuralnetwork.DelimiterOutOfBoundsException;
import neuralnetwork.InvalidPercentageException;
import neuralnetwork.MainColumnIdMaxValueException;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.NeuralNetworkExecution;
import neuralnetwork.NeuralNetworkExecutionFactory;
import neuralnetwork.NeuralNetworkFactory;

import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;
import filemonitoring.FileMonitorRobot;

public class TradingRobot implements IRobot {

	private static final int EXEC_RETURN = 1;
	private static final int EXEC_NO_RETURN = 2;
	
	private String filename;
	private String desiredInputs;
	private int hiddenCount;
	private int deltaTime;
	private int mainColumnId;
	private int epochs;
	private int delimiter;
	private double learningRate;
	private double momentum;
	private CSVParser csvParser;
	private NeuralNetworkExecution neuralnetworkexecution;
	private int mode = EXEC_RETURN;
	private PricesRecord pricesrecord = new PricesRecord();
	private TradesRecord tradesrecord = new TradesRecord();
	
	@SuppressWarnings("static-access")
	public void run() {
		
		try {
			
			csvParser = new CSVParser(filename);
			
			String[] splitDesiredInputs = desiredInputs.split(Constants.SEPARATOR);
			int[] intDesiredInputs = new int[csvParser.countColumns()];
		    
			for (int i = 0; i < splitDesiredInputs.length; i++) {
		    	intDesiredInputs[i] = Integer.parseInt(splitDesiredInputs[i]);
		     }
			
			int inputCount = 0;
			for(int i = 0; i < intDesiredInputs.length; i++)
				inputCount += intDesiredInputs[i];
			
			NeuralNetwork neuralnetwork = NeuralNetworkFactory.createInstance()
															  .createInputLayer(inputCount)
															  .createHiddenLayer(hiddenCount)
															  .createOuputLayer(Constants.OUTPUT_COUNT);
			
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvParser);
			CSVParser csvParser2 = csvparserTransformer.getCSVParser();
			
			neuralnetworkexecution = NeuralNetworkExecutionFactory.createInstance()
																  .setParser(csvParser2)
																  .setNeuralNetwork(neuralnetwork)
																  .setLearningRate(learningRate)
																  .setMomentum(momentum)
																  .setEpochs(epochs)
																  .setDelimiter(delimiter)
																  .setDeltaTime(deltaTime)
																  .setDesiredInputs(intDesiredInputs)
																  .setMainColumnId(mainColumnId)
																  .setMaximumOfMainColumnId(csvParser.getMaxValueFromColumn(mainColumnId));
			
			//Pattern observer
			pricesrecord.registerObserver(tradesrecord);
			
			TimerTask task = new FileMonitorRobot(new File(filename), this);
			
			while(true) {
				task.run();
				Thread.currentThread().sleep(Constants.LATENCY_ON_DETECTING_FILE_MODIFICATION);
			}
			
		} catch (IOException 
				| InvalidCSVFile 
				| UnknownColumnException 
				| InvalidPercentageException 
				| InvalidStateOfParser 
				| InterruptedException e) {
		}
	}
	
	public void onChange() {
		
		try {
			
			csvParser.reload();
			
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvParser);
			CSVParser csvParser2 = csvparserTransformer.getCSVParser();

			neuralnetworkexecution.setParser(csvParser2)
								  .setDelimiter(neuralnetworkexecution.getPercentage())
								  .setMaximumOfMainColumnId(csvParser.getMaxValueFromColumn(mainColumnId));
			
			neuralnetworkexecution.getNeuralNetwork().resetWeights();

			
			System.out.print("File has changed... Locking resource ... Processing ");
			
			if(mode == EXEC_RETURN) {
				
				double predictedFutureValue = neuralnetworkexecution.execGetValueWithLowestError();
				double lastKnownValue = csvParser.getValue(csvParser.countLines()-1, neuralnetworkexecution.getMainColumnId());
				
				System.out.println(" ... [OK]");
				System.out.println("Last = " + lastKnownValue);
				System.out.println("Future (prediction) = " + predictedFutureValue);
				
				pricesrecord.addPrice(lastKnownValue);
				
				if(lastKnownValue <= predictedFutureValue) {
					tradesrecord.saveTrade(lastKnownValue, predictedFutureValue, Constants.VOLUME, "BUY");
				}
				else {
					tradesrecord.saveTrade(lastKnownValue, predictedFutureValue, Constants.VOLUME, "SELL");
				}
				
				tradesrecord.debug();
				
				System.out.println("Profit and Loss = " + tradesrecord.getProfitLoss());
				
			}
			else if(mode == EXEC_NO_RETURN) {
			
				neuralnetworkexecution.exec();
				
			}
		}
		
		catch (UnknownColumnException
			 | InterruptedException
			 | InvalidStateOfParser 
			 | MainColumnIdMaxValueException
			 | IOException
			 | InvalidCSVFile 
			 | InvalidPercentageException 
			 | DelimiterOutOfBoundsException e) {
		}
		
	}
	
	
	public TradingRobot setFilename(String filename) {
		this.filename = filename;
		return this;
	}
	
	public TradingRobot setDesiredInputs(String desiredInputs) {
		this.desiredInputs = desiredInputs;
		return this;
	}
	
	public TradingRobot setHiddenCount(int hiddenCount) {
		this.hiddenCount = hiddenCount;
		return this;
	}
	
	public TradingRobot setDeltaTime(int deltaTime) {
		this.deltaTime = deltaTime;
		return this;
	}

	public TradingRobot setMainColumnId(int mainColumnId) {
		this.mainColumnId = mainColumnId;
		return this;
	}

	public TradingRobot setDelimiter(int delimiter) {
		this.delimiter = delimiter;
		return this;
	}

	public TradingRobot setEpochs(int epochs) {
		this.epochs = epochs;
		return this;
	}

	public TradingRobot setLearningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}

	public TradingRobot setMomentum(double momentum) {
		this.momentum = momentum;
		return this;
	}
}
