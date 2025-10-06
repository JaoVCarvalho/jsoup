package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {
    @Test
    public void testLocation() throws IOException {
        // tests location vs base href
        File in = ParseTest.getFile("/htmltests/basehref.html");
        Document doc = Jsoup.parse(in, "UTF-8", "http://example.com/");
        String location = doc.location();
        String baseUri = doc.baseUri();
        assertEquals("http://example.com/", location);
        assertEquals("https://example.com/path/file.html?query", baseUri);
        assertEquals("./anotherfile.html", doc.expectFirst("a").attr("href"));
        assertEquals("https://example.com/path/anotherfile.html", doc.expectFirst("a").attr("abs:href"));
    }

    @Test public void testLocationFromString() {
        Document doc = Jsoup.parse("<p>Hello");
        assertEquals("", doc.location());
    }
}
