package com.sc.googlesearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * Extract JavaScript Library from list of HTML pages improve performance by using ExecutorService
 * 
 * @author yogilal List<Document> htmlDocuments
 */
@Component("javaScriptLibExtractor")
public class JavaScriptLibExtractor {

    public List<String> GetJavaScriptLibraries(List<Document> htmlDocuments) {
        List<String> libraries = new ArrayList<>();
        if (htmlDocuments == null || htmlDocuments.isEmpty()) {
            return libraries;
        }

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(cores);
        CompletionService<List<String>> completionService =
                new ExecutorCompletionService<>(executorService);

        try {
            int taskCount = 0;
            // submit task to get libraries from HTML file
            for (Document htmlFile : htmlDocuments) {
                completionService.submit(() -> GetJavaScriptLibrariesContainIn(htmlFile));
                taskCount++;
            }

            // Obtain results from tasks as they complete
            for (int i = 0; i < taskCount; i++) {
                try {
                    Future<List<String>> future = completionService.take();
                    List<String> librariesSublist = future.get();
                    libraries.addAll(librariesSublist);
                    // System.out.println("Done with [" + (i + 1) + " out of " + taskCount
                    // + "] parallel tasks!\n");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    System.out.println(
                            "Failed to process some html file. Please check earlier log messages for details! "
                                    + e);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(
                    "Failed to process some html file. Please check earlier log messages for details!"
                            + e);
        } finally {
            executorService.shutdown();
        }
        return libraries;
    }

    public List<String> GetJavaScriptLibrariesContainIn(Document htmldoc) {
        List<String> javascriptLibNames = new ArrayList<>();

        Elements scriptTags = htmldoc.getElementsByTag("script");
        String javaScriptLibraryName = null;
        for (Element scriptTag : scriptTags) {
            String javaScriptURL = scriptTag.attr("src");

            javaScriptLibraryName = getJavaScriptLibFileNamefrom(javaScriptURL);

            if (!javaScriptLibraryName.isEmpty()) {
                javascriptLibNames.add(javaScriptLibraryName);
            }
        }
        return javascriptLibNames;
    }

    private String getJavaScriptLibFileNamefrom(String javaScriptURL) {
        String javaScriptLibraryName = "";
        if (!javaScriptURL.isEmpty()) {
            try {
                int backSlashStartIndex = javaScriptURL.lastIndexOf("/");
                int questionMarkStartIndex = javaScriptURL.indexOf("?");
                // if char '?' and '/' present extract JavaScript file name this may happen if URL
                // contain JavaScript library version as well
                // example
                // 'http://allaboutscala.com/wp-content/themes/TESSERACT/js/helpers-beaver.js?ver=1.0.0'
                if (backSlashStartIndex > 0 && questionMarkStartIndex > 0 && backSlashStartIndex < questionMarkStartIndex) {
                    javaScriptLibraryName =
                            javaScriptURL.substring(backSlashStartIndex + 1, questionMarkStartIndex);
                } else if (backSlashStartIndex > 0 && backSlashStartIndex > questionMarkStartIndex) {
                    //for scenario https://scala.com/wp-content/plugins/bwp-minify/min/?f=wp-includes/js/underscore.min.js
                    javaScriptLibraryName = javaScriptURL.substring(backSlashStartIndex + 1);
                } else {
                    // javaScriptURL is simple file name
                    javaScriptLibraryName = javaScriptURL;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("malfunctioned htmp file url--:" + javaScriptURL + " ERROR Detail:" + e);
            }
        }
        // ignore invalid file name
        if (!javaScriptLibraryName.endsWith(".js")) {
            javaScriptLibraryName = "";
        }
        return javaScriptLibraryName;
    }
}
