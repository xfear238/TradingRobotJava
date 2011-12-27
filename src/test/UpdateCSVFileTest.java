package test;

import java.io.IOException;

import org.junit.Test;

import csvparser.UpdateCSVFile;

public class UpdateCSVFileTest {

	@Test
	public void test() {
		UpdateCSVFile updatecsvfile = new UpdateCSVFile("files/read.csv", "files/write.csv");
		updatecsvfile.update();
		try {
			updatecsvfile.writeTest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
