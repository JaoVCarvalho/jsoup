package org.jsoup.tcc.llm.selectTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectTest_Prompt3 {

    @Test
    void select_shouldReturnElementsByTagName() {
        // Arrange
        String html = "<div><p>First</p><span>Text</span><p>Second</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements paragraphs = div.select("p");

        // Assert
        assertEquals(2, paragraphs.size(), "should find all paragraph elements");
        assertEquals("First", paragraphs.get(0).text());
        assertEquals("Second", paragraphs.get(1).text());
    }

    @Test
    void select_shouldReturnElementsByClass() {
        // Arrange
        String html = "<div><span class='active'>A</span><span>B</span><span class='active'>C</span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements activeSpans = div.select("span.active");

        // Assert
        assertEquals(2, activeSpans.size(), "should find elements with specified class");
        assertEquals("A", activeSpans.get(0).text());
        assertEquals("C", activeSpans.get(1).text());
    }

    @Test
    void select_shouldReturnElementsByAttribute() {
        // Arrange
        String html = "<div><a href='/page1'>Link1</a><a>NoHref</a><a href='/page2'>Link2</a></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements linksWithHref = div.select("a[href]");

        // Assert
        assertEquals(2, linksWithHref.size(), "should find elements with href attribute");
        assertEquals("Link1", linksWithHref.get(0).text());
        assertEquals("Link2", linksWithHref.get(1).text());
    }

    @Test
    void select_shouldReturnDirectChildrenWithCombinator() {
        // Arrange
        String html = "<div><p>Direct</p><section><p>Nested</p></section></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements directChildren = div.select("> p");

        // Assert
        assertEquals(1, directChildren.size(), "should find only direct children");
        assertEquals("Direct", directChildren.get(0).text());
    }

    @Test
    void select_shouldReturnSelfWhenMatchingQuery() {
        // Arrange
        String html = "<div id='container'><p>Content</p></div>";
        Document doc = Jsoup.parse(html);
        Element container = doc.selectFirst("#container");

        // Act
        Elements selfMatch = container.select("div");

        // Assert
        assertEquals(1, selfMatch.size(), "should include self when matching query");
        assertSame(container, selfMatch.get(0), "should be the same element instance");
    }

    @Test
    void select_shouldReturnEmptyWhenNoMatches() {
        // Arrange
        String html = "<div><p>Content</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements noMatches = div.select("span.missing");

        // Assert
        assertTrue(noMatches.isEmpty(), "should return empty list when no elements match");
    }

    @Test
    void select_shouldFindDescendantElements() {
        // Arrange
        String html = "<div><section><p>Deep</p></section></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements descendants = div.select("* p");

        // Assert
        assertEquals(1, descendants.size(), "should find descendant elements at any level");
        assertEquals("Deep", descendants.get(0).text());
    }

    @Test
    void select_shouldThrowSelectorParseExceptionForInvalidQuery() {
        // Arrange
        String html = "<div>Content</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");
        String invalidQuery = "div[invalid::selector]";

        // Act & Assert
        assertThrows(Selector.SelectorParseException.class,
                () -> div.select(invalidQuery),
                "should throw exception for invalid CSS query");
    }
}