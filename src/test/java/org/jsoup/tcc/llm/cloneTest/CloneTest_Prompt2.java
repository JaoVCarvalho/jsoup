package org.jsoup.tcc.llm.cloneTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloneTest_Prompt2 {

    @Test
    void clone_shouldCreateIdenticalDocumentWithSameContent() {
        // Arrange
        String html = "<html><head><title>Test</title></head><body><p>Content</p></body></html>";
        Document original = Jsoup.parse(html);

        // Act
        Document clone = original.clone();

        // Assert
        assertTrue(original.hasSameValue(clone), "Clone should have same content as original document");
        assertNotSame(original, clone, "Clone should be different object instance");
    }

    @Test
    void clone_shouldCreateIndependentAttributesInstance() {
        // Arrange
        Document original = Jsoup.parse("<div id='test' class='container'>Content</div>");
        original.attr("data-custom", "value");

        // Act
        Document clone = original.clone();
        clone.attr("data-custom", "modified");

        // Assert
        assertEquals("value", original.attr("data-custom"),
                "Original attributes should not be affected by clone modification");
        assertEquals("modified", clone.attr("data-custom"),
                "Clone should have independent attributes");
    }

    @Test
    void clone_shouldCreateIndependentOutputSettings() {
        // Arrange
        Document original = Jsoup.parse("<p>Test</p>");
        boolean originalPrettyPrint = original.outputSettings().prettyPrint();

        // Act
        Document clone = original.clone();
        clone.outputSettings().prettyPrint(!originalPrettyPrint);

        // Assert
        assertEquals(originalPrettyPrint, original.outputSettings().prettyPrint(),
                "Original output settings should not be affected by clone modification");
        assertNotEquals(original.outputSettings().prettyPrint(), clone.outputSettings().prettyPrint(),
                "Clone should have independent output settings");
    }

    @Test
    void clone_shouldShareSameParserReference() {
        // Arrange
        Document original = Jsoup.parse("<div>Content</div>");

        // Act
        Document clone = original.clone();

        // Assert
        assertSame(original.parser(), clone.parser(),
                "Clone should share the same parser instance as original");
    }

    @Test
    void clone_shouldWorkWithEmptyDocument() {
        // Arrange
        Document original = Jsoup.parse("");

        // Act
        Document clone = original.clone();

        // Assert
        assertTrue(original.hasSameValue(clone),
                "Clone of empty document should have same content as original");
        assertNotNull(clone.selectFirst("html"),
                "Clone should maintain basic document structure");
    }

    @Test
    void clone_shouldMaintainComplexDocumentStructure() {
        // Arrange
        String html = "<html><body><div><p>Paragraph 1</p><p>Paragraph 2</p><ul><li>Item 1</li><li>Item 2</li></ul></div></body></html>";
        Document original = Jsoup.parse(html);

        // Act
        Document clone = original.clone();

        // Assert
        assertEquals(original.html(), clone.html(),
                "Clone should maintain identical HTML structure for complex documents");
        assertEquals(2, clone.select("p").size(),
                "Clone should maintain same number of paragraph elements");
        assertEquals(2, clone.select("li").size(),
                "Clone should maintain same number of list items");
    }

    @Test
    void clone_shouldCreateFullyIndependentDocumentInstance() {
        // Arrange
        Document original = Jsoup.parse("<main><section>Content</section></main>");
        String originalTitle = "Original Title";
        original.title(originalTitle);

        // Act
        Document clone = original.clone();
        String cloneTitle = "Clone Title";
        clone.title(cloneTitle);

        // Assert
        assertEquals(originalTitle, original.title(),
                "Original document title should remain unchanged");
        assertEquals(cloneTitle, clone.title(),
                "Clone document should have independent title");
        assertNotSame(original, clone,
                "Clone should be completely separate document instance");
    }
}