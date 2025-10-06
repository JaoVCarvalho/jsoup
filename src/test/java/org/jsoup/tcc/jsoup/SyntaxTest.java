package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SyntaxTest {
    @Test
    public void testHtmlAndXmlSyntax() {
        String h = "<!DOCTYPE html><body><img async checked='checked' src='&<>\"'>&lt;&gt;&amp;&quot;<foo />bar";
        Parser parser = Parser.htmlParser();
        parser.tagSet().valueOf("foo", Parser.NamespaceHtml).set(Tag.SelfClose); // customize foo to allow self close
        Document doc = Jsoup.parse(h, parser);

        doc.outputSettings().syntax(Document.OutputSettings.Syntax.html);
        assertEquals("<!doctype html>\n" +
                "<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                "  <img async checked src=\"&amp;&lt;&gt;&quot;\">&lt;&gt;&amp;\"<foo></foo>bar\n" + // html won't include self-closing
                " </body>\n" +
                "</html>", doc.html());

        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        assertEquals("<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                "  <img async=\"\" checked=\"checked\" src=\"&amp;&lt;&gt;&quot;\" />&lt;&gt;&amp;\"<foo />bar\n" + // xml will
                " </body>\n" +
                "</html>", doc.html());
    }

    @Test public void htmlParseDefaultsToHtmlOutputSyntax() {
        Document doc = Jsoup.parse("x");
        assertEquals(Document.OutputSettings.Syntax.html, doc.outputSettings().syntax());
    }
}
