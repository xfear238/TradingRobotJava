package test;

import java.io.IOException;

import org.junit.Test;

import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidStateOfParser;

public class CSVParserTransformerTest {

	@Test
	public void CSVParserTransformertest() {
		
		System.out.println("Call - CSVParserTransformertest()");
		
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvparser);
			csvparserTransformer.debug();
			
		} catch (IOException 
				| InvalidCSVFile e) {}
	}

	@Test
	public void CSVParserTransformerGenerateFileTest() {
		
		System.out.println("Call - CSVParserTransformerGenerateFileTest()");
		
		try {
			
			CSVParser csvparser = new CSVParser("files/test.csv");
			CSVParserTransformer csvparserTransformer = new CSVParserTransformerNormalization(csvparser);
			csvparserTransformer.write("files/normalization.csv");
			
			
		} catch (IOException 
				| InvalidCSVFile e) {}
	}
	
	@Test
	public void CSVParserTransformerGetCSVParserTest() {
		
		System.out.println("Call - CSVParserTransformerGetCSVParserTest()");
		
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
