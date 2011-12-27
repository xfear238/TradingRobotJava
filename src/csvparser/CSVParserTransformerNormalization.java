package csvparser;

public class CSVParserTransformerNormalization extends CSVParserTransformer {

	public CSVParserTransformerNormalization(CSVParser csvparser) {
		super(csvparser);
		transform();
	}
	
	@Override
	public void transform() {
		 
		for (int y = 0; y < this.countColumns; y++) {
		    	for (int x = 0; x < this.countLines; x++) {
		    		try {
						transformedValues[x][y] = (double) (csvparser.getValue(x,y) / csvparser.getMaxValueFromColumn(y));
					} catch (UnknownColumnException e) {}
		        }
		}
		
		isFilledTransformed = true;
		this.countLines--;
	}

	
}
