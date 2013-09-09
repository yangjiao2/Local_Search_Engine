package ir.assignment.three.searching;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class SearchProvider {
	
	
    public static void main (String[] args) throws Exception{
    	Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
    	
    	File file = new File("googlesearch.txt");
    	FileWriter writer = new FileWriter(file,true);
    	final String[] searchquerylist = {"mondego", "machine learning", "software engineering","security","student affairs",
    		"graduate courses","Crista Lopes","REST","computer games","information retrieval"};
    	
        for (int i=0; i <searchquerylist.length; i++){
        	try {
                com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(searchquerylist[i]);
                list.setKey("AIzaSyCv98Fl_EXUTs5SPY3b5I8YNsUEfMkcexg");
                list.setCx("009833342735470745061:d15nlklheke");
  
                Search results = list.execute();
                List<Result> items = results.getItems();
                
                writer.write(searchquerylist[i]+'\n'+'\n');
                
                if(items!=null){                	
                	for(Result result:items)
                	{
                		writer.write("URL: "+result.getLink()+'\n');
                	}
                }
                
                writer.write('\n'+"=========================="+'\n');
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    	
        writer.close();
    }
}
