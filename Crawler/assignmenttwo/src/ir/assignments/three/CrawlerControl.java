package ir.assignments.three;


import java.util.Timer;
import java.util.TimerTask;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * this is for configuration of crawlers that visiting websites
 *
 */
public class CrawlerControl{
	static int mytime = 0;
	static Boolean runing = true;
	public Timer timer;
	class TimerTestTask extends TimerTask {     
        public void run() {     
        	mytime++;
            System.out.println("Executing Time is "+String.valueOf(mytime)+" seconds" );     
            if(!runing)
            	timer.cancel();
        }     
    }  
	private void settimer()
	{
		timer = new Timer();     
        timer.schedule(new TimerTestTask(),0, 1000);
        
	}

	 public static void main(String[] args) throws Exception {
         
         String crawlStorageFolder = "args";
         int numberOfCrawlers = Integer.parseInt("1");
         
         CrawlConfig config = new CrawlConfig();
         config.setCrawlStorageFolder(crawlStorageFolder);
         config.setUserAgentString("UCI IR crawler student_81646751(xs)_36088298(hc)");
         
         config.setPolitenessDelay(300);//delay time
         config.setResumableCrawling(false);
         
         //instantiate
         PageFetcher pageFetcher = new PageFetcher(config);
         RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
         RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
         CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
         
         CrawlerControl mycrawler = new CrawlerControl();
 		 mycrawler.settimer();
 		 
         //add seed
         controller.addSeed(args[0]);
         
         controller.start(Crawler.class, numberOfCrawlers);

         controller.start(Crawler.class, numberOfCrawlers);
         
         if(controller.isFinished())
         {
        	 mycrawler.timer.cancel();
        	 System.out.println("finished");
         }
	 }
         
}
