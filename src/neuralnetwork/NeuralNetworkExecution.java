package neuralnetwork;

import csvparser.CSVParser;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import utils.Constants;

public class NeuralNetworkExecution {
	
	static Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	private double learningRate = 0.1;
	private double momentum = 0.9;
	private int epochs = 100000;
	private int delimiter; //Initialized in constructor
	private int deltaTime = 1;
	private int percentage; //Initialized in constructor
	private int[] desiredInputs; //Initialized in setCSVParser()
	private int mainColumnId; // //Initialized in setCSVParser()
	private NeuralNetwork neuralnetwork;
	private CSVParser csvparser;
	private double mainColumnIdMaxValue = 0;

	public NeuralNetworkExecution() {
		DOMConfigurator.configure("xml/LogNeuralNetworkExecution.xml");
		
		try {
			setDelimiter(90);
		} catch (InvalidPercentageException e) {
			System.exit(0);
		}
	
	}
	
	public void exec() throws DelimiterOutOfBoundsException, UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {
		
		if(delimiter <= 0 || delimiter >= csvparser.countLines())
			throw new DelimiterOutOfBoundsException();
		
		double error = 0;
		double errorMin = Constants.INT_MAX;
		int epochsIt = 0;
		int bestEpoch = 0;
		
		while(epochsIt < epochs) {
			
			neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
			error = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, mainColumnIdMaxValue);
			
			if(error < errorMin) {
				errorMin = error;
				bestEpoch = epochsIt;
			}
			
			if(epochsIt % 100 == 0)
				logger.debug("error : " + error + " EUR - error min : " + errorMin + " EUR at epoch : " + bestEpoch + " epochs : " + epochsIt);
			
			epochsIt++;
		}
		
		logger.debug("error min (at epoch : " + bestEpoch + " ) : " + errorMin);
	}
	
	
	public double execGetValue() throws UnknownColumnException, InterruptedException, InvalidStateOfParser {

		int epochsIterator = 0;
		 
		 while(epochsIterator < epochs) {
			 neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
		        epochsIterator++;
		 }
		 return neuralnetwork.runningProcess(csvparser.countLines()-1, desiredInputs, csvparser, mainColumnId, mainColumnIdMaxValue);
	}
	
	public double execGetValueWithLowestError() throws UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {
		
	    double errorRate;
	    double errorRateMin = (double) Constants.INT_MAX;
	    int epochsIterator = 0;
	    int bestEpoch = 0;

	    while(epochsIterator < epochs) {
	    	neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
	        errorRate = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, mainColumnIdMaxValue);

	        if(errorRate <= errorRateMin)
	        {
	            errorRateMin = errorRate;
	            bestEpoch = epochsIterator;
	        }

	        epochsIterator++;
	    }

	    logger.debug("Best epoch : " + bestEpoch);
	   
	    //Best epoch
	    epochsIterator = 0;
	    while(epochsIterator < bestEpoch) {
	    	neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
	        epochsIterator++;
	    }
	    
	    return neuralnetwork.runningProcess(csvparser.countLines()-1, desiredInputs, csvparser, mainColumnId, mainColumnIdMaxValue);
	}
	
	public NeuralNetworkExecution setLearningRate(double learningRate) {
		logger.debug("Learning Rate = " + learningRate);
		this.learningRate = learningRate;
		return this;
	}
	
	public NeuralNetworkExecution setMomentum(double momentum) {
		logger.debug("Momentum = " + momentum);
		this.momentum = momentum;
		return this;
	}
	
	public NeuralNetworkExecution setEpochs(int epochs) {
		logger.debug("Epochs = " + epochs);
		this.epochs = epochs;
		return this;
	}
	
	public NeuralNetworkExecution setDelimiter(int percentage) throws InvalidPercentageException {
		
		if(percentage >= 100 || percentage <= 0)
			throw new InvalidPercentageException(percentage);
		
		logger.debug("Percentage = " + percentage);
		this.delimiter = (this.csvparser.countLines() * percentage) / 100;
		logger.debug("Delimiter = " + delimiter);
		this.percentage = percentage;
		return this;
	}
	
	public NeuralNetworkExecution setParser(CSVParser csvparser) {
		this.csvparser = csvparser;
		
		for(int i=0; i<csvparser.countColumns(); i++) {
			
		}
		
		return this;
	}
	
	public NeuralNetworkExecution setDeltaTime(int deltaTime) {
		logger.debug("Delta Time = " + deltaTime);
		this.deltaTime = deltaTime;
		return this;
	}
	
	public NeuralNetworkExecution setDesiredInputs(int[] desiredInputs) {
		for(int i=0; i<csvparser.countColumns(); i++)
			logger.debug("Desired Inputs["+i+"] = " + desiredInputs[i]);
		this.desiredInputs = desiredInputs;
		return this;
	}
	
	public NeuralNetworkExecution setMainColumnId(int mainColumnId) {
		logger.debug("Main Column Id = " + mainColumnId);
		this.mainColumnId = mainColumnId;
		return this;
	}

	public NeuralNetworkExecution setNeuralNetwork(NeuralNetwork neuralnetwork) {
		this.neuralnetwork = neuralnetwork;
		return this;
	}
	
	public NeuralNetworkExecution setMaximumOfMainColumnId(double mainColumnIdMaxValue) {
		logger.debug("Main Column Id Max Value = " + mainColumnIdMaxValue);
		this.mainColumnIdMaxValue = mainColumnIdMaxValue;
		return this;
	}
	
	public CSVParser getParser() {
		return csvparser;
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	public NeuralNetwork getNeuralNetwork() {
		return neuralnetwork;
	}
	
	public int getDeltaTime() {
		return deltaTime;
	}
	
	public int getMainColumnId() {
		return mainColumnId;
	}
}