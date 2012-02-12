package test;

import neuralnetwork.NeuralNetworkExecution;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import csvparser.UpdateCSVFileRunnable;

@SuppressWarnings("deprecation")
public class UpdateCSVFileRunnableTest {

	Logger logger = Logger.getLogger(NeuralNetworkExecution.class);
	
	@BeforeClass
	public static void setUp() {
		DOMConfigurator.configure("xml/Test.xml");
	}	
	@Test
	public void launchTwoThreadsTest() {
		
		String reader = "files/read.csv", writer = "files/write.csv";
		
		Thread thread1 = new Thread(new UpdateCSVFileRunnable(reader, writer), "thread1");
		Thread thread2 = new Thread(new UpdateCSVFileRunnable(reader, writer), "thread2");
		
		thread1.start();
		thread2.start();
		
	}
	
}
