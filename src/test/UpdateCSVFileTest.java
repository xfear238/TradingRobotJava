package test;

import java.io.IOException;

import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import csvparser.UpdateCSVFile;

public class UpdateCSVFileTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void test() {
		UpdateCSVFile updatecsvfile = new UpdateCSVFile("files/read.csv", "files/write.csv");
		updatecsvfile.update();
		try {
			updatecsvfile.writeTest();
		} catch (IOException e) { logger.debug(e.getMessage());}
	}

}
