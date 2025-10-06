package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CloneTest {
    @Test public void testClone() {
        Document doc = Jsoup.parse("<title>Hello</title> <p>One<p>Two");
        Document clone = doc.clone();
        assertNotSame(doc, clone);
        assertTrue(doc.hasSameValue(clone));
        assertSame(doc.parser(), clone.parser());
        assertNotSame(doc.outputSettings(), clone.outputSettings());

        assertEquals("<html><head><title>Hello</title></head><body><p>One</p><p>Two</p></body></html>", TextUtil.stripNewlines(clone.html()));
        clone.title("Hello there");
        assertFalse(doc.hasSameValue(clone));
        clone.expectFirst("p").text("One more").attr("id", "1");
        assertEquals("<html><head><title>Hello there</title></head><body><p id=\"1\">One more</p><p>Two</p></body></html>", TextUtil.stripNewlines(clone.html()));
        assertEquals("<html><head><title>Hello</title></head><body><p>One</p><p>Two</p></body></html>", TextUtil.stripNewlines(doc.html()));
    }

    @Test public void testClonesDeclarations() {
        Document doc = Jsoup.parse("<!DOCTYPE html><html><head><title>Doctype test");
        Document clone = doc.clone();

        assertEquals(doc.html(), clone.html());
        assertEquals("<!doctype html><html><head><title>Doctype test</title></head><body></body></html>",
                TextUtil.stripNewlines(clone.html()));
    }

    @Test
    public void testOverflowClone() {
        StringBuilder sb = new StringBuilder();
        sb.append("<head><base href='https://jsoup.org/'>");
        for (int i = 0; i < 100000; i++) {
            sb.append("<div>");
        }
        sb.append("<p>Hello <a href='/example.html'>there</a>");

        Document doc = Jsoup.parse(sb.toString());

        String expectedLink = "https://jsoup.org/example.html";
        assertEquals(expectedLink, doc.selectFirst("a").attr("abs:href"));
        Document clone = doc.clone();
        doc.hasSameValue(clone);
        assertEquals(expectedLink, clone.selectFirst("a").attr("abs:href"));
    }
}
