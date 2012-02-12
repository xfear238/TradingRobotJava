package test;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.List;

import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Utils;

import file.ExtendedFile;

public class ExtendedFileTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void countLinesTest() throws IOException {
		
		ExtendedFile file = new ExtendedFile("files/testExtendedFile.csv");
		int count = file.countLines();
		assertTrue(count == 7);
    }
    
	@Test
    public void extractLinesTest() throws IOException {
    
		ExtendedFile file = new ExtendedFile("files/testExtendedFile.csv");
		List<String> results = file.extractLines(2, 3);
		logger.debug(results.toString());
		
		for(int i=0; i<results.size(); i++) {
			logger.debug("i=" + i);
			switch(i) {
			case 0:
				logger.debug(results.get(i));
				assertTrue(results.get(i) != "3.0;3.0;3.0;3.0;3.0");
				break;
			
			case 1:
				assertTrue(results.get(i) != "4.0;4.0;4.0;4.0;4.0");
				break;
			
			case 2:
				assertTrue(results.get(i) != "5.0;5.0;5.0;5.0;5.0");
				break;
			
			default:
				logger.debug("wrong extractLinesTest - default case");
				assertTrue(false);
				break;
			
			}
		}	
	}
    
	@Test
    public void appendTest() {
       
		ExtendedFile filecpy = new ExtendedFile("files/testExtendedFile_COPY.csv");
		
		if(filecpy.exists()) {
			System.out.print("test copy already exists. Preparing to delete ...");
			filecpy.delete();
			logger.debug(" [OK]");
		}
		
		filecpy = null;
		
		ExtendedFile file = new ExtendedFile("files/testExtendedFile.csv");
		file.copy("files/testExtendedFile_COPY.csv");
		if(!file.exists()) {
			logger.debug("File test_COPY.csv has not been created.");
			fail();
		}
		
		filecpy = new ExtendedFile("files/testExtendedFile_COPY.csv");
		filecpy.append("8.0;8.0;8.0;8.0;8.0");
		
		try {
			List<String> results = filecpy.extractLines(filecpy.countLines()-1, 1);
			List<Double> doubleList = Utils.strtokList(results.get(0));
			
			for(int i=0; i<doubleList.size(); i++)
				assertTrue(8.0 == doubleList.get(i));
		} catch (IOException e) {}
	}
    
	@Test
    public void copyTest() {
		
		ExtendedFile filecpy = new ExtendedFile("files/testExtendedFile_COPY.csv");
		
		if(filecpy.exists()) {
			System.out.print("test copy already exists. Preparing to delete ...");
			filecpy.delete();
			logger.debug(" [OK]");
		}
		
		ExtendedFile file = new ExtendedFile("files/testExtendedFile.csv");
		file.copy("files/testExtendedFile_COPY.csv");
		if(!file.exists()) {
			logger.debug("File test_COPY.csv has not been created.");
			fail();
		}	
	}
}
