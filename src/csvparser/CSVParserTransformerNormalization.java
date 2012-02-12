package csvparser;

public class CSVParserTransformerNormalization extends CSVParserTransformer {

	public CSVParserTransformerNormalization(CSVParser csvparser) {
		super(csvparser);
	}
	
	@Override
	public void transform() {
		
		for(int y = 0; y < csvparser.countColumns(); y++) {
		    	for (int x = 0; x < csvparser.countLines(); x++) {
		    		try {
						transformedValues[x][y] = (double) (csvparser.getValue(x,y) / csvparser.getMaxValueFromColumn(y));
					} catch (UnknownColumnException e) {}
		        }
		}
		setTransformed(true);
	}
}
