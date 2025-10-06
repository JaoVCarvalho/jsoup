package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdTest {
    @Test
    public void testId() {
        Document doc = Jsoup.parse("<div id=Foo>");
        Element el = doc.selectFirst("div");
        assertEquals("Foo", el.id());
    }

    @Test
    public void testSetId() {
        Document doc = Jsoup.parse("<div id=Boo>");
        Element el = doc.selectFirst("div");
        el.id("Foo");
        assertEquals("Foo", el.id());
    }
}
