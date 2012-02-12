package test;

import static org.junit.Assert.*;

import java.io.IOException;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.NeuralNetworkExecution;
import neuralnetwork.NeuralNetworkExecutionFactory;

import org.junit.Test;
import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidNewLineValuesException;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;
import file.ExtendedFile;

public class CSVParserTest {

	@Test
	public void set() {
		assertTrue(true);
	}
	
	@Test
	public void CSVParsertest() throws IOException, InvalidCSVFile {
		CSVParser csvparser = new CSVParser("files/test.csv");
		
		assertTrue(csvparser.countColumns() == 5);
		assertTrue(csvparser.countLines() == 6);
		
		for(int i=0; i<csvparser.countColumns(); i++) {
			assertTrue(csvparser.getValue(3, i) == 4.0);
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	(expected=InvalidCSVFile.class)
	public void CSVParserInvalidTest() throws IOException, InvalidCSVFile {
		CSVParser csvparser = new CSVParser("files/invalid_test.csv");
	}
	
	@SuppressWarnings("unused")
	@Test
	(expected=IOException.class)
	public void CSVParserNonExistingTest() throws IOException, InvalidCSVFile {
		CSVParser csvparser = new CSVParser("files/non_existing_test.csv");
	}
	
	@Test
	public void normalizeTest() throws IOException, InvalidCSVFile, UnknownColumnException {
		CSVParser csvparser = new CSVParser("files/test.csv");
		
		assertTrue(csvparser.countColumns() == 5);
		assertTrue(csvparser.countLines() == 6);
		
		CSVParserTransformer csvparsertransformer = new CSVParserTransformerNormalization(csvparser);

		CSVParser newCsvParser;
		try {
			newCsvParser = csvparsertransformer.getCSVParser();
		
			assertTrue(newCsvParser.getValue(0, 0) == 0.16666666666666666);
			assertTrue(newCsvParser.getValue(2, 0) == 0.5);
			assertTrue(newCsvParser.getValue(5, 0) == 1.0);
		
		} catch (InvalidStateOfParser e) { }
		
	}
	
	@Test
	public void addNewLineTest() {
		
		System.out.println("- addNewLineTest() -");
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			
			double[] values = new double[csvparser.countColumns()];
			for(int i=0; i<csvparser.countColumns(); i++) {
				values[i] = 7.0;
			}
			
			csvparser.addNewLine(values);
			csvparser.debug();
			
		} catch (IOException | InvalidCSVFile | InvalidNewLineValuesException e) {}
	}
	
	@Test
	(expected=InvalidNewLineValuesException.class)
	public void addNewLineExceptionTest() throws InvalidNewLineValuesException {
		
		System.out.println("- addNewLineExceptionTest() -");
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			
			double[] values = new double[csvparser.countColumns()+1];
			for(int i=0; i<csvparser.countColumns()+1; i++) {
				values[i] = 7.0;
			}
			
			csvparser.addNewLine(values);
			
			csvparser.debug();
			
		} catch (IOException | InvalidCSVFile e) {}
		
	}
	
	@Test
	public void reloadTest() {
		
		System.out.println("- reload2Test() - ");
		
		try {

			ExtendedFile filecpy = new ExtendedFile("files/test_COPY.csv");
			
			if(filecpy.exists()) {
				System.out.print("test copy already exists. Preparing to delete ...");
				filecpy.delete();
				System.out.println(" [OK]");
			}
			
			filecpy = null;
			
			ExtendedFile file = new ExtendedFile("files/test.csv");
			file.copy("files/test_COPY.csv");
			if(!file.exists()) {
				System.out.println("File test_COPY.csv has not been created.");
				fail();
			}
			
			filecpy = new ExtendedFile("files/test_COPY.csv");
			
			//First read in file
			CSVParser csvparser = new CSVParser("files/test_COPY.csv");
			csvparser.debug();
			
			filecpy.append("8.0;8.0;8.0;8.0;8.0");
			
			//Second read in file
			csvparser.reload();
			csvparser.debug();
			
		} catch (Exception e) {}
		
	}
	
	//@Test
	public void CSVParserRegisterTransformerTest() {
		
		try {
			
		CSVParser csvparser = new CSVParser("files/testIntegrationDependances.csv");
		csvparser.debug();
		
		CSVParserTransformer csvparsertransformer = new CSVParserTransformerNormalization(csvparser);
		csvparsertransformer.debug();
		
		System.out.println("Waiting for the user to press a key...");
		System.in.read();
		
		csvparser.reload();
		
		csvparser.debug();
		csvparsertransformer.debug();
		
		} catch (Exception e) {}
	}
	
	@Test
	public void CSVParserRegisterNeuralNetworkExecutionTest() {
			
		try {
			
			CSVParser csvparser = new CSVParser("files/testIntegrationDependances.csv");
			
			CSVParserTransformer csvparsertransformer = new CSVParserTransformerNormalization(csvparser);
			csvparsertransformer.debug();
				
			double learningRate = 0.1;
			double momentum = 0.9;
			NeuralNetwork neuralnetwork = null;
			int percentage = 90;
			int deltaTime = 7;
			int[] intDesiredInputs = {4,4,4,4,4};
			int epochs = 2000;
			int mainColumnId = 3;
			
			NeuralNetworkExecution neuralnetworkexecution = NeuralNetworkExecutionFactory.createInstance();
			
			neuralnetworkexecution.setParser(csvparsertransformer.getCSVParser())
								  .setLearningRate(learningRate)
								  .setNeuralNetwork(neuralnetwork)
								  .setMomentum(momentum)
								  .setEpochs(epochs)
								  .setPercentageTrainingTesting(percentage)
								  .setDeltaTime(deltaTime)
								  .setDesiredInputs(intDesiredInputs)
								  .setMainColumnId(mainColumnId);
		
		csvparser.reload();
		
		csvparser.debug();
		csvparsertransformer.debug();
		
		} catch (Exception e) {}
	}
}