package csvparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import file.ExtendedFile;

import utils.Constants;
import utils.Utils;

public class CSVParser implements ICSVParser {

	public String filename = null;
	private double [][] values = new double[MAX_LINES][MAX_COLUMNS];
	private int countLines;
	private int countColumns;
	List<CSVParserTransformer> observersTransformers = new ArrayList<CSVParserTransformer>();
	
	private boolean isFilled = false;
	
	public boolean isFilled() {
		return isFilled;
	}
	
	private void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	
	public double[][] getValues() {
		return values;
	}
	
	public void debug() {
		if(isFilled) {
			for(int x = 0; x < this.countLines; x++) {
				for(int y = 0; y < this.countColumns; y++)
					System.out.print(values[x][y] + " ");
				System.out.println("");
			}
		}
		else
			System.out.println("Parser not filled !");
	}
	
	public double getValue(int x, int y) {
		return values[x][y];
	}
	
	public int countColumns() {
		return countColumns;
	}
	
	public int countLines() {
		return countLines;
	}
	
	public double getMaxValueFromColumn(int y) throws UnknownColumnException {	
		if(y >= 0 && y < this.countColumns) {
	        double maxValue = getValue(0, y);
	        for(int x = 1; x < this.countLines; x++) {
	            if(getValue(x,y) > maxValue)
	            	maxValue = getValue(x,y);
	        }
	        return maxValue;
	    }
	    else
	        throw new UnknownColumnException(y);
	}
	
	public CSVParser(String filename) throws IOException, InvalidCSVFile {
		this.filename = filename;
		this.countLines = 0;
		this.countColumns = 0;
		List<String> records = new ArrayList<String>();
		int tmpCountColumns = 0;
		BufferedReader reader = new BufferedReader(new FileReader(this.filename));

		String line;
		while ((line = reader.readLine()) != null) {
			records.add(line);
			countLines++;
	    }
		
		int[] checkCountColumnsValidityTab = new int[countLines];
		
		for(int i=0; i<countLines; i++){
			
			StringTokenizer stringtokenizer = new StringTokenizer(records.get(i), Constants.SEPARATOR);
			
			while (stringtokenizer.hasMoreTokens()) {
		         values[i][tmpCountColumns] = Double.parseDouble(stringtokenizer.nextToken());
		         tmpCountColumns++;
		     }
			
			checkCountColumnsValidityTab[i] = tmpCountColumns;
			tmpCountColumns = 0;
			
		}
		
		countColumns = checkCountColumnsValidityTab[0];
		for(int countColumnValidityEntity : checkCountColumnsValidityTab) {
			if(countColumns != countColumnValidityEntity)
				throw new InvalidCSVFile();
		}
		
		setFilled(true);
	}
	
	public CSVParser(String filename, int countLines, int countColumns, double[][] values) {
		this.filename = filename;
		this.countLines = countLines;
		this.countColumns = countColumns;
		this.values = values;
		setFilled(true);
	}
	
	public void addNewLine(double[] newLineValues) throws InvalidNewLineValuesException {
		
		if(newLineValues.length != countColumns)
			throw new InvalidNewLineValuesException(newLineValues.length, countColumns);
		
		for(int y=0; y<newLineValues.length; y++)
			values[countLines][y] = newLineValues[y];
		countLines++;
	}
	
	public void registerTransformer(CSVParserTransformer csvparsertransformer) {
		System.out.println("CSVParser - register Transformer");
		observersTransformers.add(csvparsertransformer);
	}
	
	public void unregisterTransformer(CSVParserTransformer csvparsertransformer) {
		System.out.println("CSVParser - unregister Transformer");
		observersTransformers.remove(csvparsertransformer);
	}
	
	public void notifyObservers() {
		System.out.println("CSVParser - notify observers");
		
		for(CSVParserTransformer csvparsertransformer : observersTransformers)
			csvparsertransformer.update();
	}

	public void reload() throws IOException, InvalidCSVFile {
	
		int newCountColumns = (new ExtendedFile(filename)).countLines() - countLines;
		if(newCountColumns < 0)
			throw new InvalidCSVFile();
	
		else if(newCountColumns == 0) {
			System.out.println("Nothing to change");
			return;
		}
		
		else {
			System.out.println("CSVParser has changed.");
			List<String> results = (new ExtendedFile(filename)).extractLines(countLines, newCountColumns);	
	
			for(int i=0; i<results.size(); i++) {
				double[] doubleResults = Utils.strtokArray(results.get(i));
				values[countLines] = doubleResults;
				countLines++;
			}
			
			notifyObservers();
		}
	}
	
}