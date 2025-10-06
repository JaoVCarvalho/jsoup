package org.jsoup.tcc.llm.cleanTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CleanTest_Prompt2 {

    @Test
    void clean_shouldAllowBasicTags_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<p>Hello <b>world</b> from <a href='http://example.com'>link</a></p>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello <b>world</b> from <a href=\"http://example.com\" rel=\"nofollow\">link</a></p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve basic tags and add rel=nofollow to links");
    }

    @Test
    void clean_shouldRemoveDisallowedTags_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<div><script>alert('xss')</script><p>Hello</p><img src='image.jpg'></div>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello</p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove script, img and div tags but preserve p tag");
    }

    @Test
    void clean_shouldRemoveDisallowedAttributes_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<p class='highlight' style='color:red'>Hello</p><a href='http://example.com' target='_blank'>link</a>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello</p><a href=\"http://example.com\" rel=\"nofollow\">link</a>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove class and style attributes from p, and target from a");
    }

    @Test
    void clean_shouldReturnEmptyString_whenInputIsEmpty() {
        // Arrange
        String inputHtml = "";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals("", result, "Should return empty string for empty input");
    }

    @Test
    void clean_shouldThrowException_whenInputIsNull() {
        // Arrange
        String inputHtml = null;
        Safelist safelist = Safelist.basic();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> Jsoup.clean(inputHtml, safelist),
                "Should throw IllegalArgumentException for null input");
    }

    @Test
    void clean_shouldPreserveTextContent_whenTagsAreRemoved() {
        // Arrange
        String inputHtml = "<div>Hello <span>world</span> from <script>alert('test')</script> jsoup</div>";
        Safelist safelist = Safelist.none();
        String expected = "Hello world from  jsoup";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve text content even when all tags are removed");
    }

    @Test
    void clean_shouldAllowMoreTags_whenUsingRelaxedSafelist() {
        // Arrange
        String inputHtml = "<div class='container'><h1>Title</h1><img src='http://example.com/image.jpg' alt='Image'></div>";
        Safelist safelist = Safelist.relaxed();
        String expected = "<div class=\"container\"><h1>Title</h1><img src=\"http://example.com/image.jpg\" alt=\"Image\"></div>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should allow div, h1, and img tags with relaxed safelist");
    }

    @Test
    void clean_shouldHandleHtmlEntitiesCorrectly_whenUsingNoneSafelist() {
        // Arrange
        String inputHtml = "<p>5 is &lt; 6 &amp; 7 is &gt; 6</p>";
        Safelist safelist = Safelist.none();
        String expected = "5 is &lt; 6 &amp; 7 is &gt; 6";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve HTML entities even with none safelist");
    }

    @Test
    void clean_shouldRemoveScriptTagsAndEventHandlers_whenPresentInInput() {
        // Arrange
        String inputHtml = "<p onclick='alert(\"xss\")'>Hello</p><script>malicious()</script><a href='javascript:alert()'>click</a>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello</p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove all script tags, event handlers, and javascript links");
    }

    @Test
    void clean_shouldHandleModifiedSafelist_whenTagsAreRemoved() {
        // Arrange
        String inputHtml = "<p>Paragraph</p><b>Bold</b><i>Italic</i>";
        Safelist safelist = Safelist.basic().removeTags("b", "i");
        String expected = "<p>Paragraph</p>BoldItalic";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove specified tags from safelist but preserve their text content");
    }
}