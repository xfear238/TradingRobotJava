package filemonitoring;

import java.io.File;

import robot.TradingRobot;

public class FileMonitorRobot extends FileMonitor {
	
	private TradingRobot tradingrobot;
	
	private FileMonitorRobot(File file) {
		super(file);
	}

	public FileMonitorRobot(File file, TradingRobot tradingrobot) {
		super(file);
		this.tradingrobot = tradingrobot;
	}
	
	@Override
	protected void onChange() {
		
		setLocked(true);
		tradingrobot.onChange();
		setLocked(false);
	
	}
}
