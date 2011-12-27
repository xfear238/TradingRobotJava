package csvparser;

public interface ICSVParser {
	
	final int MAX_LINES = 4000;
	final int MAX_COLUMNS = 20;
	
	public boolean isFilled();
	
	public double[][] getValues();

	public double getValue(int x, int y);
	
	public int countColumns();
	
	public int countLines();

}
