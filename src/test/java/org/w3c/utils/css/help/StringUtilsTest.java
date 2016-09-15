package org.w3c.utils.css.help;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 15.09.2016.
 */
public class StringUtilsTest
{

    @Test
    public void testGetSolidCssStringLiteral() throws Exception
    {
        assertEquals(StringUtils.getSolidCssStringLiteral("test 1-line string"), "test 1-line string");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\r next line"), "test multi-line string next line");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\n next line"), "test multi-line string next line");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\f next line"), "test multi-line string next line");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\r\\\n next line"), "test multi-line string next line");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\n\\\r next line"), "test multi-line string next line");
        assertEquals(StringUtils.getSolidCssStringLiteral("test multi-line string\\\t next line"), "test multi-line string\t next line");
    }

    @Test(dataProvider = "dataProvider_getUnquotedStringLiteral")
    public void testGetUnquotedStringLiteral(String literal, String expected) throws Exception
    {
        String actual = StringUtils.getUnquotedStringLiteral(literal);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_getUnquotedStringLiteral()
    {
        return new Object[][] {
                {"\"test\"", "test"},
                {"'test'", "test"},
                {"'te\"st'", "te\"st"},
        };
    }

    @Test(dataProvider = "dataProvider_IsStringLiteralCanUnquoted")
    public void testIsStringLiteralCanUnquoted(String literal, boolean canUnquoted) throws Exception
    {
        boolean actual = StringUtils.isStringLiteralCanUnquoted(literal);
        assertEquals(actual, canUnquoted);
    }

    @DataProvider
    public Object[][] dataProvider_IsStringLiteralCanUnquoted()
    {
        return new Object[][] {
                {"\"test\"", true},
                {"'test'", true},
                {"'te st'", false},
                {"'te st'", false},
                {"'t\te st'", false},
                {"'t\n\re st'", false},
                {"'t\r\ne st'", false},
                {"'t\r\fe st'", false},
        };
    }

    @Test(dataProvider = "dataProvider_FindNotEscapedCharPos")
    public void testFindNotEscapedCharPos(String literal, int pos) throws Exception
    {
        int actual = StringUtils.findNotEscapedCharPos(literal, '|');
        assertEquals(actual, pos);
    }

    @DataProvider
    private Object[][] dataProvider_FindNotEscapedCharPos()
    {
        return new Object[][] {
                {"TEXTAREA", -1},
                {"|TEXTAREA", 0},
                {"namespace|TEXTAREA", 9},
                {"namespace\\|TEXTAREA", -1},
                {"namespace\\\\|TEXTAREA", 11},
        };
    }
}