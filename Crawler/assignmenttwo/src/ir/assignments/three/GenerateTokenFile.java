package ir.assignments.three;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//This class is to taken input into token into separate file
public class GenerateTokenFile {
	
	private static final String TokenFileName  ="TokenFile.txt";
	private static final String filenameforpages = "Pagetext.txt";
	
	
	public static void WriteTokenizeFile(String fromfilename) throws IOException{

		File newfilewrite =new File(TokenFileName);
		FileWriter writer = new FileWriter(newfilewrite);//construct file for write
		
		InputStream in = new FileInputStream(fromfilename);
        InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
        BufferedReader br = new BufferedReader(isr); 
		StringBuffer sb = new StringBuffer();
		
		String temp =null;
        while((temp=br.readLine())!=null){

        	
        	sb.append(temp +" ");
        	String mystring = sb.toString();

        	mystring = mystring.toLowerCase();
        	mystring = mystring.replaceAll("(\\s)(')([^']+)(')(\\s)", "$1$3$5"); //eliminate ' ' only
        	mystring = mystring.replaceAll("\\-{2,}"," ");// if hypen>1, eleminate to -
        	mystring = mystring.replaceAll("[^a-z0-9-']", " "); //eliminate special character
 		
        	String[] mytemp = mystring.split("\\s");
	
        	for(int i=0;i<mytemp.length;++i)
        		if(!mytemp[i].equals("")&& mytemp[i].length()<=15
        		&& !mytemp[i].matches("'.*'")
        		// only add word has length<15(avg length of english is 5) and not empty and not ''
        		&& !mytemp[i].matches(".*\\d.*")
        		){
        		//NOTE HERE: IF YOU NEED TO LIST ALL THINGS INCLUDING NUMERICAL WORDS, DELETE THIS CONDITION	
         	       	writer.write(mytemp[i]+" ");
        		}
        	writer.write("\n");
    		sb.delete(0, sb.length()); 
    		temp =null;
    		
        	}//write
        	
		writer.close();
        br.close();
	}
	
	public static void main(String args[]) throws IOException{
		WriteTokenizeFile(filenameforpages);
	}
}


