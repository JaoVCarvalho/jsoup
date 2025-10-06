package org.jsoup.tcc.llm.elementClassTest;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementClassTest_Prompt3 {
    private Element element;

    @BeforeEach
    void setUp() {
        element = new Element("div");
    }

    // className() tests
    @Test
    void className_shouldReturnEmptyString_whenNoClassAttribute() {
        assertEquals("", element.className());
    }

    @Test
    void className_shouldReturnSingleClassName() {
        element.attr("class", "header");
        assertEquals("header", element.className());
    }

    @Test
    void className_shouldReturnMultipleClassesSpaceSeparated() {
        element.attr("class", "header gray bold");
        assertEquals("header gray bold", element.className());
    }

    @Test
    void className_shouldTrimWhitespace() {
        element.attr("class", "  header  gray  ");
        assertEquals("header  gray", element.className());
    }

    // hasClass() tests
    @Test
    void hasClass_shouldReturnTrue_whenClassExists() {
        element.attr("class", "header gray");
        assertTrue(element.hasClass("header"));
    }

    @Test
    void hasClass_shouldBeCaseInsensitive() {
        element.attr("class", "Header Gray");
        assertTrue(element.hasClass("header"));
        assertTrue(element.hasClass("GRAY"));
    }

    @Test
    void hasClass_shouldReturnFalse_whenClassDoesNotExist() {
        element.attr("class", "header gray");
        assertFalse(element.hasClass("footer"));
    }

    @Test
    void hasClass_shouldReturnFalse_whenNoClassAttribute() {
        assertFalse(element.hasClass("any"));
    }

    @Test
    void hasClass_shouldReturnFalse_whenEmptyString() {
        element.attr("class", "header gray");
        assertFalse(element.hasClass(""));
    }

    // classNames() tests
    @Test
    void classNames_shouldReturnEmptySet_whenNoClassAttribute() {
        assertTrue(element.classNames().isEmpty());
    }

    @Test
    void classNames_shouldReturnSingleClassName() {
        element.attr("class", "header");
        assertEquals(1, element.classNames().size());
        assertTrue(element.classNames().contains("header"));
    }

    @Test
    void classNames_shouldRemoveEmptyClassNames() {
        element.attr("class", "header  gray"); // double space
        assertEquals(2, element.classNames().size());
        assertFalse(element.classNames().contains(""));
    }

    // put() tests (via Attributes)
    @Test
    void put_shouldAddNewAttribute() {
        element.attributes().put("data-test", "value");
        assertEquals("value", element.attr("data-test"));
    }

    @Test
    void put_shouldReplaceExistingAttribute() {
        element.attr("class", "old");
        element.attributes().put("class", "new");
        assertEquals("new", element.attr("class"));
    }

    @Test
    void put_shouldSetBooleanAttribute_whenValueIsNull() {
        element.attributes().put("hidden", null);
        assertTrue(element.hasAttr("hidden"));
        assertEquals("", element.attr("hidden"));
    }

    @Test
    void put_shouldThrowException_whenKeyIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                element.attributes().put(null, "value"));
    }

    // addClass() tests
    @Test
    void addClass_shouldAddClassToEmptyElement() {
        Element result = element.addClass("new-class");
        assertEquals("new-class", element.attr("class"));
        assertSame(element, result);
    }

    @Test
    void addClass_shouldAddClassToExistingClasses() {
        element.attr("class", "existing");
        element.addClass("new-class");
        assertEquals("existing new-class", element.attr("class"));
    }

    @Test
    void addClass_shouldNotDuplicateExistingClass() {
        element.attr("class", "existing");
        element.addClass("existing");
        assertEquals("existing", element.attr("class"));
    }

    @Test
    void addClass_shouldThrowException_whenClassNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                element.addClass(null));
    }

    // removeClass() tests
    @Test
    void removeClass_shouldRemoveExistingClass() {
        element.attr("class", "header gray");
        Element result = element.removeClass("gray");
        assertEquals("header", element.attr("class"));
        assertSame(element, result);
    }

    @Test
    void removeClass_shouldRemoveClassAttribute_whenLastClass() {
        element.attr("class", "header");
        element.removeClass("header");
        assertEquals("", element.attr("class"));
    }

    @Test
    void removeClass_shouldDoNothing_whenClassDoesNotExist() {
        element.attr("class", "header gray");
        element.removeClass("footer");
        assertEquals("header gray", element.attr("class"));
    }

    @Test
    void removeClass_shouldThrowException_whenClassNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                element.removeClass(null));
    }

    // toggleClass() tests
    @Test
    void toggleClass_shouldAddClass_whenNotPresent() {
        element.attr("class", "existing");
        Element result = element.toggleClass("new-class");
        assertEquals("existing new-class", element.attr("class"));
        assertSame(element, result);
    }

    @Test
    void toggleClass_shouldRemoveClass_whenPresent() {
        element.attr("class", "existing new-class");
        element.toggleClass("new-class");
        assertEquals("existing", element.attr("class"));
    }

    @Test
    void toggleClass_shouldHandleEmptyElement() {
        element.toggleClass("first-class");
        assertEquals("first-class", element.attr("class"));
    }

    @Test
    void toggleClass_shouldThrowException_whenClassNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                element.toggleClass(null));
    }
}