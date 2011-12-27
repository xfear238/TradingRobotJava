package csvparser;

public class UnknownColumnException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UnknownColumnException(int y) {
		System.out.println("Unknown column " + y);
	}
}
