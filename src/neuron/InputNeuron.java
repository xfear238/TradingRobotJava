package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class InputNeuron extends Neuron implements INeuron {

	public InputNeuron() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Input neuron created");
	}

}
