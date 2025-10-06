package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {

    /** Test that the selected elements match exactly the specified IDs. */
    public static void assertSelectedIds(Elements els, String... ids) {
        assertNotNull(els);
        assertEquals(ids.length, els.size(), "Incorrect number of selected elements");
        for (int i = 0; i < ids.length; i++) {
            assertEquals(ids[i], els.get(i).id(), "Incorrect content at index");
        }
    }

    public static void assertSelectedOwnText(Elements els, String... ownTexts) {
        assertNotNull(els);
        assertEquals(ownTexts.length, els.size(), "Incorrect number of selected elements");
        for (int i = 0; i < ownTexts.length; i++) {
            assertEquals(ownTexts[i], els.get(i).ownText(), "Incorrect content at index");
        }
    }

    @Test
    public void testByTag() {
        // should be case-insensitive
        Elements els = Jsoup.parse("<div id=1><div id=2><p>Hello</p></div></div><DIV id=3>").select("DIV");
        assertSelectedIds(els, "1", "2", "3");

        Elements none = Jsoup.parse("<div id=1><div id=2><p>Hello</p></div></div><div id=3>").select("span");
        assertTrue(none.isEmpty());
    }

    @Test public void byEscapedTag() {
        // tested same result as js document.querySelector
        Document doc = Jsoup.parse("<p.p>One</p.p> <p\\p>Two</p\\p>");

        Element one = doc.expectFirst("p\\.p");
        assertEquals("One", one.text());

        Element two = doc.expectFirst("p\\\\p");
        assertEquals("Two", two.text());
    }

    @Test public void testById() {
        Elements els = Jsoup.parse("<div><p id=foo>Hello</p><p id=foo>Foo two!</p></div>").select("#foo");
        assertSelectedOwnText(els, "Hello", "Foo two!");

        Elements none = Jsoup.parse("<div id=1></div>").select("#foo");
        assertTrue(none.isEmpty());
    }

    @Test public void byEscapedId() {
        Document doc = Jsoup.parse("<p id='i.d'>One</p> <p id='i\\d'>Two</p> <p id='one-two/three'>Three</p>");

        Element one = doc.expectFirst("#i\\.d");
        assertEquals("One", one.text());

        Element two = doc.expectFirst("#i\\\\d");
        assertEquals("Two", two.text());

        Element thr = doc.expectFirst("p#one-two\\/three");
        assertEquals("Three", thr.text());
    }

    @Test public void testByClass() {
        Elements els = Jsoup.parse("<p id=0 class='ONE two'><p id=1 class='one'><p id=2 class='two'>").select("P.One");
        assertSelectedIds(els, "0", "1");

        Elements none = Jsoup.parse("<div class='one'></div>").select(".foo");
        assertTrue(none.isEmpty());

        Elements els2 = Jsoup.parse("<div class='One-Two' id=1></div>").select(".one-two");
        assertSelectedIds(els2, "1");
    }

    @Test public void byEscapedClass() {
        Document doc = Jsoup.parse("<p class='one.two#three'>One</p>");
        assertSelectedOwnText(doc.select("p.one\\.two\\#three"), "One");
    }

    @Test public void testByClassCaseInsensitive() {
        String html = "<p Class=foo>One <p Class=Foo>Two <p class=FOO>Three <p class=farp>Four";
        Elements elsFromClass = Jsoup.parse(html).select("P.Foo");
        Elements elsFromAttr = Jsoup.parse(html).select("p[class=foo]");

        assertEquals(elsFromAttr.size(), elsFromClass.size());
        assertSelectedOwnText(elsFromClass, "One", "Two", "Three");
    }


    @MultiLocaleExtension.MultiLocaleTest
    public void testByAttribute(Locale locale) {
        Locale.setDefault(locale);

        String h = "<div Title=Foo /><div Title=Bar /><div Style=Qux /><div title=Balim /><div title=SLIM />" +
                "<div data-name='with spaces'/>";
        Document doc = Jsoup.parse(h);

        Elements withTitle = doc.select("[title]");
        assertEquals(4, withTitle.size());

        Elements foo = doc.select("[TITLE=foo]");
        assertEquals(1, foo.size());

        Elements foo2 = doc.select("[title=\"foo\"]");
        assertEquals(1, foo2.size());

        Elements foo3 = doc.select("[title=\"Foo\"]");
        assertEquals(1, foo3.size());

        Elements dataName = doc.select("[data-name=\"with spaces\"]");
        assertEquals(1, dataName.size());
        assertEquals("with spaces", dataName.first().attr("data-name"));

        Elements not = doc.select("div[title!=bar]");
        assertEquals(5, not.size());
        assertEquals("Foo", not.first().attr("title"));

        Elements starts = doc.select("[title^=ba]");
        assertEquals(2, starts.size());
        assertEquals("Bar", starts.first().attr("title"));
        assertEquals("Balim", starts.last().attr("title"));

        Elements ends = doc.select("[title$=im]");
        assertEquals(2, ends.size());
        assertEquals("Balim", ends.first().attr("title"));
        assertEquals("SLIM", ends.last().attr("title"));

        Elements contains = doc.select("[title*=i]");
        assertEquals(2, contains.size());
        assertEquals("Balim", contains.first().attr("title"));
        assertEquals("SLIM", contains.last().attr("title"));
    }

    @Test public void testNamespacedTag() {
        Document doc = Jsoup.parse("<div><abc:def id=1>Hello</abc:def></div> <abc:def class=bold id=2>There</abc:def>");
        Elements byTag = doc.select("abc|def");
        assertSelectedIds(byTag, "1", "2");

        Elements byAttr = doc.select(".bold");
        assertSelectedIds(byAttr, "2");

        Elements byTagAttr = doc.select("abc|def.bold");
        assertSelectedIds(byTagAttr, "2");

        Elements byContains = doc.select("abc|def:contains(e)");
        assertSelectedIds(byContains, "1", "2");
    }

    @Test public void testWildcardNamespacedTag() {
        Document doc = Jsoup.parse("<div><abc:def id=1>Hello</abc:def></div> <abc:def class=bold id=2>There</abc:def>");
        Elements byTag = doc.select("*|def");
        assertSelectedIds(byTag, "1", "2");

        Elements byAttr = doc.select(".bold");
        assertSelectedIds(byAttr, "2");

        Elements byTagAttr = doc.select("*|def.bold");
        assertSelectedIds(byTagAttr, "2");

        Elements byContains = doc.select("*|def:contains(e)");
        assertSelectedIds(byContains, "1", "2");
    }

    @Test public void testNamespacedWildcardTag() {
        // https://github.com/jhy/jsoup/issues/1811
        Document doc = Jsoup.parse("<p>One</p> <ac:p id=2>Two</ac:p> <ac:img id=3>Three</ac:img>");
        Elements byNs = doc.select("ac|*");
        assertSelectedIds(byNs, "2", "3");
    }

    @Test public void testWildcardNamespacedXmlTag() {
        Document doc = Jsoup.parse(
                "<div><Abc:Def id=1>Hello</Abc:Def></div> <Abc:Def class=bold id=2>There</abc:def>",
                "", Parser.xmlParser()
        );

        Elements byTag = doc.select("*|Def");
        assertSelectedIds(byTag, "1", "2");

        Elements byAttr = doc.select(".bold");
        assertSelectedIds(byAttr, "2");

        Elements byTagAttr = doc.select("*|Def.bold");
        assertSelectedIds(byTagAttr, "2");

        Elements byContains = doc.select("*|Def:contains(e)");
        assertSelectedIds(byContains, "1", "2");
    }

    @Test public void testWildCardNamespacedCaseVariations() {
        Document doc = Jsoup.parse("<One:Two>One</One:Two><three:four>Two</three:four>", "", Parser.xmlParser());
        Elements els1 = doc.select("One|Two");
        Elements els2 = doc.select("one|two");
        Elements els3 = doc.select("Three|Four");
        Elements els4 = doc.select("three|Four");

        assertEquals(els1, els2);
        assertEquals(els3, els4);
        assertEquals("One", els1.text());
        assertEquals(1, els1.size());
        assertEquals("Two", els3.text());
        assertEquals(1, els2.size());
    }
}
