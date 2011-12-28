package neuralnetwork;

import java.util.ArrayList;

import csvparser.CSVParser;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import utils.Constants;
import utils.Utils;
import layer.Layer;
import neuron.INeuron;
import neuron.InputNeuron;
import neuron.HiddenNeuron;
import neuron.OutputNeuron;

public class NeuralNetwork {

	static Logger logger = Logger.getLogger(NeuralNetwork.class);
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private int layercount = 0;
	private double[][] weights = new double[Constants.MAX_WEIGHTS][Constants.MAX_WEIGHTS];
	private ArrayList<double[][]> weightsList = new ArrayList<double[][]>(1000);
	private int weightsListCurrent = 0;

	public void addLayer(Layer layer) {
		layers.add(layer);
		layercount++;
		logger.debug("layer added");
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public int getLayerCount() {
		return layercount;
	}

	public void resetWeights() {

		weightsListCurrent = 0;
		weightsList.clear();

		for(int i=0;i<Constants.MAX_WEIGHTS;i++)
			for (int j=0;j<Constants.MAX_WEIGHTS;j++)
				weights[i][j] = (double) (Math.random() - 0.5);

	    weightsList.add(0, weights);
	    addWeights(weights);
	}

	public void addWeights(double[][] weightsAdd) {

		if(weightsListCurrent == 999) {
			weightsList.clear();
			weightsList.add(0, weightsAdd);
			weightsListCurrent = 0;
		}

		weightsListCurrent++;
		weightsList.add(weightsListCurrent, weightsAdd);

	}

	public NeuralNetwork() {

		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Neural Network created");

		for(int i=0;i<Constants.MAX_WEIGHTS;i++)
			for (int j=0;j<Constants.MAX_WEIGHTS;j++)
				weights[i][j] = (double) (Math.random() - 0.5);

	    weightsList.add(0, weights);
	    addWeights(weights);
	}

	public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {

		this();
		
		Layer inputLayer = new Layer();
		Layer hiddenLayer = new Layer();
		Layer outputLayer = new Layer();

		for(int i=0; i<inputCount; i++) {
			INeuron neuron = new InputNeuron();
			inputLayer.addNeuron(neuron);
		}

		for(int i=0; i<hiddenCount; i++) {
			INeuron neuron = new HiddenNeuron();
			hiddenLayer.addNeuron(neuron);
		}


		for(int i=0; i<=outputCount; i++) {
			INeuron neuron = new OutputNeuron();
			outputLayer.addNeuron(neuron);
		}

		addLayer(inputLayer);
		addLayer(hiddenLayer);
		addLayer(outputLayer);
	}

	public double train(double[] inputValues, double trueValue, double learningRate, double momentum) throws InterruptedException {

		double[] net = new double[1024];
		double[] signet = new double[1024];
		double[] input = new double[1024];
		double[] deltas = new double[1024];
		double out;

		int inputCount = getLayers().get(0).getCount();
		int hiddenCount = getLayers().get(1).getCount();

		for(int i=0; i<inputCount; i++) {
			input[i] = inputValues[i];
		}

		for(int i=0; i<hiddenCount; i++) {
			net[i] = 1 * weights[0][i];
			for(int j=0; j<inputCount; j++) {
				net[i] += input[j] * weights[j+1][i];   // j+1 car bias
			}

		}

		for(int i=0; i<hiddenCount; i++) {
			signet[i] = Utils.sigmoid(net[i]);
		}

		out  = 1 * weights[0][hiddenCount];

		for(int i=0; i<hiddenCount; i++) {
			out += signet[i] * weights[i+1][hiddenCount]; //i+1 a cause du bias
		}

		out = Utils.sigmoid(out);

		deltas[hiddenCount] = out*(1-out)*(trueValue-out);

		for(int i=0; i<hiddenCount; i++) {
			deltas[i] = signet[i] * (1-signet[i]) * weights[i+1][hiddenCount] * deltas[hiddenCount];
		}

		//Update before learning
		addWeights(weights);

		for(int i=0 ; i<=hiddenCount; i++) {
			//i ranges between 0 and hiddenNeuronsNumber
			if(i == hiddenCount) {
				//weights[0][hiddenCount] += learningRate * 1 * deltas[hiddenCount];
				weights[0][hiddenCount] = weights[0][hiddenCount] + (1-momentum) * learningRate * 1 * deltas[hiddenCount] + momentum * (weights[0][hiddenCount] - weightsList.get(weightsListCurrent-1)[0][hiddenCount]);

				for(int j=0; j <hiddenCount; j++) {
	                //weights[j+1][hiddenCount] += learningRate * signet[j] * deltas[hiddenCount];
					weights[j+1][hiddenCount] = weights[j+1][hiddenCount] + (1-momentum) * learningRate * signet[j] * deltas[hiddenCount] + momentum * (weights[j+1][hiddenCount] - weightsList.get(weightsListCurrent-1)[j+1][hiddenCount]);
	            }
			}
			else {

				//weights[0][i] += learningRate * 1 * deltas[i];
				weights[0][i] = weights[0][i] + (1-momentum) * learningRate * 1 * deltas[i] + momentum * (weights[0][i] - weightsList.get(weightsListCurrent-1)[0][i]);

	            for(int k = 0; k < inputCount; k++)
	            {
	                //weights[k+1][i] += learningRate * input[k] * deltas[i]; // k+1 a cause du bias
	            	weights[k+1][i] = weights[k+1][i] + (1-momentum) * learningRate * input[k] * deltas[i] + momentum * (weights[k+1][i] -  weightsList.get(weightsListCurrent-1)[k+1][i]);
	            }
			}
		}

		return out;
	}

	public double run(double[] inputValues) {

		double[] net = new double[1024];
		double[] signet = new double[1024];
		double[] input = new double[1024];
		double out;

		int inputCount = getLayers().get(0).getCount();
		int hiddenCount = getLayers().get(1).getCount();

		//inputNeuronsNumber => not very efficient
		for(int i=0; i<inputCount; i++) {
			input[i] = inputValues[i];
		}

		for(int i=0; i<hiddenCount; i++) {
			net[i] = 1 * weights[0][i];
			for(int j=0; j<inputCount; j++) {
				net[i] += input[j] * weights[j+1][i];   // j+1 car bias
			}

		}

		for(int i=0; i<hiddenCount; i++) {
			signet[i] = Utils.sigmoid(net[i]);
		}

		out  = 1 * weights[0][hiddenCount];

		for(int i=0; i<hiddenCount; i++) {
			out += signet[i] * weights[i+1][hiddenCount]; //i+1 a cause du bias
		}

		out = Utils.sigmoid(out);

		return out;
	}

	public double error(int delimiter, CSVParser csvparser, int deltaTime, int[] desiredInputs, int mainColumnId, double mainColumnIdMaxValue) throws UnknownColumnException, InvalidStateOfParser, MainColumnIdMaxValueException {

		int end = csvparser.countLines()-deltaTime;
		double[] input = new double[1024];
		int desiredInputsCountSize = desiredInputs.length;
		int tmpVar;
		double runningValue = 0, trueValue = 0, errorRate = 0;
		int inputIteratorAll = 0;

		for(int i=delimiter; i<end; i++){

			for(int j=0; j < desiredInputsCountSize; j++) {
				tmpVar = desiredInputs[j]-1;

				for(int inputIterator=0; inputIterator<desiredInputs[j]; inputIterator++) {

					if(csvparser.isFilled())
						input[inputIteratorAll] = csvparser.getValue(i-tmpVar, j);	
					else
						throw new InvalidStateOfParser();

					tmpVar--;
	                inputIteratorAll++;
				}
			}

			if(mainColumnIdMaxValue == 0)
				throw new MainColumnIdMaxValueException();

			runningValue = run(input)*mainColumnIdMaxValue;

			if(csvparser.isFilled())
				trueValue = csvparser.getValue(i+deltaTime, mainColumnId)*mainColumnIdMaxValue;
			else
				throw new InvalidStateOfParser();

			errorRate += Math.abs(trueValue-runningValue);

	        inputIteratorAll = 0;
		}

		errorRate /= (end-delimiter);
		return errorRate;
	}

	public void trainProcess(int delimiter, int[] desiredInputs, CSVParser csvparser, int mainColumnId, int deltaTime, double learningRate, double momentum) throws InterruptedException, InvalidStateOfParser {

		double[] input = new double[1024];
        int tmpVar;
        int inputIteratorAll = 0;
        int maxDesiredInputs = 0;
        int desiredInputsCountSize = desiredInputs.length;
        
        for(int i = 0; i < desiredInputsCountSize; i++)
            if(desiredInputs[i] > maxDesiredInputs)
                maxDesiredInputs = desiredInputs[i];

        for(int i = maxDesiredInputs; i < delimiter; i++)
        {
            for(int j = 0; j < desiredInputsCountSize; j++) {
               
            	tmpVar = desiredInputs[j]-1;

                for(int inputIterator = 0; inputIterator < desiredInputs[j]; inputIterator++) {
                	
                	if(csvparser.isFilled())
						input[inputIteratorAll] = csvparser.getValue(i-tmpVar, j);	

                    tmpVar--;
                    inputIteratorAll++;
                }

            }
            
            if(csvparser.isFilled())
            	train(input, csvparser.getValue(i+deltaTime, mainColumnId), learningRate, momentum);
            else
            	throw new InvalidStateOfParser();
            
            inputIteratorAll = 0;
        }
	}

	public double runningProcess(int delimiter, int[] desiredInputs, CSVParser csvparser, int mainColumnId, double mainColumnIdMaxValue) throws UnknownColumnException, InvalidStateOfParser {

		double[] input = new double[1024];
	    int tmpVar;
	    double runningValue = 0;
	    int inputIteratorAll = 0;
	    int desiredInputsCountSize = desiredInputs.length;

	    for(int j = 0; j < desiredInputsCountSize; j++) {
	    	tmpVar = desiredInputs[j]-1;

	        for(int inputIterator = 0; inputIterator < desiredInputs[j]; inputIterator ++) {

	        	if(csvparser.isFilled())
	        		input[inputIteratorAll] = csvparser.getValue(delimiter-tmpVar, j);
	        	else
	        		throw new InvalidStateOfParser();

	        	tmpVar--;
                inputIteratorAll++;
	        }
	    }

	    runningValue = run(input)*mainColumnIdMaxValue;
	    inputIteratorAll = 0;
	    return runningValue;
	}

	public NeuralNetwork createInputLayer(int inputCount) {

		Layer inputLayer = new Layer();

		for(int i=0; i<inputCount; i++) {
			INeuron neuron = new InputNeuron();
			inputLayer.addNeuron(neuron);
		}

		addLayer(inputLayer);

		return this;
	}

	public NeuralNetwork createHiddenLayer(int hiddenCount) {

		Layer hiddenLayer = new Layer();

		for(int i=0; i<hiddenCount; i++) {
			INeuron neuron = new HiddenNeuron();
			hiddenLayer.addNeuron(neuron);
		}

		addLayer(hiddenLayer);

		return this;
	}

	public NeuralNetwork createOuputLayer(int outputCount) {

		Layer outputLayer = new Layer();

		for(int i=0; i<outputCount; i++) {
			INeuron neuron = new OutputNeuron();
			outputLayer.addNeuron(neuron);
		}

		addLayer(outputLayer);

		return this;
	}
}