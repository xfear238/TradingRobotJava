package test;

import static org.junit.Assert.*;
import layer.ILayer;
import layer.Layer;
import layer.RemoveNeuronOutOfBounds;

import neuralnetwork.NeuralNetworkExecution;
import neuron.INeuron;
import neuron.InputNeuron;
import neuron.HiddenNeuron;
import neuron.OutputNeuron;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class LayerTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void addNeuronTest() {
		ILayer layer = new Layer();
		
		INeuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		
		int id = layer.addNeuron(neuron1);
		
		assertTrue(id == 0);
		assertTrue(layer.getNeuron(id).getValue() == 1.5);
		
		INeuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		
		id = layer.addNeuron(neuron2);
		
		assertTrue(id == 1);
		assertTrue(layer.getNeuron(id).getValue() == 2.5);
		
		INeuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		
		id = layer.addNeuron(neuron3);
		
		assertTrue(id == 2);
		assertTrue(layer.getNeuron(id).getValue() == 3.5);
		
	}
	
	@Test
	(expected=RemoveNeuronOutOfBounds.class)
	public void removeNeuronExceptionTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		INeuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		INeuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		INeuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		INeuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.removeNeuron(5);
		
	}
	
	@Test
	public void removeNeuronTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		INeuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		INeuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		INeuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		INeuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.removeNeuron(2);
		
		int id = layer.getCount() - 1;
		assertTrue(layer.getNeuron(id).getValue() == 2.5);
		
	}
	
	@Test
	public void freeLayerTest() throws RemoveNeuronOutOfBounds {
		
		ILayer layer = new Layer();
		
		INeuron neuron1 = new InputNeuron();
		neuron1.setValue(1.5);
		layer.addNeuron(neuron1);
		
		INeuron neuron2 = new OutputNeuron();
		neuron2.setValue(2.5);
		layer.addNeuron(neuron2);
		
		INeuron neuron3 = new HiddenNeuron();
		neuron3.setValue(3.5);
		layer.addNeuron(neuron3);
		
		INeuron neuron4 = new HiddenNeuron();
		neuron4.setValue(4.5);
		layer.addNeuron(neuron4);
		
		layer.freeLayer();
		
		assertTrue(layer.getCount() == 0);
		
	}

}
