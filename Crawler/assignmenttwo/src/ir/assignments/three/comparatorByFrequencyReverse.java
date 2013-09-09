package ir.assignments.three;

import java.util.Comparator;
import java.util.Map;

/**
 * Used for comparing words in the decresing order of frequency
 */
public class comparatorByFrequencyReverse implements Comparator<Map.Entry<String, Integer>>{
	
	public comparatorByFrequencyReverse(){}
	
	public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer>e2){
		if(e1.getValue()>e2.getValue())
			return 1;
		else if(e1.getValue()<e2.getValue())
			return -1;
		else// when frequency of two words are equal, compare words with alphabetical order
			return e1.getKey().compareToIgnoreCase(e2.getKey());
		}
	}
	

