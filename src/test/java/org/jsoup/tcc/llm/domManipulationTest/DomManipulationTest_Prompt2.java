package org.jsoup.tcc.llm.domManipulationTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomManipulationTest_Prompt2 {

    // hasText() tests
    @Test
    void hasText_shouldReturnTrue_whenElementHasDirectNonBlankText() {
        // Arrange
        Document doc = Jsoup.parse("<div>Hello</div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertTrue(element.hasText(), "should have text when element contains non-blank text directly");
    }

    @Test
    void hasText_shouldReturnTrue_whenChildElementHasNonBlankText() {
        // Arrange
        Document doc = Jsoup.parse("<div><span>Hello</span></div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertTrue(element.hasText(), "should have text when child element contains non-blank text");
    }

    @Test
    void hasText_shouldReturnFalse_whenElementIsEmpty() {
        // Arrange
        Document doc = Jsoup.parse("<div></div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasText(), "should not have text when element is completely empty");
    }

    @Test
    void hasText_shouldReturnFalse_whenElementContainsOnlyWhitespace() {
        // Arrange
        Document doc = Jsoup.parse("<div>   \t\n   </div>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasText(), "should not have text when element contains only whitespace");
    }

    // before() tests
    @Test
    void before_shouldInsertHtmlAsPrecedingSibling() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");
        String htmlToInsert = "<span>before</span>";

        // Act
        Element result = target.before(htmlToInsert);

        // Assert
        assertEquals("<span>before</span><p>original</p>", doc.selectFirst("div").html(),
                "should insert HTML as preceding sibling");
        assertEquals(target, result, "should return the original element for chaining");
    }

    @Test
    void before_shouldHandleEmptyHtml() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");

        // Act
        Element result = target.before("");

        // Assert
        assertEquals("<p>original</p>", doc.selectFirst("div").html(),
                "should not change DOM when empty HTML is inserted");
        assertEquals(target, result, "should return the original element for chaining");
    }

    // after() tests
    @Test
    void after_shouldInsertHtmlAsFollowingSibling() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");
        String htmlToInsert = "<span>after</span>";

        // Act
        Element result = target.after(htmlToInsert);

        // Assert
        assertEquals("<p>original</p><span>after</span>", doc.selectFirst("div").html(),
                "should insert HTML as following sibling");
        assertEquals(target, result, "should return the original element for chaining");
    }

    @Test
    void after_shouldHandleEmptyHtml() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");

        // Act
        Element result = target.after("");

        // Assert
        assertEquals("<p>original</p>", doc.selectFirst("div").html(),
                "should not change DOM when empty HTML is inserted");
        assertEquals(target, result, "should return the original element for chaining");
    }

    // wrap() tests
    @Test
    void wrap_shouldSurroundElementWithHtml() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");
        String wrapperHtml = "<div class='wrapper'></div>";

        // Act
        Element result = target.wrap(wrapperHtml);

        // Assert
        Element wrapper = doc.selectFirst("div.wrapper");
        assertNotNull(wrapper, "should create wrapper element");
        assertEquals(target, wrapper.selectFirst("p"), "should place original element inside wrapper");
        assertEquals(target, result, "should return the original element for chaining");
    }

    @Test
    void wrap_shouldHandleComplexHtmlStructure() {
        // Arrange
        Document doc = Jsoup.parse("<body><p>original</p></body>");
        Element target = doc.selectFirst("p");
        String wrapperHtml = "<div><section><article></article></section></div>";

        // Act
        Element result = target.wrap(wrapperHtml);

        // Assert
        Element article = doc.selectFirst("article");
        assertNotNull(article, "should create complex wrapper structure");
        assertEquals(target, article.selectFirst("p"), "should place original element in deepest part of wrapper");
        assertEquals(target, result, "should return the original element for chaining");
    }

    @Test
    void wrap_shouldHandleEmptyHtml() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>original</p></div>");
        Element target = doc.selectFirst("p");
        String originalHtml = doc.html();

        // Act
        Element result = target.wrap("");

        // Assert
        assertEquals(originalHtml, doc.html(), "should not change DOM when empty HTML is used for wrapping");
        assertEquals(target, result, "should return the original element for chaining");
    }
}