package main;

public class ArgsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArgsException(int argsCount) {
		System.out.println("The number of arguments do not match : " + argsCount);
		System.exit(0);
	}
		
}
