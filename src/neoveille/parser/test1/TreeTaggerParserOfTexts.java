package neoveille.parser.test1;
import java.io.IOException;

/**
 * 
 * Description: Uses TreeTagger tool for annotating Russian, French, Polish, Chinese, Portuguese texts. 
 * URL: http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/
 * 
 */

public class TreeTaggerParserOfTexts extends ParserOfTexts{

	public TreeTaggerParserOfTexts() {
		super();
	}
	
	@Override
	void runTagger(String language, String inputDirectory , String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String treeTaggerFile) throws IOException, InterruptedException {
		  
        Process p;
		String ch = null;
		
		inputFile = inputFile + "/" + language + "/all.txt";
		
		Utils.saveNameOfFiles(listOfFiles, inputDirectory);
		Utils.concatenateFiles(listOfFiles, inputFile, language);
		
		outputXMLverticalizedText = outputXMLverticalizedText + "/" + language + "/outputText.vrt";
	    outputVerticalizedText = outputVerticalizedText + "/" + language + "/outputText.vrt";
		
	    System.out.println(outputVerticalizedText);
	    
		/*uses treeTagger*/
		ch = "cat " + inputFile + " | " + treeTaggerFile + " > " + outputVerticalizedText;
	    p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
	    p.waitFor();
	    
	    /*annotates the sentences of .vrt file*/
	    Utils.annotateSentences(outputXMLverticalizedText,outputVerticalizedText);
	
	}

	@Override
	void postProcessing() {
		
	}
	
}
