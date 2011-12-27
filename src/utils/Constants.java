package utils;

public class Constants {

	private Constants() {}
	
	//Used in the main function (not the properties-based one)
	public final static int ARGS_COUNT = 9;
	
	//Used in the computation of the error of the neural network convergence
	public static final double INT_MAX = 1000000;
	
	//If this constant is changed, the neural kernel has to refactored.
	public static final int OUTPUT_COUNT = 1;
	
	//Can be changed to fasten slightly the algorithm. Loop is going to be active and UC near to 100% if the latency is too low.
	public static final int LATENCY_ON_DETECTING_FILE_MODIFICATION = 1000;
	
	public static final String SEPARATOR = ";";
	
	//Used in executing the trades.
	public static final int VOLUME = 1;

}
