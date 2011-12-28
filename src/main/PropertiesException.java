package main;

public class PropertiesException extends Exception {

	private static final long serialVersionUID = -4480617216666217223L;

	public PropertiesException() {
		System.out.println("Error detected in properties file");
		System.exit(0);
	}
}
