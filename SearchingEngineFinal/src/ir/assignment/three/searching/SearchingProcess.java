package ir.assignment.three.searching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchingProcess {
	final String fileforpagerank = "/home/jason/workspace/SearchingEngineFinal/pagerank.txt";
	ArrayList<Float>  scoreboost = new ArrayList<Float>(); // record pagerank boost factor
	
	public SearchingProcess()  {
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileforpagerank)), "UTF-8"));
			String s= "";
			while((s=buffer.readLine())!=null)
				scoreboost.add(Float.parseFloat(s));
				buffer.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	  
	  public static void main(String[] args) throws Exception {

//	      System.out.print(doPagingSearch("rest",0).returnresult());

	    
	  }

	  /**
	   * This demonstrates a typical paging search scenario, where the search engine presents 
	   * pages of size n to the user. The user can then go to the next page if interested in
	   * the next hits.
	   * 
	   * When the query is executed for the first time, then only enough results are collected
	   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
	   * is executed another time and all hits are collected.
	 * @throws ParseException 
	   * 
	   */
	  public Result doPagingSearch(String querystring, int page) throws IOException, ParseException {
	 
	    // Collect enough docs to show 5 pages

		    String index = "/home/jason/workspace/SearchingEngineFinal/index/";
		    String field = "contents";
		    
		    Result result = new Result();
		    
		    int repeat = 0;
//		    boolean raw = false;
		    int hitsPerPage = 20;
		    
		    
		    IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		    IndexSearcher searcher = new IndexSearcher(reader);
		    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);

		    BufferedReader in = null;
		    in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		    
		    QueryParser parser = new QueryParser(Version.LUCENE_41, field, analyzer);

		    String line = querystring;
		    line = line.trim();
		      
		    Query query = parser.parse(line);
//		    System.out.println("Searching for: " + query.toString(field));
		      
		    TopDocs results = searcher.search(query, hitsPerPage*(page+1));
		    ScoreDoc[] hitstemp = results.scoreDocs;
		    int lengthforlist = hitstemp.length;
		    ArrayList<ScoreDoc> list = new ArrayList<ScoreDoc>();
		    
		    for(int i=0; i<lengthforlist;i++){
		    	hitstemp[i].score =  hitstemp[i].score * scoreboost.get(hitstemp[i].doc);  // adding pagescore to result
		    	list.add(hitstemp[i]);
		    }
		    
		    Collections.sort(list, new Mycomparator()); // sort new list
		    
		    ScoreDoc[] hits = new ScoreDoc[lengthforlist]; //rebulit hits
		    
		    for(int i=0;i<hits.length;i++)
		    	hits[i] = list.get(i);

		    
		    int numTotalHits = results.totalHits;
//		    System.out.println(numTotalHits + " total matching documents");
	
		    int start = hitsPerPage*page;
		    int end = Math.min(numTotalHits, hitsPerPage*(page+1));
	        
	     
		      for (int i = start; i < end; i++) {
		                                     // output raw format
		        	 Document doc = searcher.doc(hits[i].doc);
		        	 String path = doc.get("path");
		        	    
			        if (path != null) {
//			        	File input = new File(path);
//			        	FileInputStream in1 = new FileInputStream(input);
//			        	InputStreamReader isr = new InputStreamReader(in1,"US-ASCII");
//			        	BufferedReader br = new BufferedReader(isr);
//			        	String mystring = br.readLine();
//			        	br.close();
//			        	isr.close();
//			        	in1.close();
			        	Charset charset = Charset.forName("GB18030");  
			            CharsetDecoder decoder = charset.newDecoder();  
			            String mystring = new String();
			        	FileInputStream fis = new FileInputStream(path);  
		                FileChannel fc = fis.getChannel();  
		                  
		                int sz = (int)fc.size();  
		                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);  
		                CharBuffer cb = decoder.decode(bb);  
		                String s = String.valueOf(cb);  
		                int n = s.indexOf(querystring);
		                if(n!=-1)
		                {
		                	if(s.length()>n+100)
		                		mystring = s.substring(n,n+99);
		                	else
		                		mystring = s.substring(n,s.length()-1);
		                }
		                else if(s.length()>n+100)
		                {
		                	mystring = s.substring(0,99);
		                }
		                else
		                {
		                	mystring = s.substring(0,s.length()-1);
		                }
		                fc.close();
		                fis.close();
		                
//			          System.out.println((i+1) + ". " + mystring);
			          String title = doc.get("title");
			          String url = doc.get("url");
			          if (url != null) {
//			            System.out.println("   url: " + url);
			          }
			          result.add(numTotalHits);
			          result.add(title, url, mystring);
			          
			        } else {
//			          System.out.println((i+1) + ". " + "No path for this document");
			        }
	                  
		      	}

	    reader.close();
	    return  result;
	  }
	  static class Mycomparator implements Comparator <ScoreDoc>{
			
		  public Mycomparator(){}
		  
		  public int compare(ScoreDoc doc1, ScoreDoc doc2) {
			if(doc1.score<=doc2.score)
				return 1;
			else 
				return -1; //increasing order
		}
	  }
}
