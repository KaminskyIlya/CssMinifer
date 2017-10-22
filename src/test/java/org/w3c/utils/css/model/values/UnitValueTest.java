package org.w3c.utils.css.model.values;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Unit value unit tests.
 *
 * Created by Home on 22.10.2017.
 */
public class UnitValueTest
{

    @Test(dataProvider = "unitValuesSamples")
    public void testToString(Float number, String unit, String expected) throws Exception
    {
        UnitValue value = new UnitValue();
        value.setValue(number);
        value.setUnit(unit);
        assertEquals(value.toString(), expected);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetValue() throws Exception
    {
        UnitValue value = new UnitValue();
        value.setValue(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetUnit() throws Exception
    {
        UnitValue value = new UnitValue();
        value.setUnit(null);
    }

    @DataProvider
    private Object[][] unitValuesSamples()
    {
        return new Object[][] {
                {1f, "em", "1em" },
                {5.25f, "%", "5.25%" },
                {0f, "", "0" },
        };
    }
}