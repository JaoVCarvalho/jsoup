package org.jsoup.tcc.llm.locationTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest_Prompt2 {

    @Test
    void location_shouldReturnEmptyString_whenDocumentParsedFromString() {
        // Arrange
        String html = "<html><body>Test content</body></html>";

        // Act
        Document doc = Jsoup.parse(html);

        // Assert
        assertEquals("", doc.location(),
                "Location should be empty when document is parsed from string");
    }

    @Test
    void location_shouldReturnBaseUri_whenDocumentParsedWithBaseUri() {
        // Arrange
        String html = "<html><body>Test content</body></html>";
        String baseUri = "https://example.com";

        // Act
        Document doc = Jsoup.parse(html, baseUri);

        // Assert
        assertEquals(baseUri, doc.location(),
                "Location should return the base URI used during parsing");
    }

    @Test
    void baseUri_shouldReturnEmptyString_whenElementHasNoBaseUriInHierarchy() {
        // Arrange
        String html = "<div><p>Test paragraph</p></div>";
        Document doc = Jsoup.parse(html);
        Element paragraph = doc.selectFirst("p");

        // Act
        String baseUri = paragraph.baseUri();

        // Assert
        assertEquals("", baseUri,
                "Base URI should be empty when no element in hierarchy has base URI defined");
    }

    @Test
    void baseUri_shouldReturnParentBaseUri_whenElementInheritsFromParent() {
        // Arrange
        String html = "<div><p>Test paragraph</p></div>";
        String baseUri = "https://parent-example.com";
        Document doc = Jsoup.parse(html, baseUri);
        Element paragraph = doc.expectFirst("p");

        // Act
        String resultBaseUri = paragraph.baseUri();

        // Assert
        assertEquals(baseUri, resultBaseUri,
                "Element should inherit base URI from parent document");
    }

    @Test
    void baseUri_shouldReturnElementBaseUri_whenElementHasOwnBaseUriAttribute() {
        // Arrange
        String html = "<html><body><div baseuri='https://custom-base.com'><p>Test</p></div></body></html>";
        Document doc = Jsoup.parse(html, "https://default-base.com");
        Element div = doc.expectFirst("div");

        // Act
        String baseUri = div.baseUri();

        // Assert
        assertEquals("https://custom-base.com", baseUri,
                "Element should return its own base URI when defined via attribute");
    }

    @Test
    void baseUri_shouldReturnEmptyString_whenElementHasEmptyBaseUriAttribute() {
        // Arrange
        String html = "<div baseuri=''><p>Test paragraph</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.expectFirst("div");

        // Act
        String baseUri = div.baseUri();

        // Assert
        assertEquals("", baseUri,
                "Base URI should return empty string when element has empty baseuri attribute");
    }
}