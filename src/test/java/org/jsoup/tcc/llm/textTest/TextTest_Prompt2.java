package org.jsoup.tcc.llm.textTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TextTest_Prompt2 {

    @Test
    void text_shouldReturnNormalizedCombinedText_whenElementHasMixedContent() {
        // Arrange
        String html = "<p>Hello   <b>there</b> \n now! </p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.text();

        // Assert
        assertEquals("Hello there now!", result,
                "should normalize whitespace and combine all text content");
    }

    @Test
    void text_shouldReturnEmptyString_whenElementHasNoTextContent() {
        // Arrange
        String html = "<div><span></span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.text();

        // Assert
        assertEquals("", result,
                "should return empty string for element with no text content");
    }

    @Test
    void text_shouldIgnoreScriptContent_whenElementContainsScriptTag() {
        // Arrange
        String html = "<div>Hello <script>alert('test')</script> world</div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.text();

        // Assert
        assertEquals("Hello world", result,
                "should ignore text content inside script tags");
    }

    @Test
    void text_shouldHandleBrTagAsSpace_whenBrIsBetweenText() {
        // Arrange
        String html = "<p>Hello<br>world</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.text();

        // Assert
        assertEquals("Hello world", result,
                "should treat br tag as space between text");
    }

    @Test
    void ownText_shouldReturnOnlyDirectText_whenElementHasChildElements() {
        // Arrange
        String html = "<p>Hello <b>there</b> now!</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.ownText();

        // Assert
        assertEquals("Hello now!", result,
                "should return only text directly belonging to the element");
    }

    @Test
    void ownText_shouldReturnEmptyString_whenElementHasOnlyChildText() {
        // Arrange
        String html = "<div><span>child text</span></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.ownText();

        // Assert
        assertEquals("", result,
                "should return empty string when element has no direct text");
    }

    @Test
    void ownText_shouldHandleBrInOwnText_whenBrIsBetweenDirectText() {
        // Arrange
        String html = "<p>Hello<br>direct text</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.selectFirst("p");

        // Act
        String result = p.ownText();

        // Assert
        assertEquals("Hello direct text", result,
                "should include br as space in own text calculation");
    }

    @Test
    void wholeText_shouldPreserveWhitespace_whenElementHasNonNormalizedSpaces() {
        // Arrange
        String html = "<div>  Hello   there  \n  world  </div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.wholeText();

        // Assert
        assertEquals("  Hello   there  \n  world  ", result,
                "should preserve original whitespace including newlines");
    }

    @Test
    void wholeText_shouldConvertBrToNewline_whenBrTagIsPresent() {
        // Arrange
        String html = "<div>Hello<br>world<br></div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String result = div.wholeText();

        // Assert
        assertEquals("Hello\nworld\n", result,
                "should convert br tags to newline characters");
    }

    @Test
    void wholeText_shouldShowDifferenceFromText_whenComparingNormalizedVsRawText() {
        // Arrange
        String html = "<div>  Multiple   spaces  </div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act
        String wholeText = div.wholeText();
        String normalizedText = div.text();

        // Assert
        assertEquals("  Multiple   spaces  ", wholeText,
                "wholeText should preserve original spacing");
        assertEquals("Multiple spaces", normalizedText,
                "text should normalize spacing");
        assertNotEquals(wholeText, normalizedText,
                "wholeText and text should differ for non-normalized content");
    }

    @Test
    void text_vs_ownText_vs_wholeText_shouldDemonstrateDifferences_whenComplexStructure() {
        // Arrange
        String html = "<div>  Parent \n <span>  Child  </span> text  </div>";
        Document doc = Jsoup.parse(html);
        Element div = doc.selectFirst("div");

        // Act & Assert
        assertEquals("Parent Child text", div.text(),
                "text should return normalized combined text");
        assertEquals("Parent text", div.ownText(),
                "ownText should return only direct parent text");
        assertEquals("  Parent \n   Child   text  ", div.wholeText(),
                "wholeText should preserve all original whitespace");
    }
}