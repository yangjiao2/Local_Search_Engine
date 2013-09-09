package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * utility methods for text processing(tokenize).
 */
public class Utilities {
	/**
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words)
	 * @throws IOException 
	 */
	public static ArrayList<String> tokenizeFile(File input) throws IOException {
		
		ArrayList<String> mylist = new ArrayList<String>();
		InputStream in = new FileInputStream(input);
        InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
        BufferedReader br = new BufferedReader(isr); 
		StringBuffer sb = new StringBuffer();
		
		String temp =null;
        while((temp=br.readLine())!=null){
        sb.append(temp +" ");
        String mystring = sb.toString();
                
        mystring = mystring.toLowerCase();
 		String[] mytemp = mystring.split("\\s");
	
		for(int i=0;i<mytemp.length;++i)
			mylist.add(mytemp[i]);
		
		sb.delete(0, sb.length());
		temp =null;
        }
		br.close();

		return (mylist);
	}
}