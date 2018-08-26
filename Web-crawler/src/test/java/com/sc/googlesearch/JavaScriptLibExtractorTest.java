package com.sc.googlesearch;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sc.utility.TopFivePopularLibraryFinder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptLibExtractorTest {
    
    @Autowired
    private JavaScriptLibExtractor libExtractor;

    @Test
    public void shouldExtractLib() throws Exception {
        File htmlFile1 = new File("src/test/java/com/sc/testdata/scala-tutorial_.html");
        File htmlFile2 = new File("src/test/java/com/sc/testdata/what-is-scala_.html");
        Document htmlDoc1 = Jsoup.parse(htmlFile1, "UTF-8", "");
        Document htmlDoc2 = Jsoup.parse(htmlFile2, "UTF-8", "");
        List<Document> htmlDocs = new ArrayList<>();
        htmlDocs.add(htmlDoc1);
        htmlDocs.add(htmlDoc2);
               
        List<String> libraries = libExtractor.GetJavaScriptLibraries(htmlDocs);
        System.out.println(libraries);
        assertTrue(!libraries.isEmpty());
    }
    
    @Test
    public void shouldExtractTopFiveLib() throws Exception {
        File htmlFile1 = new File("src/test/java/com/sc/testdata/scala-tutorial_.html");
        File htmlFile2 = new File("src/test/java/com/sc/testdata/what-is-scala_.html");
        File htmlFile3 = new File("src/test/java/com/sc/testdata/scala-language.html");
        File htmlFile4 = new File("src/test/java/com/sc/testdata/scala-lang.org.html");
        Document htmlDoc1 = Jsoup.parse(htmlFile1, "UTF-8", "");
        Document htmlDoc2 = Jsoup.parse(htmlFile2, "UTF-8", "");
        Document htmlDoc3 = Jsoup.parse(htmlFile3, "UTF-8", "");
        Document htmlDoc4 = Jsoup.parse(htmlFile4, "UTF-8", "");
        List<Document> htmlDocs = new ArrayList<>();
        htmlDocs.add(htmlDoc1);
        htmlDocs.add(htmlDoc2);
        htmlDocs.add(htmlDoc3);
        htmlDocs.add(htmlDoc4); 
        List<String> libraries = libExtractor.GetJavaScriptLibraries(htmlDocs);
        List<String> topFiveLib = TopFivePopularLibraryFinder.findTopNLibraries(libraries,5);
        assertEquals("jquery-migrate.min.js", topFiveLib.get(0));
        assertEquals("jquery.js", topFiveLib.get(1));
        assertEquals("comment-reply.min.js", topFiveLib.get(2));
        assertEquals("wp-embed.min.js", topFiveLib.get(3));
        assertEquals("front.min.js", topFiveLib.get(4));
    } 
}
