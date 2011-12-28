package records;

public class InvalidPosition extends Exception {

	private static final long serialVersionUID = -4927256426127419004L;

	public InvalidPosition(String position) {
		System.out.println("Invalid position for the trade : " + position);
	}
}
