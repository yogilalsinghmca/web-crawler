package com.sc.googlesearch;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoolgeSearchResultURLExtractorTest {

    @Autowired
    GoolgeSearchResultURLExtractor urlExtractor;
    
    @Test
    public void shouldExtractURLs() throws IOException{
        String query = "scala language";
        List<String> urls = urlExtractor.extractGoogleSearchResultURL(query);
        System.out.println(urls);
        assertTrue(!urls.isEmpty());
    }
}
