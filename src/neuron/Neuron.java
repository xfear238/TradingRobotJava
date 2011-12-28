package neuron;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public abstract class Neuron implements INeuron {

	protected double value;
	protected static Logger logger = Logger.getLogger(INeuron.class);
	
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}
	
	public Neuron() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");
	}

}
