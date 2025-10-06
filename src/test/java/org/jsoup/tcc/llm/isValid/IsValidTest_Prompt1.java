package org.jsoup.tcc.llm.isValid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsValidTest_Prompt1 {

    @Test
    void isValid_withValidHtmlAndBasicSafelist_returnsTrue() {
        String html = "<p>Simple paragraph</p>";
        Safelist safelist = Safelist.basic();
        boolean result = Jsoup.isValid(html, safelist);
        assertTrue(result);
    }

    @Test
    void isValid_withInvalidHtmlAndBasicSafelist_returnsFalse() {
        String html = "<script>alert('xss')</script>";
        Safelist safelist = Safelist.basic();
        boolean result = Jsoup.isValid(html, safelist);
        assertFalse(result);
    }

    @Test
    void isValid_withEmptyHtmlAndRelaxedSafelist_returnsTrue() {
        String html = "";
        Safelist safelist = Safelist.relaxed();
        boolean result = Jsoup.isValid(html, safelist);
        assertTrue(result);
    }

    @Test
    void isValid_withValidLinkAndBasicSafelist_returnsTrue() {
        String html = "<a href=\"https://example.com\">Link</a>";
        Safelist safelist = Safelist.basic();
        boolean result = Jsoup.isValid(html, safelist);
        assertTrue(result);
    }

    @Test
    void isValid_withNoneSafelistAndAnyHtml_returnsFalse() {
        String html = "<p>Any HTML</p>";
        Safelist safelist = Safelist.none();
        boolean result = Jsoup.isValid(html, safelist);
        assertFalse(result);
    }

    @Test
    void cleanerIsValid_withValidDocumentAndBasicSafelist_returnsTrue() {
        String html = "<html><body><p>Test</p></body></html>";
        Document doc = Jsoup.parse(html);
        Cleaner cleaner = new Cleaner(Safelist.basic());
        boolean result = cleaner.isValid(doc);
        assertTrue(result);
    }

    @Test
    void cleanerIsValid_withInvalidDocumentAndBasicSafelist_returnsFalse() {
        String html = "<html><body><script>alert('xss')</script></body></html>";
        Document doc = Jsoup.parse(html);
        Cleaner cleaner = new Cleaner(Safelist.basic());
        boolean result = cleaner.isValid(doc);
        assertFalse(result);
    }

    @Test
    void cleanerIsValid_withEmptyBodyAndRelaxedSafelist_returnsTrue() {
        String html = "<html><body></body></html>";
        Document doc = Jsoup.parse(html);
        Cleaner cleaner = new Cleaner(Safelist.relaxed());
        boolean result = cleaner.isValid(doc);
        assertTrue(result);
    }

    @Test
    void cleanerIsValid_withContentInHeadAndBasicSafelist_returnsFalse() {
        String html = "<html><head><title>Test</title></head><body><p>Content</p></body></html>";
        Document doc = Jsoup.parse(html);
        Cleaner cleaner = new Cleaner(Safelist.basic());
        boolean result = cleaner.isValid(doc);
        assertFalse(result);
    }

    @Test
    void cleanerIsValid_withValidImageAndRelaxedSafelist_returnsTrue() {
        String html = "<html><body><img src=\"https://example.com/image.jpg\" alt=\"test\"></body></html>";
        Document doc = Jsoup.parse(html);
        Cleaner cleaner = new Cleaner(Safelist.relaxed());
        boolean result = cleaner.isValid(doc);
        assertTrue(result);
    }

    @Test
    void isValidBodyHtml_withValidHtmlAndBasicSafelist_returnsTrue() {
        String html = "<p>Simple paragraph</p>";
        Cleaner cleaner = new Cleaner(Safelist.basic());
        boolean result = cleaner.isValidBodyHtml(html);
        assertTrue(result);
    }

    @Test
    void isValidBodyHtml_withInvalidHtmlAndBasicSafelist_returnsFalse() {
        String html = "<script>alert('xss')</script>";
        Cleaner cleaner = new Cleaner(Safelist.basic());
        boolean result = cleaner.isValidBodyHtml(html);
        assertFalse(result);
    }
}