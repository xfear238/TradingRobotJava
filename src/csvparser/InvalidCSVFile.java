package csvparser;

public class InvalidCSVFile extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCSVFile() {
		System.out.println("Invalid CSV File");
	}
}
