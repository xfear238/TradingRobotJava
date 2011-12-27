package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.junit.Test;

public class PropertiesFileTest {

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
				System.out.println(e.getMessage());
			}
			
	}

}
