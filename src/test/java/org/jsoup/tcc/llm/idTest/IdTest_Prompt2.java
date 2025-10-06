package org.jsoup.tcc.llm.idTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdTest_Prompt2 {

    // Tests for id() getter

    @Test
    void id_shouldReturnIdAttribute_whenElementHasId() {
        // Arrange
        String html = "<div id=\"main\">Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("main", result, "should return the id attribute value");
    }

    @Test
    void id_shouldReturnEmptyString_whenElementHasNoId() {
        // Arrange
        String html = "<div class=\"container\">Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("", result, "should return empty string when no id attribute");
    }

    @Test
    void id_shouldBeCaseInsensitive_whenGettingIdAttribute() {
        // Arrange
        String html = "<div ID=\"caseTest\">Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        String result = element.id();

        // Assert
        assertEquals("caseTest", result, "should get id attribute case insensitively");
    }

    // Tests for id(String) setter

    @Test
    void id_shouldSetIdAttribute_whenValidIdProvided() {
        // Arrange
        String html = "<div>Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        Element result = element.id("newId");

        // Assert
        assertEquals("newId", element.id(), "should set the id attribute");
        assertSame(element, result, "should return this for method chaining");
    }

    @Test
    void id_shouldOverwriteExistingId_whenElementAlreadyHasId() {
        // Arrange
        String html = "<div id=\"oldId\">Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        element.id("newId");

        // Assert
        assertEquals("newId", element.id(), "should overwrite existing id attribute");
    }

    @Test
    void id_shouldAcceptEmptyString_whenSettingId() {
        // Arrange
        String html = "<div id=\"oldId\">Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act
        element.id("");

        // Assert
        assertEquals("", element.id(), "should accept empty string as id");
    }

    @Test
    void id_shouldThrowException_whenNullIdProvided() {
        // Arrange
        String html = "<div>Content</div>";
        Element element = Jsoup.parse(html).selectFirst("div");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> element.id(null),
                "should throw exception for null id");
    }
}