package records;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TradesRecord implements Record {

	static Logger logger = Logger.getLogger(TradesRecord.class);
	
	List<Trade> trades = new ArrayList<Trade>();
	private int count = 0;
	
	public TradesRecord() {
		DOMConfigurator.configure("xml/TradesRecord.xml");
	}
	
	@Override
	public void saveTrade(double priceIn, double pricePredicted, int volume, String position) {
		Trade trade = new Trade(null, priceIn, pricePredicted, volume, position);
		trades.add(trade);
		count++;
		//System.out.println("Trade recorded");
		logger.debug("trade recorded");
	}
	
	public double getProfitLoss() {
		double profitLoss = 0.0;
		for(int i=0; i<count; i++) {
			if(trades.get(i).isClosed()) {
				if(trades.get(i).getPosition().equals("BUY"))
					profitLoss += ( trades.get(i).getPriceOut() - trades.get(i).getPriceIn() ) * trades.get(i).getVolume();
				else
					profitLoss += ( trades.get(i).getPriceIn() - trades.get(i).getPriceOut() ) * trades.get(i).getVolume();
			}
		}
		return profitLoss;
	}

	@Override
	public void update(double price) {
		logger.debug("UPDATE : = " + price);
		//System.out.println("UPDATE : " + price);
		updateLastTrade(price);
	}

	@Override
	public void updateLastTrade(double priceOut) {
		if(count >= 1) {
			Trade trade = trades.get(count-1);
			trade.setPriceOut(priceOut);
		}
	}
	
	public void debug() {
		for(int i=0; i<count; i++)
			trades.get(i).debug();
	}
}
