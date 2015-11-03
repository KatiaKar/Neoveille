package neoveille.parser.test2;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	
	public static void main(String args[]) throws IOException, InterruptedException, URISyntaxException {
		
		if(args.length == 6){

			ParserOfTexts p = null;
			if(args[0].equals("French") || args[0].equals("Chinese") || args[0].equals("Polish") || args[0].equals("Russian") || args[0].equals("Portuguese"))
				p = new  TreeTaggerParserOfTexts();
			else if(args[0].equals("Czech"))
				p = new MorphoDitaParserOfTexts();
			else if(args[0].equals("Greek"))
				p = new ILSPParserOfTexts();
			
			if(p!=null){
				p.runTagger(args[1], args[2], args[3], args[4], args[0]);
				p.createEncodedData(args[3], args[5], args[0]);
			}
			
		}
	}	

}