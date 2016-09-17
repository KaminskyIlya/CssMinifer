package org.w3c.utils.css.model.selectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import test.TestHelpers;

import static org.testng.Assert.assertEquals;

/**
 * Order selector functional test.
 *
 * Created by Home on 17.09.2016.
 */
public class OrderSelectorTest
{

    @Test(dataProvider = "dataProvider_Analyze")
    public void testAnalyze(String text, String name, int a, int b) throws Exception
    {
        OrderSelector selector = new OrderSelector(text);
        selector.analyze();

        assertEquals(selector.getName(), name);
        assertEquals(selector.getA(), a);
        assertEquals(selector.getB(), b);
        TestHelpers.equalsSpecificity(selector.getSpecificity(), 0, 1, 0);
    }

    @DataProvider
    private Object[][] dataProvider_Analyze()
    {
        return new Object[][]{
                {"nth-child(2n)", "nth-child", 2, 0},
                {"nth-child(+2n)", "nth-child", 2, 0},
                {"nth-child(-2n)", "nth-child", -2, 0},
                {"nth-child(2n+3)", "nth-child", 2, 3},
                {"nth-child(+2n+3)", "nth-child", 2, 3},
                {"nth-child(-2n+3)", "nth-child", -2, 3},
                {"nth-child(2n-3)", "nth-child", 2, -3},
                {"nth-child(+2n-3)", "nth-child", 2, -3},
                {"nth-child(-2n-3)", "nth-child", -2, -3},
                {"nth-child(3)", "nth-child", 0, 3},
                {"nth-child(+3)", "nth-child", 0, 3},
                {"nth-child(-3)", "nth-child", 0, -3},

                {"Nth-Child(  2n )", "nth-child", 2, 0},
                {"nth-child(  +2n)", "nth-child", 2, 0},
                {"nth-child(-2n   )", "nth-child", -2, 0},
                {"nth-child( 2n  +  3 )", "nth-child", 2, 3},
                {"nth-child( +2n+  3)", "nth-child", 2, 3},
                {"nth-last-child(-2n+3  )", "nth-last-child", -2, 3},
                {"nth-child(2n - 3)", "nth-child", 2, -3},
                {"nth-of-type(+2n  -  3)", "nth-of-type", 2, -3},
                {"nth-child(  -2n- 3  )", "nth-child", -2, -3},
                {"last-of-type( 3 )", "last-of-type", 0, 3},
                {"nth-child(  +3)", "nth-child", 0, 3},
                {"nth-child(-3  )", "nth-child", 0, -3},
        };
    }

    @Test(expectedExceptions = CssParsingException.class, dataProvider = "dataProvider_InvalidSelector")
    public void testInvalidSelectors(String text) throws Exception
    {
        OrderSelector selector = new OrderSelector(text);
        selector.analyze();
    }

    @DataProvider
    private Object[][] dataProvider_InvalidSelector()
    {
        return new Object[][] {
                {"nth-child(3 n)"},
                {"nth-child(+ 2n)"},
                {"nth-child(+ 2)"},
        };
    }
}