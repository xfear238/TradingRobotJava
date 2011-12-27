package main;

import java.io.FileInputStream;
import java.util.Properties;

import robot.ArgsException;
import robot.TradingRobot;
import robot.TradingRobotFactory;

public class MainProperties {

	public static void main(String[] args) {
		
		try {
			
			Properties properties = new Properties();
			
			FileInputStream propertiesInputFileStream = new FileInputStream("properties/parameters.properties");
			properties.load(propertiesInputFileStream);
			
			String filename = properties.getProperty("filename");
			String desiredInputs = properties.getProperty("desired_inputs");
			int hiddenCount = Integer.parseInt(properties.getProperty("hidden_neurons_count"));
			double momentum = Double.parseDouble(properties.getProperty("momentum"));
			double learningRate = Double.parseDouble(properties.getProperty("learning_rate"));
			int epochs = Integer.parseInt(properties.getProperty("max_epoch"));
			int delimiter = Integer.parseInt(properties.getProperty("delimiter_percentage"));
			int mainColumnId = Integer.parseInt(properties.getProperty("price_column_identifier"));
			int deltaTime = Integer.parseInt(properties.getProperty("delta_time"));
			
			if(filename == null
			   || desiredInputs == null
			   || hiddenCount == 0
			   || momentum == 0.0
			   || learningRate == 0.0
			   || epochs == 0
			   || delimiter == 0
			   || deltaTime == 0)
				throw new ArgsException();
			
			System.out.println("USAGE OF PROPERTIES");
			System.out.println("filename = " + filename);
			System.out.println("desiredInputs = " + desiredInputs);
			System.out.println("hiddenCount = " + hiddenCount);
			System.out.println("momentum = " + momentum);
			System.out.println("learningRate = " + learningRate);
			System.out.println("epochs = " + epochs);
			System.out.println("delimiter = " + delimiter);
			System.out.println("mainColumnId = " + mainColumnId);
			System.out.println("deltatime = " + deltaTime);
			
			TradingRobot tradingRobot = TradingRobotFactory.createInstance()
					   .setFilename(filename)
					   .setDesiredInputs(desiredInputs)
					   .setHiddenCount(hiddenCount)
					   .setMomentum(momentum)
					   .setLearningRate(learningRate)
					   .setEpochs(epochs)
					   .setDelimiter(delimiter)
					   .setMainColumnId(mainColumnId)
					   .setDeltaTime(deltaTime);

			tradingRobot.run();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
