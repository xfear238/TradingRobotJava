package test;

import org.junit.Test;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jTest {

	@Test
	public void Log4jWriteIntoConsoleTest() {
		Logger logger = Logger.getLogger(Log4jTest.class);
		
		System.out.println("Call - Log4jWriteIntoConsoleTest()");
		
		BasicConfigurator.configure();
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
		
		System.out.println("End");
	}
	
	@Test
	public void Log4jReadFromXmlAndWriteIntoConsoleTest() {
		Logger logger = Logger.getLogger(Log4jTest.class);
		
		System.out.println("Call - Log4jReadFromXmlAndWriteIntoConsoleTest()");
		
		DOMConfigurator.configure("xml/log4j.xml");
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
		
		System.out.println("End");
	}

}
