package com.sc;

import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.sc.googlesearch.GoolgeSearchResultURLExtractor;
import com.sc.googlesearch.JavaScriptLibExtractor;
import com.sc.utility.HTMLFileDownLoader;
import com.sc.utility.TopFivePopularLibraryFinder;

@SpringBootApplication
public class WebCrawlerApplication {

       
	public static void main(String[] args) throws IOException {
	    ApplicationContext context = SpringApplication.run(WebCrawlerApplication.class, args);
	    
	    if(args[0]== null || args[0] == ""){
	        throw new RuntimeException("Please provide search string on argument");
	    }
	 // step 1. get google search result main URL links
	    GoolgeSearchResultURLExtractor urlExtractor = context.getBean("goolgeSearchResultURLExtractor",GoolgeSearchResultURLExtractor.class);
        List<String> urls = urlExtractor.extractGoogleSearchResultURL(args[0]);
        
        // step 2. get the pages from URLs and extract used libraries in the page
        JavaScriptLibExtractor libExtractor = context.getBean("javaScriptLibExtractor",JavaScriptLibExtractor.class);
        List<Document> htmlDocs = HTMLFileDownLoader.downlaodHTLFiles(urls);
        
        // step 3. download html files from web       
        List<String> libraries = libExtractor.GetJavaScriptLibraries(htmlDocs);
        System.out.println(libraries);
        //Step 5 get top five used libraries
        
        List<String> topFiveLib = TopFivePopularLibraryFinder.findTopNLibraries(libraries, 5);
        // print top 5 libraries on console 
        System.out.println("---------TOP 5 Libraries------ ");
        System.out.println(topFiveLib);
	}
}
