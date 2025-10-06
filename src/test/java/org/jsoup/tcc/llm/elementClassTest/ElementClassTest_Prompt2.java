package org.jsoup.tcc.llm.elementClassTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ElementClassTest_Prompt2 {

    @Test
    void className_shouldReturnEmptyString_whenNoClassAttribute() {
        // Arrange
        Element element = new Element("div");

        // Act
        String result = element.className();

        // Assert
        assertEquals("", result, "should return empty string when no class attribute");
    }

    @Test
    void className_shouldReturnTrimmedClassString_whenClassHasSpaces() {
        // Arrange
        Document doc = Jsoup.parse("<div class='  header  gray  '>");
        Element element = doc.selectFirst("div");

        // Act
        String result = element.className();

        // Assert
        assertEquals("header  gray", result, "should return trimmed class string");
    }

    @Test
    void hasClass_shouldReturnTrue_whenClassExistsCaseInsensitive() {
        // Arrange
        Document doc = Jsoup.parse("<div class='Header Gray'>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertTrue(element.hasClass("header"), "should find class case-insensitive");
        assertTrue(element.hasClass("HEADER"), "should find class case-insensitive");
        assertTrue(element.hasClass("gray"), "should find class case-insensitive");
    }

    @Test
    void hasClass_shouldReturnFalse_whenClassDoesNotExist() {
        // Arrange
        Document doc = Jsoup.parse("<div class='header gray'>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasClass("footer"), "should return false for non-existent class");
    }

    @Test
    void hasClass_shouldReturnFalse_whenElementHasNoClassAttribute() {
        // Arrange
        Element element = new Element("div");

        // Act & Assert
        assertFalse(element.hasClass("any"), "should return false when no class attribute");
    }

    @Test
    void hasClass_shouldReturnFalse_whenClassNameIsSubstringOfExistingClass() {
        // Arrange
        Document doc = Jsoup.parse("<div class='header gray'>");
        Element element = doc.selectFirst("div");

        // Act & Assert
        assertFalse(element.hasClass("hea"), "should return false for partial class name");
        assertFalse(element.hasClass("ead"), "should return false for partial class name");
    }

    @Test
    void classNames_shouldReturnEmptySet_whenNoClassAttribute() {
        // Arrange
        Element element = new Element("div");

        // Act
        Set<String> result = element.classNames();

        // Assert
        assertTrue(result.isEmpty(), "should return empty set when no classes");
    }

    @Test
    void classNames_shouldReturnAllClasses_whenMultipleClassesExist() {
        // Arrange
        Document doc = Jsoup.parse("<div class='header gray active'>");
        Element element = doc.selectFirst("div");

        // Act
        Set<String> result = element.classNames();

        // Assert
        assertEquals(3, result.size(), "should return all classes");
        assertTrue(result.contains("header"), "should contain 'header'");
        assertTrue(result.contains("gray"), "should contain 'gray'");
        assertTrue(result.contains("active"), "should contain 'active'");
    }

    @Test
    void classNames_shouldRemoveDuplicateClasses() {
        // Arrange
        Document doc = Jsoup.parse("<div class='header header gray'>");
        Element element = doc.selectFirst("div");

        // Act
        Set<String> result = element.classNames();

        // Assert
        assertEquals(2, result.size(), "should remove duplicate classes");
    }

    @Test
    void put_shouldAddNewAttribute_whenKeyDoesNotExist() {
        // Arrange
        Element element = new Element("div");

        // Act
        element.attributes().put("data-test", "value");

        // Assert
        assertEquals("value", element.attr("data-test"), "should add new attribute");
    }

    @Test
    void put_shouldReplaceValue_whenKeyExists() {
        // Arrange
        Document doc = Jsoup.parse("<div class='old-value'>");
        Element element = doc.selectFirst("div");

        // Act
        element.attributes().put("class", "new-value");

        // Assert
        assertEquals("new-value", element.attr("class"), "should replace existing attribute value");
    }

    @Test
    void addClass_shouldAddClassToElement_whenNoClassesExist() {
        // Arrange
        Element element = new Element("div");

        // Act
        Element result = element.addClass("new-class");

        // Assert
        assertEquals("new-class", element.className(), "should add class to element");
        assertSame(element, result, "should return element for chaining");
    }

    @Test
    void addClass_shouldAddClassToExistingClasses() {
        // Arrange
        Document doc = Jsoup.parse("<div class='existing-class'>");
        Element element = doc.selectFirst("div");

        // Act
        element.addClass("new-class");

        // Assert
        assertTrue(element.hasClass("existing-class"), "should keep existing class");
        assertTrue(element.hasClass("new-class"), "should add new class");
    }

    @Test
    void addClass_shouldNotDuplicateClass_whenClassAlreadyExists() {
        // Arrange
        Document doc = Jsoup.parse("<div class='existing-class'>");
        Element element = doc.selectFirst("div");

        // Act
        element.addClass("existing-class");

        // Assert
        Set<String> classes = element.classNames();
        assertEquals(1, classes.size(), "should not duplicate existing class");
    }

    @Test
    void removeClass_shouldRemoveExistingClass() {
        // Arrange
        Document doc = Jsoup.parse("<div class='class1 class2 class3'>");
        Element element = doc.selectFirst("div");

        // Act
        Element result = element.removeClass("class2");

        // Assert
        assertTrue(element.hasClass("class1"), "should keep class1");
        assertFalse(element.hasClass("class2"), "should remove class2");
        assertTrue(element.hasClass("class3"), "should keep class3");
        assertSame(element, result, "should return element for chaining");
    }

    @Test
    void removeClass_shouldDoNothing_whenClassDoesNotExist() {
        // Arrange
        Document doc = Jsoup.parse("<div class='class1 class2'>");
        Element element = doc.selectFirst("div");

        // Act
        element.removeClass("nonexistent");

        // Assert
        assertEquals(2, element.classNames().size(), "should not remove any classes");
    }

    @Test
    void removeClass_shouldClearClassAttribute_whenRemovingLastClass() {
        // Arrange
        Document doc = Jsoup.parse("<div class='last-class'>");
        Element element = doc.selectFirst("div");

        // Act
        element.removeClass("last-class");

        // Assert
        assertEquals("", element.className(), "should have empty class attribute");
    }

    @Test
    void toggleClass_shouldAddClass_whenClassIsAbsent() {
        // Arrange
        Element element = new Element("div");

        // Act
        Element result = element.toggleClass("new-class");

        // Assert
        assertTrue(element.hasClass("new-class"), "should add class when absent");
        assertSame(element, result, "should return element for chaining");
    }

    @Test
    void toggleClass_shouldRemoveClass_whenClassIsPresent() {
        // Arrange
        Document doc = Jsoup.parse("<div class='existing-class'>");
        Element element = doc.selectFirst("div");

        // Act
        element.toggleClass("existing-class");

        // Assert
        assertFalse(element.hasClass("existing-class"), "should remove class when present");
    }
}