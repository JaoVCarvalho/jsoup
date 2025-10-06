package org.jsoup.tcc.llm.domManipulationTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomManipulationTest_Prompt1 {

    @Test
    void hasText_ReturnsTrueWhenElementContainsNonBlankText() {
        Document doc = Jsoup.parse("<div>Hello</div>");
        Element element = doc.selectFirst("div");
        assertTrue(element.hasText());
    }

    @Test
    void hasText_ReturnsFalseWhenElementContainsOnlyWhitespace() {
        Document doc = Jsoup.parse("<div>   </div>");
        Element element = doc.selectFirst("div");
        assertFalse(element.hasText());
    }

    @Test
    void hasText_ReturnsTrueWhenChildElementContainsText() {
        Document doc = Jsoup.parse("<div><span>Text</span></div>");
        Element element = doc.selectFirst("div");
        assertTrue(element.hasText());
    }

    @Test
    void before_InsertsHtmlBeforeElement() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        p.before("<span>Before</span>");
        assertEquals("<span>Before</span><p>Original</p>", doc.selectFirst("div").html());
    }

    @Test
    void before_ReturnsElementForChaining() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        Element result = p.before("<span>Before</span>");
        assertSame(p, result);
    }

    @Test
    void after_InsertsHtmlAfterElement() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        p.after("<span>After</span>");
        assertEquals("<p>Original</p><span>After</span>", doc.selectFirst("div").html());
    }

    @Test
    void after_ReturnsElementForChaining() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        Element result = p.after("<span>After</span>");
        assertSame(p, result);
    }

    @Test
    void wrap_WrapsElementWithHtml() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        p.wrap("<div class='wrapper'></div>");
        Element wrapper = doc.selectFirst(".wrapper");
        assertNotNull(wrapper);
        assertEquals("Original", wrapper.selectFirst("p").text());
    }

    @Test
    void wrap_ReturnsElementForChaining() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        Element result = p.wrap("<div class='wrapper'></div>");
        assertSame(p, result);
    }

    @Test
    void hasText_ReturnsFalseForEmptyElement() {
        Document doc = Jsoup.parse("<div></div>");
        Element element = doc.selectFirst("div");
        assertFalse(element.hasText());
    }

    @Test
    void before_HandlesMultipleInsertions() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        p.before("<span>First</span>").before("<span>Second</span>");
        assertEquals("<span>Second</span><span>First</span><p>Original</p>", doc.selectFirst("div").html());
    }

    @Test
    void after_HandlesMultipleInsertions() {
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element p = doc.selectFirst("p");
        p.after("<span>First</span>").after("<span>Second</span>");
        assertEquals("<p>Original</p><span>Second</span><span>First</span>", doc.selectFirst("div").html());
    }

    @Test
    void wrap_HandlesNestedHtml() {
        Document doc = Jsoup.parse("<p>Text</p>");
        Element p = doc.selectFirst("p");
        p.wrap("<div><section></section></div>");
        Element section = doc.selectFirst("section");
        assertNotNull(section);
        assertEquals("Text", section.selectFirst("p").text());
    }
}