package neuralnetwork;

public class InvalidPercentageException extends Exception {

	private static final long serialVersionUID = 195068758050786049L;

	public InvalidPercentageException(int percentage) {
		System.out.println("Invalid percentage " + percentage + ". Must be between 1% and 99%");
	}
}
