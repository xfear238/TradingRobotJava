package neuralnetwork;

public class ActivationFunctionSigmoid extends ActivationFunction {

	@Override
	public double calculate(double value) { 
		return (double)(1/(1+Math.exp(-value))); 
	}

}
