package com.sc.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class HTMLFileDownLoader {

    public static List<Document> downlaodHTLFiles(List<String> htmlFileURLs){
        List<Document> htmlDocuments = new ArrayList<>();
        for(String htmlURL :htmlFileURLs){
            try {
                Document doc = Jsoup.connect(htmlURL).get();
                htmlDocuments.add(doc);
            } catch (IOException e) {
               System.out.println("some error occured while downloading the file:" + htmlURL);
               continue;
            }
        }
        return htmlDocuments;
    }
}
