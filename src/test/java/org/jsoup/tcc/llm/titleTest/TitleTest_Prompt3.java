package org.jsoup.tcc.llm.titleTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest_Prompt3 {

    @Test
    void title_shouldReturnEmptyString_whenNoTitleElement() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("", result, "should return empty string when no title element exists");
    }

    @Test
    void title_shouldReturnTrimmedContent_whenTitleHasWhitespace() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>  Hello World  </title></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("Hello World", result, "should return trimmed title content");
    }

    @Test
    void title_shouldReturnFirstTitle_whenMultipleTitleElements() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>First</title><title>Second</title></head></html>");

        // Act
        String result = doc.title();

        // Assert
        assertEquals("First", result, "should use first title element when multiple exist");
    }

    @Test
    void titleSet_shouldCreateNewTitleElement_whenNoTitleExists() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");
        String newTitle = "New Title";

        // Act
        doc.title(newTitle);

        // Assert
        assertEquals(newTitle, doc.title(), "should create and set title when none exists");
        assertEquals(1, doc.head().select("title").size(), "should have exactly one title element");
    }

    @Test
    void titleSet_shouldUpdateExistingTitle_whenTitleAlreadyExists() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><title>Old Title</title></head></html>");
        String newTitle = "Updated Title";

        // Act
        doc.title(newTitle);

        // Assert
        assertEquals(newTitle, doc.title(), "should update existing title content");
        assertEquals(1, doc.head().select("title").size(), "should maintain single title element");
    }

    @Test
    void titleSet_shouldPreserveWhitespaceInContent() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");
        String titleWithSpaces = "  Title With  Spaces  ";

        // Act
        doc.title(titleWithSpaces);

        // Assert
        assertEquals(titleWithSpaces, doc.selectFirst("title").text(),
                "should preserve exact whitespace in title element content");
    }

    @Test
    void titleSet_shouldThrowException_whenTitleIsNull() {
        // Arrange
        Document doc = Jsoup.parse("<html><head></head></html>");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> doc.title(null),
                "should throw exception when title is null");
    }
}