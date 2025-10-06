package org.jsoup.tcc.llm.titleTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest_Prompt2 {

    @Test
    void title_shouldReturnEmptyString_whenNoTitleElement() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head><body></body></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("", result, "Should return empty string when no title element exists");
    }

    @Test
    void title_shouldReturnNormalizedText_whenTitleHasExtraWhitespace() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>  Hello   World  </title></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("Hello World", result, "Should normalize whitespace in title text");
    }

    @Test
    void title_shouldReturnFirstTitle_whenMultipleTitleElements() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>First</title><title>Second</title></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("First", result, "Should return text from first title element");
    }

    @Test
    void title_shouldReturnEmptyString_whenTitleElementIsEmpty() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title></title></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("", result, "Should return empty string when title element is empty");
    }

    @Test
    void titleSet_shouldCreateTitleElement_whenNoTitleExists() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");

        // Act
        doc.title("New Title");

        // Assert
        assertEquals("New Title", doc.title(), "Should create new title element and set text");
        assertEquals(1, doc.head().select("title").size(), "Should have exactly one title element");
    }

    @Test
    void titleSet_shouldUpdateExistingTitle_whenTitleAlreadyExists() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>Old Title</title></head></html>");

        // Act
        doc.title("Updated Title");

        // Assert
        assertEquals("Updated Title", doc.title(), "Should update existing title element");
        assertEquals(1, doc.head().select("title").size(), "Should still have exactly one title element");
    }

    @Test
    void titleSet_shouldThrowException_whenTitleIsNull() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> doc.title(null),
                "Should throw exception when title is null");
    }

    @Test
    void titleSet_shouldSetEmptyTitle_whenTitleIsEmptyString() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");

        // Act
        doc.title("");

        // Assert
        assertEquals("", doc.title(), "Should set empty string as title");
    }

    @Test
    void titleSet_shouldPreserveWhitespace_whenTitleHasSpaces() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");
        String titleWithSpaces = "  Title  with  spaces  ";

        // Act
        doc.title(titleWithSpaces);

        // Assert
        assertEquals(titleWithSpaces, doc.selectFirst("title").text(),
                "Should preserve original whitespace in title element text");
        assertEquals("Title with spaces", doc.title(),
                "Getter should return normalized version");
    }

    @Test
    void titleGetAndSet_shouldBeConsistent() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");
        String expectedTitle = "Test Title";

        // Act
        doc.title(expectedTitle);
        String retrievedTitle = doc.title();

        // Assert
        assertEquals(expectedTitle, retrievedTitle,
                "Setter and getter should work consistently");
    }
}