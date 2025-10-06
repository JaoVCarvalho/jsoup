package org.jsoup.tcc.llm.isValid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsValidTest_Prompt2 {

    // ===== TESTES PARA Jsoup.isValid(String, Safelist) =====

    @Test
    void isValid_shouldReturnTrue_whenBasicTagsWithBasicSafelist() {
        // Arrange
        String validHtml = "<p>Hello <b>world</b></p>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(validHtml, safelist);

        // Assert
        assertTrue(result, "Basic allowed tags should be valid with basic safelist");
    }

    @Test
    void isValid_shouldReturnFalse_whenInvalidTagsWithBasicSafelist() {
        // Arrange
        String invalidHtml = "<script>alert('xss')</script><p>Hello</p>";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(invalidHtml, safelist);

        // Assert
        assertFalse(result, "Script tags should be invalid with basic safelist");
    }

    @Test
    void isValid_shouldReturnTrue_whenEmptyHtmlWithAnySafelist() {
        // Arrange
        String emptyHtml = "";
        Safelist safelist = Safelist.basic();

        // Act
        boolean result = Jsoup.isValid(emptyHtml, safelist);

        // Assert
        assertTrue(result, "Empty HTML should always be valid");
    }

    @Test
    void isValid_shouldThrowException_whenNullHtml() {
        // Arrange
        Safelist safelist = Safelist.basic();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            Jsoup.isValid(null, safelist);
        }, "Null HTML should throw NullPointerException");
    }

    @Test
    void isValid_shouldReturnFalse_whenHtmlWithTagsAndNoneSafelist() {
        // Arrange
        String htmlWithTags = "<p>Text with tags</p>";
        Safelist safelist = Safelist.none();

        // Act
        boolean result = Jsoup.isValid(htmlWithTags, safelist);

        // Assert
        assertFalse(result, "HTML with tags should be invalid with none safelist");
    }

    @Test
    void isValid_shouldReturnTrue_whenOnlyTextWithNoneSafelist() {
        // Arrange
        String textOnly = "Plain text without tags";
        Safelist safelist = Safelist.none();

        // Act
        boolean result = Jsoup.isValid(textOnly, safelist);

        // Assert
        assertTrue(result, "Plain text should be valid with none safelist");
    }

    @Test
    void isValid_shouldReturnTrue_whenRelaxedTagsWithRelaxedSafelist() {
        // Arrange
        String relaxedHtml = "<div><h1>Title</h1><img src='test.jpg' alt='test'></div>";
        Safelist safelist = Safelist.relaxed();

        // Act
        boolean result = Jsoup.isValid(relaxedHtml, safelist);

        // Assert
        assertTrue(result, "Relaxed tags should be valid with relaxed safelist");
    }

    // ===== TESTES PARA Cleaner.isValid(Document) =====

    @Test
    void cleanerIsValid_shouldReturnTrue_whenValidDocumentWithBasicSafelist() {
        // Arrange
        Document validDoc = Jsoup.parse("<p>Valid <b>content</b></p>");
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act
        boolean result = cleaner.isValid(validDoc);

        // Assert
        assertTrue(result, "Document with allowed tags should be valid");
    }

    @Test
    void cleanerIsValid_shouldReturnFalse_whenInvalidDocumentWithBasicSafelist() {
        // Arrange
        Document invalidDoc = Jsoup.parse("<script>alert('xss')</script><p>Hello</p>");
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act
        boolean result = cleaner.isValid(invalidDoc);

        // Assert
        assertFalse(result, "Document with disallowed tags should be invalid");
    }

    @Test
    void cleanerIsValid_shouldReturnFalse_whenDocumentWithContentInHead() {
        // Arrange
        Document docWithHeadContent = Jsoup.parse("<html><head><title>Title</title></head><body><p>Body</p></body></html>");
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act
        boolean result = cleaner.isValid(docWithHeadContent);

        // Assert
        assertFalse(result, "Document with content in head should be invalid");
    }

    @Test
    void cleanerIsValid_shouldThrowException_whenNullDocument() {
        // Arrange
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            cleaner.isValid(null);
        }, "Null document should throw exception");
    }

    @Test
    void cleanerIsValid_shouldReturnTrue_whenEmptyDocument() {
        // Arrange
        Document emptyDoc = Document.createShell("");
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act
        boolean result = cleaner.isValid(emptyDoc);

        // Assert
        assertTrue(result, "Empty document shell should be valid");
    }

    @Test
    void cleanerIsValid_shouldReturnFalse_whenMultipleInvalidNodes() {
        // Arrange
        Document multiInvalidDoc = Jsoup.parse("<script>alert1</script><style>body{}</style><p>Text</p>");
        Cleaner cleaner = new Cleaner(Safelist.basic());

        // Act
        boolean result = cleaner.isValid(multiInvalidDoc);

        // Assert
        assertFalse(result, "Document with multiple invalid nodes should be invalid");
    }

    @Test
    void cleanerIsValid_shouldReturnTrue_whenAllowedAttributesWithRelaxedSafelist() {
        // Arrange
        Document docWithAttributes = Jsoup.parse("<a href='https://example.com' title='link'>Link</a>");
        Cleaner cleaner = new Cleaner(Safelist.relaxed());

        // Act
        boolean result = cleaner.isValid(docWithAttributes);

        // Assert
        assertTrue(result, "Document with allowed attributes should be valid with relaxed safelist");
    }
}