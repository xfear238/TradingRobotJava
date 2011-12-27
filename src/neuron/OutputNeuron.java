package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class OutputNeuron implements INeuron {

	private double value;
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
	
	public OutputNeuron() {
		//Bug
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Output neuron created");
	}

}
