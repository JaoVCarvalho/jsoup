package org.jsoup.tcc.llm.textTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTest_Prompt1 {

    @Test
    void text_returnsNormalizedCombinedText() {
        Document doc = Jsoup.parse("<p>Hello  <b>there</b> now! </p>");
        Element p = doc.selectFirst("p");
        assertEquals("Hello there now!", p.text());
    }

    @Test
    void text_returnsEmptyStringWhenNoText() {
        Document doc = Jsoup.parse("<div></div>");
        Element div = doc.selectFirst("div");
        assertEquals("", div.text());
    }

    @Test
    void text_normalizesWhitespaceInNestedElements() {
        Document doc = Jsoup.parse("<div>  Hello <span>  world  </span> !  </div>");
        Element div = doc.selectFirst("div");
        assertEquals("Hello world !", div.text());
    }

    @Test
    void ownText_returnsOnlyDirectTextChildren() {
        Document doc = Jsoup.parse("<p>Hello <b>there</b> now!</p>");
        Element p = doc.selectFirst("p");
        assertEquals("Hello now!", p.ownText());
    }

    @Test
    void ownText_returnsEmptyStringWhenNoDirectText() {
        Document doc = Jsoup.parse("<div><span>text</span></div>");
        Element div = doc.selectFirst("div");
        assertEquals("", div.ownText());
    }

    @Test
    void ownText_includesBrAsSpace() {
        Document doc = Jsoup.parse("<div>Hello<br>world</div>");
        Element div = doc.selectFirst("div");
        assertEquals("Hello world", div.ownText());
    }

    @Test
    void wholeText_preservesOriginalWhitespace() {
        Document doc = Jsoup.parse("<div>Hello   \n  world</div>");
        Element div = doc.selectFirst("div");
        assertEquals("Hello   \n  world", div.wholeText());
    }

    @Test
    void wholeText_includesBrAsNewline() {
        Document doc = Jsoup.parse("<div>Hello<br>world</div>");
        Element div = doc.selectFirst("div");
        assertEquals("Hello\nworld", div.wholeText());
    }

    @Test
    void wholeText_returnsEmptyStringWhenNoText() {
        Document doc = Jsoup.parse("<div></div>");
        Element div = doc.selectFirst("div");
        assertEquals("", div.wholeText());
    }

    @Test
    void getElementsByTag_findsMatchingElements() {
        Document doc = Jsoup.parse("<div><p>1</p><span>2</span><p>3</p></div>");
        Element div = doc.selectFirst("div");
        Elements ps = div.getElementsByTag("p");
        assertEquals(2, ps.size());
        assertEquals("1", ps.get(0).text());
        assertEquals("3", ps.get(1).text());
    }

    @Test
    void getElementsByTag_returnsEmptyWhenNoMatch() {
        Document doc = Jsoup.parse("<div><p>1</p></div>");
        Element div = doc.selectFirst("div");
        Elements spans = div.getElementsByTag("span");
        assertTrue(spans.isEmpty());
    }

    @Test
    void select_findsElementsByCssQuery() {
        Document doc = Jsoup.parse("<div><p class='test'>1</p><p>2</p></div>");
        Element div = doc.selectFirst("div");
        Elements testPs = div.select("p.test");
        assertEquals(1, testPs.size());
        assertEquals("1", testPs.first().text());
    }

    @Test
    void select_returnsEmptyWhenNoMatch() {
        Document doc = Jsoup.parse("<div><p>1</p></div>");
        Element div = doc.selectFirst("div");
        Elements spans = div.select("span");
        assertTrue(spans.isEmpty());
    }

    @Test
    void html_returnsInnerHtml() {
        Document doc = Jsoup.parse("<div><p>test</p></div>");
        Element div = doc.selectFirst("div");
        assertEquals("<p>test</p>", div.html());
    }

    @Test
    void html_returnsEmptyStringWhenNoChildren() {
        Document doc = Jsoup.parse("<div></div>");
        Element div = doc.selectFirst("div");
        assertEquals("", div.html());
    }

    @Test
    void expectFirst_returnsFirstMatchingElement() {
        Document doc = Jsoup.parse("<div><p>1</p><p>2</p></div>");
        Element div = doc.selectFirst("div");
        Element p = div.expectFirst("p");
        assertEquals("1", p.text());
    }

    @Test
    void expectFirst_throwsWhenNoMatch() {
        Document doc = Jsoup.parse("<div><p>1</p></div>");
        Element div = doc.selectFirst("div");
        assertThrows(IllegalArgumentException.class, () -> div.expectFirst("span"));
    }
}