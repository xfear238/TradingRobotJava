package test;

import static org.junit.Assert.*;
import layer.ILayer;
import layer.Layer;
import layer.RemoveNeuronOutOfBounds;

import neuron.Neuron;
import neuron.InputNeuron;
import neuron.HiddenNeuron;
import neuron.OutputNeuron;

import org.junit.Test;

public class LayerTest {

	@Test
	public void addNeuronTest() {
		ILayer layer = new Layer();
		
		Neuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		
		int id = layer.addNeuron(neuron1);
		
		assertTrue(id == 0);
		assertTrue(layer.getNeuron(id).getValue() == 1.5);
		
		Neuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		
		id = layer.addNeuron(neuron2);
		
		assertTrue(id == 1);
		assertTrue(layer.getNeuron(id).getValue() == 2.5);
		
		Neuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		
		id = layer.addNeuron(neuron3);
		
		assertTrue(id == 2);
		assertTrue(layer.getNeuron(id).getValue() == 3.5);
		
	}
	
	@Test
	(expected=RemoveNeuronOutOfBounds.class)
	public void removeNeuronExceptionTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		Neuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		Neuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		Neuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		Neuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.removeNeuron(5);
		
	}
	
	@Test
	public void removeNeuronTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		Neuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		Neuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		Neuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		Neuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.removeNeuron(2);
		
		int id = layer.getCount() - 1;
		assertTrue(layer.getNeuron(id).getValue() == 2.5);
		
	}
	
	@Test
	public void freeLayerTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		Neuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		Neuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		Neuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		Neuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.freeLayer();
		
		assertTrue(layer.getCount() == 0);
		
	}

}
