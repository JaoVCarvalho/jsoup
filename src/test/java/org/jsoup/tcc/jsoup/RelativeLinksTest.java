package org.jsoup.tcc.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RelativeLinksTest {
    @Test
    public void resolvesRelativeLinks() {
        String html = "<a href='/foo'>Link</a><img src='/bar'>";
        String clean = Jsoup.clean(html, "http://example.com/", Safelist.basicWithImages());
        assertEquals("<a href=\"http://example.com/foo\">Link</a><img src=\"http://example.com/bar\">", clean);
    }

    @Test
    public void preservesRelativeLinksIfConfigured() {
        String html = "<a href='/foo'>Link</a><img src='/bar'> <img src='javascript:alert()'>";
        String clean = Jsoup.clean(html, "http://example.com/", Safelist.basicWithImages().preserveRelativeLinks(true));
        assertEquals("<a href=\"/foo\">Link</a><img src=\"/bar\"> <img>", clean);
    }

    @Test
    public void dropsUnresolvableRelativeLinks() { // when not preserving
        String html = "<a href='/foo'>Link</a>";
        String clean = Jsoup.clean(html, Safelist.basic());
        assertEquals("<a rel=\"nofollow\">Link</a>", clean);
    }

    @Test
    void dropsJavascriptWhenRelativeLinks() {
        String html ="<a href='javascript:alert()'>One</a>";
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);
        assertEquals("<a rel=\"nofollow\">One</a>", Jsoup.clean(html, safelist));
        assertFalse(Jsoup.isValid(html, safelist));
    }

    @Test
    void dropsConcealedJavascriptProtocolWhenRelativesLinksEnabled() {
        Safelist safelist = Safelist.basic().preserveRelativeLinks(true);
        String html = "<a href=\"&#0013;ja&Tab;va&Tab;script&#0010;:alert(1)\">Link</a>";
        String clean = Jsoup.clean(html, "https://", safelist);
        assertEquals("<a rel=\"nofollow\">Link</a>", clean);
        assertFalse(Jsoup.isValid(html, safelist));

        String colon = "<a href=\"ja&Tab;va&Tab;script&colon;alert(1)\">Link</a>";
        String cleanColon = Jsoup.clean(colon, "https://", safelist);
        assertEquals("<a rel=\"nofollow\">Link</a>", cleanColon);
        assertFalse(Jsoup.isValid(colon, safelist));
    }

    @Test
    void dropsConcealedJavascriptProtocolWhenRelativesLinksDisabled() {
        Safelist safelist = Safelist.basic().preserveRelativeLinks(false);
        String html = "<a href=\"ja&Tab;vas&#0013;cript:alert(1)\">Link</a>";
        String clean = Jsoup.clean(html, "https://", safelist);
        assertEquals("<a rel=\"nofollow\">Link</a>", clean);
        assertFalse(Jsoup.isValid(html, safelist));
    }
}
