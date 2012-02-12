package csvparser;

public class CSVParserTransformerPercentage extends CSVParserTransformer {

	public CSVParserTransformerPercentage(CSVParser csvparser) {
		super(csvparser);
	}
	
	@Override
	public void transform() {
		
		for(int y = 0; y < csvparser.countColumns(); y++) {
				//x = 0
				transformedValues[0][y] = 0.0;
		    	for (int x = 1; x < csvparser.countLines(); x++) {
		    		transformedValues[x][y] = (double) (csvparser.getValue(x, y) / csvparser.getValue(x-1, y));
		        }
		}
		setTransformed(true);
	}
}
