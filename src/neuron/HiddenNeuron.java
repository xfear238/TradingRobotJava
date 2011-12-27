package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class HiddenNeuron extends Neuron implements INeuron {

	public HiddenNeuron() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Hidden neuron created");
	}

}
