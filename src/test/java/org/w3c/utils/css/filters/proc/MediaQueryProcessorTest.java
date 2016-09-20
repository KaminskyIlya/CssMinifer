package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestBooleanMethod;
import test.TestHelpers;

/**
 * MediaQueryProcessor functional tests
 *
 * Created by Home on 19.09.2016.
 */
public class MediaQueryProcessorTest
{
    @Test(dataProvider = "dataProvider_InPrefix")
    public void testInPrefix(String query, String flags) throws Exception
    {
        final MediaQueryProcessor processor = new MediaQueryProcessor();

        TestHelpers.testFlowByBitmap("inPrefix", query, flags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.inPrefix();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_InPrefix()
    {
        return new Object[][] {
                {
                        "tv, screen and (color), only projection, not print and (min-width: 12in) and (max-width: 15in)",
                        "                        1111             111                                                  "
                },
                {
                        "tv, screen and (color), only _only_not_, not print and (min-width: 12in) and (max-width: 15in)",
                        "                        1111             111                                                  "
                },
                {
                        "tv, screen and (color), only only__not_, not print and (min-width: 12in) and (max-width: 15in)",
                        "                        1111             111                                                  "
                },
        };
    }

    @Test(dataProvider = "dataProvider_InMediaType")
    public void testInMediaType(String query, String flags) throws Exception
    {
        final MediaQueryProcessor processor = new MediaQueryProcessor();

        TestHelpers.testFlowByBitmap("inMediaType", query, flags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.inMediaType();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_InMediaType()
    {
        return new Object[][] {
                {
                        "tv, screen and (color), only projection, not print and (min-width: 12in) and (max-width: 15in)",
                        "11  111111                   1111111111      11111                                            "
                },
        };
    }

    @Test(dataProvider = "dataProvider_InConjunction")
    public void testInConjunction(String query, String flags) throws Exception
    {
        final MediaQueryProcessor processor = new MediaQueryProcessor();

        TestHelpers.testFlowByBitmap("inConjunction", query, flags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.inConjunction();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_InConjunction()
    {
        return new Object[][] {
                {
                        "tv, screen and (color), only projection, not print and (min-width: 12in) and (max-width: 15in)",
                        "           111                                     111                   111                  "
                },
                {
                        "tv, screen and (color), only _only_and_, not print and (min-width: 12in) and (max-width: 15in)",
                        "           111                                     111                   111                  "
                },
        };
    }

    @Test(dataProvider = "dataProvider_InExpression")
    public void testInExpression(String query, String flags) throws Exception
    {
        final MediaQueryProcessor processor = new MediaQueryProcessor();

        TestHelpers.testFlowByBitmap("inExpression", query, flags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.inExpression();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_InExpression()
    {
        return new Object[][] {
                {
                        "tv, screen and (color), only projection, not print and (min-width: 12in) and (max-width: 15in)",
                        "               1111111                                 11111111111111111     11111111111111111"
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsFinished")
    public void testIsFinished(String query, String flags) throws Exception
    {
        final MediaQueryProcessor processor = new MediaQueryProcessor();

        TestHelpers.testFlowByBitmap("isFinished", query, flags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.isFinished();
            }
        });
    }

    @DataProvider
    private Object[][] dataProvider_IsFinished()
    {
        return new Object[][] {
                {
                        "tv, screen and (color), only projection, not print and (min-width: 12in) and (max-width: 15in) {",
                        "  1                   1                1                                                       1"
                },
        };
    }
}