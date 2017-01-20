package com.bio.graphs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

@SpringBootApplication
public class BioGraphsApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BioGraphsApp.class, args);
		String bioGraphies1 = null;
		String bioGraphies2 = null;
		
		String bioGraphies = bioGraphies2;
		System.out.println(bioGraphies);
		// InputStream modelIn =
		// MidhuGraphicsApplication.class.getResourceAsStream("en-sent.bin");
		String resPath = "C:/ProgramData/Kalyan/MyWorkSpaces/WS_STS_01/MidhuGraphics/src/main/resources/";
		InputStream modelSentenceIS = new FileInputStream(resPath + "en-sent.bin");
		InputStream modelTokenizerIS = new FileInputStream(resPath + "en-token.bin");		
		InputStream modelNameFinderDateIS = new FileInputStream(resPath + "en-ner-date.bin");
		InputStream modelNameFinderLocationIS = new FileInputStream(resPath + "en-ner-location.bin");
		InputStream modelNameFinderMoneyIS = new FileInputStream(resPath + "en-ner-money.bin");
		InputStream modelNameFinderOrgIS = new FileInputStream(resPath + "en-ner-organization.bin");
		InputStream modelNameFinderPercIS = new FileInputStream(resPath + "en-ner-percentage.bin");
		InputStream modelNameFinderPersonIS = new FileInputStream(resPath + "en-ner-person.bin");

		TokenizerModel modelTokenizer = new TokenizerModel(modelTokenizerIS);
		TokenNameFinderModel modelTokenNameFinderDate = new TokenNameFinderModel(modelNameFinderDateIS);
		TokenNameFinderModel modelTokenNameFinderLocation = new TokenNameFinderModel(modelNameFinderLocationIS);
		TokenNameFinderModel modelTokenNameFinderMoney = new TokenNameFinderModel(modelNameFinderMoneyIS);
		TokenNameFinderModel modelTokenNameFinderOrg = new TokenNameFinderModel(modelNameFinderOrgIS);
		TokenNameFinderModel modelTokenNameFinderPerc = new TokenNameFinderModel(modelNameFinderPercIS);
		TokenNameFinderModel modelTokenNameFinderPerson = new TokenNameFinderModel(modelNameFinderPersonIS);

		TokenizerME tokenizer = new TokenizerME(modelTokenizer);
		NameFinderME nameFinderDateDetector = new NameFinderME(modelTokenNameFinderDate);
		NameFinderME nameFinderLocationDetector = new NameFinderME(modelTokenNameFinderLocation);
		NameFinderME nameFinderMoneyDetector = new NameFinderME(modelTokenNameFinderMoney);
		NameFinderME nameFinderOrgDetector = new NameFinderME(modelTokenNameFinderOrg);
		NameFinderME nameFinderPercDetector = new NameFinderME(modelTokenNameFinderPerc);
		NameFinderME nameFinderPersonDetector = new NameFinderME(modelTokenNameFinderPerson);
		
		
		try {
			SentenceModel modelSentence = new SentenceModel(modelSentenceIS);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(modelSentence);
			String sentences[] = sentenceDetector.sentDetect(bioGraphies);
			for (int i = 0; i < sentences.length; i++) {
				System.out.println("======= L1 :" + sentences[i]);
//				Span[] spans = nameFinderDateDetector.find(sentences);
//				Span[] spans = nameFinderLocationDetector.find(sentences);

				String tokens[] = tokenizer.tokenize(sentences[i]);
//				for (int j = 0; j < tokens.length; j++) {
//					System.out.println("~~~~~~ " + tokens[j]);
//				}
				
				// Find Names
				Span[] spansName = nameFinderPersonDetector.find(tokens);
				System.out.println("Name: " + Arrays.toString(Span.spansToStrings(spansName, tokens)));
				
				// Find Locations
				Span[] spansLocation = nameFinderLocationDetector.find(tokens);
				System.out.println("Location: " + Arrays.toString(Span.spansToStrings(spansLocation, tokens)));
				
				// Find Organization
				Span[] spansOrganization = nameFinderOrgDetector.find(tokens);
				System.out.println("Organization: " + Arrays.toString(Span.spansToStrings(spansOrganization, tokens)));
				
				// Find Date
				Span[] spansDate = nameFinderDateDetector.find(tokens);
				System.out.println("Date: " + Arrays.toString(Span.spansToStrings(spansDate, tokens)));
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelSentenceIS != null) {
				try {
					modelSentenceIS.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
