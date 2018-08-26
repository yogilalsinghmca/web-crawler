package com.sc.googlesearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yogilal
 *
 */
@Component("goolgeSearchResultURLExtractor")
public class GoolgeSearchResultURLExtractor {

    @Value("${google.search.urlPrefix}")
    private  String urlPrefix;

    /**
     * @param query
     * @return list of JavaScript files/LibraryName URL
     * @throws IOException
     */
    public List<String> extractGoogleSearchResultURL(String query) throws IOException {
        final Document htmlDoc = Jsoup.connect(urlPrefix + query).get();
        List<String> urls = new ArrayList<>();
        // extract URLs from google search result HTML source file
        for (Element result : htmlDoc.select("h3.r a")) {
            urls.add(result.attr("href"));
        }
        return urls;
    }
}
