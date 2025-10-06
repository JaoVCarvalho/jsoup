package org.jsoup.tcc.llm.siblingsTestTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SiblingsTest_Prompt2 {

    @Test
    void previousElementSibling_shouldReturnPreviousElement_whenPreviousSiblingIsElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><b>last</b></div>");
        Element lastElement = doc.selectFirst("b");

        // Act
        Element previousSibling = lastElement.previousElementSibling();

        // Assert
        assertNotNull(previousSibling, "Should find previous element sibling");
        assertEquals("span", previousSibling.tagName(), "Previous sibling should be span element");
    }

    @Test
    void previousElementSibling_shouldSkipNonElementNodes_whenPreviousSiblingsAreTextNodes() {
        // Arrange
        Document doc = Jsoup.parse("<div>Text<p>first</p>More text<span>target</span></div>");
        Element targetElement = doc.selectFirst("span");

        // Act
        Element previousSibling = targetElement.previousElementSibling();

        // Assert
        assertNotNull(previousSibling, "Should skip text nodes and find previous element");
        assertEquals("p", previousSibling.tagName(), "Should find p element skipping text nodes");
    }

    @Test
    void previousElementSibling_shouldReturnNull_whenNoPreviousElementExists() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>second</span></div>");
        Element firstElement = doc.selectFirst("p");

        // Act
        Element previousSibling = firstElement.previousElementSibling();

        // Assert
        assertNull(previousSibling, "Should return null for first element sibling");
    }

    @Test
    void previousElementSibling_shouldReturnNull_whenElementIsOrphan() {
        // Arrange
        Element orphanElement = new Element("div");

        // Act
        Element previousSibling = orphanElement.previousElementSibling();

        // Assert
        assertNull(previousSibling, "Should return null for orphan element");
    }

    @Test
    void nextElementSibling_shouldReturnNextElement_whenNextSiblingIsElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><b>last</b></div>");
        Element firstElement = doc.selectFirst("p");

        // Act
        Element nextSibling = firstElement.nextElementSibling();

        // Assert
        assertNotNull(nextSibling, "Should find next element sibling");
        assertEquals("span", nextSibling.tagName(), "Next sibling should be span element");
    }

    @Test
    void nextElementSibling_shouldSkipNonElementNodes_whenNextSiblingsAreTextNodes() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>target</p>Text<span>next</span>More text</div>");
        Element targetElement = doc.selectFirst("p");

        // Act
        Element nextSibling = targetElement.nextElementSibling();

        // Assert
        assertNotNull(nextSibling, "Should skip text nodes and find next element");
        assertEquals("span", nextSibling.tagName(), "Should find span element skipping text nodes");
    }

    @Test
    void nextElementSibling_shouldReturnNull_whenNoNextElementExists() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>last</span></div>");
        Element lastElement = doc.selectFirst("span");

        // Act
        Element nextSibling = lastElement.nextElementSibling();

        // Assert
        assertNull(nextSibling, "Should return null for last element sibling");
    }

    @Test
    void nextElementSibling_shouldReturnNull_whenElementIsOrphan() {
        // Arrange
        Element orphanElement = new Element("div");

        // Act
        Element nextSibling = orphanElement.nextElementSibling();

        // Assert
        assertNull(nextSibling, "Should return null for orphan element");
    }

    @Test
    void firstElementSibling_shouldReturnFirstElement_whenMultipleSiblingsExist() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><b>last</b></div>");
        Element lastElement = doc.selectFirst("b");

        // Act
        Element firstSibling = lastElement.firstElementSibling();

        // Assert
        assertNotNull(firstSibling, "Should find first element sibling");
        assertEquals("p", firstSibling.tagName(), "First sibling should be p element");
    }

    @Test
    void firstElementSibling_shouldReturnSelf_whenElementIsOnlyChild() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>only child</p></div>");
        Element onlyChild = doc.selectFirst("p");

        // Act
        Element firstSibling = onlyChild.firstElementSibling();

        // Assert
        assertSame(onlyChild, firstSibling, "Should return self when no other siblings exist");
    }

    @Test
    void firstElementSibling_shouldReturnSelf_whenElementIsOrphan() {
        // Arrange
        Element orphanElement = new Element("div");

        // Act
        Element firstSibling = orphanElement.firstElementSibling();

        // Assert
        assertSame(orphanElement, firstSibling, "Should return self for orphan element");
    }

    @Test
    void lastElementSibling_shouldReturnLastElement_whenMultipleSiblingsExist() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><b>last</b></div>");
        Element firstElement = doc.selectFirst("p");

        // Act
        Element lastSibling = firstElement.lastElementSibling();

        // Assert
        assertNotNull(lastSibling, "Should find last element sibling");
        assertEquals("b", lastSibling.tagName(), "Last sibling should be b element");
    }

    @Test
    void lastElementSibling_shouldReturnSelf_whenElementIsOnlyChild() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>only child</p></div>");
        Element onlyChild = doc.selectFirst("p");

        // Act
        Element lastSibling = onlyChild.lastElementSibling();

        // Assert
        assertSame(onlyChild, lastSibling, "Should return self when no other siblings exist");
    }

    @Test
    void lastElementSibling_shouldReturnSelf_whenElementIsOrphan() {
        // Arrange
        Element orphanElement = new Element("div");

        // Act
        Element lastSibling = orphanElement.lastElementSibling();

        // Assert
        assertSame(orphanElement, lastSibling, "Should return self for orphan element");
    }

    @Test
    void parents_shouldReturnAllAncestorsUpToRoot_whenElementHasMultipleParents() {
        // Arrange
        Document doc = Jsoup.parse("<html><body><div><p><span>text</span></p></div></body></html>");
        Element span = doc.selectFirst("span");

        // Act
        Elements parents = span.parents();

        // Assert
        assertEquals(3, parents.size(), "Should return 3 parents (p, div, body)");
        assertEquals("p", parents.get(0).tagName(), "First parent should be p");
        assertEquals("div", parents.get(1).tagName(), "Second parent should be div");
        assertEquals("body", parents.get(2).tagName(), "Third parent should be body");
    }

    @Test
    void parents_shouldReturnSingleParent_whenElementIsDirectChildOfBody() {
        // Arrange
        Document doc = Jsoup.parse("<body><p>text</p></body>");
        Element p = doc.selectFirst("p");

        // Act
        Elements parents = p.parents();

        // Assert
        assertEquals(1, parents.size(), "Should return only body parent");
        assertEquals("body", parents.get(0).tagName(), "Parent should be body element");
    }

    @Test
    void parents_shouldReturnEmptyList_whenElementIsRoot() {
        // Arrange
        Document doc = Jsoup.parse("<html></html>");
        Element html = doc.selectFirst("html");

        // Act
        Elements parents = html.parents();

        // Assert
        assertTrue(parents.isEmpty(), "Should return empty list for root element");
    }

    @Test
    void elementSiblingIndex_shouldReturnCorrectIndex_whenElementIsInMiddlePosition() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>second</span><b>third</b></div>");
        Element middleElement = doc.selectFirst("span");

        // Act
        int index = middleElement.elementSiblingIndex();

        // Assert
        assertEquals(1, index, "Should return index 1 for middle element");
    }

    @Test
    void elementSiblingIndex_shouldReturnZero_whenElementIsFirstChild() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>second</span></div>");
        Element firstElement = doc.selectFirst("p");

        // Act
        int index = firstElement.elementSiblingIndex();

        // Assert
        assertEquals(0, index, "Should return index 0 for first element");
    }

    @Test
    void elementSiblingIndex_shouldReturnZero_whenElementIsOnlyChild() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>only child</p></div>");
        Element onlyChild = doc.selectFirst("p");

        // Act
        int index = onlyChild.elementSiblingIndex();

        // Assert
        assertEquals(0, index, "Should return index 0 for only child");
    }

    @Test
    void elementSiblingIndex_shouldReturnZero_whenElementIsOrphan() {
        // Arrange
        Element orphanElement = new Element("div");

        // Act
        int index = orphanElement.elementSiblingIndex();

        // Assert
        assertEquals(0, index, "Should return index 0 for orphan element");
    }
}