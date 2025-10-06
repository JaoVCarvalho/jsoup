package org.jsoup.tcc.llm.syntaxTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SyntaxTest_Prompt3 {

    @Test
    void syntax_shouldReturnHtmlByDefault() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings.Syntax result = document.outputSettings().syntax();

        // Assert
        assertEquals(Document.OutputSettings.Syntax.html, result);
    }

    @Test
    void syntax_withHtml_shouldSetAndReturnOutputSettings() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");

        // Act
        Document.OutputSettings result = document.outputSettings().syntax(Document.OutputSettings.Syntax.html);

        // Assert
        assertSame(document.outputSettings(), result);
        assertEquals(Document.OutputSettings.Syntax.html, document.outputSettings().syntax());
    }

    @Test
    void syntax_withXml_shouldSetSyntaxAndAutoConfigureEscapeMode() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");

        // Act
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        // Assert
        assertEquals(Document.OutputSettings.Syntax.xml, document.outputSettings().syntax());
        assertEquals(Entities.EscapeMode.xhtml, document.outputSettings().escapeMode());
    }

    @Test
    void syntax_withXml_shouldAllowManualEscapeModeOverride() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        // Act
        document.outputSettings().escapeMode(Entities.EscapeMode.base);

        // Assert
        assertEquals(Document.OutputSettings.Syntax.xml, document.outputSettings().syntax());
        assertEquals(Entities.EscapeMode.base, document.outputSettings().escapeMode());
    }

    @Test
    void syntax_shouldSupportMethodChaining() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");

        // Act & Assert
        assertSame(document.outputSettings(),
                document.outputSettings().syntax(Document.OutputSettings.Syntax.xml));
    }
}