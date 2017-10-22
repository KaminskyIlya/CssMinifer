package org.w3c.utils.css.model.values;

import org.testng.annotations.Test;

/**
 * String value unit tests.
 *
 * Created by Home on 22.10.2017.
 */
public class StringValueTest
{
    @Test(expectedExceptions = {AssertionError.class})
    public void testSetValue_WhenNullString() throws Exception
    {
        StringValue value = new StringValue();
        value.setValue(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetValue_WhenEmptyString() throws Exception
    {
        StringValue value = new StringValue();
        value.setValue("");
    }
}