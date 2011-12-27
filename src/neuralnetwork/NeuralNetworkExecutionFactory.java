package neuralnetwork;

public class NeuralNetworkExecutionFactory {

	private NeuralNetworkExecutionFactory() {}
	
	public static NeuralNetworkExecution createInstance() {
		NeuralNetworkExecution neuralnetworkexecution = new NeuralNetworkExecution();
		return neuralnetworkexecution;
	}
}
