package org.jsoup.tcc.llm.locationTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest_Prompt1 {

    @Test
    void location_ReturnsEmptyStringWhenParsedFromString() {
        Document doc = Jsoup.parse("<html></html>");
        String result = doc.location();
        assertEquals("", result);
    }

    @Test
    void location_ReturnsFinalUrlWhenParsedFromUrl() {
        Document doc = Jsoup.parse("<html></html>", "https://example.com");
        String result = doc.location();
        assertEquals("https://example.com", result);
    }

    @Test
    void baseUri_ReturnsEmptyStringWhenNoBaseUri() {
        Element element = new Element("div");
        String result = element.baseUri();
        assertEquals("", result);
    }

    @Test
    void baseUri_ReturnsBaseUriWhenSet() {
        Document doc = Jsoup.parse("<html><head><base href='https://base.com'></head><body><div></div></body></html>");
        Element div = doc.selectFirst("div");
        String result = div.baseUri();
        assertEquals("https://base.com", result);
    }

    @Test
    void baseUri_ReturnsParentBaseUriWhenAvailable() {
        Document doc = Jsoup.parse("<html><head><base href='https://parent.com'></head><body><div><span></span></div></body></html>");
        Element span = doc.selectFirst("span");
        String result = span.baseUri();
        assertEquals("https://parent.com", result);
    }

    @Test
    void baseUri_ReturnsEmptyStringForOrphanElement() {
        Element element = new Element("p");
        String result = element.baseUri();
        assertEquals("", result);
    }

    @Test
    void baseUri_ReturnsBaseUriFromMetaTag() {
        Document doc = Jsoup.parse("<html><head><base href='https://meta.com'></head><body><a></a></body></html>");
        Element a = doc.selectFirst("a");
        String result = a.baseUri();
        assertEquals("https://meta.com", result);
    }

    @Test
    void location_PersistsAfterDocumentModification() {
        Document doc = Jsoup.parse("<html></html>", "https://persist.com");
        doc.appendElement("div");
        String result = doc.location();
        assertEquals("https://persist.com", result);
    }

    @Test
    void baseUri_UpdatesAfterChangingBaseElement() {
        Document doc = Jsoup.parse("<html><head><base href='https://old.com'></head><body><p></p></body></html>");
        doc.selectFirst("base").attr("href", "https://new.com");
        Element p = doc.selectFirst("p");
        String result = p.baseUri();
        assertEquals("https://new.com", result);
    }
}