package org.w3c.utils.css.model.selectors;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.model.CssSelectorSpecificity;
import test.TestHelpers;

/**
 * Qualifier functional test.
 *
 * Created by Home on 28.08.2016.
 */
public class QualifierTest
{

    @Test(dataProvider = "dataProvider_Analyze")
    public void testAnalyze(String selector, int a, int b, int c) throws Exception
    {
        Qualifier qualifier = new Qualifier(selector);
        qualifier.analyze();

        CssSelectorSpecificity specificity = qualifier.getSpecificity();

        TestHelpers.equalsSpecificity(specificity, a, b, c);
    }

    @DataProvider
    public Object[][] dataProvider_Analyze()
    {
        return new Object[][] {
                {"*", 0, 0, 0},
                {"LI", 0, 0, 1},
                {"LI.red.level", 0, 2, 1},
                {"LI.red", 0, 1, 1},
                {"*[REL=up]", 0, 1, 0},
                {"A[REL=up]", 0, 1, 1},
                {"A[REL=up][href]", 0, 2, 1},
                {"A[REL=up][href].red.even", 0, 4, 1},
                {"#x34y", 1, 0, 0},
                {"#s12:not(FOO)", 1, 0, 1},
                {"#s12:nth-child(2n+1)", 1, 1, 0},
        };
    }

    @Test(dataProvider = "dataProvider_TypeAndNamespaces")
    public void testTypeAndNamespaces(String css, String expectedNS, String expectedType) throws Exception
    {
        Qualifier qualifier = new Qualifier(css);
        qualifier.analyze();

        Assert.assertEquals(qualifier.getType(), expectedType);
        Assert.assertEquals(qualifier.getNamespace(), expectedNS);

    }
    @DataProvider
    public Object[][] dataProvider_TypeAndNamespaces()
    {
        return new Object[][]{
                {"", "", "*"}, // empty qualifier
                {"P", "", "P"}, // element only
                {"SPAN", "", "SPAN"}, //
                {"|DIV", "", "DIV"}, // element without a namespace
                {"ns|P", "ns", "P"}, // element in namespace 'ns'
                {"ns|*", "ns", "*"}, // namespace and universal element
                {"*|DIV", "*", "DIV"}, // element in any namespace
                {"*|*", "*", "*"}, // any element
                {"*", "", "*"}, // any element without namespace
                {"|*", "", "*"}, // any element without namespace
        };
    }
}