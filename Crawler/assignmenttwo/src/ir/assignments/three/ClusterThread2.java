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
 * The class of computing the similar pairs between different sets
 * 
 */
public class ClusterThread2 extends Thread {
	private int id1 = 0;
	private int id2 = 0;
	public ClusterThread2(int nub1, int nub2)
	{
		id1 =nub1;
		id2 =nub2;
	}
	 public void run(){
		try {
		RandomAccessFile r = new RandomAccessFile("Shingle.txt","r");
		 
		File similarityfile =new File("Similarity"+String.valueOf(id1/2)+".txt");
		FileWriter similaritywriter = new FileWriter(similarityfile,true);
			
		File input = new File("Location.txt");
		InputStream in = new FileInputStream(input);
	    InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
	    BufferedReader br = new BufferedReader(isr);

		String tempstring = br.readLine();
		String[] locationstring = tempstring.split(" ");
		ArrayList<Integer> locationint = new ArrayList<Integer>();
			
		for(int i=0;i<locationstring.length;++i)
			locationint.add(Integer.valueOf(locationstring[i]));
		
		br.close();
		isr.close();
		in.close();
		
		input = new File("secondstep/Pairs"+String.valueOf(id1)+".txt");
		in = new FileInputStream(input);
	    isr = new InputStreamReader(in,"US-ASCII");
	    br = new BufferedReader(isr);

		tempstring = br.readLine();
		locationstring = tempstring.split(", ");
//		HashSet<Integer> hassimilar1 = new HashSet<Integer>();
		ArrayList<Integer> hassimilar1 = new ArrayList<Integer>();
		for(int i=0;i<locationstring.length;++i)
			hassimilar1.add(Integer.valueOf(locationstring[i]));
		
		br.close();
		isr.close();
		in.close();
		
		input = new File("secondstep/Pairs"+String.valueOf(id2)+".txt");
		in = new FileInputStream(input);
	    isr = new InputStreamReader(in,"US-ASCII");
	    br = new BufferedReader(isr);

		tempstring = br.readLine();
		locationstring = tempstring.split(", ");
//		HashSet<Integer> hassimilar2 = new HashSet<Integer>();
		ArrayList<Integer> hassimilar2 = new ArrayList<Integer>();
		for(int i=0;i<locationstring.length;++i)
			hassimilar2.add(Integer.valueOf(locationstring[i]));
		
		br.close();
		isr.close();
		in.close();
		
		
//		int locationsize = locationint.size()-1;
		String mystring1 = new String();
		String mystring2 = new String();
		int x=0;
		int y=0;
		
			
		for(int i=0;i<hassimilar1.size();++i){
			
			x = hassimilar1.get(i);
			System.out.println(String.valueOf(x) + "-" + String.valueOf(hassimilar1.size() - i));
			
			for(int j=0;j<hassimilar2.size();++j)
				{
					y = hassimilar2.get(j);
					if(y >= locationint.size())
					{
						hassimilar2.remove(j);
						break;
					}
					r.seek(Integer.valueOf(locationint.get(x)));
					mystring1 = r.readLine();
					r.seek(Integer.valueOf(locationint.get(y)));
					mystring2 = r.readLine();
					if(mystring1 == null || mystring2 == null)
					{
						hassimilar2.remove(j);
						break;
					}
					String mystring = SyntCluster.getsimilarity(SyntCluster.getset(mystring1),SyntCluster.getset(mystring2));
					if(mystring != null)
					{
						similaritywriter.write(String.valueOf(x)+","+String.valueOf(y)+";"+mystring+"\n");
						hassimilar2.remove(j);
						break;
					}
				}
			}
			similaritywriter.close();
			System.out.println(String.valueOf(id1)+"finished");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
