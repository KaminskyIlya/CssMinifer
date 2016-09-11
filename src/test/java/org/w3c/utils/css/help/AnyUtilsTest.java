package org.w3c.utils.css.help;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.enums.FontAbsoluteSize;
import org.w3c.utils.css.enums.FontStyle;
import org.w3c.utils.css.enums.FontWeight;
import org.w3c.utils.css.enums.Properties;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 20.08.2016.
 */
public class AnyUtilsTest
{

    @Test(dataProvider = "dataProvider_getUrlFormExpression")
    public void testGetUrlFromExpression(String url, String expected) throws Exception
    {
        String actual = AnyUtils.getUrlFromExpression(url);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_getUrlFormExpression()
    {
        return new Object[][]{
                {"url('/test.css')", "/test.css"},
                {"url(/test.css)", "/test.css"},
                {"/test.css", "/test.css"},
                {"'/test.css'", "/test.css"},
                {"\"/test.css\"", "/test.css"},
        };
    }

    @Test(dataProvider = "dataProvider_removeBrowserPrefix")
    public void testRemoveBrowserPrefix(String identifier, String expected) throws Exception
    {
        String actual = AnyUtils.removeBrowserPrefix(identifier);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_removeBrowserPrefix()
    {
        return new Object[][]{
                {"keyframes", "keyframes"},
                {"-o-keyframes", "keyframes"},
                {"-moz-keyframes", "keyframes"},
                {"-ms-keyframes", "keyframes"},
                {"-webkit-keyframes", "keyframes"},
                {"-a-keyframes", "-a-keyframes"},
                {"@-webkit-keyframes", "@-webkit-keyframes"},
        };
    }

    @Test(dataProvider = "dataProvider_getKeywordByName")
    public void testGetKeywordByName(Enum expected, String name) throws Exception
    {
        Enum actual = AnyUtils.getKeywordByName(expected.getClass(), name);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_getKeywordByName()
    {
        return new Object[][] {
                {Properties.LETTER_SPACING, "letter-spacing"},
                {Properties.Z_INDEX, "z-index"},
                {FontStyle.NORMAL, "normal"},
                {FontWeight.BOLDER, "bolder"},
                {FontAbsoluteSize.XX_LARGE, "xx-large"},
                {FontAbsoluteSize.X_SMALL, "x-small"},
        };
    }
}