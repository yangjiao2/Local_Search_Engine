package ir.assignment.three.searching;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class URLProcess {
	
	
	static final String fileforurl = "url.txt";
	static final String fileforurlindex = "urlindex.txt";
	static final String fileforsequentialurl = "sequentialurl.txt";
	static final String fileforlurlrelation = "urlrelation.txt";
	
	public static void main(String args[]) throws Exception{
		
	File file1 = new File(fileforurlindex);
	FileInputStream fis = new FileInputStream(file1);
	InputStreamReader isr = new  InputStreamReader (fis,"US-ASCII");
	BufferedReader br = new BufferedReader(isr);
	String temp = br.readLine();
	String[] indexlist = temp.split(" "); //indexlist for storing url index
		
	fis.close();
	isr.close();
	br.close();
	
	FileWriter writer1 = new FileWriter(new File(fileforsequentialurl), true);
	FileWriter writer2 = new FileWriter(new File(fileforlurlrelation ), true);
	InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fileforurl)));
	StringBuffer mybuffer = new StringBuffer();
	
	char tempchar= 0;
	int tempcount = 0, tempoffset=-1; 
	int docid1 = 0,docid2 = 0,docid3 = 0;
	boolean allowtoappend=false, writetag=false;

	ArrayList<Integer> indexforurl = new ArrayList<Integer>(); 
	ArrayList<Integer> indexforparenturl = new ArrayList<Integer>(); 
	ArrayList<Integer> indexforoutlinks = new ArrayList<Integer>(); 
	
	HashMap<Integer, String> mapforidurl = new HashMap<Integer, String>(); // docid_ url pair
	HashMap<Integer, String> mapforidurlparent = new HashMap<Integer, String>(); // docid_ urlparent pair
	HashMap<Integer, String> mapforidoutlinks = new HashMap<Integer,  String>(); // docid_ outlinks pair
	
	indexforurl.add(0); 
	indexforurl.add(Integer.parseInt(indexlist[0])-1);
	indexforparenturl.add(Integer.parseInt(indexlist[0])+1);
	indexforparenturl.add(Integer.parseInt(indexlist[1])-1);
	indexforoutlinks.add(Integer.parseInt(indexlist[1])+1);
	
	int temptag=0, tempint=-1 , test=0;
	
	for(int i=1; i<indexlist.length-2; i++){//last ? not count
		if(indexlist[i].equals("?")){
			System.out.println(test++);
			if(tempint<=Integer.parseInt(indexlist[i-1])-1){
				temptag++;
				indexforoutlinks.add(Integer.parseInt(indexlist[i-1])-1);
			}//end of outlink
			
			else{
				indexforoutlinks.remove(temptag);
				indexforoutlinks.add(temptag,-2);
				temptag++;
				indexforoutlinks.add(temptag,-2);
			}
			indexforurl.add(Integer.parseInt(indexlist[i-1])+1);// start of url
			indexforurl.add(Integer.parseInt(indexlist[i+1])-1);// end of url
			indexforparenturl.add(Integer.parseInt(indexlist[i+1])+1);// start of parenturl
			indexforparenturl.add(Integer.parseInt(indexlist[i+2])-1);// end of parenturl
			indexforoutlinks.add(Integer.parseInt(indexlist[i+2])+1);
			temptag++;//start of outlink(not including last ?)
			tempint=Integer.parseInt(indexlist[i+2])+1;			
		}
	}
	
	indexforoutlinks.add(Integer.parseInt(indexlist[indexlist.length-1-1])-1);
	
	int tempcount2=0,tempcount3=0;
	boolean allowtoappend2=false, writetag2=false;
	boolean allowtoappend3=false, writetag3=false;
	StringBuffer mybuffer2 = new StringBuffer();
	StringBuffer mybuffer3 = new StringBuffer();
	
	//write url sequentially to file, one line one url
	while((tempchar=(char)reader.read())!=-1){
		tempoffset++;
		
		if(tempcount<indexforurl.size() && indexforurl.get(tempcount)==tempoffset ){
			if(tempcount%2==1){
				allowtoappend =false;
				writetag=true;
			}
			else if (tempcount%2==0)
				allowtoappend =true;
			tempcount++;
		} //url
		
		if(allowtoappend||writetag)
			mybuffer.append(tempchar);
		
		if(writetag){
			writer1.write(mybuffer.toString()+"\n");
			mapforidurl.put(docid1, mybuffer.toString());
			mybuffer.delete(0, mybuffer.length());
			writetag=false;		
			docid1++;
		} // if satisfy end index, write url to file 
		
		
		if(((tempcount2<indexforparenturl.size()&&indexforparenturl.get(tempcount2)==tempoffset)
				||(tempcount2-1>=0 && tempcount2<indexforparenturl.size()&&
				indexforparenturl.get(tempcount2)==indexforparenturl.get(tempcount2-1))))//for " " case start index=end
		{
			if(tempcount2%2==1){
				allowtoappend2 =false;
				writetag2=true;
			}
			
			else if (tempcount2%2==0)
				allowtoappend2 =true;
			tempcount2++;		
		}//parent url
		
		
		
		if(allowtoappend2||writetag2)
			if(tempcount2==1||indexforparenturl.get(tempcount2-2)!=indexforparenturl.get(tempcount2-1))
			mybuffer2.append(tempchar);
			
		
		if(writetag2){
			mapforidurlparent.put(docid2, mybuffer2.toString());
			mybuffer2.delete(0, mybuffer2.length());
			writetag2=false;
			docid2++;
			// if satisfy end index, write urlparent to map			
		}
	
		if(tempcount3<indexforoutlinks.size()){
			if((indexforoutlinks.get(tempcount3)==tempoffset
					||(tempcount3-1>=0 && tempcount3<indexforoutlinks.size()&&
					indexforoutlinks.get(tempcount3)==indexforoutlinks.get(tempcount3-1)))||indexforoutlinks.get(tempcount3)==-2)
			{
				if(indexforoutlinks.get(tempcount3)!=-2){
					if(tempcount3%2==1){
						allowtoappend3 =false;
						writetag3=true;
					}
			
					else if (tempcount3%2==0)
						allowtoappend3 =true;
					tempcount3++;	
					}
				
				else if(indexforoutlinks.get(tempcount3)==-2){
					tempcount3=tempcount3+2;
					mapforidoutlinks.put(docid3," "); // not outgoing links just present " "
					docid3++;
				}//case for not including outlinks
					
			}//outlinks		
		}
		
		if(allowtoappend3||writetag3)
			if(tempcount3==1||indexforoutlinks.get(tempcount3-2)!=indexforoutlinks.get(tempcount3-1))
			mybuffer3.append(tempchar);
		
		if(writetag3){
			mapforidoutlinks.put(docid3, mybuffer3.toString());	
			mybuffer3.delete(0, mybuffer3.length());
			docid3++;
			writetag3=false;
			// if satisfy end index, write urlparent to map			
		}
		
		if(tempcount3==indexforoutlinks.size())
			break;
		
	}
	
//	System.out.println("docid"+docid1);
//	
//	System.out.println("mapforidoutlinks"+mapforidoutlinks.size());
//	System.out.println("indexforparenturl"+indexforparenturl.size());
//	System.out.println("indexforurl"+indexforurl.size());
	writer1.close();
	reader.close();
	String tempstring = "";
	String[] tempstr=null;

	
	//output docid to represent relationship between each url
	for(int i=0;i<docid1;i++){
		ArrayList<Integer> listforsort = new ArrayList<Integer>();
		boolean wt = true; //space tag for mapforidoutlinks
//		boolean spacewritetag = true;
		System.out.println(i);
//		tempstring=i+" ";
//		for(Entry<Integer, String> element :mapforidurl.entrySet()){
//			if(element.getValue().equals(mapforidurlparent.get(i))){
//				tempstring=tempstring+element.getKey()+" ";
//				spacewritetag = false;
//				break;
//			}
//		}		
//		if(spacewritetag)
//			tempstring=tempstring+" "+" ";	
		for(Entry<Integer, String> element :mapforidurl.entrySet()){
			if(mapforidoutlinks.get(i).equals(" "))
				break;
			
			else{
				tempstr=mapforidoutlinks.get(i).split(" ");
				for(int j=0;j<tempstr.length;j++){
					if(element.getValue().equals(tempstr[j])){
						listforsort.add(element.getKey());
						wt= false;
					}
				}
			}
		}
		
		if(wt)
			tempstring=tempstring+" "+" ";
		
		Collections.sort(listforsort);
		
		for(Integer element: listforsort)
			tempstring=tempstring+element+" ";
		
		writer2.write(tempstring+'\n');	
		tempstring="";
	}
	
	writer2.close();
	}
	
	
}
