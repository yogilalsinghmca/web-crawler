package com.sc.utility;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.sc.utility.TopFivePopularLibraryFinder;

public class TopFivePopularLibraryFinderTest {
       
    @Test
    public  void shouldReturnTopFiveLibrary() {
        List<String> subList1 = new ArrayList<>();       
        subList1.add("analytics.js");
        subList1.add("analytics.js");
        subList1.add("analytics.js");
        subList1.add("analytics.js");
        subList1.add("analytics.js");
        subList1.add("jquery.js");
        subList1.add("jquery.js");
        subList1.add("jquery.js");
        subList1.add("jquery.js");
        subList1.add("adsbygoogle.js");
        subList1.add("highlight.min.js");       
        subList1.add("jquery.min.js");        
        subList1.add("jquery.min.js");
        subList1.add("jquery.min.js");       
        subList1.add("jquery-migrate.min.js");
        subList1.add("jquery-migrate.min.js");        
        subList1.add("main.js");
        subList1.add("main.js");
        subList1.add("main.js");
        List<String> topFiveLib = TopFivePopularLibraryFinder.findTopNLibraries(subList1,5);
        assertEquals("analytics.js", topFiveLib.get(0));
        assertEquals("jquery.js", topFiveLib.get(1));
        assertEquals("main.js", topFiveLib.get(2));
        assertEquals("jquery.min.js", topFiveLib.get(3));
        assertEquals("jquery-migrate.min.js", topFiveLib.get(4));
    }
}
