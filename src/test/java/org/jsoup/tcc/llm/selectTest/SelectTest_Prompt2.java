package org.jsoup.tcc.llm.selectTest;
//SelectTest_Prompt2
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectTest_Prompt2 {

    @Test
    void select_shouldReturnElementsByTagName() {
        // Arrange
        String html = "<div><p>First</p><p>Second</p></div>";
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
    void select_shouldReturnElementsWithAttribute() {
        // Arrange
        String html = "<div><a href='#1'>Link1</a><a>NoLink</a><a href='#2'>Link2</a></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements linksWithHref = div.select("a[href]");

        // Assert
        assertEquals(2, linksWithHref.size(), "should find only links with href attribute");
        assertTrue(linksWithHref.get(0).hasAttr("href"));
        assertTrue(linksWithHref.get(1).hasAttr("href"));
    }

    @Test
    void select_shouldReturnDirectChildrenWithCombinator() {
        // Arrange
        String html = "<div><p>Direct</p><span><p>Nested</p></span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements directChildren = div.select("> p");

        // Assert
        assertEquals(1, directChildren.size(), "should find only direct child paragraphs");
        assertEquals("Direct", directChildren.get(0).text());
    }

    @Test
    void select_shouldReturnEmptyWhenNoMatches() {
        // Arrange
        String html = "<div><p>Content</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements noMatches = div.select("span");

        // Assert
        assertTrue(noMatches.isEmpty(), "should return empty elements when no matches found");
    }

    @Test
    void select_shouldHandleComplexAttributeSelectors() {
        // Arrange
        String html = "<div><a href='https://example.com/page1'>Example</a><a href='https://test.com/page2'>Test</a></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements exampleLinks = div.select("a[href*=example.com]");

        // Assert
        assertEquals(1, exampleLinks.size(), "should find links containing example.com in href");
        assertEquals("Example", exampleLinks.get(0).text());
    }

    @Test
    void select_shouldIncludeSelfWhenMatching() {
        // Arrange
        String html = "<div class='container'><p>Content</p></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements selfAndDescendants = div.select("div, p");

        // Assert
        assertEquals(2, selfAndDescendants.size(), "should include self and descendant in results");
        assertTrue(selfAndDescendants.contains(div));
    }

    @Test
    void select_shouldThrowExceptionOnInvalidCssQuery() {
        // Arrange
        String html = "<div>Content</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> div.select("div["),
                "should throw exception on invalid CSS query syntax");
    }

    @Test
    void select_shouldHandleEmptyCssQuery() {
        // Arrange
        String html = "<div>Content</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        Elements result = div.select("");

        // Assert
        assertTrue(result.isEmpty(), "should return empty elements for empty CSS query");
    }

    @Test
    void select_shouldUseElementAsContextForQuery() {
        // Arrange
        String html = "<div class='outer'><div class='inner'><p>Text</p></div></div>";
        Document doc = Jsoup.parse(html);
        Element innerDiv = doc.selectFirst("div.inner");

        // Act
        Elements paragraphsFromInner = innerDiv.select("p");

        // Assert
        assertEquals(1, paragraphsFromInner.size(),
                "should find paragraphs starting from inner div context");
        assertEquals("Text", paragraphsFromInner.get(0).text());
    }
}