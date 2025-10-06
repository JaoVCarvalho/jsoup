package org.jsoup.tcc.llm.siblingsTestTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SiblingsTest_Prompt3 {

    @Test
    void previousElementSibling_shouldReturnPreviousElement_whenSiblingExists() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element secondPara = doc.select("p").get(1);
        Element firstPara = doc.select("p").get(0);

        // Act
        Element previous = secondPara.previousElementSibling();

        // Assert
        assertEquals(firstPara, previous);
    }

    @Test
    void previousElementSibling_shouldReturnNull_whenNoPreviousElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p></div>");
        Element firstPara = doc.select("p").get(0);

        // Act
        Element previous = firstPara.previousElementSibling();

        // Assert
        assertNull(previous);
    }

    @Test
    void nextElementSibling_shouldReturnNextElement_whenSiblingExists() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element secondPara = doc.select("p").get(1);

        // Act
        Element next = firstPara.nextElementSibling();

        // Assert
        assertEquals(secondPara, next);
    }

    @Test
    void nextElementSibling_shouldReturnNull_whenNoNextElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p></div>");
        Element lastPara = doc.select("p").get(1);

        // Act
        Element next = lastPara.nextElementSibling();

        // Assert
        assertNull(next);
    }

    @Test
    void firstElementSibling_shouldReturnFirstElement_whenMultipleSiblingsExist() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><p>last</p></div>");
        Element lastPara = doc.select("p").get(1);
        Element firstPara = doc.select("p").get(0);

        // Act
        Element firstSibling = lastPara.firstElementSibling();

        // Assert
        assertEquals(firstPara, firstSibling);
    }

    @Test
    void firstElementSibling_shouldReturnSelf_whenOrphanElement() {
        // Arrange
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.selectFirst("p");

        // Act
        Element firstSibling = orphanPara.firstElementSibling();

        // Assert
        assertEquals(orphanPara, firstSibling);
    }

    @Test
    void lastElementSibling_shouldReturnLastElement_whenMultipleSiblingsExist() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><p>last</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element lastPara = doc.select("p").get(1);

        // Act
        Element lastSibling = firstPara.lastElementSibling();

        // Assert
        assertEquals(lastPara, lastSibling);
    }

    @Test
    void lastElementSibling_shouldReturnSelf_whenOrphanElement() {
        // Arrange
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.selectFirst("p");

        // Act
        Element lastSibling = orphanPara.lastElementSibling();

        // Assert
        assertEquals(orphanPara, lastSibling);
    }

    @Test
    void parents_shouldReturnAllAncestorsInOrder_whenElementHasParents() {
        // Arrange
        Document doc = Jsoup.parse("<html><body><div><p>test</p></div></body></html>");
        Element para = doc.selectFirst("p");

        // Act
        Elements parents = para.parents();

        // Assert
        assertEquals(3, parents.size());
        assertEquals("div", parents.get(0).tagName());
        assertEquals("body", parents.get(1).tagName());
        assertEquals("html", parents.get(2).tagName());
    }

    @Test
    void parents_shouldReturnEmptyList_whenElementIsRoot() {
        // Arrange
        Document doc = Jsoup.parse("<html><body>test</body></html>");
        Element html = doc.selectFirst("html");

        // Act
        Elements parents = html.parents();

        // Assert
        assertTrue(parents.isEmpty());
    }

    @Test
    void elementSiblingIndex_shouldReturnZero_whenFirstElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element firstPara = doc.select("p").get(0);

        // Act
        int index = firstPara.elementSiblingIndex();

        // Assert
        assertEquals(0, index);
    }

    @Test
    void elementSiblingIndex_shouldReturnCorrectIndex_whenMiddleElement() {
        // Arrange
        Document doc = Jsoup.parse("<div><p>first</p><span>middle</span><p>second</p></div>");
        Element secondPara = doc.select("p").get(1);

        // Act
        int index = secondPara.elementSiblingIndex();

        // Assert
        assertEquals(2, index); // p=0, span=1, p=2
    }

    @Test
    void elementSiblingIndex_shouldReturnZero_whenOrphanElement() {
        // Arrange
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.selectFirst("p");

        // Act
        int index = orphanPara.elementSiblingIndex();

        // Assert
        assertEquals(0, index);
    }

    @Test
    void siblingMethods_shouldIgnoreNonElementNodes() {
        // Arrange
        Document doc = Jsoup.parse("<div>Text<!-- comment --><p>first</p>More text<p>second</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element secondPara = doc.select("p").get(1);

        // Act & Assert - should navigate between elements ignoring text/comment nodes
        assertEquals(secondPara, firstPara.nextElementSibling());
        assertEquals(firstPara, secondPara.previousElementSibling());
    }
}