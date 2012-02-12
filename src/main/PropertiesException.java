package main;

public class PropertiesException extends Exception {

	private static final long serialVersionUID = 1L;

	public PropertiesException() {
		System.out.println("Error detected in properties file.");
		System.exit(0);
	}
	
	public PropertiesException(String message) {
		System.out.println("Error detected in properties file. Message : " + message);
		System.exit(0);
	}
}
