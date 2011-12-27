package neuron;

import org.apache.log4j.Logger;

public interface INeuron {

	static Logger logger = Logger.getLogger(INeuron.class);
	
	public double getValue();
	
	public void setValue(double value);
	
}
