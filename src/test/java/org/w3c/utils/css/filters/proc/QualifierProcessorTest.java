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
                }
        };
    }
}