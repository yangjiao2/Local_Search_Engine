package ir.assignments.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.regex.Pattern;
/**
 * The main class of bonus question
 * 
 */
public class SyntCluster {
	
	public final static double threthold1 = 0.5; // the threshold of resemblance and containment
	public	final static double threthold2 = 0.5;
	public final static double threthold3 = 0.5;
	
	public HashSet<String> getShingle(String mystring)
	{
		HashSet<String> myset = new HashSet<String>();
		String[] mytemp = Pattern.compile(" ").split(mystring);
		for(int i=0;i<mytemp.length-4;++i)
			myset.add(mytemp[i]+" "+mytemp[i+1]+" "+mytemp[i+2]+" "+mytemp[i+3]);
//		System.out.println(String.valueOf(myset.size()));
		return myset;
	
	}
	/**
	 *  compute the similarity of two shingle sets
	 * 
	 */
	public static String getsimilarity(HashSet<String> set1, HashSet<String> set2)
	{
		double oldsize1 = set1.size();
		double oldsize2 = set2.size();
		set1.removeAll(set2);
		double newsize = oldsize1 - set1.size();
		double resemblance = newsize/(oldsize1+oldsize2-newsize);
		double containment1 = newsize/oldsize1;
		double containment2 = newsize/oldsize2;
		DecimalFormat    df   = new DecimalFormat("######0.00");
//		System.out.println(String.valueOf(oldsize2));
//		System.out.println(String.valueOf(resemblance));
//		System.out.println(String.valueOf(containment1));
//		System.out.println(String.valueOf(containment2));
		if(resemblance > threthold1 || containment1 > threthold2 || containment2 > threthold3)
			return String.valueOf(df.format(resemblance))+" "+String.valueOf(df.format(containment1))+" "+String.valueOf(df.format(containment2));
		return null;
	}
	
	/**
	 *  get shingles set from a string
	 * 
	 */
	public static HashSet<String> getset(String mystring)
	{
		HashSet<String> myset = new HashSet<String>();
		String[] temp = Pattern.compile(" ").split(mystring);
		for(int i=0;i<temp.length;++i)
		{
			myset.add(temp[i]);
		}
		return myset;
	}
	
	/**
	 *  the main function of all the steps.
	 * 
	 */
	public static void main(String[] args) throws IOException{
		
		File input = new File("Sketch.txt");
		InputStream in = new FileInputStream(input);
        InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
        BufferedReader br = new BufferedReader(isr);
        
       
        
        File newfile =new File("Shingle.txt");
		FileWriter writer = new FileWriter(newfile,true);
		
		File newfile1 =new File("Location.txt");
		FileWriter writer1 = new FileWriter(newfile1,true);
		

//		writer1.write("0 ");
//		while((temp = br.readLine()) != null)
//		{
//			myset = mycluster.getShingle(temp);
//			temp = myset.toString();
//			writer.write(temp.substring(1, temp.length()-1)+"\n");
//			location += temp.length()-1;
//			writer1.write(String.valueOf(location)+" ");
//		}  
		writer.close();
		writer1.close();
		br.close();
		isr.close();
		in.close();
		
//		RandomAccessFile r = new RandomAccessFile("Shingle.txt","r");
//		
//		input = new File("Location.txt");
//		in = new FileInputStream(input);
//        isr = new InputStreamReader(in,"US-ASCII");
//        br = new BufferedReader(isr);
//		String tempstring = br.readLine();
//		String[] locationstring = tempstring.split(" ");
//		ArrayList<Integer> locationint = new ArrayList<Integer>();
//		HashSet<Integer> hassimilar = new HashSet<Integer>();
//		for(int i=0;i<locationstring.length;++i)
//			locationint.add(Integer.valueOf(locationstring[i]));
//		int locationsize = locationint.size()-1;
//		String mystring1 = new String();
//		String mystring2 = new String();
//		File similarityfile =new File("Similarity0.txt");
//		FileWriter similaritywriter = new FileWriter(similarityfile,true);
//		
//		for(int i=0;i<locationsize&&i<1000;++i){
//			System.out.println(String.valueOf(i));
//			for(int j=0;j<i;++j)
//			{
//				if(hassimilar.contains(i)||hassimilar.contains(j))
//					continue;
//				r.seek(Integer.valueOf(locationint.get(i)));
//				mystring1 = r.readLine();
//				r.seek(Integer.valueOf(locationint.get(j)));
//				mystring2 = r.readLine();
//				String mystring = mycluster.getsimilarity(mycluster.getset(mystring1),mycluster.getset(mystring2));
//				if( mystring != null)
//				{
//					similaritywriter.write(String.valueOf(i)+","+String.valueOf(j)+";"+mystring+"\n");
//					hassimilar.add(j);
//				}
//			}
//		}
//		similaritywriter.close();
		
		Thread mythread1 = new Thread(new ClusterThread2(0,1));
		Thread mythread2 = new Thread(new ClusterThread2(2,3));
		Thread mythread3 = new Thread(new ClusterThread2(4,5));
		Thread mythread4 = new Thread(new ClusterThread2(6,7));
		Thread mythread5 = new Thread(new ClusterThread2(8,9));
		Thread mythread6 = new Thread(new ClusterThread2(10,11));
		Thread mythread7 = new Thread(new ClusterThread2(12,13));
		Thread mythread8 = new Thread(new ClusterThread2(14,15));
		Thread mythread9 = new Thread(new ClusterThread2(16,17));
		Thread mythread10 = new Thread(new ClusterThread2(18,19));
//		Thread mythread11 = new Thread(new ClusterThread2(20,21));
//		Thread mythread12 = new Thread(new ClusterThread2(22,23));
//		Thread mythread13 = new Thread(new ClusterThread2(24,25));
////		Thread mythread14 = new Thread(new ClusterThread2(50,51));
////		Thread mythread15 = new Thread(new ClusterThread2(52,53));
//		
		mythread1.start();
		mythread2.start();
		mythread3.start();
		mythread4.start();
		mythread5.start();
		mythread6.start();
		mythread7.start();
		mythread8.start();
		mythread9.start();
		mythread10.start();
//		mythread11.start();
//		mythread12.start();
//		mythread13.start();
////		mythread14.start();
////		mythread15.start();
		
//		Thread mythread1 = null;
//		for(int i = 0; i<20;++i)
//		{
//		mythread1 = new Thread(new ClusterFilterTh(i));
//		mythread1.start();
//		}
// 		System.out.println(mystring1);
//		mycluster.getsimilarity(mycluster.getset(mystring1),mycluster.getset(mystring2));
	}
}
