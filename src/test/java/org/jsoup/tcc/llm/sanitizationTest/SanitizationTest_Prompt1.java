package org.jsoup.tcc.llm.sanitizationTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SanitizationTest_Prompt1 {

    @Test
    void clean_removesAllTagsWithNoneSafelist() {
        String html = "<p>Hello <b>World</b></p>";
        String result = Jsoup.clean(html, Safelist.none());
        assertEquals("Hello World", result);
    }

    @Test
    void clean_preservesSimpleTextTagsWithSimpleTextSafelist() {
        String html = "<p>Hello <b>World</b> <script>alert('xss')</script></p>";
        String result = Jsoup.clean(html, Safelist.simpleText());
        assertEquals("Hello <b>World</b> alert('xss')", result);
    }

    @Test
    void clean_preservesBasicTagsWithBasicSafelist() {
        String html = "<div><p>Hello <a href=\"http://example.com\">World</a></p><img src=\"http://example.com/img.jpg\"></div>";
        String result = Jsoup.clean(html, Safelist.basic());
        assertTrue(result.contains("<p>Hello <a href=\"http://example.com\" rel=\"nofollow\">World</a></p>"));
        assertFalse(result.contains("<img>"));
    }

    @Test
    void clean_preservesImagesWithBasicWithImagesSafelist() {
        String html = "<p>Hello <img src=\"http://example.com/img.jpg\" alt=\"image\"></p>";
        String result = Jsoup.clean(html, Safelist.basicWithImages());
        assertTrue(result.contains("<img"));
        assertTrue(result.contains("src=\"http://example.com/img.jpg\""));
    }

    @Test
    void clean_allowsFullTagsWithRelaxedSafelist() {
        String html = "<table><tr><td>Cell</td></tr></table>";
        String result = Jsoup.clean(html, Safelist.relaxed());
        assertTrue(result.contains("<table>"));
        assertTrue(result.contains("<td>"));
    }

    @Test
    void clean_escapesHtmlEntitiesWithNoneSafelist() {
        String html = "5 is < 6 & 7 > 5";
        String result = Jsoup.clean(html, Safelist.none());
        assertEquals("5 is &lt; 6 &amp; 7 &gt; 5", result);
    }

    @Test
    void clean_removesScriptTagsWithBasicSafelist() {
        String html = "<p>Hello <script>alert('xss')</script>World</p>";
        String result = Jsoup.clean(html, Safelist.basic());
        assertFalse(result.contains("<script>"));
        assertTrue(result.contains("Hello World"));
    }

    @Test
    void clean_preservesFormattingWithSimpleTextSafelist() {
        String html = "<b>Bold</b> <i>Italic</i> <u>Underline</u>";
        String result = Jsoup.clean(html, Safelist.simpleText());
        assertTrue(result.contains("<b>Bold</b>"));
        assertTrue(result.contains("<i>Italic</i>"));
        assertTrue(result.contains("<u>Underline</u>"));
    }

    @Test
    void clean_handlesEmptyInput() {
        String html = "";
        String result = Jsoup.clean(html, Safelist.basic());
        assertEquals("", result);
    }

    @Test
    void clean_removesInvalidAttributesWithBasicSafelist() {
        String html = "<a href=\"http://example.com\" onclick=\"alert('xss')\">Link</a>";
        String result = Jsoup.clean(html, Safelist.basic());
        assertTrue(result.contains("href=\"http://example.com\""));
        assertFalse(result.contains("onclick"));
    }
}