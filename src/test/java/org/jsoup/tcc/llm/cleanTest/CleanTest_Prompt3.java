package org.jsoup.tcc.llm.cleanTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanTest_Prompt3 {

    @Test
    void clean_shouldPreserveAllowedTags_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<p>Hello <b>world</b> <script>alert('xss')</script></p>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello <b>world</b> </p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void clean_shouldRemoveAllTags_whenUsingNoneSafelist() {
        // Arrange
        String inputHtml = "<p>Hello <b>world</b></p>";
        Safelist safelist = Safelist.none();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals("Hello world", result);
    }

    @Test
    void clean_shouldEscapeHtmlEntities_whenUsingNoneSafelist() {
        // Arrange
        String inputHtml = "<p>5 is < 6 & 7 > 5</p>";
        Safelist safelist = Safelist.none();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals("5 is &lt; 6 &amp; 7 &gt; 5", result);
    }

    @Test
    void clean_shouldReturnEmptyString_whenInputIsEmpty() {
        // Arrange
        String inputHtml = "";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals("", result);
    }

    @Test
    void clean_shouldHandleNullInputByConvertingToEmptyString() {
        // Arrange
        String inputHtml = null;
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals("", result);
    }

    @Test
    void clean_shouldPreserveLinks_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<a href=\"https://example.com\">Link</a>";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertTrue(result.contains("href=\"https://example.com\""));
        assertTrue(result.contains("rel=\"nofollow\""));
    }

    @Test
    void clean_shouldRemoveStyleAttributes_whenNotInSafelist() {
        // Arrange
        String inputHtml = "<p style=\"color: red;\">Text</p>";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertFalse(result.contains("style="));
        assertEquals("<p>Text</p>", result);
    }

    @Test
    void clean_shouldAllowImages_whenUsingRelaxedSafelist() {
        // Arrange
        String inputHtml = "<img src=\"https://example.com/image.jpg\" alt=\"Test\">";
        Safelist safelist = Safelist.relaxed();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertTrue(result.contains("<img"));
        assertTrue(result.contains("src=\"https://example.com/image.jpg\""));
    }
}