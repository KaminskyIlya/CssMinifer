package org.w3c.utils.css.help;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Home on 06.12.2015.
 */
public class StringPatternsTest
{
    @DataProvider
    private Object[][] leftPatternSamples()
    {
        return new Object[][] {
                {new String[]{"col-xs-1", "col-xs-2", "col-xs-3", "col-xs-4"}, "col-xs-"},
                {new String[]{"row-xs-1", "row-xs-2", "col-xs-3", "col-xs-4", "col-xs-5"}, "col-xs-"},
                {new String[]{"btn-micro", "btn-mini", "btn-small", "btn-normal", "btn-large"}, "btn-"},
        };
    }


    @Test
    public void testLeftPattern() throws Exception
    {
        List<PatternMatcher> matchers = StringPatterns.leftPattern(Arrays.asList("div", "icon", "row-xs-1", "row-xs-2", "col-xs-1", "col-xs-2", "col-xs-3", "col-xs-4"));
        assertEquals(matchers.size(), 2);
        PatternMatcher m;

        m = matchers.get(0);
        assertEquals(m.getHash(), "row-xs-");
        m = matchers.get(1);
        assertEquals(m.getHash(), "col-xs-");
    }
}