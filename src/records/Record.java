package records;

public interface Record {

	void saveTrade(double priceIn, double pricePredicted, int count, String position);
	
	void updateLastTrade(double priceOut);
	
	void update(double price);
	
}
