package csvparser;

public class InvalidNewLineValuesException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidNewLineValuesException(int length, int expected) {
		System.out.println("AddNewLine() - Invalid length parameter : " + length + " instead of expected value : " + expected);
	}
}
