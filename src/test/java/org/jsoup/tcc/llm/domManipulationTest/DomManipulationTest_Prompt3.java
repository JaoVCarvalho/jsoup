package org.jsoup.tcc.llm.domManipulationTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomManipulationTest_Prompt3 {

    // hasText() Tests
    @Test
    void hasText_shouldReturnTrue_whenElementHasNonBlankText() {
        // Arrange
        Document doc = Jsoup.parse("<div>Hello World</div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertTrue(element.hasText());
    }

    @Test
    void hasText_shouldReturnTrue_whenChildHasNonBlankText() {
        // Arrange
        Document doc = Jsoup.parse("<div><span>Text</span></div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertTrue(element.hasText());
    }

    @Test
    void hasText_shouldReturnFalse_whenElementHasOnlyWhitespace() {
        // Arrange
        Document doc = Jsoup.parse("<div>   \t\n   </div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasText());
    }

    @Test
    void hasText_shouldReturnFalse_whenElementIsEmpty() {
        // Arrange
        Document doc = Jsoup.parse("<div></div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasText());
    }

    // before() Tests
    @Test
    void before_shouldInsertHtmlAsPrecedingSibling() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element target = doc.selectFirst("p");

        // Act
        Element result = target.before("<span>Before</span>");

        // Assert
        assertEquals("<span>Before</span><p>Original</p>", doc.selectFirst("div").html());
        assertSame(target, result);
    }

    @Test
    void before_shouldHandleComplexHtmlStructure() {
        // Arrange
        Document doc = Jsoup.parse("<body><main>Content</main></body>");
        Element target = doc.selectFirst("main");

        // Act
        target.before("<header><h1>Title</h1></header>");

        // Assert
        assertEquals("header", doc.body().child(0).tagName());
        assertEquals("main", doc.body().child(1).tagName());
    }

    // after() Tests
    @Test
    void after_shouldInsertHtmlAsFollowingSibling() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>Original</p></div>");
        Element target = doc.selectFirst("p");

        // Act
        Element result = target.after("<span>After</span>");

        // Assert
        assertEquals("<p>Original</p><span>After</span>", doc.selectFirst("div").html());
        assertSame(target, result);
    }

    @Test
    void after_shouldMaintainCorrectSiblingOrder() {
        // Arrange
        Document doc = Jsoup.parse("<div><first></first><third></third></div>");
        Element target = doc.selectFirst("first");

        // Act
        target.after("<second></second>");

        // Assert
        assertEquals("first", doc.selectFirst("div").child(0).tagName());
        assertEquals("second", doc.selectFirst("div").child(1).tagName());
        assertEquals("third", doc.selectFirst("div").child(2).tagName());
    }

    // wrap() Tests
    @Test
    void wrap_shouldSurroundElementWithHtml() {
        // Arrange
        Document doc = Jsoup.parse("<div>Content</div>");
        Element target = doc.selectFirst("div");

        // Act
        Element result = target.wrap("<section class='container'></section>");

        // Assert
        assertEquals("section", target.parent().tagName());
        assertEquals("container", target.parent().className());
        assertSame(target, result);
    }

    @Test
    void wrap_shouldHandleNestedHtmlStructure() {
        // Arrange
        Document doc = Jsoup.parse("<span>Text</span>");
        Element target = doc.selectFirst("span");

        // Act
        target.wrap("<div><article><section></section></article></div>");

        // Assert
        Element parentSection = target.parent();
        assertEquals("section", parentSection.tagName());
        assertEquals("article", parentSection.parent().tagName());
        assertEquals("div", parentSection.parent().parent().tagName());
    }

    @Test
    void wrap_shouldReturnSameElementForChaining() {
        // Arrange
        Document doc = Jsoup.parse("<p>Paragraph</p>");
        Element target = doc.selectFirst("p");

        // Act
        Element result = target.wrap("<div></div>");

        // Assert
        assertSame(target, result);
    }
}