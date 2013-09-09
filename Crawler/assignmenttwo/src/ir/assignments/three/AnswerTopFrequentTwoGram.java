package ir.assignments.three;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Answer the question about top 20 frequent 2-gram
 * 
 */

public class AnswerTopFrequentTwoGram {
	
	private static final String filenameforpages = "TokenFile.txt";
	private static final String filenameforstopwords = "stopwords.txt";
	private static final String filewriteto = "Common2Grams.txt";
	private static final int top_number = 20; //top 2-gram rank
	
	//compute most frequent words
	private static void MostFrequentTwoGram() throws Exception{
		File file=new File(filenameforpages);
		List<String> words = Utilities.tokenizeFile(file);
		
		List<String> stopwordslist = Utilities.tokenizeFile(new File(filenameforstopwords));
		List<String> mostfrequenttwogramlist =computeTwoGramFrequencies(words, top_number , stopwordslist);
		WriteMostFrequentTwoGramsToFile(mostfrequenttwogramlist);
	}
	
	/**
	 * This class is used to filter stopwords and rank top frequent 2-gram
	 * @param words tokenized words list input
	 * @param top_no top number of 2-gram that needs to be output
	 * @param stopwordslist
	 * @return mostfrequent2-gramlist
	 */
	private static List<String> computeTwoGramFrequencies(List<String> words, int top_no, List<String> stopwordslist) {
			
		Map<String, Integer> tempstopmap= new HashMap<String, Integer>();
		for (int i=0; i<stopwordslist.size(); i++){
			String tempword = stopwordslist.get(i);
			tempstopmap.put(tempword, 1);
		}//construct temp stopwordmap
		
		int i=0;
		Map<String, Integer> tempmap= new HashMap<String, Integer>();
		while(i<words.size()){
			String temptwogram="";
			String tempword1=" ";
			String tempword2=" ";
			while(true){
				if(i>=words.size()-1)
					break;
				tempword1=words.get(i);
				if(!tempstopmap.containsKey(tempword1))
					break;
				i++;
				tempword1=" ";
			}
			i++;
			System.out.println("b"+i);
			while(true){
				if(i>=words.size())
					break;
				tempword2=words.get(i);
				if(!tempstopmap.containsKey(tempword2))
					break;
				i++;
				tempword2=" ";
			}//jump for the  case that word + stopword +word
			
			temptwogram = tempword1+" "+tempword2; //2-gram
			if (tempmap.containsKey(temptwogram))
				tempmap.put(temptwogram, tempmap.get(temptwogram)+1);
			else
				tempmap.put(temptwogram, 1);
		}//construct hashmap to count 2-gram words (key(word), value(frequency))
		List<Map.Entry<String, Integer>> templist = new ArrayList<Map.Entry<String, Integer>>();
		for(Map.Entry<String, Integer> entry: tempmap.entrySet()){
			
			
			templist.add(entry);
		}//construct templist with entry containing word/frequency in map
		
		Collections.sort(templist, Collections.reverseOrder(new comparatorByFrequencyReverse()));//sort templist in decresing order

		 /*change the templist into sortedlist*/
		List<String> sortedlist = new ArrayList<String> ();
		if(templist.size()>=top_no){
			for (int j=0; j<top_no;j++){
				String word = templist.get(j).getKey();
				sortedlist.add(word);
			}
		}
		else{
			for (int j=0; j<templist.size();j++){
				String word = templist.get(j).getKey();
				sortedlist.add(word);
			}
		}
		return sortedlist;
	}
	
	//generate txt file that store MostFrequent2-gramWords
	private static void WriteMostFrequentTwoGramsToFile(List<String> input) throws IOException{
		File file =new File(filewriteto);
		FileWriter writer = new FileWriter(file);
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<input.size();i++)
			buffer.append(input.get(i)+'\n');
		writer.write(buffer.toString());
		writer.close();
	}	
	
	public static void main(String[] args) throws Exception{
		MostFrequentTwoGram();
	}
}
