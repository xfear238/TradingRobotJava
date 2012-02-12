package robot;

import java.io.File;
import java.util.TimerTask;
import java.util.logging.Logger;

import records.PricesRecord;
import records.TradesRecord;
import utils.Constants;
import utils.NeuralModeExecutionEnum;

import neuralnetwork.ActivationFunctionLinear;
import neuralnetwork.ActivationFunctionSigmoid;
import neuralnetwork.Multiplier;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.NeuralNetworkExecution;
import neuralnetwork.NeuralNetworkExecutionFactory;
import neuralnetwork.NeuralNetworkFactory;

import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.CSVParserTransformerPercentage;

import filemonitoring.FileMonitorRobot;


//Normaliser ce putin de pourcentage !!!!!!!!
public class TradingRobot implements IRobot {
	
	
	private String filename;
	private String desiredInputs;
	private int hiddenCount;
	private int deltaTime;
	private int mainColumnId;
	private int epochs;
	private int percentage;
	private double learningRate;
	private double momentum;
	private CSVParser csvParser;
	private CSVParserTransformer csvparsertransformer;
	private NeuralNetworkExecution neuralnetworkexecution;
	private PricesRecord pricesrecord = new PricesRecord();
	private TradesRecord tradesrecord = new TradesRecord();
	private NeuralModeExecutionEnum neuralModeExecution = NeuralModeExecutionEnum.FIND_BEST_ERROR_DISP;
	
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
															  .addActivationFunction(new ActivationFunctionSigmoid())
															  .createHiddenLayer(hiddenCount)
															  .createOuputLayer(Constants.OUTPUT_COUNT);
			
			csvparsertransformer = new CSVParserTransformerNormalization(csvParser);
			
			neuralnetworkexecution = NeuralNetworkExecutionFactory.createInstance()
																  .setParser(csvparsertransformer.getCSVParser())
																  .setMultiplier(new Multiplier(csvParser.getMaxValueFromColumn(mainColumnId)))
																  .setRawCsvParser(csvParser)
																  .setNeuralNetwork(neuralnetwork)
																  .setLearningRate(learningRate)
																  .setMomentum(momentum)
																  .setEpochs(epochs)
																  .setPercentageTrainingTesting(percentage)
																  .setDeltaTime(deltaTime)
																  .setDesiredInputs(intDesiredInputs)
																  .setMainColumnId(mainColumnId);
			
			pricesrecord.registerObserver(tradesrecord);
			TimerTask task = new FileMonitorRobot(new File(filename), this);
			
			while(true) {
				task.run();
				Thread.sleep(Constants.LATENCY_ON_DETECTING_FILE_MODIFICATION);
			}
			
		} catch (Exception e) {}
	}
	
	public void onChange() {
		
		System.out.println("File has changed... Locking resource ... Processing");
		
		try {
			
			csvParser.reload();
			
			neuralnetworkexecution.getNeuralNetwork().resetWeights();
			neuralnetworkexecution.setMultiplier(new Multiplier(csvParser.getMaxValueFromColumn(mainColumnId)));
			
			if(neuralModeExecution == NeuralModeExecutionEnum.RUN_TRADING_ROBOT) {
				System.out.println("Mode : EXEC_MODE_RETURN enabled");
				
				double predictedFutureValue = neuralnetworkexecution.execGetFuturePrice();
				double lastKnownValue = csvParser.getValue(csvParser.countLines()-1, neuralnetworkexecution.getMainColumnId());
				
				System.out.println("Last = " + lastKnownValue);
				System.out.println("Future (prediction) = " + predictedFutureValue);
				
				pricesrecord.addPrice(lastKnownValue);
				
				if(lastKnownValue <= predictedFutureValue)
					tradesrecord.saveTrade("Credit-agricole", lastKnownValue, predictedFutureValue, Constants.VOLUME, "BUY");
				else
					tradesrecord.saveTrade("Credit-agricole", lastKnownValue, predictedFutureValue, Constants.VOLUME, "SELL");	
				
				tradesrecord.debug();
				
				System.out.println("Profit and Loss = " + tradesrecord.getProfitLoss());
				
			}
			else if(neuralModeExecution == NeuralModeExecutionEnum.FIND_BEST_ERROR_DISP) {
				System.out.println("Mode : EXEC_NO_RETURN enabled");
				neuralnetworkexecution.execFindBestErrorDisp();
			}
			
		}
		
		catch (Exception e) {}
		
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

	public TradingRobot setPercentage(int percentage) {
		this.percentage = percentage;
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

	public TradingRobot setModeForExecution(NeuralModeExecutionEnum neuralModeExecution) {
		this.neuralModeExecution = neuralModeExecution;
		return this;
	}
}
