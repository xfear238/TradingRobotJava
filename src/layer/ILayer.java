package layer;

import neuron.Neuron;

import org.apache.log4j.Logger;

public interface ILayer {
	
	public static Logger logger = Logger.getLogger(ILayer.class);
	
	public int addNeuron(Neuron neuron);
	
	public void removeNeuron(int count) throws RemoveNeuronOutOfBounds;
	
	public void createNewLayer();
	
	public void freeLayer();
	
	public int getCount();
	
	public Neuron getNeuron(int id);

}
