package neoveille.parser.test1;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;


public class Main {
	
	/**
	 * 
	 * @param args[0] language 
	 * @param args[1] the name of a directory, which contains the list of text files 
	 * @param args[2] the location of text file, which has the sentences of all text files 
	 * @param args[3] the location of .vrt file  
	 * @param args[4] the location of .vrt file with XML format
	 * @param args[5] the script which uses the tagger 
	 * @param args[6] the location of encoded corpora   
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws URISyntaxException 
	 * 
	 */
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		if(args.length == 7){
	
			ParserOfTexts p = null;
			
			if(args[0].equals("French") || args[0].equals("Chinese") || args[0].equals("Polish") || args[0].equals("Russian") || args[0].equals("Portuguese"))
				p = new TreeTaggerParserOfTexts();
			else if(args[0].equals("Czech"))
				p = new MorphoDitaParserOfTexts();
			else if(args[0].equals("Greek"))
				p = new ILSPParserOfTexts();
			
			if(p!=null){
				p.runTagger(args[0], args[1], args[2], args[3], args[4], args[5]);
				p.createEncodedData(args[4], args[6]);
			}
			
		}
		
		
	}	

}
