package layer;

public class RemoveNeuronOutOfBounds extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RemoveNeuronOutOfBounds() {
		System.out.println("You specified a value too big. The layer does not contain such a number of neurons.");
	}

}
