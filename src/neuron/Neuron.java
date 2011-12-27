package neuron;

public abstract class Neuron implements INeuron {

	private double value;
	
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}

}
