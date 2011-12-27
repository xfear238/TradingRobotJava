package test;

import org.junit.Test;

import csvparser.UpdateCSVFileRunnable;

@SuppressWarnings("deprecation")
public class UpdateCSVFileRunnableTest {

	@Test
	public void launchTwoThreadsTest() {
		
		String reader = "files/read.csv", writer = "files/write.csv";
		
		Thread thread1 = new Thread(new UpdateCSVFileRunnable(reader, writer), "thread1");
		Thread thread2 = new Thread(new UpdateCSVFileRunnable(reader, writer), "thread2");
		
		thread1.start();
		thread2.start();
		
	}
	
}
