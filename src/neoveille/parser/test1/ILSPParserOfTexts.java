package neoveille.parser.test1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.soaplab.getstarted.ilsp_nlp.IlspNlp;
import org.soaplab.getstarted.ilsp_nlp.IlspNlpService;
import org.soaplab.typedws.JobId;

/**
 * 
 * Description: Uses ILSP Tagger tool for annotating Greek texts.
 * URL: http://nlp.ilsp.gr/ws/
 * 
 */

public class ILSPParserOfTexts extends ParserOfTexts{

	public ILSPParserOfTexts() {
		super();
	}

	@Override
	void runTagger(String language, String inputDirectory, String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String treeTaggerFile) throws IOException, InterruptedException {
		
		String line = null;
		URLConnection con;
		InputStream is;
		String current_result,str;
		String []tokens,str1,str2,str3;
		BufferedReader br1,br2;
		IlspNlpService service;
		IlspNlp port;
		JobId current_job;

		Utils.saveNameOfFiles(listOfFiles, inputDirectory);
		Utils.concatenateFiles(listOfFiles, inputFile, language);
		
		inputFile = inputFile + "/" + language + "/all.txt";
		outputXMLverticalizedText = outputXMLverticalizedText + "/" + language + "/outputText.vrt";
	    outputVerticalizedText = outputVerticalizedText + "/" + language + "/outputText.vrt";
	    
		/*uses ILSP Tagger*/
		br1 = new BufferedReader(new FileReader(inputFile));
		while ((line = br1.readLine()) != null) {

			service = new IlspNlpService();
			port = service.getIlspNlpPort();
			
			if(line.length()>1){
				current_result = port.run("txt",null,line,null,null,"el",false);
				current_job = new JobId();
				current_job.setJobId(current_result);	
				port.waitfor(current_job);
				port.terminate(current_job);
			
				/*saves the results to .vrt files*/
				URL url = new URL("http://nlp.ilsp.gr/soaplab2-results//"+current_result+"_output");		
				con = url.openConnection();
				is = con.getInputStream();
				br2 = new BufferedReader(new InputStreamReader(is));
				while ((line = br2.readLine()) != null) {
					if(line.contains("<t ")){
						tokens = line.split("\\s+");
						str1 = tokens[3].split("\"");
						str2 = tokens[4].split("\"");
						str3 = tokens[5].split("\"");
						str = str1[1] + "\t" + str2[1] + "\t" + str3[1] + "\n";  
						Utils.writeInFile(outputVerticalizedText,str);		
					}
				}
				br2.close();
			}
		}
		br1.close();
        
		/*annotates the sentences of .vrt file*/
	    Utils.annotateSentences(outputXMLverticalizedText,outputVerticalizedText);
			
	}

	@Override
	void postProcessing() {
		
	}

}
