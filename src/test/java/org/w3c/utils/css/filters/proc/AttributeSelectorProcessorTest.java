package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestBooleanMethod;
import test.TestHelpers;

/**
 * Attribute selector processor test.
 *
 * Created by k002 on 09.09.2016.
 */
public class AttributeSelectorProcessorTest {

    @Test(dataProvider = "dataProvider_IsInAttributeName")
    public void testIsInAttributeName(String text, String flags) throws Exception
    {
        final AttributeSelectorProcessor processor = new AttributeSelectorProcessor();

        TestHelpers.testFlowByBitmapEx("isInAttributeName", text, flags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isInAttributeName();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeName()
    {
        return new Object[][] {
                {"   toto|src  ^=  '/img/portal'   ",
                 "   11111111                      "},
                {"   *|src  ^=  '/img/portal'   ",
                 "   11111                      "},
                {"   |src  ^=  '/img/portal'   ",
                 "   1111                      "},
                {"toto|src  ^=  '/img/portal'   ",
                 "11111111                      "},
                {"*|src  ^=  '/img/portal'   ",
                 "11111                      "},
                {"|src  ^=  '/img/portal'   ",
                 "1111                      "},
                {"   src  ^=  '/img/portal'   ",
                 "   111                      "},
                {"   src  ^=  '/img/portal'   ",
                 "   111                      "},
                {"   s\\rc  ^=  '/img/portal'   ",
                 "   1\\11                      "},
        };
    }



    @Test(dataProvider = "dataProvider_IsInAttributeMatcher")
    public void testIsInAttributeMatcher(String text, String flags) throws Exception
    {
        final AttributeSelectorProcessor processor = new AttributeSelectorProcessor();

        TestHelpers.testFlowByBitmapEx("isInAttributeMatcher", text, flags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isInAttributeMatcher();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeMatcher()
    {
        return new Object[][] {
                {
                        "src^=/img/",
                        "   11     "
                },
                {
                        "src*=/img/",
                        "   11     "
                },
                {
                        "src|=/img/",
                        "   11     "
                },
                {
                        "   src  ^=  '/img/portal'   ",
                        "        11                  "
                },
                {
                        "   src  ^=  '/img/portal'   ",
                        "        11                  "
                },
                {
                        "   src  ^==  '/img/portal'   ",
                        "        11                   "
                },
                {
                        "    src   ^=  '/img/portal'   ",
                        "          11                  "
                },
                {
                        "   src  *=  '/img/portal'   ",
                        "        11                  "
                },
                {
                        "   src  |=  '/img/portal'   ",
                        "        11                  "
                },
                {
                        "   s\\rc  *=  '/img/portal'   ",
                        "    \t    11                  "
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsInAttributeValue")
    public void testIsInAttributeValue(String text, String flags) throws Exception
    {
        final AttributeSelectorProcessor processor = new AttributeSelectorProcessor();

        TestHelpers.testFlowByBitmapEx("isInAttributeValue", text, flags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isInAttributeValue();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttributeValue()
    {
        return new Object[][] {
                {" href=\" ",
                 "      11"},
                {"*|src^=/img/",
                 "       11111"},
                {"|src^=/img/  ",
                 "      11111  "},
                {"foo|src  ^=  '/img/portal'   ",
                 "             1111111111111   "},
                {"   src  ^=  /img/portal   ",
                 "            11111111111   "},
                {"    src   ^=  '/img/portal'   ",
                 "              1111111111111   "},
                {"    src    ^=  '/img/  portal'   ",
                 "               111111111111111   "},
                {"    src    ^=  /img/  portal   ",
                 "               11111           "},
                {"     src    ^=  '/img/\\ portal'   ",
                 "                111111\\11111111   "},
                {"   src  ^=  '/img/ portal'   ",
                 "            11111111111111   "},
        };
    }
}