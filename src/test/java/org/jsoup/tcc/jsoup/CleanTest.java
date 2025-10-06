package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CleanTest {
    @Test
    public void testRemoveTags() {
        String h = "<div><p><A HREF='HTTP://nice.com'>Nice</a></p><blockquote>Hello</blockquote>";
        String cleanHtml = Jsoup.clean(h, Safelist.basic().removeTags("a"));

        assertEquals("<p>Nice</p><blockquote>Hello</blockquote>", TextUtil.stripNewlines(cleanHtml));
    }

    @Test public void testRemoveAttributes() {
        String h = "<div><p>Nice</p><blockquote cite='http://example.com/quotations'>Hello</blockquote>";
        String cleanHtml = Jsoup.clean(h, Safelist.basic().removeAttributes("blockquote", "cite"));

        assertEquals("<p>Nice</p><blockquote>Hello</blockquote>", TextUtil.stripNewlines(cleanHtml));
    }

    @Test void allAttributes() {
        String h = "<div class=foo data=true><p class=bar>Text</p></div><blockquote cite='https://example.com'>Foo";
        Safelist safelist = Safelist.relaxed();
        safelist.addAttributes(":all", "class");
        safelist.addAttributes("div", "data");

        String clean1 = Jsoup.clean(h, safelist);
        assertEquals("<div class=\"foo\" data=\"true\"><p class=\"bar\">Text</p></div><blockquote cite=\"https://example.com\">Foo</blockquote>", TextUtil.stripNewlines(clean1));

        safelist.removeAttributes(":all", "class", "cite");

        String clean2 = Jsoup.clean(h, safelist);
        assertEquals("<div data=\"true\"><p>Text</p></div><blockquote>Foo</blockquote>", TextUtil.stripNewlines(clean2));
    }

    @Test void removeProtocols() {
        String h = "<a href='any://example.com'>Link</a>";
        Safelist safelist = Safelist.relaxed();
        String clean1 = Jsoup.clean(h, safelist);
        assertEquals("<a>Link</a>", clean1);

        safelist.removeProtocols("a", "href", "ftp", "http", "https", "mailto");
        String clean2 = Jsoup.clean(h, safelist); // all removed means any will work
        assertEquals("<a href=\"any://example.com\">Link</a>", clean2);
    }

    @Test public void testRemoveEnforcedAttributes() {
        String h = "<div><p><A HREF='HTTP://nice.com'>Nice</a></p><blockquote>Hello</blockquote>";
        String cleanHtml = Jsoup.clean(h, Safelist.basic().removeEnforcedAttribute("a", "rel"));

        assertEquals("<p><a href=\"http://nice.com\">Nice</a></p><blockquote>Hello</blockquote>",
                TextUtil.stripNewlines(cleanHtml));
    }

    @Test public void testRemoveProtocols() {
        String h = "<p>Contact me <a href='mailto:info@example.com'>here</a></p>";
        String cleanHtml = Jsoup.clean(h, Safelist.basic().removeProtocols("a", "href", "ftp", "mailto"));

        assertEquals("<p>Contact me <a rel=\"nofollow\">here</a></p>",
                TextUtil.stripNewlines(cleanHtml));
    }
}
