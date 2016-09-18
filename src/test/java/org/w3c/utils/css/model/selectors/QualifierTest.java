package org.w3c.utils.css.model.selectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestHelpers;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Qualifier functional test.
 *
 * Created by Home on 28.08.2016.
 */
public class QualifierTest
{

    @Test(dataProvider = "dataProvider_Specificity")
    public void testSpecificity(String selector, int a, int b, int c) throws Exception
    {
        Qualifier qualifier = new Qualifier(selector);
        qualifier.analyze();
        TestHelpers.equalsSpecificity(qualifier.getSpecificity(), a, b, c);
    }

    @DataProvider
    public Object[][] dataProvider_Specificity()
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

        assertEquals(qualifier.getType(), expectedType);
        assertEquals(qualifier.getNamespace(), expectedNS);

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
                {"*|DIV", "*", "DIV"}, // element in any namespace
                {"ns|*", "ns", "*"}, // namespace and universal element
                {"*|*", "*", "*"}, // all elements
                {"|*", "", "*"}, // all elements without a namespace
                {"*", "", "*"}, // any element without namespace = all elements
        };
    }

    @Test
    public void testTestModel() throws Exception
    {
        Qualifier qualifier = new Qualifier("ns|P[aria-label].article.prolog:not(:first-child):hover");
        qualifier.analyze();
        assertEquals(qualifier.getNamespace(), "ns");
        assertEquals(qualifier.getType(), "P");
        assertEquals(qualifier.getAttributes().size(), 1);
        for (AttributeSelector s : qualifier.getAttributes())
        {
            assertEquals(s.getAttribute(), "aria-label");
        }
        assertTrue(qualifier.getClasses().contains("article"));
        assertTrue(qualifier.getClasses().contains("prolog"));
        assertEquals(qualifier.getConjunction(), ' ');

        List<PseudoSelector> list = new ArrayList<PseudoSelector>(qualifier.getPseudo());
        assertEquals(list.get(0).getName(), "hover");
        assertEquals(list.get(1).getName(), "not");
        assertTrue(list.get(1) instanceof NegateSelector);

        NegateSelector negate = (NegateSelector) list.get(1);
        assertEquals(negate.getNegation().getPseudo().size(), 1);

        list = new ArrayList<PseudoSelector>(negate.getNegation().getPseudo());
        assertEquals(list.get(0).getName(), "first-child");
    }
}