package csvparser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import utils.Constants;

public abstract class CSVParserTransformer implements ICSVParser {
	
	protected double[][] transformedValues = new double[MAX_LINES][MAX_COLUMNS];
	protected boolean isFilledTransformed = false;
	protected CSVParser csvparser;
	
	public abstract void transform();
	
	private void init(CSVParser csvparser) {
		this.csvparser = csvparser;
		
		System.out.println("CSVParserTransformer - countLines = " + csvparser.countLines());
		System.out.println("CSVParserTransformer - countColumns = " + csvparser.countColumns());
	}
	
	public CSVParserTransformer(CSVParser csvparser) {
		init(csvparser);
		csvparser.registerTransformer(this);
		transform();
	}
	
	public void debug() {
		if(isFilledTransformed) {
			for(int x = 0; x < csvparser.countLines(); x++) {
				for(int y = 0; y < csvparser.countColumns(); y++)
					System.out.print(transformedValues[x][y] + " ");
				System.out.println("");
			}
		}
	}
	
	public void write(String filename) throws IOException {
		if(isFilledTransformed) {
			PrintWriter writer =  new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			for(int x = 0; x < csvparser.countLines(); x++) {
				for(int y = 0; y < csvparser.countColumns(); y++) {
					if(y == csvparser.countColumns()-1) //Last column
						writer.print(transformedValues[x][y]);
					else
						writer.print(transformedValues[x][y] + Constants.SEPARATOR);
				}
				
				if(x != csvparser.countLines()-2)	//Last line
					writer.println("");
			}
			writer.close();
		}
	}
	
	public CSVParser getCSVParser() throws InvalidStateOfParser {
		if(!isFilledTransformed)
			throw new InvalidStateOfParser();
		CSVParser csvparserResult = new CSVParser(csvparser.filename, this.csvparser.countLines(), this.csvparser.countColumns(), this.transformedValues);
		return csvparserResult;
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
		return csvparser.countColumns();
	}

	public int countLines() {
		return csvparser.countLines();
	}

	public void update() {
		System.out.println("CSVParserTransformer - Update()");
		init(csvparser);
		transform();
	}
	
	public boolean isTransformed() {
		return isFilledTransformed;
	}
	
	public void setTransformed(boolean isFilledTransformed) {
		this.isFilledTransformed = isFilledTransformed;
	}
}
