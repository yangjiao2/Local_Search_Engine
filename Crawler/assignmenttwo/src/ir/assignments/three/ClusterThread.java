package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * The class of computing the first step
 * 
 */
public class ClusterThread extends Thread {
	private int id = 0;
	private int start = 0;
	public ClusterThread(int nub)
	{
		id =nub;
		start = 500*id;
	}
	 public void run(){
		try {
		RandomAccessFile r = new RandomAccessFile("Shingle.txt","r");
		 
		File similarityfile =new File("Similarity"+String.valueOf(id)+".txt");
		FileWriter similaritywriter = new FileWriter(similarityfile,true);
			
		File input = new File("Location.txt");
		InputStream in = new FileInputStream(input);
	    InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
	    BufferedReader br = new BufferedReader(isr);

		String tempstring = br.readLine();
		String[] locationstring = tempstring.split(" ");
		ArrayList<Integer> locationint = new ArrayList<Integer>();
		HashSet<Integer> hassimilar = new HashSet<Integer>();
			
		for(int i=0;i<locationstring.length;++i)
			locationint.add(Integer.valueOf(locationstring[i]));
		int locationsize = locationint.size()-1;
		String mystring1 = new String();
		String mystring2 = new String();
			
		
			
		for(int i=start;i<locationsize&&i<500+start;++i){
			System.out.println(String.valueOf(i));
			for(int j=start;j<i;++j)
				{
					if(hassimilar.contains(i)||hassimilar.contains(j))
						continue;
					
					r.seek(Integer.valueOf(locationint.get(i)));
					
					mystring1 = r.readLine();
					r.seek(Integer.valueOf(locationint.get(j)));
					mystring2 = r.readLine();
					String mystring = SyntCluster.getsimilarity(SyntCluster.getset(mystring1),SyntCluster.getset(mystring2));
					if( mystring != null)
					{
						similaritywriter.write(String.valueOf(i)+","+String.valueOf(j)+";"+mystring+"\n");
						hassimilar.add(j);
					}
				}
			}
			similaritywriter.close();
			System.out.println(String.valueOf(id)+"finished");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
