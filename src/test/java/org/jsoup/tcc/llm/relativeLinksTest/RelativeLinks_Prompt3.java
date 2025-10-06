package org.jsoup.tcc.llm.relativeLinksTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelativeLinks_Prompt3 {

    @Test
    void clean_withBaseUriAndPreserveRelativeLinks_shouldKeepRelativeLinks() {
        // Arrange
        String html = "<a href=\"/relative/path\">Link</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);

        // Act
        String result = Jsoup.clean(html, "https://example.com", safelist);

        // Assert
        assertTrue(result.contains("href=\"/relative/path\""),
                "Should preserve relative links when preservation is enabled");
    }

    @Test
    void clean_withoutBaseUri_shouldRemoveRelativeLinks() {
        // Arrange
        String html = "<a href=\"/relative/path\">Link</a>";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(html, safelist);

        // Assert
        assertFalse(result.contains("href=\"/relative/path\""),
                "Should remove relative links when no baseUri provided");
    }

    @Test
    void clean_withBasicSafelist_shouldFilterNonBasicTags() {
        // Arrange
        String html = "<p>Valid</p><script>alert('xss')</script><div>Invalid</div>";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(html, safelist);

        // Assert
        assertTrue(result.contains("<p>Valid</p>"), "Should keep basic tags");
        assertFalse(result.contains("script"), "Should remove script tags");
        assertFalse(result.contains("<div>"), "Should remove non-basic tags");
    }

    @Test
    void clean_withNoneSafelist_shouldRemoveAllTagsButEscapeEntities() {
        // Arrange
        String html = "<p>5 is < 6 & 7 > 5</p>";
        Safelist safelist = Safelist.none();

        // Act
        String result = Jsoup.clean(html, safelist);

        // Assert
        assertEquals("5 is &lt; 6 &amp; 7 &gt; 5", result,
                "Should remove all tags but escape HTML entities");
    }

    @Test
    void preserveRelativeLinks_shouldConfigurePreservationState() {
        // Arrange
        Safelist safelist = new Safelist();

        // Act & Assert - default state
        assertFalse(safelist.preserveRelativeLinks(),
                "Default should be false");

        // Act & Assert - after configuration
        Safelist result = safelist.preserveRelativeLinks(true);
        assertTrue(safelist.preserveRelativeLinks(),
                "Should be true after configuration");
        assertSame(safelist, result, "Should return this for chaining");
    }

    @Test
    void clean_withEmptyHtml_shouldReturnEmptyString() {
        // Arrange
        String html = "";
        Safelist safelist = Safelist.basic();

        // Act
        String result = Jsoup.clean(html, safelist);

        // Assert
        assertEquals("", result, "Should return empty string for empty input");
    }

    @Test
    void clean_withEmptyBaseUriAndPreserveTrue_shouldHandleInternally() {
        // Arrange
        String html = "<a href=\"/test\">Link</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);

        // Act
        String result = Jsoup.clean(html, "", safelist);

        // Assert - Should not throw exception and process HTML
        assertNotNull(result, "Should process HTML without errors");
        assertTrue(result.contains("a"), "Should process anchor tags");
    }
}