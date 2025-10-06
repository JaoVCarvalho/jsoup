package org.jsoup.tcc.llm.OutputSettingsTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class OutputSettingsTest_Prompt2 {

    @Test
    void outputSettings_shouldReturnNonNullOutputSettings() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings outputSettings = doc.outputSettings();

        // Assert
        assertNotNull(outputSettings, "OutputSettings should not be null");
    }

    @Test
    void outputSettings_shouldReturnSameInstanceAfterModification() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings initialSettings = doc.outputSettings();

        // Act
        doc.outputSettings().charset(StandardCharsets.ISO_8859_1);
        Document.OutputSettings afterModification = doc.outputSettings();

        // Assert
        assertSame(initialSettings, afterModification,
                "Should return the same OutputSettings instance after modification");
    }

    @Test
    void charset_shouldReturnUtf8ByDefault() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Charset charset = doc.outputSettings().charset();

        // Assert
        assertEquals(StandardCharsets.UTF_8, charset,
                "Default charset should be UTF-8");
    }

    @Test
    void charset_shouldReturnConfiguredCharset() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        doc.outputSettings().charset(StandardCharsets.ISO_8859_1);
        Charset charset = doc.outputSettings().charset();

        // Assert
        assertEquals(StandardCharsets.ISO_8859_1, charset,
                "Should return the configured charset");
    }

    @Test
    void charsetWithString_shouldSetCharsetAndReturnThisForChaining() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings returnedSettings = doc.outputSettings().charset("ISO-8859-1");

        // Assert
        assertSame(doc.outputSettings(), returnedSettings,
                "Should return this for method chaining");
        assertEquals(StandardCharsets.ISO_8859_1, doc.outputSettings().charset(),
                "Should set the specified charset");
    }

    @Test
    void charsetWithString_shouldThrowExceptionForInvalidCharset() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> doc.outputSettings().charset("INVALID_CHARSET_NAME"),
                "Should throw IllegalArgumentException for invalid charset name");
    }

    @Test
    void escapeMode_shouldReturnBaseByDefault() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Entities.EscapeMode escapeMode = doc.outputSettings().escapeMode();

        // Assert
        assertEquals(Entities.EscapeMode.base, escapeMode,
                "Default escape mode should be 'base'");
    }

    @Test
    void escapeMode_shouldReturnAllAvailableEscapeModes() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act & Assert for base
        doc.outputSettings().escapeMode(Entities.EscapeMode.base);
        assertEquals(Entities.EscapeMode.base, doc.outputSettings().escapeMode(),
                "Should return 'base' escape mode");

        // Act & Assert for xhtml
        doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        assertEquals(Entities.EscapeMode.xhtml, doc.outputSettings().escapeMode(),
                "Should return 'xhtml' escape mode");

        // Act & Assert for extended
        doc.outputSettings().escapeMode(Entities.EscapeMode.extended);
        assertEquals(Entities.EscapeMode.extended, doc.outputSettings().escapeMode(),
                "Should return 'extended' escape mode");
    }

    @Test
    void escapeModeWithParameter_shouldSetEscapeModeAndReturnThisForChaining() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings returnedSettings = doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);

        // Assert
        assertSame(doc.outputSettings(), returnedSettings,
                "Should return this for method chaining");
        assertEquals(Entities.EscapeMode.xhtml, doc.outputSettings().escapeMode(),
                "Should set the specified escape mode");
    }

    @Test
    void escapeModeChanges_shouldAffectHtmlOutput() {
        // Arrange
        Document doc = Jsoup.parse("<div>&</div>");
        String expectedBase = "&amp;";
        String expectedXhtml = "&amp;";

        // Act & Assert for base mode
        doc.outputSettings().escapeMode(Entities.EscapeMode.base);
        String baseOutput = doc.body().html();
        assertTrue(baseOutput.contains(expectedBase),
                "Base escape mode should escape ampersand");

        // Act & Assert for xhtml mode
        doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        String xhtmlOutput = doc.body().html();
        assertTrue(xhtmlOutput.contains(expectedXhtml),
                "XHTML escape mode should escape ampersand");
    }
}