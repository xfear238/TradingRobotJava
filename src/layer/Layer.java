package layer;

import java.util.ArrayList;

import org.apache.log4j.xml.DOMConfigurator;

import neuron.INeuron;

/**
 * Container for neurons. Layer implements ILayer.
 * @author Philippe REMY
 */
public class Layer implements ILayer {

	/**
	 * ArrayList of neurons (inputNeurons, hiddenNeurons, outputNeurons).
	 */
	private ArrayList<INeuron> neurons;
	
	/**
	 * The number of neurons contained in the layer.
	 */
	private int count;
	
	/**
	 * Adds a neuron to the layer.
	 * @param neuron
	 * @return count The number of neurons in the layer.
	 */
	@Override
	public int addNeuron(INeuron neuron) {
		neurons.add(neuron);
		return this.count++;
	}

	/**
	 * Removes count neuron(s) of the layer.
	 * @param count The number of neurons to remove
	 * @throws RemoveNeuronOutOfBounds When count is superior to the number of neurons in the layer
	 */
	@Override
	public void removeNeuron(int count) throws RemoveNeuronOutOfBounds {
		
		if(this.count >= count)
		{
			while(count > 0)
			{
				neurons.remove(this.count-1);
				this.count--;
				count--;
			}
		}
		else
			throw new RemoveNeuronOutOfBounds();
	}
	
	/**
	 * Gets a neuron from the layer.
	 * @param id The id of the neuron
	 * @return neuron
	 */
	@Override
	public INeuron getNeuron(int id) {
		return neurons.get(id);
	}
	
	/**
	 * Removes all neurons in the layer.
	 */
	@Override
	public void freeLayer() {
		neurons.removeAll(neurons);
		this.count = 0;
	}

	/**
	 * Creates a new layer.
	 */
	@Override
	public void createNewLayer() {
		logger.debug("layer created");
	}
	
	/**
	 * Gets the number of neurons in the layer.
	 * @return count The number of neurons in the layer
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Default constructor of the Layer class.
	 */
	public Layer() {
		DOMConfigurator.configure("xml/LogNeuralNetwork.xml");		
		this.count = 0;
		neurons = new ArrayList<INeuron>();
		createNewLayer();
	}

}
