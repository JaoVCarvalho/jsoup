package org.jsoup.tcc.llm.cleanTest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CleanTest_Prompt1 {

    @Test
    void clean_withBasicSafelist_removesUnsafeTags() {
        String inputHtml = "<div><script>alert('xss')</script><p>Safe text</p></div>";
        String result = Jsoup.clean(inputHtml, Safelist.basic());
        assertEquals("<p>Safe text</p>", result);
    }

    @Test
    void clean_withNoneSafelist_removesAllTags() {
        String inputHtml = "<p>Test <b>bold</b> text</p>";
        String result = Jsoup.clean(inputHtml, Safelist.none());
        assertEquals("Test bold text", result);
    }

    @Test
    void clean_withRelaxedSafelist_preservesComplexStructure() {
        String inputHtml = "<table><tr><td>Cell</td></tr></table><img src='image.jpg'>";
        String result = Jsoup.clean(inputHtml, Safelist.relaxed());
        assertTrue(result.contains("table") && result.contains("td"));
    }

    @Test
    void clean_withBasicSafelist_preservesAllowedAttributes() {
        String inputHtml = "<a href='https://example.com'>Link</a>";
        String result = Jsoup.clean(inputHtml, Safelist.basic());
        assertTrue(result.contains("href") && result.contains("nofollow"));
    }

    @Test
    void clean_withModifiedSafelist_respectsTagRemoval() {
        Safelist safelist = Safelist.basic().removeTags("a", "b");
        String inputHtml = "<a href='#'>Link</a><b>Bold</b><p>Text</p>";
        String result = Jsoup.clean(inputHtml, safelist);
        assertEquals("<p>Text</p>", result);
    }

    @Test
    void clean_withEmptyInput_returnsEmptyString() {
        String result = Jsoup.clean("", Safelist.basic());
        assertEquals("", result);
    }

    @Test
    void clean_withOnlyUnsafeTags_returnsEmptyString() {
        String inputHtml = "<script>alert('xss')</script><style>body{color:red}</style>";
        String result = Jsoup.clean(inputHtml, Safelist.basic());
        assertEquals("", result);
    }

    @Test
    void clean_preservesTextContentWhenTagsRemoved() {
        String inputHtml = "<div>Hello <script>alert('xss')</script>World</div>";
        String result = Jsoup.clean(inputHtml, Safelist.basic());
        assertEquals("Hello World", result);
    }

    @Test
    void clean_withBasicSafelist_handlesNestedLists() {
        String inputHtml = "<ul><li>Item <b>1</b></li><li>Item 2</li></ul>";
        String result = Jsoup.clean(inputHtml, Safelist.basic());
        assertTrue(result.contains("ul") && result.contains("li") && !result.contains("b"));
    }

    @Test
    void clean_withRelaxedSafelist_allowsImagesWithAttributes() {
        String inputHtml = "<img src='test.jpg' alt='Test' width='100' height='50'>";
        String result = Jsoup.clean(inputHtml, Safelist.relaxed());
        assertTrue(result.contains("img") && result.contains("src") && result.contains("alt"));
    }
}