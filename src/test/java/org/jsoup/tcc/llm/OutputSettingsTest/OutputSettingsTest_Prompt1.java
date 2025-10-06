package org.jsoup.tcc.llm.OutputSettingsTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class OutputSettingsTest_Prompt1 {

    @Test
    void outputSettings_ReturnsCurrentOutputSettings() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings originalSettings = doc.outputSettings();

        assertNotNull(originalSettings);
        assertSame(originalSettings, doc.outputSettings());
    }

    @Test
    void charset_ReturnsDefaultCharset() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        assertEquals(StandardCharsets.UTF_8, settings.charset());
    }

    @Test
    void charset_WithString_SetsCharsetCorrectly() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        Document.OutputSettings returnedSettings = settings.charset("ISO-8859-1");

        assertEquals(Charset.forName("ISO-8859-1"), settings.charset());
        assertSame(settings, returnedSettings);
    }

    @Test
    void escapeMode_ReturnsDefaultEscapeMode() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        assertEquals(Entities.EscapeMode.base, settings.escapeMode());
    }

    @Test
    void escapeMode_WithEscapeMode_SetsEscapeModeCorrectly() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        Document.OutputSettings returnedSettings = settings.escapeMode(Entities.EscapeMode.xhtml);

        assertEquals(Entities.EscapeMode.xhtml, settings.escapeMode());
        assertSame(settings, returnedSettings);
    }

    @Test
    void charsetAndEscapeMode_ChainCorrectly() {
        Document doc = Jsoup.parse("<html></html>");
        Document.OutputSettings settings = doc.outputSettings();

        Document.OutputSettings result = settings.charset("UTF-16")
                .escapeMode(Entities.EscapeMode.extended);

        assertEquals(StandardCharsets.UTF_16, settings.charset());
        assertEquals(Entities.EscapeMode.extended, settings.escapeMode());
        assertSame(settings, result);
    }

    @Test
    void outputSettings_AreIndependentPerDocument() {
        Document doc1 = Jsoup.parse("<html></html>");
        Document doc2 = Jsoup.parse("<html></html>");

        Document.OutputSettings settings1 = doc1.outputSettings();
        Document.OutputSettings settings2 = doc2.outputSettings();

        assertNotSame(settings1, settings2);

        settings1.charset("ISO-8859-1");
        settings2.charset("UTF-16");

        assertEquals(Charset.forName("ISO-8859-1"), settings1.charset());
        assertEquals(StandardCharsets.UTF_16, settings2.charset());
    }
}