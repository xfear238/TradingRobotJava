package neuralnetwork;

public class NeuralNetworkFactory {

	private NeuralNetworkFactory() {}
	
	public static NeuralNetwork createInstance() {
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		return neuralNetwork;
	}
}
