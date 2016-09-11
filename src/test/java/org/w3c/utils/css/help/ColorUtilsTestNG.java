package org.w3c.utils.css.help;

import org.testng.annotations.*;

import java.awt.*;
import java.util.Arrays;

import static org.testng.Assert.*;

/**
 * ColorUtils class unit test.
 *
 * Created by Home on 03.11.2015.
 */
public class ColorUtilsTestNG {

    @Test(dataProvider = "dataProvider_toColorStr")
    public void testToColorStr(String expected, int r, int g, int b) throws Exception
    {
        String actual = ColorUtils.toColorStr(r, g, b);
        assertEquals(actual, expected, String.format("Invalid presentation %s for color (%d, %d, %d).", actual, r, g, b));
    }

    @DataProvider
    private Object[][] dataProvider_toColorStr()
    {
        return new Object[][] {
                {"#807f7e", 128, 127, 126},
                {"#0901ff", 9, 1, 256},
                {"#00ff36", -10, 300, 54}
        };
    }

    @Test(dataProvider = "dataProvider_toColorStr2")
    public void testToColorStr2(String expected, int r, int g, int b) throws Exception
    {
        Color color = new Color(r, g, b);
        String actual = ColorUtils.toColorStr(color);
        assertEquals(actual, expected, String.format("Invalid presentation %s for %s.", actual, color));
    }

    @DataProvider
    private Object[][] dataProvider_toColorStr2()
    {
        return new Object[][] {
                //result, r, g, b
                {"#807f7e", 128, 127, 126},
                {"#0901ff", 9, 1, 255},
                {"#00fe36", 0, 254, 54}
        };
    }

    @Test(dataProvider = "dataProvider_SimplifyColor")
    public void testSimplifyColor(String expected, String color) throws Exception
    {
        String actual = ColorUtils.simplifyColor(color);
        assertEquals(actual, expected, String.format("Invalid transform %s for %s.", actual, color));
    }

    @DataProvider
    private Object[][] dataProvider_SimplifyColor()
    {
        return new Object[][]{
                // result, source
                {"#123", "#112233"}, // simplify success (1 <= 11, 22 <= 22, 33 <= 33)
                {"#456", "#445566"}, // simplify success
                {"#112234", "#112234"}, // we cann't simplify it, skip it (return as is)
                {"#172834", "#172834"}, // we cann't simplify it, skip it (return as is)
                {"#testme", "#testme"}, // we cann't simplify it, skip it (return as is)
                //{"#123", "#112234"}, // failed, because we cann't simplify it (bytes in each color components must be equals! we have 3 != 4)
        };
    }

    @Test(dataProvider = "dataProvider2_SimplifyColor")
    public void testSimplifyColor(char expected[], char source[]) throws Exception
    {
        char actual[] = ColorUtils.simplifyColor(source);
        assertEquals(actual, expected, String.format("actual(%s) != expected(%s)", Arrays.toString(actual), Arrays.toString(expected)));
    }

    @DataProvider
    private Object[][] dataProvider2_SimplifyColor()
    {
        return new Object[][]{
                // result, source
                {new char[]{'1','2','3'}, new char[]{'1','1','2','2','3','3'}}, // simplify success (1 <= 11, 22 <= 22, 33 <= 33)
                {new char[]{'4','a','f'} ,new char[]{'4','4','a','a','f','f'}}, // simplify success
                {new char[]{'1','1','2','2','3','4'}, new char[]{'1','1','2','2','3','4'}}, // we cann't simplify it, skip it (return as is)
                {new char[]{'1','7','2','8','3','4'}, new char[]{'1','7','2','8','3','4'}}, // we cann't simplify it, skip it (return as is)
                {new char[]{'t','e','s','t','m','e'},new char[]{'t','e','s','t','m','e'}}, // return as is
                {new char[]{'t','t','s','s','m','m'},new char[]{'t','t','s','s','m','m'}}, // return as is
                //{"#123", "#112234"}, // failed, because we cann't simplify it (bytes in each color components must be equals! we have 3 != 4)
        };
    }

    @Test(dataProvider = "dataProvider_ReduceColorSpace")
    public void testReduceColorSpace(String expected, String color) throws Exception
    {
        String actual = ColorUtils.reduceColorSpace(color);
        assertEquals(actual, expected, String.format("Invalid reduce color space (%s) for color %s", actual, color));
    }

    @DataProvider
    private Object[][] dataProvider_ReduceColorSpace()
    {
        return new Object[][]{
                // simple standard tests (cases when result of reduceColorSpace equivalent to simplifyColor)
                {"#000", "#000000"},
                {"#111", "#111111"},
                {"#222", "#222222"},
                {"#333", "#333333"},
                {"#444", "#444444"},
                {"#555", "#555555"},
                {"#666", "#666666"},
                {"#777", "#777777"},
                {"#888", "#888888"},
                {"#999", "#999999"},
                {"#aaa", "#aaaaaa"},
                {"#bbb", "#bbbbbb"},
                {"#ccc", "#cccccc"},
                {"#ddd", "#dddddd"},
                {"#eee", "#eeeeee"},
                {"#fff", "#ffffff"},

                // complex tests without overflow
                {"#113", "#171832"},
                {"#89a", "#889cae"},
                {"#89a", "#889ca7"},
                {"#89a", "#889ca8"},
                {"#777", "#777777"},
                {"#888", "#878787"},
                {"#888", "#888887"},

                // complex tests with low overflow
                {"#aaa", "#b3b2b1"},
                {"#aaa", "#b0afae"},
                {"#aaa", "#adacab"},
                {"#aaa", "#aaa9a8"},
                {"#aaa", "#a7a6a5"},
                {"#aa9", "#a4a3a2"},
                {"#999", "#a2a1a0"},
                {"#788", "#808887"},
                {"#eff", "#f0f8ff"},
                {"#eee", "#eef0f7"},

                // complex tests with high overflow
                {"#011", "#08090a"},
                {"#122", "#181a1b"},
                {"#233", "#2a2b2c"},
                {"#344", "#3b3c3d"},
                {"#455", "#4c4d4e"},
                {"#566", "#5d5e5f"},
                {"#677", "#6e6f70"},


                // another demo tests
                {"#000", "#000102"},
                {"#000", "#030405"},
                {"#000", "#060708"},
                {"#111", "#090a0b"},
                {"#111", "#0c0d0e"},
                {"#111", "#0f1011"},
                {"#111", "#121314"},
                {"#111", "#151617"},
                {"#112", "#18191a"},
                {"#222", "#1b1c1d"},
                {"#222", "#1e1f20"},
                {"#222", "#212223"},
                {"#222", "#242526"},
                {"#222", "#272829"},
                {"#233", "#2a2b2c"},
        };
    }

    @Test(dataProvider = "dataProvider_FromRGB")
    public void testFromRGB(String expected, String source) throws Exception
    {
        String actual = ColorUtils.fromRGB(source);
        assertEquals(actual, expected, String.format("Invalid transform instruction %s to %s", source, actual));
    }

    @DataProvider
    private Object[][] dataProvider_FromRGB()
    {
        return new Object[][]{
                //expected   source string
                {"#807e7f",  "rgb(128,126,127)"},
                {"#010a00", "rgb(1,10,0)"},
                {"#110a00", "rgb(17,10,0)"},
                {"rgb(,126,127)", "rgb(,126,127)"}, // invalid translation returned as is
                {"#ff7f3f", "rgb(100%,50%,25%)"},
                //{"#007e7f", "rgb(,126,127)"}, // it's not passed
        };
    }

    @Test(dataProvider = "dataProvider_FromHSL")
    public void testFromHSL(String expected, String source) throws Exception
    {
        String actual = ColorUtils.fromHSL(source);
        assertEquals(actual, expected, String.format("Invalid transform instruction %s to %s", source, actual));
    }

    @DataProvider
    private Object[][] dataProvider_FromHSL()
    {
        return new Object[][]{
                {"#ffffff", "hsl(0,100%,100%)"},
                {"#bf3f3f", "hsl(0,50%,50%)"},
                {"#ffffff", "hsl(0,0%,100%)"},
                {"#000000", "hsl(0,100%,0%)"},
                {"#000000", "hsl(0,50%,0%)"},
                {"#7f7f7f", "hsl(0,0%,50%)"},
                {"#ff0000", "hsl(0,100%,50%)"},
                {"#7f0000", "hsl(0,100%,25%)"},
                {"#ffffff", "hsl(0,50%,100%)"},
                {"#d96868", "hsl(0,60%,63%)"},
                {"#278a1e", "hsl(115,64%,33%)"},
                {"#6b1e8a", "hsl(283,64%,33%)"},
        };
    }

    // Demo
    @Test(expectedExceptions = {NumberFormatException.class})
    public void testName() throws Exception
    {
        assertEquals(Integer.valueOf("gh", 16), Integer.valueOf(0));
    }

    @Test(dataProvider = "dataProvider_ExtractColorFrom")
    public void testExtractColorFrom(String source, String expected) throws Exception
    {
        String actual = ColorUtils.extractColorFrom(source);
        assertEquals(actual, expected, String.format("Invalid color extraction from '%s'", source));
    }

    @DataProvider
    private Object[][] dataProvider_ExtractColorFrom()
    {
        return new Object[][] {
                {"border: solid 1px rgb(30,40,50)", "rgb(30,40,50)"},
                {"border: solid 1px rgba(30,40,50,1)", "rgba(30,40,50,1)"},
                {"border: solid 1px rgb(30%,40%,50%)", "rgb(30%,40%,50%)"},
                {"border: solid 1px rgba(30%,40%,50%,1)", "rgba(30%,40%,50%,1)"},
                {"border: solid 1px rgba(30%,40%,50%,0)", "rgba(30%,40%,50%,0)"},
                {"border: solid 1px rgba(30%,40%,50%,0.1)", "rgba(30%,40%,50%,0.1)"},
                {"border: solid 1px rgba(30%,40%,50%,0.007)", "rgba(30%,40%,50%,0.007)"},
                {"border: solid 1px hsl(0,50%,50%)", "hsl(0,50%,50%)"},
                {"border: solid 1px hsla(0,50%,50%,1)", "hsla(0,50%,50%,1)"},
                {"border: solid 1px hsla(0,50%,50%,0)", "hsla(0,50%,50%,0)"},
                {"border: solid 1px hsla(0,50%,50%,0.1)", "hsla(0,50%,50%,0.1)"},
                {"border: solid 1px hsla(0,50%,50%,0.08)", "hsla(0,50%,50%,0.08)"},
                {"border: solid 1px #eee", "#eee"},
                {"border: solid 1px #f0f0f0", "#f0f0f0"},
                {"border: solid 1px aqua", "aqua"},
        };
    }
}