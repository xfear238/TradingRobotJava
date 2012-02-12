package csvparser;

import java.io.IOException;

import utils.Constants;

@Deprecated
public class UpdateCSVFileRunnable extends UpdateCSVFile implements Runnable {

	protected int ms = Constants.DEFAULT_MS;
	
	public UpdateCSVFileRunnable(String readFilename, String writeFilename) {
		super(readFilename, writeFilename);
	}
	
	public UpdateCSVFileRunnable(String readFilename, String writeFilename, int ms) {
		super(readFilename, writeFilename);
		this.ms = ms;
	}

	@SuppressWarnings("static-access")
	public void updateRunnable() {
		boolean firstLine = true;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				
				if(firstLine) {
					writer.newLine();
					firstLine = false;
				}
					
				writer.write(line);
				writer.flush();
				System.out.println("File changed");
				Thread.currentThread().sleep(ms);
				writer.newLine();
				writer.flush();
			}
			
		} catch (IOException | InterruptedException e) {}
	}
	
	@Override
	public void run() {
		updateRunnable();
		close();
	}

}
