package org.w3c.utils.css.filters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.testng.Assert.*;

/**
 * InlineCommentsFilter functional test.
 *
 * Created by Home on 08.11.2015.
 */
public class InlineCommentsFilterTest
{

    @Test(dataProvider = "dataProvider")
    public void testApply(String source, String expected) throws Exception
    {
        InlineCommentsFilter filter = new InlineCommentsFilter(new RecycledCharBuffer(source));
        filter.apply();
        assertEquals(filter.getBuffer().getOutput(), expected);
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][]{
                {"h1 {color:red; }\n" +
                 "  //h2 { color: yellow; }\n" +
                "body {background:url(http://image.gif);}\n",

                 "h1 {color:red; }\n" +
                 "  body {background:url(http://image.gif);}\n"},

                {"h1 {color:red; background: url('\\\'//test.me\\\'') }",
                 "h1 {color:red; background: url('\\\'//test.me\\\'') }"},

                {"h1 {color:red; background: url('test//.png') }",
                 "h1 {color:red; background: url('test//.png') }"},

                {"@media (screen//) {color:red; background: url('test//.png') }",
                 "@media (screen"},

                {"h1[attr~='//']) {color:red; background: url('test//.png') }",
                 "h1[attr~='//']) {color:red; background: url('test//.png') }"},
        };
    }
}