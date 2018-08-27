package com.sc.googlesearch;

import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sc.utility.HTMLFileDownLoader;
import com.sc.utility.TopFivePopularLibraryFinder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebCrawlerApplicationIntTests {

    @Autowired
    GoolgeSearchResultURLExtractor urlExtractor;

    @Autowired
    private JavaScriptLibExtractor libExtractor;
    
    @Test
    public  void shouldReturnTopFiveLibrary() throws IOException  {
        String query = "scala language";
        // step 1. get google search result main URL links
        List<String> urls = urlExtractor.extractGoogleSearchResultURL(query);
        
        // step 2. get the pages from URLs and extract used libraries in the page
        List<Document> htmlDocs = HTMLFileDownLoader.downlaodHTMLFiles(urls);
        // step 3. download HTML files from web
       
        List<String> libraries = libExtractor.GetJavaScriptLibraries(htmlDocs);
        System.out.println(libraries);
        //Step 5 get top five used libraries
        
        List<String> topFiveLib = TopFivePopularLibraryFinder.findTopNLibraries(libraries,5);
        // print top 5 libraries on console 
        System.out.println(topFiveLib);
    }

}
