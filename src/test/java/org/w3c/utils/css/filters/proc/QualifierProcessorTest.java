package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestBooleanMethod;
import test.TestHelpers;

/**
 * Qualifier Processor functional tests
 *
 * Created by Home on 13.09.2016.
 */
public class QualifierProcessorTest
{

    @Test(dataProvider = "dataProvider_IsInTag")
    public void testIsInTag(String text, String bitmap) throws Exception
    {
        final QualifierProcessor processor = new QualifierProcessor();

        TestHelpers.testFlowByBitmap("isInTag", text, bitmap, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInTag();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInTag()
    {
        return new Object[][] {
                {
                        "*.test",
                        "1     "
                },
                {
                        "*|*.test",
                        "111     "
                },
                {
                        "*|SPAN#label",
                        "111111      "
                },
                {
                        "*|S\\PAN#label",
                        "111\\111      "
                },
                {
                        "TEXTAREA[lang = 'ru']",
                        "11111111             "
                },
                {
                        "DIV:hover",
                        "111      "
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsInHash")
    public void testIsInHash(String text, String bitmap) throws Exception
    {
        final QualifierProcessor processor = new QualifierProcessor();

        TestHelpers.testFlowByBitmap("isInHash", text, bitmap, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInHash();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInHash()
    {
        return new Object[][] {
                {
                        "*",
                        " "
                },
                {
                        "H1.selector#id#name.wrapper.a#even:hover(#test)",
                        "            11 1111           1111             "
                },
                {
                        "P.selector#id#name.wrapper::before#dd",
                        "           11 1111                 11"
                },
                {
                        "*.active",
                        "        "
                },
                {
                        "P\\#hash.active#test",
                        " \t             1111"
                },
                {
                        "P\\\\#hash.active#test",
                        " \t\t 1111        1111"
                },
                {
                        "P\\\\\\#hash.active#test",
                        " \t\t\t             1111"
                },
                {
                        "[attr#=hash]:nth-child(2n+1):not(a#test)[href='#test for.class'].selected#ref",
                        "                                                                          111"
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsInClass")
    public void testIsInClass(String text, String bitmap) throws Exception
    {
        final QualifierProcessor processor = new QualifierProcessor();

        TestHelpers.testFlowByBitmap("isInClass", text, bitmap, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInClass();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInClass()
    {
        return new Object[][] {
                {
                        "*",
                        " "
                },
                {
                        "*.active",
                        "  111111"
                },
                {
                        "H1.selector#id",
                        "   11111111   "
                },
                {
                        "A[REL=up][href].red.even",
                        "                111 1111"
                },
                {
                        ".active.wrapper.prefix",
                        " 111111 1111111 111111"
                },
                {
                        "P:not(.active).red[lang=ru]",
                        "               111         "
                },
                {
                        "P[\\.active='.test'].red[lang=ru]",
                        "  \t                 111         "
                },
                {
                        "#hash-code_selected.active",
                        "                    111111"
                },
                {
                        "#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected",
                        "                                                                      11111111"
                },
                {
                        ".test\\{15\\}:hover",
                        " 1111\\111\\1      "
                },
                {
                        ".test{15}",
                        " 1111    "
                },
        };
    }


    @Test(dataProvider = "dataProvider_IsInPseudo")
    public void testIsInPseudo(String text, String bitmap) throws Exception
    {
        final QualifierProcessor processor = new QualifierProcessor();

        TestHelpers.testFlowByBitmap("isInPseudo", text, bitmap, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInPseudo();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInPseudo()
    {
        return new Object[][] {
                {
                        "*",
                        " "
                },
                {
                        "*:active",
                        "  111111"
                },
                {
                        "*.active",
                        "        "
                },
                {
                        "H1.selector:not(#id)[attr:=ref]",
                        "            11111111           "
                },
                {
                        "H1.selector\\:not(#id)[attr:=ref]",
                        "           \t                    "
                },
                {
                        "A[REL=up][href].red.even",
                        "                        "
                },
                {
                        ":active::hover.prefix",
                        " 111111  11111        "
                },
                {
                        ".prefix:active::hover",
                        "        111111  11111"
                },
                {
                        "P:not(.active).red[lang=ru]",
                        "  111111111111             "
                },
                {
                        "P[\\.active='.test']:nth-child(2n+3)",
                        "  \t                 111111111111111"
                },
                {
                        "#hash-code_selected.active",
                        "                          "
                },
                {
                        "#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test for.class'].selected",
                        "           111111111111111 1111111111111111111                                "
                },
                {
                        "[href=':not(3n+1)'].selected:active",
                        "                             111111"
                },
                {
                        ".test:hover\\{15\\}#test",
                        "      11111\\111\\1     "
                },
                {
                        ":hover{15}",
                        " 11111    "
                },
                {
                        ":not([href])",
                        " 11111111111"
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsInAttr")
    public void testIsInAttr(String text, String bitmap) throws Exception
    {
        final QualifierProcessor processor = new QualifierProcessor();

        TestHelpers.testFlowByBitmap("isInAttr", text, bitmap, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isInAttr();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsInAttr()
    {
        return new Object[][] {
                {
                        "A[href^='/img']:not([lang=ru])",
                        " 11111111111111               "
                },
                {
                        "A\\[BCD\\]:not([lang=ru])[color=red]",
                        " \t    \t                11111111111"
                },
        };
    }
}