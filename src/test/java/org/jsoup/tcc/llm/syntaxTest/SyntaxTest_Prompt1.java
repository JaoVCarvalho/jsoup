package org.jsoup.tcc.llm.syntaxTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SyntaxTest_Prompt1 {

    @Test
    void syntaxSetterChangesSyntaxToHtml() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        outputSettings.syntax(Document.OutputSettings.Syntax.html);

        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }

    @Test
    void syntaxSetterChangesSyntaxToXml() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        assertEquals(Document.OutputSettings.Syntax.xml, outputSettings.syntax());
    }

    @Test
    void syntaxSetterReturnsOutputSettingsForChaining() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        Document.OutputSettings result = outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        assertSame(outputSettings, result);
    }

    @Test
    void syntaxGetterReturnsDefaultHtmlSyntax() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        Document.OutputSettings.Syntax syntax = outputSettings.syntax();

        assertEquals(Document.OutputSettings.Syntax.html, syntax);
    }

    @Test
    void syntaxSetterWithXmlChangesEscapeModeToXhtml() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        assertEquals(Entities.EscapeMode.xhtml, outputSettings.escapeMode());
    }

    @Test
    void syntaxSetterWithHtmlDoesNotChangeEscapeMode() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();
        Entities.EscapeMode originalEscapeMode = outputSettings.escapeMode();

        outputSettings.syntax(Document.OutputSettings.Syntax.html);

        assertEquals(originalEscapeMode, outputSettings.escapeMode());
    }

    @Test
    void syntaxSetterWithXmlThenHtmlPreservesEscapeMode() {
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        outputSettings.syntax(Document.OutputSettings.Syntax.xml);
        outputSettings.syntax(Document.OutputSettings.Syntax.html);

        assertEquals(Entities.EscapeMode.xhtml, outputSettings.escapeMode());
    }
}