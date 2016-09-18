package org.w3c.utils.css.model.selectors;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import test.TestHelpers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

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

    @Test(expectedExceptions = CssParsingException.class)
    public void testBadSelector_RecursiveNegation() throws Exception
    {
        NegateSelector selector = new NegateSelector("not(:not(FOO))");
        selector.analyze();
    }

    @Test
    public void testRecursiveNegation_TestMessage() throws Exception
    {
        CssParsingException expect = null;
        NegateSelector selector = new NegateSelector(" not( :not(FOO) )");
        try
        {
            selector.analyze();
        }
        catch (CssParsingException e)
        {
            expect = e;
        }
        assertNotNull(expect);
        assertEquals(expect.getMessage(), "Forbidden recursive negation  not( :not(FOO) )");
        assertEquals(expect.getPosition(), 5);
        assertEquals(expect.getLength(), 11);
        assertEquals(expect.getLevel(), EExceptionLevel.ERROR);
    }

    @Test(expectedExceptions = CssParsingException.class)
    public void testBadSelector_EmptyNegation() throws Exception
    {
        NegateSelector selector = new NegateSelector("not()");
        selector.analyze();
    }


    @Test
    public void testEmptyNegation_TestMessage() throws Exception
    {
        CssParsingException expect = null;
        NegateSelector selector = new NegateSelector(" not(   )");
        try
        {
            selector.analyze();
        }
        catch (CssParsingException e)
        {
            expect = e;
        }
        assertNotNull(expect);
        assertEquals(expect.getMessage(), "Empty negation:  not(   )");
        assertEquals(expect.getPosition(), 5);
        assertEquals(expect.getLength(), 3);
        assertEquals(expect.getLevel(), EExceptionLevel.ERROR);
    }

    @Test
    public void testEmptyNegation_TestMessage2() throws Exception
    {
        CssParsingException expect = null;
        NegateSelector selector = new NegateSelector(" not()");
        try
        {
            selector.analyze();
        }
        catch (CssParsingException e)
        {
            expect = e;
        }
        assertNotNull(expect);
        assertEquals(expect.getMessage(), "Empty negation:  not()");
        assertEquals(expect.getPosition(), 5);
        assertEquals(expect.getLength(), 0);
        assertEquals(expect.getLevel(), EExceptionLevel.ERROR);
    }
}