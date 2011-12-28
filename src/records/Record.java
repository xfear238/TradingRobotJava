package records;

public interface Record {

	void saveTrade(String ref, double priceIn, double pricePredicted, int count, String position);
	
	void updateLastTrade(double priceOut);
	
	void update(double price);
	
}
