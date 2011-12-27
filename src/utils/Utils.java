package utils;

public final class Utils {
	
	public final static double sigmoid(double value) {
		return (float)(1/(1+Math.exp(-value)));
	}
	
	private Utils() {}
}
