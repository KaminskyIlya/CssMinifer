package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.WhiteSpaceProcessor;

import java.util.Arrays;

import static org.testng.Assert.*;

/**
 * Functional tests for WhiteSpaceProcess class.
 * It's class of central logic for WhiteSpaceFilter.
 *
 *
 * Created by Home on 07.11.2015.
 */
public class WhiteSpaceProcessorFunctionalTest
{

    @Test(dataProvider = "dataProvider_isMustSkipNext")
    public void testIsMustSkipNext(String cssText, String expectedFlags, String expectedResult) throws Exception
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();
        StringBuilder actualResult = new StringBuilder();
        boolean actual = false;

        for (int i = 0, n = cssText.length(); i < n; i++)
        {
            char current = cssText.charAt(i);
            char next = (i < n-1) ? cssText.charAt(i+1) : 0;
            boolean expected = expectedFlags.charAt(i) == '1';

            if (!actual) actualResult.append(current);

            processor.before(current);
            actual = processor.isMustSkipNext(current, next);
            processor.after(current);

            assertEquals(actual, expected, getMessageForSkipSpace(cssText, i + 1, expected));
        }

        assertEquals(actualResult.toString(), expectedResult);
    }

    private String getMessageForSkipSpace(String css, int len, boolean expected)
    {
        if (expected)
        {
            return String.format("Space must be removed on processing, but will skipped:\n%s\n%s at here", css.substring(0, len), getMarker(len));
        }
        else
        {
            return String.format("Space must be skipped on processing, but will removed:\n%s\n%s at here", css.substring(0, len), getMarker(len));
        }
    }

    @DataProvider
    private Object[][] dataProvider_isMustSkipNext()
    {
        return new Object[][]{
                {"@media(min-width:768px){.dl-horizontal dt{float:left}",
                 "00000000000000000000000000000000000000000000000000000",
                 "@media(min-width:768px){.dl-horizontal dt{float:left}"},

                {"@media(min-width:768px){.dl-horizontal  dt{float:left}",
                 "000000000000000000000000000000000000001000000000000000",
                 "@media(min-width:768px){.dl-horizontal dt{float:left}"},

                {"H1.member    .wrapped { border :  1px   solid   black ! important; }",
                 "00000000011100000000001000000101100001100000011000000010000000000100",
                "H1.member .wrapped {border:1px solid black !important;}"}
        };
    }







    @Test(dataProvider = "dataProvider_canWriteSpace")
    public void testCanWriteSpace(String cssText, String expectedFlags, String expectedResult) throws Exception
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();
        StringBuilder actualResult = new StringBuilder();

        for (int i = 0, n = cssText.length(); i < n; i++)
        {
            char current = cssText.charAt(i);
            char next = (i < n-1) ? cssText.charAt(i+1) : 0;
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.canWriteSpace(current, next);
            processor.after(current);

            if (current != ' ' || actual) actualResult.append(current);

            assertEquals(actual, expected, getMessageForWriteSpace(cssText, i, expected));
        }

        assertEquals(actualResult.toString(), expectedResult);
    }

    private String getMessageForWriteSpace(String css, int len, boolean expected)
    {
        if (expected)
        {
            return String.format("Space must be wrote:\n%s\n%s at here", css.substring(0, len), getMarker(len));
        }
        else
        {
            return String.format("Space must not be wrote:\n%s\n%s at here", css.substring(0, len > 0 ? len-1 : 1 ), getMarker(len));
        }
    }

    @DataProvider
    private Object[][] dataProvider_canWriteSpace()
    {
        return new Object[][]{
                {"H1.member    .wrapped { border :  1px   solid   black ! important; }",
                 "00000000010000000000000000000000100001000000010000000001000000000000",
                 "H1.member .wrapped{border: 1px solid black! important;}"},
                {"H1.member .wrapped { border :  1px   solid   black ! important; }",
                 "00000000010000000000000000000100001000000010000000001000000000000",
                 "H1.member .wrapped{border: 1px solid black! important;}"},
                {"H1.member [title = '  test ' ] .wrapped { border :  1px   solid   black ! important; }",
                 "00000000000000000000110000100010000000000000000000100001000000010000000001000000000000",
                 "H1.member[title='  test '] .wrapped{border: 1px solid black! important;}"},
        };
    }







    @Test(dataProvider = "dataProvider_canWriteThis")
    public void testCanWriteIt(String cssText, String expectedFlags) throws Exception
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();

        for (int i = 0, n = cssText.length(); i < n; i++)
        {
            char current = cssText.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.canWriteIt(current);
            processor.after(current);

            assertEquals(actual, expected, getMessageForWriteThis(cssText, i+1, expected));
        }
    }

    private String getMessageForWriteThis(String css, int len, boolean expected)
    {
        if (expected)
        {
            return String.format("This space can be written:\n%s\n%s at here", css.substring(0, len), getMarker(len-1));
        }
        else
        {
            return String.format("This symbol must not be written:\n%s\n%s at here", css.substring(0, len), getMarker(len-1));
        }
    }

    @DataProvider
    private Object[][] dataProvider_canWriteThis()
    {
        return new Object[][]{
                {"H1.member[font='Arial   sans serif']    .wrapped { border :  1px   solid   black ! important; }",
                 "11111111111111111111111111111111111100001111111101011111101001110001111100011111010111111111101"}
        };
    }


    @Test(dataProvider = "dataProvider_canWriteSpaceNow")
    public void testCanWriteSpaceNow(String cssText, String expectedFlags) throws Exception
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();

        for (int i = 0, n = cssText.length(); i < n; i++)
        {
            char current = cssText.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = current == ' ' && processor.canWriteSpaceNow();
            processor.after(current);

            assertEquals(actual, expected, getMessageForCanWriteSpaceNow(cssText, i + 1, expected));
        }
    }

    private String getMessageForCanWriteSpaceNow(String css, int len, boolean expected)
    {
        if (expected)
        {
            return String.format("This space can be written:\n%s\n%s at here", css.substring(0, len), getMarker(len-1));
        }
        else
        {
            return String.format("This space must not be written:\n%s\n%s at here", css.substring(0, len), getMarker(len-1));
        }
    }

    @DataProvider
    private Object[][] dataProvider_canWriteSpaceNow()
    {
        return new Object[][]{
                {"H1.member[font='Arial   sans serif']    .wrapped { border :  1px   solid   black ! important; }",
                 "00000000000000000000011100001000000010000000000010100000010100001000000010000000101000000000010"}
        };
    }




    private String getMarker(int len)
    {
        if (len <= 0) return "";

        char spaces[] = new char[len+1];
        Arrays.fill(spaces, ' ');
        spaces[len] = '^';
        return new String(spaces);
    }

}