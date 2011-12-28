package csvparser;

import utils.Constants;

public interface ICSVParser {
	
	final int MAX_LINES = Constants.MAX_LINES;
	final int MAX_COLUMNS = Constants.MAX_COLUMNS;
	
	public boolean isFilled();
	
	public double[][] getValues();

	public double getValue(int x, int y);
	
	public int countColumns();
	
	public int countLines();

}
