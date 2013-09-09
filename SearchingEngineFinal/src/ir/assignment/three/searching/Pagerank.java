package ir.assignment.three.searching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Pagerank {
	private static class parentoutpair{
		
		int docid=0;
		ArrayList<Integer> parentlists  = new ArrayList<Integer> ();
		ArrayList<Integer> outlists  = new ArrayList<Integer>  ();		
		
		private parentoutpair(){}
		
		private void setId(int docid){
			this.docid = docid;
		}
		
		private void setParentlists(ArrayList<Integer> parentlists){
			this.parentlists = parentlists;
		}
		
		private void setOutlists( ArrayList<Integer> outlists){
			this.outlists = outlists;
		}
		
		private ArrayList<Integer> getParentlists(){
			return parentlists;
		}
		
		private ArrayList<Integer> getOutlists(){
			return outlists;
		}
		
		
	}
	
	static final String fileforlurlrelation = "urlrelation.txt";
	static final double dampingfactor = 0.85, iter_max = 20, tolerance = 0.03;
	
	static HashMap<Integer, parentoutpair> pagerelation= new HashMap<Integer, parentoutpair>();
	static HashMap<Integer,ArrayList<Integer> > temprelation= new HashMap<Integer, ArrayList<Integer>>();
	
	public Pagerank() {
		
	}
	
	public double[] Calculatepagescore () throws Exception{
		
		/*Constructrelation*/
		
		File file1 = new File(fileforlurlrelation);
		FileInputStream fis = new FileInputStream(file1);
		InputStreamReader isr = new  InputStreamReader (fis,"US-ASCII");
		BufferedReader br = new BufferedReader(isr);
		String temp = "";
		int lineindex = 0;
		while((temp=br.readLine())!=null){
			parentoutpair pair= new parentoutpair();
			ArrayList<Integer> templist = new ArrayList<Integer>();
			String[] tp = temp.split(" ");
			for (int i=0; i<tp.length;i++)
				if(!tp[i].equals(" "))
					templist.add(Integer.parseInt(tp[i]));
				else 
					break;// in case that there is no outgoing links, outlist is empty
			
			pair.setId(lineindex);
			pair.setOutlists(templist);
			pair.setParentlists(new ArrayList<Integer>());
			pagerelation.put(lineindex, pair); // consturct pagerelation map
			
			temprelation.put(lineindex, templist);
			lineindex++;
		}
		
		br.close();
		
		for(Entry<Integer,ArrayList<Integer>> element : temprelation.entrySet()){
			ArrayList<Integer> ls = element.getValue();
			for(Integer iter : ls){
				if(pagerelation.containsKey(iter)){
					ArrayList<Integer> parent = pagerelation.get(iter).getParentlists();
					parent.add(element.getKey());
				}
			}
		} // update parent lists for each urlid by iterating all outgoing lists and update in increasing order because lists 
		  //have already been sorted in previous step
		
		temprelation.clear();
		
		/* Calculatepagescore*/
		double[] pagescore = new double[pagerelation.size()];
		double[] outlink_no_reverse = new double[pagerelation.size()]; // reverse of outlink# of page
		for(int i=0; i< outlink_no_reverse.length; i++){
			if(pagerelation.get(i).getOutlists().size()!=0)
				outlink_no_reverse[i]= 1.0/pagerelation.get(i).getOutlists().size();
			else
				outlink_no_reverse[i]=0;
		}
		
		Arrays.fill(pagescore, -1); //init
		
		int iter=0;
		while(iter<iter_max){
			for(int j=0; j<pagescore.length; j++){
				if(pagerelation.get(j).getParentlists().size()==0)
					pagescore[j]= (1-dampingfactor);
				
				else{
					pagescore[j]= (1-dampingfactor);
				for(int k=0; k<pagerelation.get(j).getParentlists().size(); k++)
					if(pagescore[pagerelation.get(j).getParentlists().get(k)]!=-1) // already been calculated
						pagescore[j]= pagescore[j] + dampingfactor*(pagescore[pagerelation.get(j).getParentlists().get(k)]*
								outlink_no_reverse[pagerelation.get(j).getParentlists().get(k)]);
					else
						pagescore[j]= pagescore[j] + dampingfactor* outlink_no_reverse[pagerelation.get(j).getParentlists().get(k)];
						//assign init value
				}
						// pagescore(A) = 1-dampfactor + dampfactor*(sum(pagescore(parent of A)/ outlink# of A))
			}
			iter++;
		}
		
		double max = -1;
		for(int i=0; i<pagescore.length; i++){
			if(pagescore[i]>max)
				max = pagescore[i];		
		}//find max value of pagescore list
		
		for(int i=0; i<pagescore.length; i++)
			 pagescore[i]=1+ pagescore[i]/max; //nomalize
		
		return pagescore;		
	}
	
	
}
