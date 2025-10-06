package org.jsoup.tcc.llm.selectTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectTest_Prompt1 {

    @Test
    void select_withSimpleTagQuery_returnsMatchingElements() {
        String html = "<div><p>First</p><span>Text</span><p>Second</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("p");

        assertEquals(2, result.size());
        assertEquals("First", result.get(0).text());
        assertEquals("Second", result.get(1).text());
    }

    @Test
    void select_withAttributeQuery_returnsMatchingElements() {
        String html = "<div><a href='#1'>Link1</a><a>NoHref</a><a href='#2'>Link2</a></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("a[href]");

        assertEquals(2, result.size());
        assertEquals("Link1", result.get(0).text());
        assertEquals("Link2", result.get(1).text());
    }

    @Test
    void select_withDescendantCombinator_returnsNestedElements() {
        String html = "<div><ul><li>Item1</li></ul><li>Outside</li></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("ul li");

        assertEquals(1, result.size());
        assertEquals("Item1", result.get(0).text());
    }

    @Test
    void select_withChildCombinator_returnsDirectChildren() {
        String html = "<div><p>Child</p><span><p>Grandchild</p></span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("> p");

        assertEquals(1, result.size());
        assertEquals("Child", result.get(0).text());
    }

    @Test
    void select_withUniversalSelector_returnsAllDescendants() {
        String html = "<div><p>Text</p><span>More</span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("*");

        assertEquals(2, result.size());
        assertTrue(result.get(0).tagName().equals("p") || result.get(0).tagName().equals("span"));
        assertTrue(result.get(1).tagName().equals("p") || result.get(1).tagName().equals("span"));
    }

    @Test
    void select_withComplexAttributeQuery_returnsFilteredElements() {
        String html = "<div><a href='example.com'>Link1</a><a href='test.org'>Link2</a></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("a[href*=example]");

        assertEquals(1, result.size());
        assertEquals("Link1", result.get(0).text());
    }

    @Test
    void select_withNoMatches_returnsEmptyElements() {
        String html = "<div><p>Content</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("span");

        assertTrue(result.isEmpty());
    }

    @Test
    void select_onSelfWithMatchingQuery_returnsIncludingSelf() {
        String html = "<div class='container'>Content</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("div.container");

        assertEquals(1, result.size());
        assertEquals("Content", result.get(0).text());
    }

    @Test
    void select_withMultipleFilters_returnsPreciselyMatchedElements() {
        String html = "<div><input type='text' disabled><input type='checkbox'><input type='text'></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        Elements result = div.select("input[type=text]:not([disabled])");

        assertEquals(1, result.size());
        assertEquals("text", result.get(0).attr("type"));
        assertFalse(result.get(0).hasAttr("disabled"));
    }
}