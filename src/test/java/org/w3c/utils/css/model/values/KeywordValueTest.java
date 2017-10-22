package org.w3c.utils.css.model.values;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Keyword value unit tests.
 *
 * Created by Home on 22.10.2017.
 */
public class KeywordValueTest
{
    @Test(dataProvider = "testIsPrefixed_DataProvider")
    public void testIsPrefixed(String identifier, boolean expected) throws Exception
    {
        KeywordValue value = new KeywordValue(identifier);
        assertEquals(value.isPrefixed(), expected);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testConstructor() throws Exception
    {
        KeywordValue value = new KeywordValue(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetKeyword() throws Exception
    {
        KeywordValue value = new KeywordValue("test");
        value.setKeyword(null);
    }

    @DataProvider
    private Object[][] testIsPrefixed_DataProvider()
    {
        return new Object[][] {
                {"border-radius", false},
                {"text-shadow", false},


                {"-ms-border-radius", true},
                {"-mso-border-radius", true},
                {"-moz-border-radius", true},
                {"-o-border-radius", true},
                {"-xv-border-radius", true},
                {"-atsc-border-radius", true},
                {"-wap-border-radius", true},
                {"-khtml-border-radius", true},
                {"-webkit-border-radius", true},
                {"prince-border-radius", true},
                {"-ah-border-radius", true},
                {"-hp-border-radius", true},
                {"-ro-border-radius", true},
                {"-rim-border-radius", true},
                {"-tc-border-radius", true},
        };
    }
}