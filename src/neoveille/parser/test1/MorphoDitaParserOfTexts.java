package neoveille.parser.test1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * Description: Uses MorphoDita tool for annotating Czech texts.
 * URL: http://ufal.mff.cuni.cz/morphodita
 * 
 */
public class MorphoDitaParserOfTexts extends ParserOfTexts{

	public MorphoDitaParserOfTexts() {
		super();
	}

	public static void changeOrderOfLemmaAndPOStags(String tmpOutputVerticalizedText, String outputVerticalizedText) throws IOException{
		
		String line,newline;
		String []tokens;
		
		BufferedReader br = new BufferedReader(new FileReader(tmpOutputVerticalizedText));
		
		while((line = br.readLine())!=null){
			tokens = line.split("\\s+");
			if(tokens.length==3){
				newline = tokens[0] + "\t" + tokens[2] + "\t" + tokens[1] + "\n";
				Utils.writeInFile(outputVerticalizedText,newline);
			}
		}
		
		br.close();		
	}

	@Override
	void runTagger(String language, String inputDirectory, String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String morphoDitaTaggerFile) throws IOException, InterruptedException {
		
		Process p;
		String ch,tmpOutputVerticalizedText;
		
			Utils.saveNameOfFiles(listOfFiles, inputDirectory);
			Utils.concatenateFiles(listOfFiles, inputFile, language);
		
			tmpOutputVerticalizedText = outputVerticalizedText.substring(0,outputVerticalizedText.lastIndexOf("/")); 
			tmpOutputVerticalizedText = tmpOutputVerticalizedText +  "/" + language + "/tmpOutputText.vrt";
			
			/*uses MorphoDita Tagger*/
			ch = morphoDitaTaggerFile + "/./run_tagger " + " --input=untokenized --output=vertical "  +  morphoDitaTaggerFile + "/czech-morfflex-pdt-131112-pos_only.tagger " + inputFile + ":" + tmpOutputVerticalizedText;
	        p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
	        p.waitFor();
	  
	        outputXMLverticalizedText = outputXMLverticalizedText + "/" + language + "/outputText.vrt";
		    outputVerticalizedText = outputVerticalizedText + "/" + language + "/outputText.vrt";
	        
	        /*change the order of lemma and pos-tags of .vrt file*/
	        changeOrderOfLemmaAndPOStags(tmpOutputVerticalizedText, outputVerticalizedText);
	        
	        /*annotates the sentences of .vrt file*/
	        Utils.annotateSentences(outputXMLverticalizedText,outputVerticalizedText);
	        
	 }
	

	@Override
	void postProcessing() {
		
	}

	
}
