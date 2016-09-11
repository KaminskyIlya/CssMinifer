package org.w3c.utils.css.filters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.testng.Assert.*;

/**
 * CommentsBlockFilter functional tests.
 *
 * Created by Home on 09.11.2015.
 */
public class CommentsBlockFilterTest
{

    @Test(dataProvider = "dataProvider")
    public void testApply(String source, String expected) throws Exception
    {
        CommentsBlockFilter filter = new CommentsBlockFilter(new RecycledCharBuffer(source));
        filter.apply();
        assertEquals(filter.getBuffer().getOutput(), expected);
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][]{
                {"h1 {color:red; }\n" +
                 "  h2 { /*color: yellow;*/ }\n" +
                 "/*body {background:url(http://image.gif);}\n*/",

                 "h1 {color:red; }\n" +
                 "  h2 {  }\n"},
        };
    }
}