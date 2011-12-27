package records;

import java.util.ArrayList;
import java.util.List;

public class PricesRecord {

	private final static int MAX_PRICES = 8096;
	private double[] prices = new double[MAX_PRICES];
	private int count = 0;
	List<Record> observers = new ArrayList<Record>();
	
	public void addPrice(double price) {
		System.out.println("Prices records updated");
		prices[count++] = price;
		notifyObservers();
	}
	
	public double[] getPrices() {
		return prices;
	}
	
	public double getPrice(int i) {
		return prices[i];
	}
	
	public int getCount() {
		return count;
	}
	
	public double getLastPrice() {
		return prices[count-1];
	}
	
	public double getPreviousPrice() {
		return prices[count-2];
	}
	
	public PricesRecord() {
		
	}
	
	public void registerObserver(Record observer) {
		System.out.println("Observer added");
		observers.add(observer);
	}
	
	public void notifyObservers() {
		System.out.println("Notify Observers");
		for(int i=0; i<observers.size(); i++) {
			observers.get(i).update(getLastPrice());
		}
	}
	
	public void debug() {
		for(int i=0; i<count; i++)
			System.out.println("price record [" +i+ "] = " + prices[i]);
	}
}
