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
            {".active.active",
             "11111111111111"},
            {"#hash-code.active",
             "11111111111111111"},
            {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected",
             "11111111111111111111      1111                                       111111111"},
            {"html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
             "1111   1111   111111111    111111111111111111   1111                "},
            {".test\\{15\\} > body",
             "11111111111   1111"},
            {".test{15} > body",
             "11111       1111"},
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
                {"*", "0"},
                {"*, H1 + P, .selector#id, .wrapper a:hover, p::before",
                 "  1  111  1             1        1        1         "},
                {"*.active",
                 "        "},
                {".active .wrapper  .prefix",
                 "       1        11       "},
                {".active.active + P:pseudo",
                 "              111        "},
                {"#hash-code.active",
                 "                 "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                                                              11111   "},
                {"html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
                 "    111    111         1111                  111                    "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInTag")
    public void testIsInTag(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInTag();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInTag", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInTag()
    {
        return new Object[][]{
                {"*", "1"},
                {"*, H1 + P, .selector#id, .wrapper a:hover, p::before",
                 "1  11   1                         1        1        "},
                {"*.active",
                 "1       "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                 1       "},
                {"#hash-code.active",
                 "                 "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                                                                   111"},
                {"html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
                 "1111   1111   1            1                    1111                "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInHash")
    public void testIsInHash(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInHash();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInHash", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInHash()
    {
        return new Object[][]{
                {"*", "0"},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                     11   1111           1111                 "},
                {"*, H1 + P, .selector#id, #name.wrapper a#even#test:hover, p::before",
                 "                     11   1111           1111 1111                 "},
                {"*.active",
                 "        "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                         "},
                {"#hash-code_selected.active",
                 " 111111111111111111       "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 " 111111111                                                                            "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                               111111                                             "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInClassName")
    public void testIsInClassName(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInClassName();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInClassName", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInClassName()
    {
        return new Object[][]{
                {"*", "0"},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "            11111111           1111111                        "},
                {"A[REL=up][href].red.even",
                 "                111 1111"},
                {"*.active",
                 "  111111"},
                {".active .wrapper  .prefix",
                 " 111111  1111111   111111"},
                {".active.active + P:pseudo",
                 " 111111 111111           "},
                {"#hash-code_selected.active",
                 "                    111111"},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                                                      11111111        "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                1111111                    11111                                  "},
                {".test\\{15\\} > body",
                 " 1111111111       "},
                {".test{15} > body",
                 " 1111           "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInPseudoClass")
    public void testIsInPseudoClass(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInPseudoClass();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInPseudoClass", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInPseudoClass()
    {
        return new Object[][]{
                {"*", "0"},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                                              11111     111111"},
                {"*.active",
                 "        "},
                {"*:active",
                 "  111111"},
                {"(2n+1):lang(ru)", // 1-st wrong, but next is passed
                 "       11111111"},
                {":active::before ",
                 " 111111  111111 "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                   111111"},
                {"#hash-code_selected.active",
                 "                          "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "           111111111111111 1111111111111111111                                        "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                                                 1111111111                       "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInAttributeMatcher")
    public void testIsInAttributeMatcher(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInAttributeMatcher();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInAttributeMatcher", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeMatcher()
    {
        return new Object[][]{
                {"*", "0"},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                                                              "},
                {"*.active[color]",
                 "               "},
                {"*:active[lang|=ru]",
                 "             11   "},
                {"#:active",
                 "        "},
                {"[href ^= 'image']:lang(ru)",
                 "      11                  "},
                {"[href ^= 'image'][rel=up]:lang(ru)",
                 "      11             1            "},
                {"[href ^= 'image'][rel|=up]:lang(ru)",
                 "      11             11            "},
                {":active::before ",
                 "                "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                         "},
                {"#hash-code_selected.active",
                 "                          "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                                   1                                  "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                                                                          11      "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInAttributeName")
    public void testIsInAttributeName(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInAttributeName();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInAttributeName", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeName()
    {
        return new Object[][]{
                {"*[color]",
                 "  11111 "},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                                                              "},
                {"*.active[color]",
                 "         11111 "},
                {"*:active[lang|=ru]",
                 "         1111     "},
                {"*:active[lang|=ru][href]",
                 "         1111      1111 "},
                {"*:active[lang|=ru][href=test]",
                 "         1111      1111      "},
                {"#:active",
                 "        "},
                {"[href ^= 'image']:lang(ru)",
                 " 1111                     "},
                {":active::before ",
                 "                "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                         "},
                {"#hash-code_selected.active",
                 "                          "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                               1111                                   "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                                                                    11111         "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInAttributeValue")
    public void testIsInAttributeValue(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInAttributeValue();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInAttributeValue", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeValue()
    {
        return new Object[][]{
                {"*[color]",
                 "        "},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                                                              "},
                {"*.active[color]",
                 "               "},
                {"*:active[lang|=ru]",
                 "               11 "},
                {"#:active",
                 "        "},
                {"[href ^= 'image']:lang(ru)",
                 "         1111111          "},
                {":active::before ",
                 "                "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo",
                 "                         "},
                {"#hash-code_selected.active",
                 "                          "},
                {"#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected  >  div",
                 "                                                    1111111111111111                  "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                 "                                                                             111  "},
        };
    }





    @Test(dataProvider = "dataProvider_IsInPseudoExpression")
    public void testIsInPseudoExpression(String selector, String expectedFlags) throws Exception
    {
        SelectorProcessor processor = new SelectorProcessor();

        for (int i = 0, n = selector.length(); i < n; i++)
        {
            char current = selector.charAt(i);
            boolean expected = expectedFlags.charAt(i) == '1';

            processor.before(current);
            boolean actual = processor.isInPseudoExpression();
            processor.after(current);

            assertEquals(actual, expected, TestHelpers.getMessageFor("isInPseudoExpression", selector, i));
        }
    }

    @DataProvider
    private Object[][] dataProvider_IsInPseudoExpression()
    {
        return new Object[][]{
                {"*", "0"},
                {"*, H1 + P, .selector#id, #name.wrapper a#even:hover, p::before",
                 "                                                              "},
                {"[href ^= 'image']:nth(2n+1) ",
                 "                     111111 "},
                {".active .wrapper  .prefix",
                 "                         "},
                {".active.active + P:pseudo:lang(ru)",
                 "                              1111"},
                {"[href ^= 'image']:lang(2n+1) ", // <-- incorrect, but must passed
                 "                      111111 "},
                {":active::before:nth(even):test",
                 "                   111111     "},
                {".active .wrapper:nth(odd) .prefix",
                 "                    11111        "},
                {"#hash-code_selected.active",
                 "                          "},
                {"#hash-code:nth-child(2n+1):not( :nth-child(3n) )[href='test for.class'].selected  >  div",
                 "                    111111    111111111111111111                                        "},
                {"html > body > p.article ~ span#active +  p.pages:first-line ~ span[ color ~= red ]",
                        "                                                                                  "},
        };
    }


}