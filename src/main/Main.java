package main;

import robot.TradingRobot;
import robot.TradingRobotFactory;
import utils.Constants;

public class Main {

	public static void main(String[] args) {
			
			System.out.println("Starting Robot - default mode");
			
			if(args.length != Constants.ARGS_COUNT)
				try {
					throw new ArgsException(args.length);
				} catch (ArgsException e) {}
			
			TradingRobot tradingRobot = TradingRobotFactory.createInstance()
														   .setFilename(args[0])
														   .setDesiredInputs(args[1])
														   .setHiddenCount(Integer.parseInt(args[2]))
														   .setMomentum(Double.parseDouble(args[3]))
														   .setLearningRate(Double.parseDouble(args[4]))
														   .setEpochs(Integer.parseInt(args[5]))
														   .setPercentage(Integer.parseInt(args[6]))
														   .setMainColumnId(Integer.parseInt(args[7]))
														   .setDeltaTime(Integer.parseInt(args[8]));
			
			tradingRobot.run();
	
	}
}
