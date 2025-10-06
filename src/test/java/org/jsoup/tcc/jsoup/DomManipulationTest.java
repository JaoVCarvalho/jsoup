package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomManipulationTest {

    @Test
    public void before() {
        Document doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        Element p1 = doc.select("p").first();
        p1.before("<div>one</div><div>two</div>");
        assertEquals("<div><div>one</div><div>two</div><p>Hello</p><p>There</p></div>", TextUtil.stripNewlines(doc.body().html()));

        doc.select("p").last().before("<p>Three</p><!-- four -->");
        assertEquals("<div><div>one</div><div>two</div><p>Hello</p><p>Three</p><!-- four --><p>There</p></div>", TextUtil.stripNewlines(doc.body().html()));
    }

    @Test
    public void after() {
        Document doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        Element p1 = doc.select("p").first();
        p1.after("<div>one</div><div>two</div>");
        assertEquals("<div><p>Hello</p><div>one</div><div>two</div><p>There</p></div>", TextUtil.stripNewlines(doc.body().html()));

        doc.select("p").last().after("<p>Three</p><!-- four -->");
        assertEquals("<div><p>Hello</p><div>one</div><div>two</div><p>There</p><p>Three</p><!-- four --></div>", TextUtil.stripNewlines(doc.body().html()));
    }

    @Test
    public void testHasText() {
        Document doc = Jsoup.parse("<div><p>Hello</p><p></p></div>");
        Element div = doc.select("div").first();
        Elements ps = doc.select("p");

        assertTrue(div.hasText());
        assertTrue(ps.first().hasText());
        assertFalse(ps.last().hasText());
    }

    @Test
    public void testWrap() {
        Document doc = Jsoup.parse("<div><p>Hello</p><p>There</p></div>");
        Element p = doc.select("p").first();
        p.wrap("<div class='head'></div>");
        assertEquals("<div><div class=\"head\"><p>Hello</p></div><p>There</p></div>", TextUtil.stripNewlines(doc.body().html()));

        Element ret = p.wrap("<div><div class=foo></div><p>What?</p></div>");
        assertEquals("<div><div class=\"head\"><div><div class=\"foo\"><p>Hello</p></div><p>What?</p></div></div><p>There</p></div>",
                TextUtil.stripNewlines(doc.body().html()));

        assertEquals(ret, p);
    }

    @Test
    public void testWrapNoop() {
        Document doc = Jsoup.parse("<div><p>Hello</p></div>");
        Node p = doc.select("p").first();
        Node wrapped = p.wrap("Some junk");
        assertSame(p, wrapped);
        assertEquals("<div><p>Hello</p></div>", TextUtil.stripNewlines(doc.body().html()));
        // should be a NOOP
    }

    @Test
    public void testWrapOnOrphan() {
        Element orphan = new Element("span").text("Hello!");
        assertFalse(orphan.hasParent());
        Element wrapped = orphan.wrap("<div></div> There!");
        assertSame(orphan, wrapped);
        assertTrue(orphan.hasParent()); // should now be in the DIV
        assertNotNull(orphan.parent());
        assertEquals("div", orphan.parent().tagName());
        assertEquals("<div>\n <span>Hello!</span>\n</div>", orphan.parent().outerHtml());
    }

    @Test
    public void testWrapArtificialStructure() {
        // div normally couldn't get into a p, but explicitly want to wrap
        Document doc = Jsoup.parse("<p>Hello <i>there</i> now.");
        Element i = doc.expectFirst("i");
        i.wrap("<div id=id1></div> quite");
        assertEquals("div", i.parent().tagName());
        assertEquals("<p>Hello\n <div id=\"id1\">\n  <i>there</i>\n </div>\n quite now.</p>",(doc.body().html()));
        // gives us a TextNode seq of "quite" and " now"; make sure not to collapse to "quitenow" when pretty print.
    }
}
