package neoveille.parser.test1;
import java.io.IOException;
import java.util.LinkedList;

public abstract class ParserOfTexts  {
	
	public static LinkedList<String> listOfFiles;
	
	public ParserOfTexts(){
		listOfFiles = new LinkedList<String>();
	}
	
	public void createEncodedData(String inputXMLverticalizedFile,String outputEncodedCorporaFile) throws  InterruptedException, IOException{
		
		String ch = "cwb-encode -d" + outputEncodedCorporaFile + " -f " + inputXMLverticalizedFile + " -R /usr/local/share/cwb/registry/example -P pos -P lemma -S s";  
        Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
        p.waitFor();
	
	}

	public static LinkedList<String> getListOfFiles() {
		return listOfFiles;
	}

	public static void setListOfFiles(LinkedList<String> listOfFiles) {
		ParserOfTexts.listOfFiles = listOfFiles;
	}
	
	abstract void runTagger(String language, String inputDirectory,String inputFile, String outputVerticalizedFile, String outputXMLverticalizedFile, String taggerFile) throws IOException, InterruptedException ;
	
	abstract void postProcessing();
	
}
