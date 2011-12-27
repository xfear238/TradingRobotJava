package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class OutputNeuron extends Neuron implements INeuron {

	public OutputNeuron() {
		//Bug
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Output neuron created");
	}

}
