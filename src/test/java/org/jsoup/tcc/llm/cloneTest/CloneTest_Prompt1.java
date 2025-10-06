package org.jsoup.tcc.llm.cloneTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloneTest_Prompt1 {

    @Test
    void cloneDocumentShouldCreateEqualDocument() {
        String html = "<html><body><div>Test</div></body></html>";
        Document original = Jsoup.parse(html);
        Document clone = original.clone();

        assertNotSame(original, clone);
        assertTrue(original.hasSameValue(clone));
    }

    @Test
    void cloneDocumentShouldHaveSameOutputSettings() {
        Document original = Jsoup.parse("<p>Test</p>");
        Document clone = original.clone();

        assertEquals(original.outputSettings().prettyPrint(),
                clone.outputSettings().prettyPrint());
    }

    @Test
    void cloneDocumentShouldHaveSameParser() {
        Document original = Jsoup.parse("<div>Content</div>");
        Document clone = original.clone();

        assertEquals(original.parser(), clone.parser());
    }

    @Test
    void clonedDocumentModificationShouldNotAffectOriginal() {
        Document original = Jsoup.parse("<div id='test'>Original</div>");
        Document clone = original.clone();

        Element originalDiv = original.selectFirst("#test");
        Element cloneDiv = clone.selectFirst("#test");

        cloneDiv.attr("id", "modified");

        assertEquals("test", originalDiv.attr("id"));
        assertEquals("modified", cloneDiv.attr("id"));
    }

    @Test
    void cloneDocumentWithAttributesShouldCreateSeparateCopy() {
        String html = "<div data-key='value'>Content</div>";
        Document original = Jsoup.parse(html);
        Document clone = original.clone();

        Element originalEl = original.selectFirst("div");
        Element cloneEl = clone.selectFirst("div");

        cloneEl.attr("data-key", "changed");

        assertEquals("value", originalEl.attr("data-key"));
        assertEquals("changed", cloneEl.attr("data-key"));
    }

    @Test
    void cloneEmptyDocumentShouldWork() {
        Document original = Jsoup.parse("");
        Document clone = original.clone();

        assertNotNull(clone);
        assertTrue(original.hasSameValue(clone));
    }

    @Test
    void cloneDocumentWithMultipleElementsShouldMaintainStructure() {
        String html = "<html><head><title>Test</title></head><body><p>Para1</p><p>Para2</p></body></html>";
        Document original = Jsoup.parse(html);
        Document clone = original.clone();

        assertEquals(original.html(), clone.html());
    }
}