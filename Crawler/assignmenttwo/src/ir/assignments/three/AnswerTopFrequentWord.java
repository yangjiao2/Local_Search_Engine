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
 * Answer the question about top 500 frequent words
 * 
 */

public class AnswerTopFrequentWord {
	
	private static final String filenameforpages = "TokenFile.txt";
	private static final String filenameforstopwords = "stopwords.txt";
	private static final String filewriteto = "CommonWords.txt";
	private static final int top_number = 500; //top words rank
	
	//compute most frequent words
	private static void MostFrequentWords() throws Exception{
		
		File file=new File(filenameforpages);
		List<String> words = Utilities.tokenizeFile(file);
		
		List<String> stopwordslist = Utilities.tokenizeFile(new File(filenameforstopwords));
		
		List<String> mostfrequentwordslist = computeWordFrequencies(words, top_number, stopwordslist);
		WriteMostFrequentWordsToFile(mostfrequentwordslist);
		
	}
	
	/**
	 * This class is used to filter stopwords and rank top frequent words
	 * @param words tokenized words list input
	 * @param top_no top number of words that needs to be output
	 * @param stopwordslist
	 * @return mostfrequentwordslist
	 */
	private static List<String> computeWordFrequencies(List<String> words, int top_no, List<String> stopwordslist) {

		Map<String, Integer> tempmap= new HashMap<String, Integer>();
		for (int i=0; i<words.size(); i++){
			String tempword = words.get(i);
				if (tempmap.containsKey(tempword))
					tempmap.put(tempword, tempmap.get(tempword)+1);
				else
					tempmap.put(tempword, 1);
		} //construct hashmap to count words (key(word), value(frequency))
		
		for(String element: stopwordslist)
			if(tempmap.containsKey(element))
			tempmap.remove(element); //filter stopwords
		
		List<Map.Entry<String, Integer>> templist = new ArrayList<Map.Entry<String, Integer>>();
		for(Map.Entry<String, Integer> entry: tempmap.entrySet()){
			templist.add(entry);
		}//construct templist with entry containing word/frequency in map
		
		Collections.sort(templist, Collections.reverseOrder(new comparatorByFrequencyReverse()));//sort templist in decresing order
		
		 /*change the templist into sortedlist*/
		List<String> sortedlist = new ArrayList<String> ();
		if(templist.size()>=top_no){
			for (int i=0; i<top_no;i++){
				String word = templist.get(i).getKey();
				sortedlist.add(word);
			}
		}
		else{
			for (int i=0; i<templist.size();i++){
				String word = templist.get(i).getKey();
				sortedlist.add(word);
			}
		}
		return sortedlist;
	}
	
	//generate txt file that store MostFrequentWords
	private static void WriteMostFrequentWordsToFile(List<String> input) throws IOException{
		File newfile =new File(filewriteto);
		FileWriter writer = new FileWriter(newfile);
		StringBuffer buffer = new StringBuffer();
		
		for(int i=0; i<input.size();i++)
			buffer.append(input.get(i)+'\n');
		
		writer.write(buffer.toString());
		writer.close();
	}
	
	public static void main(String[] args) throws Exception{
		MostFrequentWords();
	}
	
}
