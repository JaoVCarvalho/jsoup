package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SiblingsTest {
    @Test
    public void testGetSiblings() {
        Document doc = Jsoup.parse("<div><p>Hello<p id=1>there<p>this<p>is<p>an<p id=last>element</div>");
        Element p = doc.getElementById("1");
        assertEquals("there", p.text());
        assertEquals("Hello", p.previousElementSibling().text());
        assertEquals("this", p.nextElementSibling().text());
        assertEquals("Hello", p.firstElementSibling().text());
        assertEquals("element", p.lastElementSibling().text());
        assertNull(p.lastElementSibling().nextElementSibling());
        assertNull(p.firstElementSibling().previousElementSibling());
    }

    @Test public void nextElementSibling() {
        Document doc = Jsoup.parse("<p>One</p>Two<p>Three</p>");
        Element el = doc.expectFirst("p");
        assertNull(el.previousElementSibling());
        Element next = el.nextElementSibling();
        assertNotNull(next);
        assertEquals("Three", next.text());
        assertNull(next.nextElementSibling());
    }

    @Test public void prevElementSibling() {
        Document doc = Jsoup.parse("<p>One</p>Two<p>Three</p>");
        Element el = doc.expectFirst("p:contains(Three)");
        assertNull(el.nextElementSibling());
        Element prev = el.previousElementSibling();
        assertNotNull(prev);
        assertEquals("One", prev.text());
        assertNull(prev.previousElementSibling());
    }

    @Test
    public void testGetSiblingsWithDuplicateContent() {
        Document doc = Jsoup.parse("<div><p>Hello<p id=1>there<p>this<p>this<p>is<p>an<p id=last>element</div>");
        Element p = doc.getElementById("1");
        assertEquals("there", p.text());
        assertEquals("Hello", p.previousElementSibling().text());
        assertEquals("this", p.nextElementSibling().text());
        assertEquals("this", p.nextElementSibling().nextElementSibling().text());
        assertEquals("is", p.nextElementSibling().nextElementSibling().nextElementSibling().text());
        assertEquals("Hello", p.firstElementSibling().text());
        assertEquals("element", p.lastElementSibling().text());
    }

    @Test
    public void testFirstElementSiblingOnOrphan() {
        Element p = new Element("p");
        assertSame(p, p.firstElementSibling());
        assertSame(p, p.lastElementSibling());
    }

    @Test
    public void testFirstAndLastSiblings() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three");
        Element div = doc.expectFirst("div");
        Element one = div.child(0);
        Element two = div.child(1);
        Element three = div.child(2);

        assertSame(one, one.firstElementSibling());
        assertSame(one, two.firstElementSibling());
        assertSame(three, three.lastElementSibling());
        assertSame(three, two.lastElementSibling());
        assertNull(one.previousElementSibling());
        assertNull(three.nextElementSibling());
    }

    @Test
    public void testGetParents() {
        Document doc = Jsoup.parse("<div><p>Hello <span>there</span></div>");
        Element span = doc.select("span").first();
        Elements parents = span.parents();

        assertEquals(4, parents.size());
        assertEquals("p", parents.get(0).tagName());
        assertEquals("div", parents.get(1).tagName());
        assertEquals("body", parents.get(2).tagName());
        assertEquals("html", parents.get(3).tagName());

        Element orphan = new Element("p");
        Elements none = orphan.parents();
        assertEquals(0, none.size());
    }

    @Test
    public void testElementSiblingIndex() {
        Document doc = Jsoup.parse("<div><p>One</p>...<p>Two</p>...<p>Three</p>");
        Elements ps = doc.select("p");
        assertEquals(0, ps.get(0).elementSiblingIndex());
        assertEquals(1, ps.get(1).elementSiblingIndex());
        assertEquals(2, ps.get(2).elementSiblingIndex());
    }

    @Test
    public void testElementSiblingIndexSameContent() {
        Document doc = Jsoup.parse("<div><p>One</p>...<p>One</p>...<p>One</p>");
        Elements ps = doc.select("p");
        assertEquals(0, ps.get(0).elementSiblingIndex());
        assertEquals(1, ps.get(1).elementSiblingIndex());
        assertEquals(2, ps.get(2).elementSiblingIndex());
    }
}
