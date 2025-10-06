package org.jsoup.tcc.llm.isValid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsValidTest_Prompt3 {

    @Test
    void isValid_withBodyHtml_shouldReturnTrue_whenBasicTagsInRelaxedSafelist() {
        // Arrange
        String validHtml = "<p>Hello <b>world</b></p>";
        Safelist safelist = Safelist.relaxed();

        // Act
        boolean result = Jsoup.isValid(validHtml, safelist);

        // Assert
        assertTrue(result, "should validate basic HTML with relaxed safelist");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnTrue_whenTextTagsInBasicSafelist() {
        // Arrange
        String validHtml = "<p>Text with <b>bold</b> and <em>emphasis</em></p>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(validHtml, safelist);

        // Assert
        assertTrue(result, "should validate text-only HTML with basic safelist");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnFalse_whenContainsProhibitedTags() {
        // Arrange
        String invalidHtml = "<script>alert('xss')</script><p>Content</p>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(invalidHtml, safelist);

        // Assert
        assertFalse(result, "should reject HTML with prohibited script tags");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnFalse_whenContainsProhibitedAttributes() {
        // Arrange
        String invalidHtml = "<p onclick=\"alert('xss')\">Click me</p>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(invalidHtml, safelist);

        // Assert
        assertFalse(result, "should reject HTML with prohibited event attributes");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnTrue_whenEmptyHtml() {
        // Arrange
        String emptyHtml = "";
        Safelist safelist = Safelist.relaxed();

        // Act
        boolean result = Jsoup.isValid(emptyHtml, safelist);

        // Assert
        assertTrue(result, "should consider empty HTML as valid");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnFalse_whenNoneSafelistAndAnyTags() {
        // Arrange
        String htmlWithTags = "<p>Any tags are invalid</p>";
        Safelist safelist = Safelist.none();

        // Act
        boolean result = Jsoup.isValid(htmlWithTags, safelist);

        // Assert
        assertFalse(result, "should reject any HTML tags with none safelist");
    }

    @Test
    void isValid_withDocument_shouldReturnTrue_whenCleanBodyAndEmptyHead() {
        // Arrange
        Document cleanDoc = Jsoup.parse("<div>Valid content</div>");
        Safelist safelist = Safelist.relaxed();
        Cleaner cleaner = new Cleaner(safelist);

        // Act
        boolean result = cleaner.isValid(cleanDoc);

        // Assert
        assertTrue(result, "should validate document with clean body and empty head");
    }

    @Test
    void isValid_withDocument_shouldReturnFalse_whenContentInHead() {
        // Arrange
        Document docWithHead = Jsoup.parse("<html><head><title>Title</title></head><body>Content</body></html>");
        Safelist safelist = Safelist.relaxed();
        Cleaner cleaner = new Cleaner(safelist);

        // Act
        boolean result = cleaner.isValid(docWithHead);

        // Assert
        assertFalse(result, "should reject document with content in head");
    }

    @Test
    void isValid_withDocument_shouldReturnFalse_whenProhibitedContentInBody() {
        // Arrange
        Document dirtyDoc = Jsoup.parse("<script>alert('xss')</script><p>Content</p>");
        Safelist safelist = Safelist.basic();
        Cleaner cleaner = new Cleaner(safelist);

        // Act
        boolean result = cleaner.isValid(dirtyDoc);

        // Assert
        assertFalse(result, "should reject document with prohibited content in body");
    }

    @Test
    void isValid_withBodyHtml_shouldReturnFalse_whenInvalidProtocolInHref() {
        // Arrange
        String htmlWithInvalidProtocol = "<a href=\"javascript:alert('xss')\">Click</a>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(htmlWithInvalidProtocol, safelist);

        // Assert
        assertFalse(result, "should reject HTML with invalid protocol in href");
    }
}