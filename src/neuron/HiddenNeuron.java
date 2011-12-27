package neuron;

import org.apache.log4j.xml.DOMConfigurator;

public class HiddenNeuron implements Neuron {

	private double value;
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
	
	public HiddenNeuron() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
		logger.debug("Hidden neuron created");
	}

}
