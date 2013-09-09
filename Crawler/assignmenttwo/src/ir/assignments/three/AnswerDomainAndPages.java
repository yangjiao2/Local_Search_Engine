package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This is to answer the question about number of unique domain and its pages number and output txt file for description
 */

public class AnswerDomainAndPages {
	
	public static HashMap <String, String> urlsubdomainmap = new HashMap <String, String>() ;
	private static int subdomain_num=0;
	private static final String filenameforlog = "CrawlLog.txt";
	private static final String filewriteto = "Subdomains.txt";
	
	//answer the question about subdomain and its unique websites
	private  static TreeMap<String, Integer> SubdomainAndPages() throws IOException{
		FileReader fr=new FileReader(filenameforlog);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(fr);
		String temp ="";
		while((temp=br.readLine())!=null)
			sb.append(temp+' ');

		br.close();
		
		String temp1=sb.toString();
		String temp2=sb.toString();
		sb.delete(0, sb.length());
		
		Pattern f1 = Pattern.compile("(URL: )(http://)(.*?)(Domain:)");
		Matcher m1 = f1.matcher(temp1);
		String temp11="";
		while(m1.find()){
			temp11=temp11+m1.group(2)+m1.group(3);
		} 	//parse log file and find out URL
		
		Pattern f2 = Pattern.compile("(Sub-domain: ')(.*?)(\\.ics\\.uci\\.edu)(.*?)(Path:)");
		Matcher m2 = f2.matcher(temp2);
		String temp22="";
		while(m2.find()){
			temp22=temp22+m2.group(2)+m2.group(3)+" ";
		}	//parse log file and find out Sub-domain

		String[] spliturl = temp11.split(" ");
		String[] splitdomain= temp22.split(" ");
				
		
		for(int i=0; i<splitdomain.length;i++)
			urlsubdomainmap.put(spliturl[i], splitdomain[i]);//map url<key> and subdomain<value>
		
		
		HashMap <String, Integer> subdomainmap = new HashMap <String, Integer>() ;//count unique subdomain
		
		for(Entry<String, String> entry:urlsubdomainmap.entrySet()){	
			if(subdomainmap.containsKey(entry.getValue()))
				subdomainmap.put(entry.getValue(),subdomainmap.get(entry.getValue())+1);
			else{
				subdomainmap.put(entry.getValue(),1);
				subdomain_num++;
			}
		}
		
		//sort by treemap
		TreeMap<String, Integer> sortedsubdomainmap = new TreeMap<String, Integer>(subdomainmap);
		return sortedsubdomainmap ;
	}
	
	//generate txt file that store Subdomains information
	private static void WriteSubdomainsToFile(TreeMap<String, Integer> input) throws IOException{
		File newfile =new File(filewriteto);
		FileWriter writer = new FileWriter(newfile);
		StringBuffer buffer = new StringBuffer();
		
		for(Entry<String, Integer> entry : input.entrySet())
			buffer.append(entry.getKey()+","+entry.getValue()+'\n');
		
		writer.write("There are "+Integer.toString(urlsubdomainmap.size())+" pages "
				+"and "+subdomain_num+" subdomain in entire domain."+"\n");
		writer.write(buffer.toString());
		writer.close();
	}	
	
	public static void main(String[] args) throws IOException{
		WriteSubdomainsToFile(SubdomainAndPages());
	}
}
