package org.jsoup.tcc.llm.elementClassTest;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ElementClassTest_Prompt1 {

    @Test
    void className_returnsTrimmedClassAttribute() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "  header gray  ");
        assertEquals("header gray", element.className());
    }

    @Test
    void className_returnsEmptyStringWhenNoClassAttribute() {
        Element element = new Element(Tag.valueOf("div"), "");
        assertEquals("", element.className());
    }

    @Test
    void hasClass_returnsTrueForExactMatch() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header");
        assertTrue(element.hasClass("header"));
    }

    @Test
    void hasClass_returnsTrueForCaseInsensitiveMatch() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "HEADER");
        assertTrue(element.hasClass("header"));
    }

    @Test
    void hasClass_returnsTrueForOneOfMultipleClasses() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header gray bold");
        assertTrue(element.hasClass("gray"));
    }

    @Test
    void hasClass_returnsFalseWhenClassNotPresent() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header gray");
        assertFalse(element.hasClass("bold"));
    }

    @Test
    void hasClass_returnsFalseWhenNoAttributes() {
        Element element = new Element(Tag.valueOf("div"), "");
        assertFalse(element.hasClass("header"));
    }

    @Test
    void classNames_returnsSetOfClassNames() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header gray bold");
        Set<String> classNames = element.classNames();
        assertTrue(classNames.contains("header"));
        assertTrue(classNames.contains("gray"));
        assertTrue(classNames.contains("bold"));
        assertEquals(3, classNames.size());
    }

    @Test
    void classNames_returnsEmptySetWhenNoClassAttribute() {
        Element element = new Element(Tag.valueOf("div"), "");
        Set<String> classNames = element.classNames();
        assertTrue(classNames.isEmpty());
    }

    @Test
    void classNames_removesEmptyClassNames() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header  gray");
        Set<String> classNames = element.classNames();
        assertEquals(2, classNames.size());
        assertFalse(classNames.contains(""));
    }

    @Test
    void put_addsNewAttribute() {
        Attributes attributes = new Attributes();
        attributes.put("id", "test");
        assertEquals("test", attributes.get("id"));
    }

    @Test
    void put_replacesExistingAttribute() {
        Attributes attributes = new Attributes();
        attributes.put("id", "old");
        attributes.put("id", "new");
        assertEquals("new", attributes.get("id"));
    }

    @Test
    void addClass_addsClassNameToElement() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.addClass("header");
        assertTrue(element.hasClass("header"));
    }

    @Test
    void addClass_appendsToExistingClasses() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header");
        element.addClass("gray");
        assertTrue(element.hasClass("header"));
        assertTrue(element.hasClass("gray"));
    }

    @Test
    void removeClass_removesClassNameFromElement() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header gray");
        element.removeClass("gray");
        assertTrue(element.hasClass("header"));
        assertFalse(element.hasClass("gray"));
    }

    @Test
    void removeClass_doesNothingWhenClassNotPresent() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header");
        element.removeClass("gray");
        assertTrue(element.hasClass("header"));
        assertEquals("header", element.className());
    }

    @Test
    void toggleClass_addsClassWhenNotPresent() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.toggleClass("header");
        assertTrue(element.hasClass("header"));
    }

    @Test
    void toggleClass_removesClassWhenPresent() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header");
        element.toggleClass("header");
        assertFalse(element.hasClass("header"));
    }

    @Test
    void toggleClass_preservesOtherClasses() {
        Element element = new Element(Tag.valueOf("div"), "");
        element.attr("class", "header gray");
        element.toggleClass("header");
        assertFalse(element.hasClass("header"));
        assertTrue(element.hasClass("gray"));
    }
}