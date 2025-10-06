package org.jsoup.tcc.llm.syntaxTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SyntaxTest_Prompt2 {

    @Test
    void syntax_shouldReturnHtmlByDefault() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        // Act
        Document.OutputSettings.Syntax result = outputSettings.syntax();

        // Assert
        assertEquals(Document.OutputSettings.Syntax.html, result,
                "Default syntax should be HTML");
    }

    @Test
    void syntax_shouldSetXmlSyntaxAndReturnThisForChaining() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        // Act
        Document.OutputSettings result = outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        // Assert
        assertSame(outputSettings, result,
                "Should return this for method chaining");
        assertEquals(Document.OutputSettings.Syntax.xml, outputSettings.syntax(),
                "Syntax should be set to XML");
    }

    @Test
    void syntax_shouldSetHtmlSyntaxAndReturnThisForChaining() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        // Act
        Document.OutputSettings result = outputSettings.syntax(Document.OutputSettings.Syntax.html);

        // Assert
        assertSame(outputSettings, result,
                "Should return this for method chaining");
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax(),
                "Syntax should be set to HTML");
    }

    @Test
    void syntax_whenSettingToXml_shouldAutomaticallySetEscapeModeToXhtml() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        // Act
        outputSettings.syntax(Document.OutputSettings.Syntax.xml);

        // Assert
        assertEquals(Entities.EscapeMode.xhtml, outputSettings.escapeMode(),
                "Escape mode should be automatically set to xhtml when syntax is XML");
    }

    @Test
    void syntax_whenSettingToHtml_shouldNotChangeEscapeMode() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();
        Entities.EscapeMode originalEscapeMode = outputSettings.escapeMode();

        // Act
        outputSettings.syntax(Document.OutputSettings.Syntax.html);

        // Assert
        assertEquals(originalEscapeMode, outputSettings.escapeMode(),
                "Escape mode should remain unchanged when setting syntax to HTML");
    }

    @Test
    void syntax_shouldReflectLastSetValue() {
        // Arrange
        Document document = Jsoup.parse("<html></html>");
        Document.OutputSettings outputSettings = document.outputSettings();

        // Act & Assert - Multiple transitions
        outputSettings.syntax(Document.OutputSettings.Syntax.xml);
        assertEquals(Document.OutputSettings.Syntax.xml, outputSettings.syntax(),
                "Should reflect XML syntax after setting");

        outputSettings.syntax(Document.OutputSettings.Syntax.html);
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax(),
                "Should reflect HTML syntax after setting");
    }
}