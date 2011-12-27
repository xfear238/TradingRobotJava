package neuron;

import org.apache.log4j.Logger;

public interface Neuron {

	static Logger logger = Logger.getLogger(Neuron.class);
	
	public double getValue();
	
	public void setValue(double value);
	
}
