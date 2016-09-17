package org.w3c.utils.css.model.selectors;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestHelpers;

/**
 * NegateSelector functional tests.
 *
 * Created by Home on 17.09.2016.
 */
public class NegateSelectorTest
{
    @Test(dataProvider = "dataProvider_Analyze")
    public void testAnalyze(String expression, int a, int b, int c) throws Exception
    {
        NegateSelector selector = new NegateSelector(expression);
        selector.analyze();

        Assert.assertEquals(selector.getName(), "not");
        TestHelpers.equalsSpecificity(selector.getSpecificity(), a, b, c);
    }

    @DataProvider
    private Object[][] dataProvider_Analyze()
    {
        return new Object[][] {
                {"not(input[disabled].red)", 0, 2, 1},
                {"not(.red.yellow.white)", 0, 3, 0},
                {"not(:link:visited)", 0, 2, 0},
        };
    }
}