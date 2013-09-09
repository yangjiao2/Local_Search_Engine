package ir.assignments.three;

import java.util.ArrayList;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.List;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Crawler extends WebCrawler {
	
	static ArrayList <String> crawlweblist = new ArrayList<String>();
	
	//fiter URL containing downloadable link	
	private final static Pattern FILTERS1 = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
             + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
//	private final static Pattern FILTERS2 = Pattern.compile(".*(\\.ics\\.uci\\.edu).*");
	
	//define which urls crawler should visit (any sub domain of ics.uci.edu but not dynamic web contains ?)
	public boolean shouldVisit(WebURL url) {
		 String mystring = url.getURL().toLowerCase();
//       System.out.println(href);
//       String mystring = url.toLowerCase();
		if(!mystring.matches("http://.*\\.ics\\.uci\\.edu.*"))
			return false;
		if(mystring.matches(".*\\.java"))
			return false;
		if(mystring.matches(".*ftp\\.ics\\.uci\\.edu.*"))
			return false;
		if(mystring.matches(".*seraja\\.ics\\.uci\\.edu.*"))
			return false;
		if(mystring.matches(".*fano\\.ics\\.uci\\.edu.*"))
			return false;
		if(mystring.contains("?"))
			return false;
		if(mystring.matches(".*edu/.*\\.(html|htm|php|jsp)"))
			return true;
		if(mystring.matches(".*edu/[^\\.]*"))
			return true;
		
       return false;
	}
	
	 /**
     * This function is called when a page is fetched and ready to be processed
     */
 
    public void visit(Page page) {
    		StringBuffer buffer = new StringBuffer();//buffer for writing in logtext
    		
    		int docid = page.getWebURL().getDocid();
    		
            String url = page.getWebURL().getURL();
            crawlweblist.add(url); //add url in crawlweblist 
//          System.out.println(crawlweblist); //test
    	
            String domain = page.getWebURL().getDomain();
            String path = page.getWebURL().getPath();
            String subDomain = page.getWebURL().getSubDomain();
            
            String domainforstore = subDomain.replaceAll("(.*)(\\.ics$)", "$1$2\\.uci.edu");

            String parentUrl = page.getWebURL().getParentUrl();
            
            System.out.println("Docid: " + docid); //test
            buffer.append("Docid: " + docid+'\n');
            buffer.append("URL: " + url+'\n');
            buffer.append("Domain: '" + domain + "'"+'\n');
            buffer.append("Sub-domain: '" + domainforstore + "'"+'\n');
            buffer.append("Path: '" + path + "'"+'\n');
            buffer.append("Parent page: " + parentUrl+'\n');
            buffer.append("Time: " + CrawlerControl.mytime+'\n');
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
 
                    String text = htmlParseData.getText();
                    buffer.append("Text length: " + text.length()+'\n');

                    	try {
                    		WritePageTextToFile(text);
                    		SketchToFile(text);
                    	} catch (IOException e) {
                    		e.printStackTrace();
                    	}

                    
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();

                    buffer.append("Html length: " + html.length()+'\n');
                    buffer.append("Number of outgoing links: " + links.size()+'\n');
            }

            buffer.append("============="+'\n');
            try {
				CrawlLogToFile(buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}//write crawl log
            
     }
    
	//generate txt file that store the text content of visited websites
	public void WritePageTextToFile(String input) throws IOException{
		File newfile =new File("Pagetext.txt");
		FileWriter writer = new FileWriter(newfile,true);
		writer.write(input);
		writer.close();
	}
	
	//generate txt file that store CrawlLog
	public void CrawlLogToFile(String input) throws IOException{
		File newfile =new File("CrawlLog.txt");
		FileWriter writer = new FileWriter(newfile,true);
		writer.write(input);
		writer.close();
	}
	
	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 * 
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
     * @throws Exception 
	 */
	public static ArrayList <String> crawl(String seedURL) throws Exception {
		String [] input ={seedURL};
		CrawlerControl.main(input);
		return crawlweblist;
	}
	
	public static void main(String[] args) throws Exception{
		String seed = "http://www.ics.uci.edu/"; //for test input your seed here
		ArrayList<String> list = Crawler.crawl(seed);
		for(String element: list)
			System.out.print(element); // when finished, show url list
	}
	//generate txt file that store the sketch of webpages
			public void SketchToFile(String input) throws IOException{
				File newfile =new File("Sketch.txt");
				FileWriter writer = new FileWriter(newfile,true);
				String mystring = new String(input);
				mystring = mystring.toLowerCase();
				mystring = mystring.replaceAll(" \'", " ");
				mystring = mystring.replaceAll("\' ", " ");
				mystring = mystring.replaceAll(" -* ", " ");
				mystring = mystring.replaceAll("\\n", " ");
				mystring = mystring.replaceAll("[^a-z 0-9 ' -]+?", " ");
				mystring = mystring.replaceAll(" +", " ");
				if(mystring.length()>2000){
					int length = mystring.length()/2;
					mystring = mystring.substring(length, length+1000);
				}
				else if(mystring.length()>1000)
					mystring = mystring.substring(0, 1000);
				if(mystring.length()>0)
					writer.write(mystring+"\n");
				writer.close();
			}
			
}
