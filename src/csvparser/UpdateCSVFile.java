package csvparser;

import java.io.*;

public class UpdateCSVFile {
	
	protected BufferedReader reader;
	protected BufferedWriter writer;
	
	public UpdateCSVFile(String readFilename, String writeFilename) {
	
		try {
			
			writer = new BufferedWriter(new FileWriter(writeFilename,true));
			reader = new BufferedReader(new FileReader(readFilename));
			
		} catch (IOException e) { }
	}
	
	public void update(int count) {
		String line;
		try {
			while ((line = reader.readLine()) != null && count > 0) {
				writer.write(line);
				writer.newLine();
				count--;
			}
			writer.flush();
		} catch (IOException e) {}
	}
	
	public void update() {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
				System.out.println(line);
			}
			writer.flush();
		} catch (IOException e) {}
	}
	
	public void close() {
		try {
			writer.close();
			reader.close();
		} catch (IOException e) {}
	}
	
	public void writeTest() throws IOException {
		writer.write("hello");
		writer.newLine();
		writer.flush();
	}
}
