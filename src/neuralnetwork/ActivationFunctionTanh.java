package neuralnetwork;

public class ActivationFunctionTanh extends ActivationFunction {

	@Override
	public double calculate(double value) {
		return (double)((Math.exp(2*value)-1)/(Math.exp(2*value)+1));
	}

}
