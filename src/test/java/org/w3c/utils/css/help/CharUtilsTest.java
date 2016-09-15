package org.w3c.utils.css.help;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.w3c.utils.css.help.CharUtils.*;

/**
 * Test for CharUtils unit.
 *
 * Created by Home on 03.11.2015.
 */
public class CharUtilsTest
{

    @Test
    public void testIsInlineCommentStart() throws Exception
    {
        assertTrue(isInlineCommentStart('/', '/'));
        assertFalse(isInlineCommentStart('/', '*'));
    }

    @Test
    public void testIsEOL() throws Exception
    {
        assertTrue(isEOL('\n'));
        assertTrue(isEOL('\r'));
        assertTrue(isEOL('\f'));
    }

    @Test
    public void testIsUnixEOL() throws Exception
    {
        assertTrue(isUnixEOL('\n'));
        assertFalse(isUnixEOL('\r'));
    }

    @Test
    public void testIsWindowsEOL() throws Exception
    {
        assertTrue(isWindowsEOL('\r', '\n'));
        assertFalse(isWindowsEOL('\n', '\r'));
    }

    @Test
    public void testIsBlockCommentStart() throws Exception
    {
        assertTrue(isBlockCommentStart('/', '*'));

        assertFalse(isBlockCommentStart('/', '/'));
        assertFalse(isBlockCommentStart('*', '/'));
    }

    @Test
    public void testIsBlockCommendEnd() throws Exception
    {
        assertTrue(isBlockCommendEnd('*', '/'));

        assertFalse(isBlockCommendEnd('/', '/'));
        assertFalse(isBlockCommendEnd('/', '*'));
    }

    @Test
    public void testIsWhiteSpace() throws Exception
    {
        assertTrue(isWhiteSpace(' '));
        assertTrue(isWhiteSpace('\t'));
        assertTrue(isWhiteSpace('\f'));
        assertTrue(isWhiteSpace('\n'));
        assertTrue(isWhiteSpace('\r'));
    }

    @Test(dataProvider = "dataProvider_isWhiteSpace")
    public void testIsWhiteSpace1(char c, boolean mode, boolean expected) throws Exception
    {
        boolean actual = isWhiteSpace(c, mode);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_isWhiteSpace()
    {
        return new Object[][] {
                // char, mode           expected
                {' ', false,            true},
                {'\t', false,           true},
                {'\r', false,           false},
                {'\n', false,           false},
                {'\f', false,           false},
                {' ', true,             true},
                {'\t', true,            true},
                {'\f', true,            true},
                {'\n', true,            true},
                {'\r', true,            true},
        };
    }

    @Test
    public void testIsQuote() throws Exception
    {
        assertTrue(isQuote('\''));
        assertTrue(isQuote('"'));
    }

    @Test(dataProvider = "dataProvider_isHexDigit")
    public void testIsHexDigit(char digit, boolean expected) throws Exception
    {
        assertEquals(isHexDigit(digit), expected);
    }

    @DataProvider
    public Object[][] dataProvider_isHexDigit()
    {
        return new Object[][] {
                {'0', true},
                {'1', true},
                {'2', true},
                {'3', true},
                {'4', true},
                {'5', true},
                {'6', true},
                {'7', true},
                {'8', true},
                {'9', true},
                {'-', false},
                {'a', true},
                {'b', true},
                {'c', true},
                {'d', true},
                {'e', true},
                {'f', true},
                {'g', false},
                {'A', true},
                {'B', true},
                {'C', true},
                {'D', true},
                {'E', true},
                {'F', true},
                {'G', false},
        };
    }

    @Test(dataProvider = "dataProvider_getCharForUnicodeRange")
    public void testGetCharForUnicodeRange(String unicode, char expected) throws Exception
    {
        char actual = getCharForUnicodeRange(unicode);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_getCharForUnicodeRange()
    {
        return new Object[][] {
                {"U+000026", '&'},
                {"U+0026", '&'},
                {"u+0026", '&'},
                {"U+003?", '0'},
                {"U+0030-003f", '0'},
                {"U+000000", (char)0xfffd},
        };
    }

    @Test(dataProvider = "dataProvider_isDigit")
    public void testIsDigit(char digit, boolean expected) throws Exception
    {
        assertEquals(isDigit(digit), expected);
    }

    @DataProvider
    public Object[][] dataProvider_isDigit()
    {
        return new Object[][] {
                {'0', true},
                {'1', true},
                {'2', true},
                {'3', true},
                {'4', true},
                {'5', true},
                {'6', true},
                {'7', true},
                {'8', true},
                {'9', true},
                {' ', false},
                {'a', false},
                {'b', false},
                {'-', false},
        };
    }

    @Test
    public void testIsLetter() throws Exception
    {
        assertTrue(isLetter('a'));
        assertTrue(isLetter('A'));
        assertTrue(isLetter('z'));
        assertTrue(isLetter('Z'));

        assertFalse(isLetter('0'));
    }

    @Test
    public void testIsLowercaseLetter() throws Exception
    {
        assertTrue(isLowercaseLetter('a'));
        assertTrue(isLowercaseLetter('b'));
        assertTrue(isLowercaseLetter('z'));

        assertFalse(isLowercaseLetter('A'));
        assertFalse(isLowercaseLetter('B'));
        assertFalse(isLowercaseLetter('Z'));
        assertFalse(isLowercaseLetter('-'));
    }

    @Test
    public void testIsUppercaseLetter() throws Exception
    {
        assertTrue(isUppercaseLetter('A'));
        assertTrue(isUppercaseLetter('B'));
        assertTrue(isUppercaseLetter('Z'));

        assertFalse(isUppercaseLetter('a'));
        assertFalse(isUppercaseLetter('b'));
        assertFalse(isUppercaseLetter('z'));
        assertFalse(isLowercaseLetter('-'));
    }


    @Test
    public void testIsNonASCII() throws Exception
    {
        assertFalse(isNonASCII((char) 0x80));
        assertTrue(isNonASCII((char) 0x81));
        assertTrue(isNonASCII((char) 0x1081));
    }

    @Test(dataProvider = "dataProvider_isURLStartChar")
    public void testIsURLStartChar(String probe, boolean thisIsUrlChar) throws Exception
    {
        boolean actual = CharUtils.isURLStartChar(probe.charAt(0));
        assertEquals(actual, thisIsUrlChar);
    }

    @DataProvider
    public Object[][] dataProvider_isURLStartChar()
    {
        return new Object[][] {
                {"/template.css", true},
                {"./template.css", true},
                {"_template.css", true},
                {"123-template.css", true},
                {"template.css", true},
                {"Template.css", true},
                {"*Template.css", false},
        };
    }

    @Test
    public void testIsMacEOL() throws Exception
    {
        assertEquals(CharUtils.isMacEOL('\r'), true);
        assertEquals(CharUtils.isMacEOL('\n'), false);
        assertEquals(CharUtils.isMacEOL('\f'), false);
    }

}