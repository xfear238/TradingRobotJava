package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import neuralnetwork.NeuralModeExecutionEnum;

import main.PropertiesException;

public final class Utils {
	
	public final static NeuralModeExecutionEnum convertStringToNeuralModeExecutionEnum(String string) throws PropertiesException {
		NeuralModeExecutionEnum[] executionEnumList = NeuralModeExecutionEnum.values();
		for(NeuralModeExecutionEnum executionEnum : executionEnumList) {
			if(string.equals(executionEnum.name()))
					return executionEnum;
		}
		
		throw new PropertiesException("error in neural mode execution enum");
	}
	
	public final static List<Double> strtokList(String result) {
		
		List<Double> results = new ArrayList<Double>(); 
		StringTokenizer stringtokenizer = new StringTokenizer(result, Constants.SEPARATOR);
		
		while (stringtokenizer.hasMoreTokens()) {
			results.add(Double.parseDouble(stringtokenizer.nextToken()));
		}
		
		return results;
	}
	
	public final static double[] strtokArray(String result) {
		
		List<Double> resultsList = strtokList(result);
		double[] results = new double[resultsList.size()];
		for(int i=0; i<resultsList.size(); i++)
			results[i] = resultsList.get(i);
		
		return results;
	}
	
	private Utils() {}
}
