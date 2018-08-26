package com.sc.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopFivePopularLibraryFinder {

    public static List<String> findTopNLibraries(List<String> librariesList, int topNLib) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String libraryName : librariesList) {
            if (map.get(libraryName) != null) {
                map.put(libraryName, map.get(libraryName) + 1);
            } else {
                map.put(libraryName, 1);
            }
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());

        Collections.sort(entries, (e1, e2) -> {
            return -e1.getValue().compareTo(e2.getValue());
        });
        List<String> topNLibraries = new ArrayList<>();

        int count = entries.size() > topNLib ? topNLib : entries.size();
        for (int i = 0; i < count; i++) {
            Map.Entry<String, Integer> e = entries.get(i);
            topNLibraries.add(e.getKey());
        }
        return topNLibraries;
    }       
}
