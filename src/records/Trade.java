package records;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Trade {
	
	private static Logger logger = Logger.getLogger(Trade.class);
	
	private String ref = "";
	private double priceIn;
	private double priceOut;
	private double pricePredicted = 0;
	private int volume = 1;
	private String position;
	private boolean isClosed = false;
	
	@SuppressWarnings("unused")
	private Trade() {}
	
	public Trade(String ref, double priceIn, double pricePredicted, int volume, String position) {
		DOMConfigurator.configure("xml/TradesRecord.xml");
		setRef(ref);
		setPriceIn(priceIn);
		setPricePredicted(pricePredicted);
		setVolume(volume);
		try {
			setPosition(position);
		} catch (InvalidPosition e) {
			System.exit(0);
		}
		setClosed(false);
		
	}
	
	public void setPosition(String position) throws InvalidPosition {
		if(position.equals("BUY")) {
			this.position = "BUY";
		}
		else if(position.equals("SELL")) {
			this.position = "SELL";
		}
		else
			throw new InvalidPosition(position);
	}
	
	public String getPosition() {
		return position;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public double getPriceIn() {
		return priceIn;
	}

	public void setPriceIn(double priceIn) {
		this.priceIn = priceIn;
	}

	public double getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(double priceOut) {
		this.priceOut = priceOut;
		setClosed(true);
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setPricePredicted(double pricePredicted) {
		this.pricePredicted = pricePredicted;
	}
	
	public void debug() {
		logger.info("Trade " + ref + " position = " + getPosition() + " : priceIn = " + priceIn + " pricePredicted = " + pricePredicted + " priceOut = " + priceOut + " volume = " + volume);
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
}