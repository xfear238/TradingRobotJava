package test;

import static org.junit.Assert.*;

import java.io.IOException;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.DelimiterOutOfBoundsException;

import org.junit.Test;

import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

public class NeuralNetworkTest {

	@Test
	public void neuralNetworkLayerCountTest() {
		NeuralNetwork neuralnetwork = new NeuralNetwork(4,4,1);
		assertTrue(neuralnetwork.getLayerCount() == 3);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void execTest() throws IOException, InvalidCSVFile, DelimiterOutOfBoundsException, UnknownColumnException, InterruptedException, InvalidStateOfParser {
		
		System.out.println("---- execTest() --- BEGINNING ----");
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
		
		System.out.println("---- execTest() --- ENDING ----");
	}
	
}
