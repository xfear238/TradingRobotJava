package file;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class ExtendedFile extends java.io.File {

	private static final long serialVersionUID = 1L;

	public ExtendedFile(String filename) {
		super(filename);
	}

	public int countLines() throws IOException {
		
		LineNumberReader lineCounter = new LineNumberReader(new InputStreamReader(new FileInputStream(super.getAbsolutePath())));
	    while ((lineCounter.readLine()) != null);
	    return lineCounter.getLineNumber();
	}
	    
	public List<String> extractLines(int begin, int count) throws IOException {
	    	
		List<String> results = new ArrayList<String>();
		LineNumberReader lineCounter = new LineNumberReader(new InputStreamReader(new FileInputStream(super.getAbsolutePath())));
	        
		int counter = 0;
		while ((lineCounter.readLine()) != null) {
    		counter++;
    		if(counter == begin)
	    			break;
	    	}
		    	
    	while(count > 0) {
    		results.add(lineCounter.readLine());
    		count--;
    	}
    	
    	return results;
	}
	    
	public void append(String text) {
		BufferedWriter bufWriter = null;
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(super.getAbsolutePath(), true);
	        bufWriter = new BufferedWriter(fileWriter);   
	        bufWriter.newLine();
	        bufWriter.write(text);
	        bufWriter.close();
	    } catch (IOException ex) {} 
	        
	    finally {
	       
	    	try {
	    		bufWriter.close();
	    		fileWriter.close();
	    	} catch (IOException ex) {}
	    
	    }
	}
	    
	public boolean copy(String destinationPath) {
	    	
		boolean resultat = false;  
	    	
	    java.io.File destination = new java.io.File(destinationPath);
	    java.io.File source = new java.io.File(super.getAbsolutePath());
	    	
	    java.io.FileInputStream sourceFile = null; 
	    java.io.FileOutputStream destinationFile = null; 
	    	
	    try { 
	    	 
	    	destination.createNewFile(); 
	    	sourceFile = new java.io.FileInputStream(source);
	    	destinationFile = new java.io.FileOutputStream(destination); 
	    	
	    	byte buffer[]=new byte[512*1024]; 
	    	int nbLecture; 
	    	
	    	while((nbLecture = sourceFile.read(buffer)) != -1 ) 
	    		destinationFile.write(buffer, 0, nbLecture); 

	  		resultat = true; 
	    	
	    } catch(Exception e) {} 
	    	
	    finally {

	    	try { 
	    		sourceFile.close(); 
	    	} catch(Exception e) { } 

	    	try { 
	    		destinationFile.close();
	    	} catch(Exception e) { } 
	    }
	    
	    return(resultat); 
	}  
	
}