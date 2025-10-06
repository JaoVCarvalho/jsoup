package org.jsoup.tcc.llm.idTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdTest_Prompt3 {

    @Test
    void id_shouldReturnId_whenElementHasIdAttribute() {
        // Arrange
        String html = "<div id=\"main-content\"></div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("main-content", result);
    }

    @Test
    void id_shouldReturnEmptyString_whenElementHasNoIdAttribute() {
        // Arrange
        String html = "<div class=\"container\"></div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("", result);
    }

    @Test
    void id_shouldReturnEmptyString_whenElementHasNoAttributes() {
        // Arrange
        String html = "<div></div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("", result);
    }

    @Test
    void idWithParameter_shouldSetIdAndReturnElementInstance() {
        // Arrange
        String html = "<div></div>";
        Element element = Jsoup.parse(html).selectFirst("div");
        String newId = "test-id";

        // Act
        Element result = element.id(newId);

        // Assert
        assertSame(element, result, "should return same instance for chaining");
        assertEquals(newId, element.id(), "should set the id attribute");
    }

    @Test
    void idWithParameter_shouldReplaceExistingId() {
        // Arrange
        String html = "<div id=\"old-id\"></div>";
        Element element = Jsoup.parse(html).selectFirst("div");
        String newId = "new-id";

        // Act
        element.id(newId);

        // Assert
        assertEquals(newId, element.id());
    }

    @Test
    void idWithParameter_shouldThrowException_whenIdIsNull() {
        // Arrange
        String html = "<div></div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> element.id(null));
    }
}