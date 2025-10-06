package org.jsoup.tcc.llm.siblingsTestTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SiblingsTest_Prompt1 {

    @Test
    void previousElementSibling_returnsPreviousElement() {
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element secondPara = doc.select("p").get(1);
        Element firstPara = doc.select("p").get(0);

        Element result = secondPara.previousElementSibling();

        assertEquals(firstPara, result);
    }

    @Test
    void previousElementSibling_returnsNullWhenNoPreviousElement() {
        Document doc = Jsoup.parse("<div><p>first</p></div>");
        Element firstPara = doc.select("p").first();

        Element result = firstPara.previousElementSibling();

        assertNull(result);
    }

    @Test
    void nextElementSibling_returnsNextElement() {
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element secondPara = doc.select("p").get(1);

        Element result = firstPara.nextElementSibling();

        assertEquals(secondPara, result);
    }

    @Test
    void nextElementSibling_returnsNullWhenNoNextElement() {
        Document doc = Jsoup.parse("<div><p>first</p></div>");
        Element firstPara = doc.select("p").first();

        Element result = firstPara.nextElementSibling();

        assertNull(result);
    }

    @Test
    void firstElementSibling_returnsFirstElementChildOfParent() {
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element thirdPara = doc.select("p").get(2);
        Element firstPara = doc.select("p").get(0);

        Element result = thirdPara.firstElementSibling();

        assertEquals(firstPara, result);
    }

    @Test
    void firstElementSibling_returnsSelfWhenOrphan() {
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.select("p").first();
        doc.select("p").remove();

        Element result = orphanPara.firstElementSibling();

        assertEquals(orphanPara, result);
    }

    @Test
    void lastElementSibling_returnsLastElementChildOfParent() {
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element thirdPara = doc.select("p").get(2);

        Element result = firstPara.lastElementSibling();

        assertEquals(thirdPara, result);
    }

    @Test
    void lastElementSibling_returnsSelfWhenOrphan() {
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.select("p").first();
        doc.select("p").remove();

        Element result = orphanPara.lastElementSibling();

        assertEquals(orphanPara, result);
    }

    @Test
    void parents_returnsAllAncestorsExcludingRoot() {
        Document doc = Jsoup.parse("<html><body><div><p>text</p></div></body></html>");
        Element para = doc.select("p").first();

        Elements parents = para.parents();

        assertEquals(2, parents.size());
        assertEquals("div", parents.get(0).tagName());
        assertEquals("body", parents.get(1).tagName());
    }

    @Test
    void parents_returnsEmptyWhenNoParents() {
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.select("p").first();
        doc.select("p").remove();

        Elements parents = orphanPara.parents();

        assertEquals(0, parents.size());
    }

    @Test
    void elementSiblingIndex_returnsCorrectIndex() {
        Document doc = Jsoup.parse("<div><p>first</p><p>second</p><p>third</p></div>");
        Element firstPara = doc.select("p").get(0);
        Element secondPara = doc.select("p").get(1);
        Element thirdPara = doc.select("p").get(2);

        assertEquals(0, firstPara.elementSiblingIndex());
        assertEquals(1, secondPara.elementSiblingIndex());
        assertEquals(2, thirdPara.elementSiblingIndex());
    }

    @Test
    void elementSiblingIndex_returnsZeroWhenNoParent() {
        Document doc = Jsoup.parse("<p>orphan</p>");
        Element orphanPara = doc.select("p").first();
        doc.select("p").remove();

        assertEquals(0, orphanPara.elementSiblingIndex());
    }
}