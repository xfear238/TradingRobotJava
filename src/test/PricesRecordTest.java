package test;

import static org.junit.Assert.*;

import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import records.PricesRecord;

public class PricesRecordTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void test() {
		PricesRecord pricesrecord = new PricesRecord();
		
		pricesrecord.addPrice(1.0);
		pricesrecord.addPrice(2.0);
		pricesrecord.addPrice(3.0);
		
		assertTrue(pricesrecord.getCount() == 3);
		assertTrue(pricesrecord.getLastPrice() == 3.0);
		assertTrue(pricesrecord.getPreviousPrice() == 2.0);
	}

}
