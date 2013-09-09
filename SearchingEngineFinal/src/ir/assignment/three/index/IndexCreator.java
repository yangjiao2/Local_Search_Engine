package ir.assignment.three.index;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


/** Index all text files under a directory.
 * <p>
 * This is a command-line application demonstrating simple Lucene indexing.
 * Run it with no command-line arguments for usage information.
 */
public class IndexCreator {
 
  static int indexforboostingscore = 0;
  private IndexCreator() {}
  static private ArrayList<String> urllist = new ArrayList<String>();
  static private ArrayList<String> pagelist = new ArrayList<String>();
 /** Index all text files under a directory. 
 * @throws Exception */
  public static void main(String[] args) throws Exception {
   String usage = "java org.apache.lucene.demo.IndexFiles"
                 + " [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\n"
                 + "This indexes the documents in DOCS_PATH, creating a Lucene index"
                 + "in INDEX_PATH that can be searched with SearchFiles";
//   String indexPath = "C://Users//sunxi//Desktop//search//index";
   String indexPath = "/home/jason/Downloads/319data/index";
   String docsPath = "/home/jason/Downloads/319data/page/";
   String urlPath = "/home/jason/Downloads/319data/sequentialurl.txt";
   String pagePath = "/home/jason/Downloads/319data/pagetitle.txt";
   
   File input = new File(urlPath);
   FileInputStream in = new FileInputStream(input);
   InputStreamReader isr = new InputStreamReader(in,"US-ASCII");
   BufferedReader br = new BufferedReader(isr);
   
   String temp = br.readLine();
   while(temp != null)
   {
	   urllist.add(temp);
	   temp = br.readLine();
   }
	br.close();
	isr.close();
	in.close();
	
		File input1 = new File(pagePath);
	   FileInputStream in1 = new FileInputStream(input1);
	   InputStreamReader isr1 = new InputStreamReader(in1,"US-ASCII");
	   BufferedReader br1 = new BufferedReader(isr1);
	   
	   String temp1 = br1.readLine();
	   while(temp1 != null)
	   {
		   pagelist.add(temp1);
		   temp1 = br1.readLine();
	   }
		br1.close();
		isr1.close();
		in1.close();
		
		
    boolean create = true;
    for(int i=0;i<args.length;i++) {
      if ("-index".equals(args[i])) {
        indexPath = args[i+1];
        i++;
      } else if ("-docs".equals(args[i])) {
        docsPath = args[i+1];
        i++;
      } else if ("-update".equals(args[i])) {
        create = false;
      }
   }

    if (docsPath == null) {
      System.err.println("Usage: " + usage);
      System.exit(1);
    }
    
    int indexforfile=-1;
    final File docDirtemp = new File(docsPath);
    if (!docDirtemp.exists() || !docDirtemp.canRead()) {
     System.out.println("Document directory '" +docDirtemp.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
      System.exit(1);
    }
    int filelength = docDirtemp.isDirectory() ? docDirtemp.list().length : 0;
   
    Date start = new Date();
    try {
      System.out.println("Indexing to directory '" + indexPath + "'...");

      Directory dir = FSDirectory.open(new File(indexPath));
      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_41, analyzer);

      if (create) {
       // Create a new index in the directory, removing any
        iwc.setOpenMode(OpenMode.CREATE);
      } else {
        // Add new documents to an existing index:
       iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
      }

     // Optional: for better indexing performance, if you
      // are indexing many documents, increase the RAM
     // buffer.  But if you do this, increase the max heap
      // size to the JVM (eg add -Xmx512m or -Xmx1g):
     //
      // iwc.setRAMBufferSizeMB(256.0);

      IndexWriter writer = new IndexWriter(dir, iwc);
      
      while(indexforfile<filelength){
    	    File docDir = new File(docsPath+indexforfile+".txt");
    	    indexforfile++;
    	   
    	    indexDocs(writer, docDir, indexforfile);  	    
      }

      // NOTE: if you want to maximize search performance,
     // you can optionally call forceMerge here.  This can be
     // a terribly costly operation, so generally it's only
      // worth it when your index is relatively static (ie
      // you're done adding documents to it):
      //
      // writer.forceMerge(1);

      writer.close();

      Date end = new Date();
      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

   } catch (IOException e) {
      System.out.println(" caught a " + e.getClass() +
       "\n with message: " + e.getMessage());
    }
  }

 /**
   * Indexes the given file using the given writer, or if a directory is given,
   * recurses over files and directories found under the given directory.
   * 
  * NOTE: This method indexes one document per input file.  This is slow.  For good
   * throughput, put multiple documents into your input file(s).  An example of this is
  * in the benchmark module, which can create "line doc" files, one document per line,
  * using the
  * <a href="../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
   * >WriteLineDocTask</a>.
   *  
   * @param writer Writer to the index where the given file/dir info will be stored
   * @param file The file to index, or the directory to recurse into to find files to index
   * @throws IOException If there is a low-level I/O error
   */
  
  
  static void indexDocs(IndexWriter writer, File file) throws IOException
  {
	  if (file.isDirectory()) {
	        String[] files = file.list();
	        // an IO error could occur
	        if (files != null) {
	         for (int i = 0; i < files.length; i++) {
	           indexDocs(writer, new File(file, files[i]), i);
	          }
	        }
	      } 
  }
  
  
  static void indexDocs(IndexWriter writer, File file ,int nub)
    throws IOException {
    // do not try to index files that cannot be read
    if (file.canRead()) {
      {

        FileInputStream fis;
       try {
          fis = new FileInputStream(file);
        } catch (FileNotFoundException fnfe) {
          // at least on windows, some temporary files raise this exception with an "access denied" message
          // checking if the file can be read doesn't help
          return;
        }

        try {

          // make a new, empty document
         Document doc = new Document();

         // Add the path of the file as a field named "path".  Use a
          // field that is indexed (i.e. searchable), but don't tokenize 
         // or positional information:
         Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
         doc.add(pathField);

         doc.add(new StringField("docid", String.valueOf(nub), Field.Store.YES));
         
         doc.add(new StringField("url", urllist.get(nub-1).toString(), Field.Store.YES));
         
         doc.add(new StringField("title", pagelist.get(nub-1).toString(), Field.Store.YES));
         
         // Add the last modified date of the file a field named "modified".
          // Use a LongField that is indexed (i.e. efficiently filterable with
          // NumericRangeFilter).  This indexes to milli-second resolution, which
          // is often too fine.  You could instead create a number based on
         // year/month/day/hour/minutes/seconds, down the resolution you require.
          // For example the long value 2011021714 would mean
          // February 17, 2011, 2-3 PM.
         doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));

         // Add the contents of the file to a field named "contents".  Specify a Reader,
          // Note that FileReader expects the file to be in UTF-8 encoding.
          // If that's not the case searching for special characters will fail.
          
         doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
          
          if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
            // New index, so we just add the document (no old document can be there):
            System.out.println("adding " + file);
           writer.addDocument(doc);
         } else {
            // Existing index (an old copy of this document may have been indexed) so 
            // we use updateDocument instead to replace the old one matching the exact 
           // path, if present:
            System.out.println("updating " + file);
            writer.updateDocument(new Term("path", file.getPath()), doc);
          }
          
       } finally {
         fis.close();
       }
      }
    }
  }
}
