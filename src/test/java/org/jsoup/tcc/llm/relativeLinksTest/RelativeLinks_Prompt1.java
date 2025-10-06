package org.jsoup.tcc.llm.relativeLinksTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelativeLinks_Prompt1 {

    @Test
    void clean_withBodyHtmlBaseUriAndSafelist_returnsCleanHtml() {
        String bodyHtml = "<p>Test <script>alert('xss')</script></p>";
        String baseUri = "https://example.com";
        Safelist safelist = Safelist.basic();

        String result = Jsoup.clean(bodyHtml, baseUri, safelist);

        assertEquals("<p>Test </p>", result);
    }

    @Test
    void clean_withEmptyBaseUriAndPreserveRelativeLinks_preservesRelativeLinks() {
        String bodyHtml = "<a href=\"/relative\">Link</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);

        String result = Jsoup.clean(bodyHtml, "", safelist);

        assertEquals("<a href=\"/relative\" rel=\"nofollow\">Link</a>", result);
    }

    @Test
    void clean_withSafelistOnly_delegatesToThreeArgClean() {
        String bodyHtml = "<p>Test <script>alert('xss')</script></p>";
        Safelist safelist = Safelist.none();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertEquals("Test alert('xss')", result);
    }

    @Test
    void clean_withBasicSafelist_removesUnsafeTags() {
        String bodyHtml = "<div><p>Allowed</p><script>NotAllowed</script></div>";
        Safelist safelist = Safelist.basic();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertEquals("<p>Allowed</p>", result);
    }

    @Test
    void clean_withBasicWithImagesSafelist_allowsImages() {
        String bodyHtml = "<p>Text <img src=\"https://example.com/image.jpg\" alt=\"test\"></p>";
        Safelist safelist = Safelist.basicWithImages();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertTrue(result.contains("<img"));
        assertTrue(result.contains("src=\"https://example.com/image.jpg\""));
    }

    @Test
    void clean_withNoneSafelist_returnsOnlyText() {
        String bodyHtml = "<p>5 is < 6.</p>";
        Safelist safelist = Safelist.none();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertEquals("5 is &lt; 6.", result);
    }

    @Test
    void clean_withEmptyInput_returnsEmptyString() {
        String bodyHtml = "";
        Safelist safelist = Safelist.basic();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertEquals("", result);
    }

    @Test
    void safelistPreserveRelativeLinks_setTrue_preservesState() {
        Safelist safelist = new Safelist();

        Safelist result = safelist.preserveRelativeLinks(true);

        assertSame(safelist, result);
    }

    @Test
    void clean_withMalformedHtml_cleansSuccessfully() {
        String bodyHtml = "<p>Unclosed <div>tags";
        Safelist safelist = Safelist.basic();

        String result = Jsoup.clean(bodyHtml, safelist);

        assertTrue(result.contains("Unclosed"));
        assertFalse(result.contains("<div>"));
    }
}