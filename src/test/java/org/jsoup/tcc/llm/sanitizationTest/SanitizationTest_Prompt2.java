package org.jsoup.tcc.llm.sanitizationTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SanitizationTest_Prompt2 {

    @Test
    void clean_shouldRemoveAllTags_whenSafelistIsNone() {
        // Arrange
        String inputHtml = "<p>Hello <b>world</b>!</p>";
        Safelist safelist = Safelist.none();
        String expected = "Hello world!";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove all tags and preserve only text content");
    }

    @Test
    void clean_shouldPreserveSimpleTextTags_whenUsingSimpleTextSafelist() {
        // Arrange
        String inputHtml = "Hello <b>bold</b> and <em>emphasis</em> text";
        Safelist safelist = Safelist.simpleText();
        String expected = "Hello <b>bold</b> and <em>emphasis</em> text";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve simple text formatting tags");
    }

    @Test
    void clean_shouldRemoveScriptTags_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<p>Hello <script>alert('xss')</script>world</p>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello world</p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove script tags and malicious content");
    }

    @Test
    void clean_shouldPreserveLinks_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "Visit <a href=\"https://example.com\">example</a>";
        Safelist safelist = Safelist.basic();
        String expected = "Visit <a href=\"https://example.com\" rel=\"nofollow\">example</a>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve allowed links with nofollow attribute");
    }

    @Test
    void clean_shouldPreserveImages_whenUsingBasicWithImagesSafelist() {
        // Arrange
        String inputHtml = "<img src=\"https://example.com/image.jpg\" alt=\"Example\">";
        Safelist safelist = Safelist.basicWithImages();
        String expected = "<img src=\"https://example.com/image.jpg\" alt=\"Example\">";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve image tags with allowed attributes");
    }

    @Test
    void clean_shouldHandleEmptyInput_whenHtmlIsEmpty() {
        // Arrange
        String inputHtml = "";
        Safelist safelist = Safelist.basic();
        String expected = "";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should handle empty HTML input gracefully");
    }

    @Test
    void clean_shouldEscapeHtmlEntities_whenUsingNoneSafelist() {
        // Arrange
        String inputHtml = "5 is < 6 & 7 > 5";
        Safelist safelist = Safelist.none();
        String expected = "5 is &lt; 6 &amp; 7 &gt; 5";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should escape HTML entities in text output");
    }

    @Test
    void clean_shouldRemoveUnallowedAttributes_whenUsingRelaxedSafelist() {
        // Arrange
        String inputHtml = "<div onclick=\"alert('xss')\" class=\"my-class\">Content</div>";
        Safelist safelist = Safelist.relaxed();
        String expected = "<div>Content</div>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove unallowed attributes while preserving tag");
    }

    @Test
    void clean_shouldPreserveNestedAllowedTags_whenUsingBasicSafelist() {
        // Arrange
        String inputHtml = "<p>Text with <strong>bold <em>and italic</em></strong></p>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Text with <strong>bold <em>and italic</em></strong></p>";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should preserve nested structure of allowed tags");
    }

    @Test
    void clean_shouldRemoveTagsButKeepContent_whenTagsNotInSafelist() {
        // Arrange
        String inputHtml = "Keep <span>this</span> but remove <custom>that</custom>";
        Safelist safelist = Safelist.simpleText();
        String expected = "Keep this but remove that";

        // Act
        String result = Jsoup.clean(inputHtml, safelist);

        // Assert
        assertEquals(expected, result, "Should remove disallowed tags but preserve their text content");
    }
}