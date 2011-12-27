package csvparser;

import java.io.IOException;

/*Thread threadUpdateCSV = new Thread(new UpdateCSVFileRunnable("files/credit_agricole_read.csv", this.filename, 100000));
threadUpdateCSV.start();*/
@Deprecated
public class UpdateCSVFileRunnable extends UpdateCSVFile implements Runnable {

	private static int DEFAULT_MS = 200000;
	protected int ms = DEFAULT_MS;
	
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
		System.out.println("Call to run()");
		updateRunnable();
		close();
	}

}
