package neuralnetwork;

import csvparser.CSVParser;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import utils.Constants;

//mettre pattern singleton
public class NeuralNetworkExecution {

	static Logger logger = Logger.getLogger(NeuralNetworkExecution.class);

	//Dangereux de stocker des choses qui peuvent se recalculer. On risque d'oublier des choses (delimiter et tout)
	private double learningRate;
	private double momentum;
	private int epochs;
	private int deltaTime;
	private int percentage;
	private int[] desiredInputs;
	private int mainColumnId;
	private NeuralNetwork neuralnetwork;
	private CSVParser csvparser;
	private CSVParser rawCsvParser;
	private Multiplier multiplier = new Multiplier(1);
	
	public NeuralNetworkExecution() {
		DOMConfigurator.configure("xml/LogNeuralNetworkExecution.xml");
	}

	public void execFindBestErrorDisp() throws DelimiterOutOfBoundsException, UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {

		int delimiter = getDelimiter();
		
		if(delimiter <= 0 || delimiter >= csvparser.countLines())
			throw new DelimiterOutOfBoundsException();

		double error = 0;
		double errorMin = Constants.INT_MAX;
		int epochsIt = 0;
		int bestEpoch = 0;
		
		logger.debug("delimiter = " + delimiter);
		logger.debug("filename = " + rawCsvParser.filename);
		logger.debug("filename count lines = " + csvparser.countLines());
		
		logger.debug("desiredInputs = " + desiredInputs[0]);
		logger.debug("momentum = " + momentum);
		logger.debug("learningRate = " + learningRate);
		logger.debug("epochs = " + epochs);
		logger.debug("delimiter = " + percentage + " %");
		logger.debug("mainColumnId = " + mainColumnId);
		logger.debug("deltatime = " + deltaTime);
	
		while(epochsIt < epochs) {
			
			neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
			error = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, multiplier.getMultiplier());

			if(error < errorMin) {
				errorMin = error;
				bestEpoch = epochsIt;
			}

			if(epochsIt % 100 == 0)
				logger.debug("error : " + error + " EUR - error min : " + errorMin + " EUR at epoch : " + bestEpoch + " epochs : " + epochsIt);

			epochsIt++;
		}

		logger.debug("Prediction " + deltaTime + " days ahead : error min (at epoch : " + bestEpoch + " ) : " + errorMin);
	}
	
	public void execFindBestErrorSilentDisp() throws DelimiterOutOfBoundsException, UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {

		int delimiter = getDelimiter();
		
		if(delimiter <= 0 || delimiter >= csvparser.countLines())
			throw new DelimiterOutOfBoundsException();

		double error = 0;
		double errorMin = Constants.INT_MAX;
		int epochsIt = 0;
		int bestEpoch = 0;

		logger.debug("Computing the best error and the best epoch. Please wait, this may take a while...");
		
		while(epochsIt < epochs) {
			neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
			error = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, multiplier.getMultiplier());

			if(error < errorMin) {
				errorMin = error;
				bestEpoch = epochsIt;
			}

			epochsIt++;
		}

		logger.debug("Prediction " + deltaTime + " days ahead : error min (at epoch : " + bestEpoch + " ) : " + errorMin);
	}

	public void execGetFuturePriceDisp() throws UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {
		
		int delimiter = getDelimiter();
	    double errorRate;
	    double errorRateMin = (double) Constants.INT_MAX;
	    int epochsIterator = 0;

	    while(epochsIterator < epochs) {
	    	neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
	        errorRate = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, multiplier.getMultiplier());

	        if(errorRate <= errorRateMin) {
	        	neuralnetwork.saveWeights();
	            errorRateMin = errorRate;
	        }
	        
	        epochsIterator++;
	    }

	    neuralnetwork.loadWeights();
	    
	    double future = neuralnetwork.runningProcess(csvparser.countLines()-1, desiredInputs, csvparser, mainColumnId, multiplier.getMultiplier());
	    logger.debug("Prediction " + deltaTime + " days ahead : " + future);
	    
	}
	
	public double execGetFuturePrice() throws UnknownColumnException, InterruptedException, InvalidStateOfParser, MainColumnIdMaxValueException {
		
		int delimiter = getDelimiter();
	    double errorRate;
	    double errorRateMin = (double) Constants.INT_MAX;
	    int epochsIterator = 0;

	    while(epochsIterator < epochs) {
	    	neuralnetwork.trainProcess(delimiter, desiredInputs, csvparser, mainColumnId, deltaTime, learningRate, momentum);
	        errorRate = neuralnetwork.error(delimiter, csvparser, deltaTime, desiredInputs, mainColumnId, multiplier.getMultiplier());

	        if(errorRate <= errorRateMin) {
	        	neuralnetwork.saveWeights();
	            errorRateMin = errorRate;
	        }
	        epochsIterator++;
	    }
	    
	    neuralnetwork.loadWeights();
	    return neuralnetwork.runningProcess(csvparser.countLines()-1, desiredInputs, csvparser, mainColumnId, multiplier.getMultiplier());
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

	public NeuralNetworkExecution setParser(CSVParser csvparser) {
		this.csvparser = csvparser;
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
	
	public NeuralNetworkExecution setPercentageTrainingTesting(int percentage) {
		logger.debug("Percentage = " + percentage);
		if(percentage >= 100 || percentage <= 0)
			try {
				throw new InvalidPercentageException(percentage);
			} catch (InvalidPercentageException e) {}
		this.percentage = percentage;
		return this;
	}

	public NeuralNetworkExecution setNeuralNetwork(NeuralNetwork neuralnetwork) {
		this.neuralnetwork = neuralnetwork;
		return this;
	}

	public NeuralNetworkExecution setRawCsvParser(CSVParser rawcsvparser) {
		this.rawCsvParser = rawcsvparser;
		return this;
	}
	
	public NeuralNetworkExecution setMultiplier(Multiplier multiplier) {
		logger.debug("Multiplier = " + multiplier.getMultiplier());
		this.multiplier = multiplier;
		return this;
		
	}
	
	public CSVParser getRawCsvParser() {
		return rawCsvParser;
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
	
	public int getDelimiter() {
		int delimiter = (this.csvparser.countLines() * percentage) / 100;
		logger.debug("Delimiter = " + delimiter);
		return delimiter;
	}
	
	public int getMainColumnId() {
		return mainColumnId;
	}

	
}