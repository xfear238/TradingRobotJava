package test;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;
import csvparser.CSVParser;
import csvparser.CSVParserTransformer;
import csvparser.CSVParserTransformerNormalization;
import csvparser.InvalidCSVFile;
import csvparser.InvalidStateOfParser;
import csvparser.UnknownColumnException;

public class CSVParserTest {

	@Test
	public void CSVParsertest() throws IOException, InvalidCSVFile {
		CSVParser csvParser = new CSVParser("files/test.csv");
		
		assertTrue(csvParser.countColumns() == 5);
		assertTrue(csvParser.countLines() == 6);
		
		for(int i=0; i<csvParser.countColumns(); i++) {
			assertTrue(csvParser.getValue(3, i) == 4.0);
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	(expected=InvalidCSVFile.class)
	public void CSVParserInvalidTest() throws IOException, InvalidCSVFile {
		CSVParser csvParser = new CSVParser("files/invalid_test.csv");
	}
	
	@SuppressWarnings("unused")
	@Test
	(expected=IOException.class)
	public void CSVParserNonExistingTest() throws IOException, InvalidCSVFile {
		CSVParser csvParser = new CSVParser("files/non_existing_test.csv");
	}
	
	@Test
	public void normalizeTest() throws IOException, InvalidCSVFile, UnknownColumnException {
		CSVParser csvParser = new CSVParser("files/test.csv");
		
		assertTrue(csvParser.countColumns() == 5);
		assertTrue(csvParser.countLines() == 6);
		
		CSVParserTransformer csvparsertransformer = new CSVParserTransformerNormalization(csvParser);

		CSVParser newCsvParser;
		try {
			newCsvParser = csvparsertransformer.getCSVParser();
		
			assertTrue(newCsvParser.getValue(0, 0) == 0.16666666666666666);
			assertTrue(newCsvParser.getValue(2, 0) == 0.5);
			assertTrue(newCsvParser.getValue(5, 0) == 1.0);
		
		} catch (InvalidStateOfParser e) { }
		
	}

}
