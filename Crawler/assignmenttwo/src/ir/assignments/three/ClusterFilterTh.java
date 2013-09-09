package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
/**
 * The class of getting the unique pages from the similar pairs
 * 
 */
public class ClusterFilterTh extends Thread {
	private int id = 0;
	private int start = 0;
	private int pair1 = 0;
	private int pair2 = 0;
	private double resemblance = 0.0;
	private double containment1 = 0.0;
	private double containment2 = 0.0;
	public ClusterFilterTh(int nub1)
	{
		id =nub1;
		start = 500 * id;
	}
	 public void run(){
		try {
		File similarityfile =new File("Pairs"+String.valueOf(id)+".txt");
		FileWriter similaritywriter = new FileWriter(similarityfile,true);
		File input = null;
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
	    HashSet<Integer> hassimilar = new HashSet<Integer>();
	    String[] locationstring = null;
		
//		input = new File("secondstep/Pairs"+String.valueOf(id*2)+".txt");
//		in = new FileInputStream(input);
//		isr = new InputStreamReader(in,"US-ASCII");
//		br = new BufferedReader(isr);
//
//		String tempstring1 = br.readLine();
//		locationstring = tempstring1.split(", ");
//
//		for(int i=0;i<locationstring.length;++i)
//			hassimilar.add(Integer.valueOf(locationstring[i]));
//		
//		br.close();
//		isr.close();
//		in.close();
//		
//		input = new File("secondstep/Pairs"+String.valueOf(id*2+1)+".txt");
//		in = new FileInputStream(input);
//	    isr = new InputStreamReader(in,"US-ASCII");
//	    br = new BufferedReader(isr);
//	    tempstring1 = br.readLine();
//		locationstring = tempstring1.split(", ");
//
//		for(int i=0;i<locationstring.length;++i)
//			hassimilar.add(Integer.valueOf(locationstring[i]));
//		
//		br.close();
//		isr.close();
//		in.close();
//		
		input = new File("firststep/Similarity"+String.valueOf(id)+".txt");
		in = new FileInputStream(input);
	    isr = new InputStreamReader(in,"US-ASCII");
	    br = new BufferedReader(isr);
	    for(int i = start; i < start + 500; ++i)
	    {
	    	hassimilar.add(i);
	    }
	    String tempstring = new String();
	    while((tempstring = br.readLine()) != null)
	    {
	    	tempstring = tempstring.replaceAll("[,;]", " ");
			locationstring = tempstring.split(" ");
			pair1 = Integer.valueOf(locationstring[0]);
			pair2 = Integer.valueOf(locationstring[1]);
			resemblance = Double.valueOf(locationstring[2]);
			containment1 = Double.valueOf(locationstring[3]);
			containment2 = Double.valueOf(locationstring[4]);
			if(resemblance >= SyntCluster.threthold1)
				if(hassimilar.contains(pair1) && hassimilar.contains(pair2))
					hassimilar.remove(pair2);
				else{
					hassimilar.remove(pair1);
					hassimilar.remove(pair2);
				}
			
			else if (containment1 >= SyntCluster.threthold2){
				if(hassimilar.contains(pair1) )
					hassimilar.remove(pair1);
				else{
					hassimilar.remove(pair1);
					hassimilar.remove(pair2);
				}
			}
				
			else if (containment2 >= SyntCluster.threthold3)
				if(hassimilar.contains(pair2) )
					hassimilar.remove(pair2);
				else{
					hassimilar.remove(pair1);
					hassimilar.remove(pair2);
				}
		    }
		    String temp = hassimilar.toString();
		    temp = temp.substring(1,temp.length()-1);
		    similaritywriter.write(temp);
		    System.out.println(String.valueOf(id)+"finished"+String.valueOf(hassimilar.size()));
//		    System.out.println(String.valueOf(hassimilar.size()));
		    similaritywriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
