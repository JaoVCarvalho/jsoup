package org.jsoup.tcc.llm.titleTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest_Prompt1 {

    @Test
    void title_returnsEmptyStringWhenNoTitleElement() {
        Document doc = Jsoup.parse("<html><head></head></html>");
        String result = doc.title();
        assertEquals("", result);
    }

    @Test
    void title_returnsTrimmedTitleWhenTitleElementExists() {
        Document doc = Jsoup.parse("<html><head><title>   Test Title   </title></head></html>");
        String result = doc.title();
        assertEquals("Test Title", result);
    }

    @Test
    void title_returnsNormalizedWhitespaceTitle() {
        Document doc = Jsoup.parse("<html><head><title>Test   Title   with   spaces</title></head></html>");
        String result = doc.title();
        assertEquals("Test Title with spaces", result);
    }

    @Test
    void title_setTitleOnDocumentWithoutTitleElement() {
        Document doc = Jsoup.parse("<html><head></head></html>");
        doc.title("New Title");
        assertEquals("New Title", doc.title());
    }

    @Test
    void title_setTitleOnDocumentWithExistingTitleElement() {
        Document doc = Jsoup.parse("<html><head><title>Old Title</title></head></html>");
        doc.title("Updated Title");
        assertEquals("Updated Title", doc.title());
    }

    @Test
    void title_setTitleWithNullThrowsException() {
        Document doc = Jsoup.parse("<html><head></head></html>");
        assertThrows(IllegalArgumentException.class, () -> doc.title(null));
    }

    @Test
    void title_setTitleAndVerifyElementStructure() {
        Document doc = Jsoup.parse("<html><head></head></html>");
        doc.title("Test Title");
        Element titleEl = doc.head().selectFirst("title");
        assertNotNull(titleEl);
        assertEquals("Test Title", titleEl.text());
    }

    @Test
    void title_setTitleUpdatesExistingTitleElementText() {
        Document doc = Jsoup.parse("<html><head><title>Original</title></head></html>");
        doc.title("Modified");
        Element titleEl = doc.head().selectFirst("title");
        assertEquals("Modified", titleEl.text());
    }
}