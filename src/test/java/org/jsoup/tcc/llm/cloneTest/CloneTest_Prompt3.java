package org.jsoup.tcc.llm.cloneTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloneTest_Prompt3 {

    @Test
    void clone_shouldCreateIdenticalDocumentWithSameContent() {
        // Arrange
        String html = "<html><head><title>Test</title></head><body><p>Content</p></body></html>";
        Document original = Jsoup.parse(html);

        // Act
        Document clone = original.clone();

        // Assert
        assertTrue(original.hasSameValue(clone), "Clone should have identical HTML content");
        assertNotSame(original, clone, "Clone should be a different object instance");
    }

    @Test
    void clone_shouldCreateIndependentOutputSettings() {
        // Arrange
        Document original = Jsoup.parse("<div>Test</div>");
        Document clone = original.clone();

        // Act - modify clone's output settings
        clone.outputSettings().prettyPrint(false);

        // Assert
        assertTrue(original.outputSettings().prettyPrint(),
                "Original output settings should remain unchanged");
        assertFalse(clone.outputSettings().prettyPrint(),
                "Clone output settings should reflect modifications");
    }

    @Test
    void clone_shouldReferenceSameParserInstance() {
        // Arrange
        Document original = Jsoup.parse("<p>Test</p>");

        // Act
        Document clone = original.clone();

        // Assert
        assertSame(original.parser(), clone.parser(),
                "Clone should reference the same parser instance");
    }

    @Test
    void clone_shouldCreateIndependentDocumentStructure() {
        // Arrange
        Document original = Jsoup.parse("<div id=\"parent\"><span>Child</span></div>");
        Document clone = original.clone();

        // Act - modify clone's structure
        clone.selectFirst("span").text("Modified");

        // Assert
        assertEquals("Child", original.selectFirst("span").text(),
                "Original document should remain unchanged");
        assertEquals("Modified", clone.selectFirst("span").text(),
                "Clone should reflect modifications");
    }

    @Test
    void clone_shouldHandleEmptyDocument() {
        // Arrange
        Document original = Jsoup.parse("");

        // Act
        Document clone = original.clone();

        // Assert
        assertTrue(original.hasSameValue(clone),
                "Empty document clone should have identical content");
        assertEquals(original.outerHtml(), clone.outerHtml(),
                "Empty documents should produce identical outer HTML");
    }
}