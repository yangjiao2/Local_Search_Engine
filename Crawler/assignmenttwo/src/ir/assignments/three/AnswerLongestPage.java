package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This is to answer the question about longest page and output txt file to indicate longest page
 */
public class AnswerLongestPage  {
	
	public static HashMap <String, Integer> pagelengthmap = new HashMap <String, Integer>() ;
	private static final String filenameforlog = "CrawlLog.txt";
	private static final String filewriteto = "LongestPage.txt";
	
	private static void MaxLengthPages() throws IOException{
	
	FileReader fr=new FileReader(filenameforlog);
	StringBuffer sb = new StringBuffer();
	BufferedReader br = new BufferedReader(fr);
	String temp ="";
	
	while((temp=br.readLine())!=null)
		sb.append(temp+' ');
	br.close();

	//parse logtxt to get String contains URL and its length
	
	String temp1=sb.toString();
	sb.delete(0, sb.length());
	Pattern f= Pattern.compile("(URL: )(http://)(.*?)(Domain:)(.*?)(Text length: )(.*?)(Html length:)(.*?)(=)");
	Matcher m = f.matcher(temp1);
	String temp11="";
	while(m.find())
		temp11=temp11+m.group(2)+m.group(3)+m.group(7);
	String[] split=temp11.split(" ");
	
	//find out index of max length website	
	
	int max=0, index=0;
	for(int i=1; i<split.length;i=i+2){
		int tempint=0;
		if(split[i].matches("\\d+"))
			tempint=Integer.parseInt(split[i]);
		if(tempint>max){
			max=tempint;
			index=i;
		}
	}
		
	File file =new File(filewriteto);
	FileWriter writer = new FileWriter(file);
	sb.append(split[index-1]+" is longest page");
	writer.write(sb.toString());
	writer.close();
	}
	
	public static void main(String[] args) throws IOException{
		MaxLengthPages();
	}
	
}

