package neoveille.parser.test1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Utils{
	
	public static void saveNameOfFiles(LinkedList<String> listOfFiles, String inputDirectory){
		File folder = new File(inputDirectory);
		for(File f : folder.listFiles()){
			if(f.isDirectory())
				saveNameOfFiles(listOfFiles, f.getAbsolutePath());
			else
				listOfFiles.add(f.getAbsolutePath());
		}	
	}
	
	public static void concatenateFiles(LinkedList<String> listOfFiles, String outputFile, String language) throws IOException{
		String currentFile, currentLine;
		BufferedReader br = null;
		
		for(int i=0; i<listOfFiles.size(); i++){
			currentFile = listOfFiles.get(i);
			br = new BufferedReader(new FileReader(currentFile));	
			while((currentLine = br.readLine())!=null){
				Utils.writeInFile(outputFile, currentLine +"\n");	
			}
		}
	}
	
	public static void writeInFile(String pathOfOutputFile,String text) throws IOException{
        File file = new File(pathOfOutputFile);
        BufferedWriter bw;
         
        if (!file.exists()) {
                file.createNewFile();
        }    
        
        bw = new BufferedWriter(new FileWriter(file,true));
        bw.write(text);
        bw.close();
    }
	
	public static void annotateSentences(String outputXMLverticalizedText, String outputVerticalizedText) throws IOException{
		String []tokens;
		String line, previousLine = null;
		BufferedReader br = new BufferedReader(new FileReader(outputVerticalizedText));
	    Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	    int isPunctuation = 0;
	   
	    while((line = br.readLine())!=null){ 
	       tokens = line.split("\\s+"); 
	       /*chinese and european full-stop, question mark and exclamation mark*/
	       if(tokens[0].equals(".") || tokens[0].equals("!") || tokens[0].equals("?") || tokens[0].equals("。") || tokens[0].equals("！") || tokens[0].equals("？")){
	        		Utils.writeInFile(outputXMLverticalizedText, line + "\n");
	        		isPunctuation = 1;
	       }else{
	        	if(isPunctuation == 1){
	        		Utils.writeInFile(outputXMLverticalizedText,"<\\s>\n");	 
	        		Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	        	}
	        	Utils.writeInFile(outputXMLverticalizedText, line + "\n");
	        	isPunctuation = 0;
	       }
	       previousLine = line;
	 	}
	    
	    tokens = previousLine.split("\\s+");
	    /*chinese and european full-stop, question mark and exclamation mark*/
	    if(tokens[0].equals(".") || tokens[0].equals("!") || tokens[0].equals("?") || tokens[0].equals("。") || tokens[0].equals("！") || tokens[0].equals("？"))
	    	Utils.writeInFile(outputXMLverticalizedText,"<\\s>\n");	
	 	br.close();
	 	
	}
	
}
