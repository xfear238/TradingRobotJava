package test;

import java.io.IOException;

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

public class CSVParserTransformerTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void CSVParserTransformertest() {
		
		logger.debug("Call - CSVParserTransformertest()");
		
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvparser);
			csvparserTransformer.debug();
			
		} catch (IOException 
				| InvalidCSVFile e) {}
	}

	@Test
	public void CSVParserTransformerGenerateFileTest() {
		
		logger.debug("Call - CSVParserTransformerGenerateFileTest()");
		
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvparser);
			csvparserTransformer.write("files/normalization.csv");
			
			
		} catch (IOException 
				| InvalidCSVFile e) {}
	}
	
	@Test
	public void CSVParserTransformerGetCSVParserTest() {
		
		logger.debug("Call - CSVParserTransformerGetCSVParserTest()");
		
		try {

			CSVParser csvparser = new CSVParser("files/test.csv");
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvparser);
			CSVParser csvParser2 = csvparserTransformer.getCSVParser();
			csvParser2.debug();
				
		} catch (IOException 
				| InvalidCSVFile 
				| InvalidStateOfParser e) {}
	}
}
