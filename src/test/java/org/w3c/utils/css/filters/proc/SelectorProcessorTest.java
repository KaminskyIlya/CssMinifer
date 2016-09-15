package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestBooleanMethod;
import test.TestHelpers;

import static org.testng.Assert.*;

/**
 * Selector processor
 *
 * Created by Home on 29.11.2015.
 */
public class SelectorProcessorTest
{

    @Test(dataProvider = "dataProvider_IsInQualifier")
    public void testIsInQualifier(String selector, String expectedFlags) throws Exception
    {
        final SelectorProcessor processor = new SelectorProcessor();

        TestHelpers.testFlowByBitmap("isInQualifier", selector, expectedFlags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInQualifier();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInQualifier()
    {
        return new Object[][]{
            {"*", "1"},
            {"*.active",
             "11111111"},
            {".active",
             "11111111"},
            {".active.active + P > em ~ .seven:not([lang=ru]) + :hover",
             "11111111111111   1   11   111111111111111111111   111111"},
            {"#hash-code.active",
             "11111111111111111"},
            {"#hash-code:nth-\\>child(2n+1):not(:nth-child(3n)) ~ [href='test + for.class'] ~ .selected",
             "111111111111111\\11111111111111111111111111111111   1111111111111111111111111   111111111"},
            {"html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
             "1111   1111   111111111    111111111111111111   11111111111111111111"},
            {".test\\{15\\} > body",
             "11111\\111\\1   1111"},
            {".test{15} > body",
             "111111111   1111"},
        };
    }





    @Test(dataProvider = "dataProvider_IsInConjunction")
    public void testIsInConjunction(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInConjunction();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInConjunction", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInConjunction()
    {
        return new Object[][]{
                {"*", " "},
                {"* ~ H1 + P .selector#id > .wrapper > a:hover ~ p::before",
                 "  1    1                1          1         1          "},
                {"*.active",
                 "        "},
                {".active ~ .wrapper > .prefix + DIV",
                 "        1          1         1    "},
                {".active.active + P:pseudo",
                 "               1         "},
                {"#hash-code.active",
                 "                 "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test ~ > + for.class'].selected  >  div",
                 "                                                                                      1     "},
                {"html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
                 "     1      1           1                     1                     "},
        };
    }



}