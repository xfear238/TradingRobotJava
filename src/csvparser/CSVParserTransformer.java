package csvparser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import utils.Constants;

public abstract class CSVParserTransformer implements ICSVParser {
	
	protected double[][] transformedValues = new double[MAX_LINES][MAX_COLUMNS];
	protected int countLines;
	protected int countColumns;
	protected boolean isFilledTransformed = false;
	protected CSVParser csvparser;
	
	public abstract void transform();
	
	public CSVParserTransformer(CSVParser csvparser) {
		this.csvparser = csvparser;
		this.countLines = csvparser.countLines();
		this.countColumns = csvparser.countColumns();
	}
	
	public void debug() {
		if(isFilledTransformed) {
			for(int x = 0; x < this.countLines-1; x++) {
				for(int y = 0; y < this.countColumns; y++)
					System.out.print(transformedValues[x][y] + " ");
				System.out.println("");
			}
		}
	}
	
	public void write(String filename) throws IOException {
		if(isFilledTransformed) {
			PrintWriter writer =  new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			for(int x = 0; x < this.countLines-1; x++) {
				for(int y = 0; y < this.countColumns; y++) {
					if(y == this.countColumns-1) //Last column
						writer.print(transformedValues[x][y]);
					else
						writer.print(transformedValues[x][y] + Constants.SEPARATOR);
				}
				
				if(x != this.countLines-2)	//Last line
					writer.println("");
			}
			writer.close();
		}
	}
	
	public CSVParser getCSVParser() throws InvalidStateOfParser {
		if(!isFilledTransformed)
			throw new InvalidStateOfParser();
		CSVParser csvparser = new CSVParser(null, this.countLines, this.countColumns, this.transformedValues);
		return csvparser;
	}
	
	public boolean isFilled() {
		return isFilledTransformed;
	}

	public double[][] getValues() {
		return transformedValues;
	}

	public double getValue(int x, int y) {
		return transformedValues[x][y];
	}

	public int countColumns() {
		return countColumns;
	}

	public int countLines() {
		return countLines;
	}
}
