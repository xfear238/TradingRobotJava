package csvparser;

public class CSVParserTransformerPercentageNormalization extends
		CSVParserTransformerPercentage {

	public CSVParserTransformerPercentageNormalization(CSVParser csvparser) {
		super(csvparser);
	}
	
	public void transform() {
		super.transform();
		
		for(int y = 0; y < csvparser.countColumns(); y++) {
	    	for (int x = 0; x < csvparser.countLines(); x++) {
	    		try {
					transformedValues[x][y] = (double) (csvparser.getValue(x,y) / csvparser.getMaxValueFromColumn(y));
				} catch (UnknownColumnException e) {}
	        }
		}
	
	}

}
