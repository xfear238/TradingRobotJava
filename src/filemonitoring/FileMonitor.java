package filemonitoring;

import java.util.*;
import java.io.*;

public abstract class FileMonitor extends TimerTask {
	
	private long timeStamp = 0;
	private boolean lock = false;
	private File file;
	
	public FileMonitor(File file) {
		this.file = file;
	}
	
	public void run() {
		
		long timeStamp = file.lastModified();
		
		if(this.timeStamp != timeStamp) {
			this.timeStamp = timeStamp;
				
			if(!isLocked())
				onChange();
	    }
	}

	protected abstract void onChange();
	
	protected void setLocked(boolean lock) {
		this.lock = lock;
	}

	protected boolean isLocked() {
		return lock;
	}
}