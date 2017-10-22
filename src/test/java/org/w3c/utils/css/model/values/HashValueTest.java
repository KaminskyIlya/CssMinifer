package org.w3c.utils.css.model.values;

import org.testng.annotations.Test;

/**
 * Hash value unit tests.
 *
 * Created by Home on 22.10.2017.
 */
public class HashValueTest
{
    @Test(expectedExceptions = {AssertionError.class})
    public void testConstructor_WhenNullInitialValue() throws Exception
    {
        HashValue value = new HashValue(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testConstructor_WhenWrongInitialValue() throws Exception
    {
        HashValue value = new HashValue("fee"); // it must be start from #
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetHash_WhenNullValue() throws Exception
    {
        HashValue value = new HashValue("#fee");
        value.setHash(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetHash_WhenWrongValue() throws Exception
    {
        HashValue value = new HashValue("#fee");
        value.setHash("fee");
    }
}