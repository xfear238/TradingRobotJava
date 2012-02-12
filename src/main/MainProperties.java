package main;

import java.io.FileInputStream;
import java.util.Properties;

import neuralnetwork.NeuralModeExecutionEnum;
import robot.TradingRobot;
import robot.TradingRobotFactory;
import utils.Utils;

public class MainProperties {

	public static void main(String[] args) {
		
		try {
			
			Properties properties = new Properties();
			
			FileInputStream propertiesInputFileStream = new FileInputStream("properties/parameters.properties");
			properties.load(propertiesInputFileStream);
			
			String filename = properties.getProperty("file.filename");
			String desiredInputs = properties.getProperty("neural.core.inputs-shift");
			int hiddenCount = Integer.parseInt(properties.getProperty("neural.core.hidden-neurons-count"));
			double momentum = Double.parseDouble(properties.getProperty("neural.core.momentum"));
			double learningRate = Double.parseDouble(properties.getProperty("neural.core.learning-rate"));
			int epochs = Integer.parseInt(properties.getProperty("neural.execution.max-epochs"));
			int percentage = Integer.parseInt(properties.getProperty("file.delimiter-between-training-testing-percentage"));
			int mainColumnId = Integer.parseInt(properties.getProperty("file.main-price-column-identifier"));
			int deltaTime = Integer.parseInt(properties.getProperty("neural.execution.deltatime"));
			NeuralModeExecutionEnum neuralModeExecution = Utils.convertStringToNeuralModeExecutionEnum(properties.getProperty("neural.execution.mode"));
			
			if(filename == null
			   || desiredInputs == null
			   || hiddenCount == 0
			   || momentum == 0.0
			   || learningRate == 0.0
			   || epochs == 0
			   || percentage == 0
			   || deltaTime == 0)
				throw new PropertiesException();
			
			System.out.println("USAGE OF PROPERTIES");
			System.out.println("filename = " + filename);
			System.out.println("desiredInputs = " + desiredInputs);
			System.out.println("hiddenCount = " + hiddenCount);
			System.out.println("momentum = " + momentum);
			System.out.println("learningRate = " + learningRate);
			System.out.println("epochs = " + epochs);
			System.out.println("delimiter = " + percentage + " %");
			System.out.println("mainColumnId = " + mainColumnId);
			System.out.println("deltatime = " + deltaTime);
			
			TradingRobot tradingRobot = TradingRobotFactory.createInstance()
					   .setFilename(filename)
					   .setDesiredInputs(desiredInputs)
					   .setHiddenCount(hiddenCount)
					   .setMomentum(momentum)
					   .setLearningRate(learningRate)
					   .setEpochs(epochs)
					   .setPercentage(percentage)
					   .setMainColumnId(mainColumnId)
					   .setDeltaTime(deltaTime)
					   .setModeForExecution(neuralModeExecution);

			tradingRobot.run();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}