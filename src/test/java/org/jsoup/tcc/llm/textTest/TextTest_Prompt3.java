package org.jsoup.tcc.llm.textTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextTest_Prompt3 {

    @Test
    void text_shouldReturnNormalizedCombinedText_whenElementHasChildren() {
        // Arrange
        String html = "<p>Hello  <b>there</b> now! </p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.text();

        // Assert
        assertEquals("Hello there now!", result);
    }

    @Test
    void ownText_shouldReturnOnlyDirectText_whenElementHasMixedContent() {
        // Arrange
        String html = "<p>Hello <b>there</b> now!</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.ownText();

        // Assert
        assertEquals("Hello now!", result);
    }

    @Test
    void wholeText_shouldPreserveWhitespace_whenElementHasSpacesAndNewlines() {
        // Arrange
        String html = "<div>Hello  \n  <span>world</span>   !</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.wholeText();

        // Assert
        assertEquals("Hello  \n  world   !", result);
    }

    @Test
    void text_shouldReturnEmptyString_whenElementHasNoTextContent() {
        // Arrange
        String html = "<div></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.text();

        // Assert
        assertEquals("", result);
    }

    @Test
    void ownText_shouldIncludeSpaceFromBr_whenBrTagPresent() {
        // Arrange
        String html = "<p>Hello<br>world</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.ownText();

        // Assert
        assertEquals("Hello world", result);
    }

    @Test
    void text_shouldIgnoreScriptContent_whenScriptTagPresent() {
        // Arrange
        String html = "<div>Hello<script>alert('ignored')</script> world</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.text();

        // Assert
        assertEquals("Hello world", result);
    }

    @Test
    void textMethods_shouldHandleOnlyWhitespace_whenElementHasSpaces() {
        // Arrange
        String html = "<div>   \n  </div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act & Assert
        assertEquals("", div.text(), "text() should normalize to empty");
        assertEquals("", div.ownText(), "ownText() should normalize to empty");
        assertEquals("   \n  ", div.wholeText(), "wholeText() should preserve whitespace");
    }
}