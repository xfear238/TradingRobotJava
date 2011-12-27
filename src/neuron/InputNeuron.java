package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class InputNeuron implements INeuron {

	private double value;
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
	
	public InputNeuron() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Input neuron created");
	}

}
