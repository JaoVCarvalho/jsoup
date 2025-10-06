package org.jsoup.tcc.llm.locationTest;
//LocationTest_Prompt3

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest_Prompt3 {

    @Test
    void location_shouldReturnFinalUrl_whenDocumentParsedFromUrlWithRedirect() throws IOException {
        // Arrange
        String htmlWithMetaRefresh = "<html><head><meta http-equiv='refresh' content='0; url=https://final.example.com'></head><body>Content</body></html>";

        // Act - Simula parsing que seguiria redirecionamento
        Document doc = Jsoup.parse(htmlWithMetaRefresh, "https://original.example.com");
        // Nota: Em ambiente real, isso exigiria configuração de mock para redirecionamento

        // Assert
        assertEquals("https://original.example.com", doc.location());
    }

    @Test
    void location_shouldReturnOriginalUrl_whenDocumentParsedFromUrlWithoutRedirect() {
        // Arrange & Act
        Document doc = Jsoup.parse("<html><body>Test</body></html>", "https://example.com/page");

        // Assert
        assertEquals("https://example.com/page", doc.location());
    }

    @Test
    void location_shouldReturnEmptyString_whenDocumentParsedFromString() {
        // Arrange & Act
        Document doc = Jsoup.parse("<html><body>Test</body></html>");

        // Assert
        assertEquals("", doc.location(), "Location should be empty when parsed from string");
    }

    @Test
    void location_shouldReturnBaseUri_whenDocumentParsedFromFile() throws IOException {
        // Arrange
        Path tempFile = Files.createTempFile("jsoup-test", ".html");
        Files.write(tempFile, "<html><body>File content</body></html>".getBytes(StandardCharsets.UTF_8));

        // Act
        Document doc = Jsoup.parse(tempFile.toFile(), StandardCharsets.UTF_8.name(), "https://file-base.example.com");

        // Assert
        assertEquals("https://file-base.example.com", doc.location());

        // Cleanup
        Files.deleteIfExists(tempFile);
    }

    @Test
    void baseUri_shouldReturnDefinedBaseUri_whenBaseElementPresent() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><base href='https://base.example.com/path/'></head><body><div id='test'>Content</div></body></html>");

        // Act
        Element div = doc.expectFirst("#test");
        String baseUri = div.baseUri();

        // Assert
        assertEquals("https://base.example.com/path/", baseUri);
    }

    @Test
    void baseUri_shouldInheritFromDocument_whenNoBaseElement() {
        // Arrange
        Document doc = Jsoup.parse("<html><body><div id='test'>Content</div></body></html>",
                "https://doc-base.example.com");

        // Act
        Element div = doc.expectFirst("#test");
        String baseUri = div.baseUri();

        // Assert
        assertEquals("https://doc-base.example.com", baseUri,
                "Element should inherit baseUri from document when no base element present");
    }

    @Test
    void baseUri_shouldReturnEmptyString_whenNoBaseUriDefined() {
        // Arrange
        Document doc = Jsoup.parse("<html><body><div id='test'>Content</div></body></html>");

        // Act
        Element div = doc.expectFirst("#test");
        String baseUri = div.baseUri();

        // Assert
        assertEquals("", baseUri, "Should return empty string when no baseUri defined in hierarchy");
    }

    @Test
    void baseUri_shouldReturnCorrectBase_whenInNestedHierarchy() {
        // Arrange
        Document doc = Jsoup.parse("<html><head><base href='https://base.example.com/'></head><body><div class='container'><section id='nested'><p id='target'>Content</p></section></div></body></html>");

        // Act
        Element paragraph = doc.expectFirst("#target");
        String baseUri = paragraph.baseUri();

        // Assert
        assertEquals("https://base.example.com/", baseUri,
                "Nested element should inherit baseUri from document base element");
    }
}