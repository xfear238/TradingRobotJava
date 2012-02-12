package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class PropertiesFileTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}
	
	@Test
	public void test() {
			
			try {

				Properties properties = new Properties();
				
				FileInputStream propertiesInputFileStream = new FileInputStream("properties/test.properties");
				properties.load(propertiesInputFileStream);
				
				assertTrue(properties.getProperty("prop1").equals("value123"));
				
				FileOutputStream propertiesOutputFileStream = new FileOutputStream("properties/test.properties");
				properties.setProperty("prop2", "value456");
				properties.store(propertiesOutputFileStream, "comment");
				
				assertTrue(properties.getProperty("prop1").equals("value123"));
				assertTrue(properties.getProperty("prop2").equals("value456"));
				
			}
			catch(Exception e) {
				logger.debug(e.getMessage());
			}
			
	}

}
