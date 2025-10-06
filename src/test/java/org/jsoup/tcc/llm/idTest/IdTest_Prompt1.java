package org.jsoup.tcc.llm.idTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class IdTest_Prompt1 {

    @Test
    void id_returnsEmptyStringWhenNoIdAttribute() {
        Document doc = Jsoup.parse("<div>Test</div>");
        Element element = doc.selectFirst("div");
        String result = element.id();
        assertEquals("", result);
    }

    @Test
    void id_returnsIdAttributeWhenPresent() {
        Document doc = Jsoup.parse("<div id=\"testId\">Test</div>");
        Element element = doc.selectFirst("div");
        String result = element.id();
        assertEquals("testId", result);
    }

    @Test
    void id_returnsIdAttributeCaseInsensitive() {
        Document doc = Jsoup.parse("<div ID=\"caseTest\">Test</div>");
        Element element = doc.selectFirst("div");
        String result = element.id();
        assertEquals("caseTest", result);
    }

    @Test
    void id_setIdAttributeOnElement() {
        Document doc = Jsoup.parse("<div>Test</div>");
        Element element = doc.selectFirst("div");
        Element result = element.id("newId");
        assertEquals("newId", element.id());
        assertSame(element, result);
    }

    @Test
    void id_setIdAttributeOverwritesExisting() {
        Document doc = Jsoup.parse("<div id=\"oldId\">Test</div>");
        Element element = doc.selectFirst("div");
        element.id("newId");
        assertEquals("newId", element.id());
    }

    @Test
    void id_setIdAttributeReturnsElementForChaining() {
        Document doc = Jsoup.parse("<div>Test</div>");
        Element element = doc.selectFirst("div");
        Element returnedElement = element.id("chainId");
        assertSame(element, returnedElement);
    }

    @Test
    void id_setThenGetIdAttribute() {
        Document doc = Jsoup.parse("<span>Test</span>");
        Element element = doc.selectFirst("span");
        element.id("test123");
        String retrievedId = element.id();
        assertEquals("test123", retrievedId);
    }
}