package org.jsoup.tcc.llm.sanitizationTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SanitizationTest_Prompt3 {

    @Test
    void clean_shouldRemoveScriptTags_whenUsingBasicSafelist() {
        // Arrange
        String unsafeHtml = "<p>Hello <script>alert('xss')</script>World</p>";
        String expected = "<p>Hello World</p>";

        // Act
        String result = Jsoup.clean(unsafeHtml, Safelist.basic());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldEscapeHtmlEntities_whenUsingNoneSafelist() {
        // Arrange
        String sourceHtml = "<p>5 is < 6 & 7 > 5</p>";
        String expected = "5 is &lt; 6 &amp; 7 &gt; 5";

        // Act
        String result = Jsoup.clean(sourceHtml, Safelist.none());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldPreserveTextContent_whenUsingNoneSafelist() {
        // Arrange
        String sourceHtml = "<div><p>Hello <b>World</b></p></div>";
        String expected = "Hello World";

        // Act
        String result = Jsoup.clean(sourceHtml, Safelist.none());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldAllowImages_whenUsingBasicWithImagesSafelist() {
        // Arrange
        String htmlWithImg = "<p>Text <img src='http://example.com/image.jpg' alt='test' /></p>";
        String expected = "<p>Text <img alt=\"test\" src=\"http://example.com/image.jpg\" /></p>";

        // Act
        String result = Jsoup.clean(htmlWithImg, Safelist.basicWithImages());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldRemoveImageTags_whenUsingBasicSafelist() {
        // Arrange
        String htmlWithImg = "<p>Text <img src='http://example.com/image.jpg' /></p>";
        String expected = "<p>Text </p>";

        // Act
        String result = Jsoup.clean(htmlWithImg, Safelist.basic());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldHandleEmptyInput_whenUsingRelaxedSafelist() {
        // Arrange
        String emptyHtml = "";
        String expected = "";

        // Act
        String result = Jsoup.clean(emptyHtml, Safelist.relaxed());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldPreserveFormattingTags_whenUsingSimpleTextSafelist() {
        // Arrange
        String formattedText = "<b>Bold</b> and <em>italic</em> text";
        String expected = "<b>Bold</b> and <em>italic</em> text";

        // Act
        String result = Jsoup.clean(formattedText, Safelist.simpleText());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldRemoveUnsafeAttributes_whenUsingBasicSafelist() {
        // Arrange
        String htmlWithUnsafeAttr = "<a href=\"http://example.com\" onclick=\"malicious()\">Link</a>";
        String expected = "<a href=\"http://example.com\" rel=\"nofollow\">Link</a>";

        // Act
        String result = Jsoup.clean(htmlWithUnsafeAttr, Safelist.basic());

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldThrowException_whenBodyHtmlIsNull() {
        // Arrange
        String nullHtml = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> Jsoup.clean(nullHtml, Safelist.basic()));
    }

    @Test
    void clean_shouldThrowException_whenSafelistIsNull() {
        // Arrange
        String html = "<p>test</p>";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> Jsoup.clean(html, null));
    }
}