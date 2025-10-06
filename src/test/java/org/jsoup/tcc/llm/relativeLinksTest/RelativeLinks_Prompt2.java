package org.jsoup.tcc.llm.relativeLinksTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelativeLinks_Prompt2 {

    // Tests for Jsoup.clean(String bodyHtml, String baseUri, Safelist safelist)

    @Test
    void clean_shouldRemoveDisallowedTags_whenUsingBasicSafelist() {
        // Arrange
        String html = "<div><p>Hello</p><script>alert('xss')</script></div>";
        Safelist safelist = Safelist.basic();
        String expected = "<p>Hello</p>";

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals(expected, result, "should remove script tags but keep p tags");
    }

    @Test
    void clean_shouldKeepAllowedAttributes_whenUsingBasicWithImagesSafelist() {
        // Arrange
        String html = "<img src='http://example.com/image.jpg' alt='test' onclick='malicious()'>";
        Safelist safelist = Safelist.basicWithImages();
        String expected = "<img alt=\"test\" src=\"http://example.com/image.jpg\">";

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals(expected, result, "should keep allowed attributes but remove onclick");
    }

    @Test
    void clean_shouldPreserveRelativeLinks_whenPreserveRelativeLinksEnabledAndEmptyBaseUri() {
        // Arrange
        String html = "<a href='/relative/path'>Link</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);
        String expected = "<a href=\"/relative/path\" rel=\"nofollow\">Link</a>";

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals(expected, result, "should preserve relative links when preserveRelativeLinks is true");
    }

    @Test
    void clean_shouldConvertRelativeLinksToAbsolute_whenPreserveRelativeLinksDisabled() {
        // Arrange
        String html = "<a href='/relative/path'>Link</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(false);
        String baseUri = "http://example.com/";

        // Act
        String result = Jsoup.clean(html, baseUri, safelist);

        // Assert
        assertTrue(result.contains("http://example.com/relative/path"),
                "should convert relative links to absolute when preserveRelativeLinks is false");
    }

    @Test
    void clean_shouldReturnEmptyString_whenInputIsEmpty() {
        // Arrange
        String html = "";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals("", result, "should return empty string for empty input");
    }

    @Test
    void clean_shouldEscapeHtmlEntities_whenUsingNoneSafelist() {
        // Arrange
        String html = "<p>5 is < 6.</p>";
        Safelist safelist = Safelist.none();
        String expected = "5 is &lt; 6.";

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals(expected, result, "should escape HTML entities when using none safelist");
    }

    // Tests for Jsoup.clean(String bodyHtml, Safelist safelist)

    @Test
    void clean_withoutBaseUri_shouldRemoveRelativeLinksByDefault() {
        // Arrange
        String html = "<a href='/relative/path'>Link</a>";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(html, safelist);

        // Assert
        assertFalse(result.contains("href"), "should remove relative links when no baseUri provided");
    }

    @Test
    void clean_withoutBaseUri_shouldProduceSameResultAsEmptyBaseUri() {
        // Arrange
        String html = "<p>Test <b>content</b></p><script>alert('xss')</script>";
        Safelist safelist = Safelist.basic();

        // Act
        String result1 = Jsoup.clean(html, safelist);
        String result2 = Jsoup.clean(html, "", safelist);

        // Assert
        assertEquals(result1, result2, "should produce same result as method with empty baseUri");
    }

    // Tests for Safelist.preserveRelativeLinks(boolean preserve)

    @Test
    void preserveRelativeLinks_shouldConfigurePreservationAndReturnInstanceForChaining() {
        // Arrange
        Safelist safelist = new Safelist();

        // Act
        Safelist result = safelist.preserveRelativeLinks(true);

        // Assert
        assertSame(safelist, result, "should return same instance for method chaining");
    }

    @Test
    void preserveRelativeLinks_shouldAffectCleaningBehavior_whenSetToTrue() {
        // Arrange
        String html = "<a href='/test'>Link</a>";
        Safelist preservingSafelist = Safelist.basic().preserveRelativeLinks(true);
        Safelist nonPreservingSafelist = Safelist.basic().preserveRelativeLinks(false);

        // Act
        String preservingResult = Jsoup.clean(html, "", preservingSafelist);
        String nonPreservingResult = Jsoup.clean(html, "http://example.com/", nonPreservingSafelist);

        // Assert
        assertTrue(preservingResult.contains("/test"),
                "should preserve relative links when preserveRelativeLinks is true");
        assertTrue(nonPreservingResult.contains("http://example.com/test"),
                "should convert to absolute links when preserveRelativeLinks is false");
    }

    @Test
    void clean_shouldHandleProtocolRestrictions_whenUrlHasDisallowedProtocol() {
        // Arrange
        String html = "<a href='ftp://example.com/file'>FTP Link</a><a href='http://example.com'>HTTP Link</a>";
        Safelist safelist = Safelist.basic(); // Allows ftp, http, https, mailto

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertTrue(result.contains("ftp://example.com/file"), "should keep allowed ftp protocol");
        assertTrue(result.contains("http://example.com"), "should keep allowed http protocol");
    }

    @Test
    void clean_shouldRemoveDisallowedProtocols_whenUsingStricterSafelist() {
        // Arrange
        String html = "<a href='ftp://example.com/file'>FTP Link</a><a href='http://example.com'>HTTP Link</a>";
        Safelist safelist = new Safelist()
                .addTags("a")
                .addAttributes("a", "href")
                .addProtocols("a", "href", "http", "https"); // Only http and https allowed

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert
        assertFalse(result.contains("ftp://"), "should remove disallowed ftp protocol");
        assertTrue(result.contains("http://example.com"), "should keep allowed http protocol");
    }
}