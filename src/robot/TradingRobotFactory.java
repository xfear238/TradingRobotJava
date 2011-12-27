package robot;

public class TradingRobotFactory {
	
	private TradingRobotFactory() {}
	
	public static TradingRobot createInstance() {
		TradingRobot tradingRobot = new TradingRobot();
		return tradingRobot;
	}
}
