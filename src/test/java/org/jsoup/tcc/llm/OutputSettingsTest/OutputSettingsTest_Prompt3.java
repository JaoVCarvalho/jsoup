package org.jsoup.tcc.llm.OutputSettingsTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class OutputSettingsTest_Prompt3 {

    @Test
    void outputSettings_shouldReturnNonNullSettings() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings settings = doc.outputSettings();

        // Assert
        assertNotNull(settings, "output settings should not be null");
    }

    @Test
    void charset_shouldReturnUtf8ByDefault() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Charset charset = doc.outputSettings().charset();

        // Assert
        assertEquals(StandardCharsets.UTF_8, charset, "default charset should be UTF-8");
    }

    @Test
    void charset_withValidName_shouldUpdateCharsetAndAllowChaining() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        // Act
        Document.OutputSettings returnedSettings = settings.charset("ISO-8859-1");

        // Assert
        assertEquals(Charset.forName("ISO-8859-1"), settings.charset());
        assertSame(settings, returnedSettings, "should return this for chaining");
    }

    @Test
    void escapeMode_shouldReturnBaseByDefault() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");

        // Act
        Entities.EscapeMode escapeMode = doc.outputSettings().escapeMode();

        // Assert
        assertEquals(Entities.EscapeMode.base, escapeMode, "default escape mode should be base");
    }

    @Test
    void escapeMode_withValidMode_shouldUpdateModeAndAllowChaining() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        // Act
        Document.OutputSettings returnedSettings = settings.escapeMode(Entities.EscapeMode.xhtml);

        // Assert
        assertEquals(Entities.EscapeMode.xhtml, settings.escapeMode());
        assertSame(settings, returnedSettings, "should return this for chaining");
    }

    @Test
    void charsetChange_shouldAffectHtmlOutputEncoding() {
        // Arrange
        Document doc = Jsoup.parse("<html><body>café</body></html>");
        String originalHtml = doc.html();

        // Act
        doc.outputSettings().charset(StandardCharsets.ISO_8859_1);
        String newHtml = doc.html();

        // Assert
        assertNotEquals(originalHtml, newHtml, "HTML output should differ after charset change");
    }

    @Test
    void escapeModeChange_shouldAffectEntityEscapingInOutput() {
        // Arrange
        Document doc = Jsoup.parse("<html><body>&</body></html>");
        Document.OutputSettings settings = doc.outputSettings();

        // Act & Assert - Verify different escape modes produce different outputs
        settings.escapeMode(Entities.EscapeMode.base);
        String baseOutput = doc.body().html();

        settings.escapeMode(Entities.EscapeMode.xhtml);
        String xhtmlOutput = doc.body().html();

        settings.escapeMode(Entities.EscapeMode.extended);
        String extendedOutput = doc.body().html();

        // At least two outputs should differ
        assertTrue(!baseOutput.equals(xhtmlOutput) ||
                        !baseOutput.equals(extendedOutput) ||
                        !xhtmlOutput.equals(extendedOutput),
                "different escape modes should produce different outputs");
    }
}