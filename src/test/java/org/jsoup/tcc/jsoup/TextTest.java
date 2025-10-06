package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTest {
    private final String reference = "<div id=div1><p>Hello</p><p>Another <b>element</b></p><div id=div2><img src=foo.png></div></div>";

    @Test
    public void testGetText() {
        Document doc = Jsoup.parse(reference);
        assertEquals("Hello Another element", doc.text());
        assertEquals("Another element", doc.getElementsByTag("p").get(1).text());
    }

    @Test
    public void testGetChildText() {
        Document doc = Jsoup.parse("<p>Hello <b>there</b> now");
        Element p = doc.select("p").first();
        assertEquals("Hello there now", p.text());
        assertEquals("Hello now", p.ownText());
    }

    @Test
    public void testNormalisesText() {
        String h = "<p>Hello<p>There.</p> \n <p>Here <b>is</b> \n s<b>om</b>e text.";
        Document doc = Jsoup.parse(h);
        String text = doc.text();
        assertEquals("Hello There. Here is some text.", text);
    }

    @Test
    public void testKeepsPreText() {
        String h = "<p>Hello \n \n there.</p> <div><pre>  What's \n\n  that?</pre>";
        Document doc = Jsoup.parse(h);
        assertEquals("Hello there.   What's \n\n  that?", doc.text());
    }

    @Test
    public void testKeepsPreTextInCode() {
        String h = "<pre><code>code\n\ncode</code></pre>";
        Document doc = Jsoup.parse(h);
        assertEquals("code\n\ncode", doc.text());
        assertEquals("<pre><code>code\n\ncode</code></pre>", doc.body().html());
    }

    @Test
    public void testKeepsPreTextAtDepth() {
        String h = "<pre><code><span><b>code\n\ncode</b></span></code></pre>";
        Document doc = Jsoup.parse(h);
        assertEquals("code\n\ncode", doc.text());
        assertEquals("<pre><code><span><b>code\n\ncode</b></span></code></pre>", doc.body().html());
    }

    @Test void doesNotWrapBlocksInPre() {
        // https://github.com/jhy/jsoup/issues/1891
        String h = "<pre><span><foo><div>TEST\n TEST</div></foo></span></pre>";
        Document doc = Jsoup.parse(h);
        assertEquals("TEST\n TEST", doc.wholeText());
        assertEquals(h, doc.body().html());
    }

    @Test
    public void testBrHasSpace() {
        Document doc = Jsoup.parse("<p>Hello<br>there</p>");
        assertEquals("Hello there", doc.text());
        assertEquals("Hello there", doc.select("p").first().ownText());

        doc = Jsoup.parse("<p>Hello <br> there</p>");
        assertEquals("Hello there", doc.text());
    }

    @Test
    public void testBrHasSpaceCaseSensitive() {
        Document doc = Jsoup.parse("<p>Hello<br>there<BR>now</p>", Parser.htmlParser().settings(ParseSettings.preserveCase));
        assertEquals("Hello there now", doc.text());
        assertEquals("Hello there now", doc.select("p").first().ownText());

        doc = Jsoup.parse("<p>Hello <br> there <BR> now</p>");
        assertEquals("Hello there now", doc.text());
    }

    @Test public void textHasSpacesAfterBlock() {
        Document doc = Jsoup.parse("<div>One</div><div>Two</div><span>Three</span><p>Fou<i>r</i></p>");
        String text = doc.text();
        String wholeText = doc.wholeText();

        assertEquals("One Two Three Four", text);
        assertEquals("OneTwoThreeFour",wholeText);

        assertEquals("OneTwo",Jsoup.parse("<span>One</span><span>Two</span>").text());
    }

    @Test
    public void testWholeText() {
        Document doc = Jsoup.parse("<p> Hello\nthere &nbsp;  </p>");
        assertEquals(" Hello\nthere    ", doc.wholeText());

        doc = Jsoup.parse("<p>Hello  \n  there</p>");
        assertEquals("Hello  \n  there", doc.wholeText());

        doc = Jsoup.parse("<p>Hello  <div>\n  there</div></p>");
        assertEquals("Hello  \n  there", doc.wholeText());
    }

    @Test void wholeTextRuns() {
        Document doc = Jsoup.parse("<div><p id=1></p><p id=2> </p><p id=3>.  </p>");

        Element p1 = doc.expectFirst("#1");
        Element p2 = doc.expectFirst("#2");
        Element p3 = doc.expectFirst("#3");

        assertEquals("", p1.wholeText());
        assertEquals(" ", p2.wholeText());
        assertEquals(".  ", p3.wholeText());
    }

    @Test void buttonTextHasSpace() {
        // https://github.com/jhy/jsoup/issues/2105
        Document doc = Jsoup.parse("<html><button>Reply</button><button>All</button></html>");
        String text = doc.body().text();
        String wholetext = doc.body().wholeText();

        assertEquals("Reply All", text);
        assertEquals("ReplyAll", wholetext);
    }
}
