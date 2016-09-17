package org.w3c.utils.css.model.selectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static test.TestHelpers.equalsSpecificity;

/**
 * Selector functional test
 * TODO: analyze test + specificity test
 *
 * Created by Home on 15.09.2016.
 */
public class CssSelectorTest
{
    @Test(dataProvider = "dataProvider_TestSpecificity")
    public void testTestSpecificity(String css, int q, int a, int b, int c) throws Exception
    {
        CssSelector selector = new CssSelector(css);
        selector.analyze();

        assertEquals(selector.getQualifiers().size(), q);
        equalsSpecificity(selector.getSpecificity(), a, b, c);
    }

    @DataProvider
    private Object[][] dataProvider_TestSpecificity()
    {
        return new Object[][] {
                {"p.article:nth-child(even) ~ font[color=\"red\"]", 2, 0, 3, 2},
                {"* ~ H1 + P .selector#id > .wrapper > a:hover ~ p::before", 7, 1, 4, 4},
                {"*   ~ H1 + P .selector#id   *   .wrapper > a:hover ~ p::before", 7, 1, 4, 4},
                {"*", 1,                0, 0, 0},
                {"LI", 1,               0, 0, 1},
                {"UL LI", 2,            0, 0, 2},
                {"UL OL+LI", 3,         0, 0, 3},
                {"H1 + *[REL=up]", 2,   0, 1, 1},
                {"UL OL LI.red", 3,     0, 1, 3},
                {"LI.red.level", 1,     0, 2, 1},
                {"#x34y", 1,            1, 0, 0},
                {"#s12:not(FOO)", 1,    1, 0, 1},
        };
    }
}