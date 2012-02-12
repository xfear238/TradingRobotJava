package test;

import static org.junit.Assert.*;

import java.io.IOException;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.DelimiterOutOfBoundsException;
import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

public class NeuralNetworkTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}	
	
	@Test
	public void neuralNetworkLayerCountTest() {
		NeuralNetwork neuralnetwork = new NeuralNetwork(4,4,1);
		assertTrue(neuralnetwork.getLayerCount() == 3);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void execTest() throws IOException, InvalidCSVFile, DelimiterOutOfBoundsException, UnknownColumnException, InterruptedException, InvalidStateOfParser {
		
		logger.debug("---- execTest() --- BEGINNING ----");
		CSVParser csvParser = new CSVParser("files/SG.csv");
		
		CSVParserTransformer csvparsertransformer = new CSVParserTransformerNormalization(csvParser);
		
		CSVParser normalizedCsvParser = csvparsertransformer.getCSVParser();
		
		int[] desiredInputs = new int[4];
		desiredInputs[0] = 2;
		desiredInputs[1] = 2;
		desiredInputs[2] = 2;
		desiredInputs[3] = 2;
		
		int inputCount = 0;
		for(int i=0; i<desiredInputs.length; i++)
			inputCount += desiredInputs[i];
		
		NeuralNetwork neuralnetwork = new NeuralNetwork(inputCount,4,1);
		
		logger.debug("---- execTest() --- ENDING ----");
	}
	
}
