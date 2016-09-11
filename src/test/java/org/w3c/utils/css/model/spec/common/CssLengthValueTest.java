package org.w3c.utils.css.model.spec.common;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.enums.LengthType;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 16.08.2016.
 */
public class CssLengthValueTest
{

    @Test(dataProvider = "dataProvider_testIsEquals")
    public void testIsEquals(CssLengthValue a, CssLengthValue b, boolean equals) throws Exception
    {
        assertEquals(a.isEquals(b), equals);
    }

    @DataProvider
    public Object[][] dataProvider_testIsEquals()
    {
        return new Object[][] {
                // compare with different length/units (absolute)
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(10, LengthType.mm), true}, // 1cm = 10mm
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(15, LengthType.mm), false}, //1cm != 15mm

                // compare property-equals
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(1, LengthType.cm), true}, //1cm = 1cm
                {new CssLengthValue(5, LengthType.ex), new CssLengthValue(5, LengthType.ex), true}, //5ex = 5ex

                // compare zero corners
                {new CssLengthValue(0, LengthType.cm), new CssLengthValue(0, LengthType.mm), true}, //0cm = 0mm
                {new CssLengthValue(0, LengthType.ex), new CssLengthValue(0, LengthType.ex), true}, //0ex = 0ex
                {new CssLengthValue(0, LengthType.ex), new CssLengthValue(0, LengthType.vh), true}, //0ex = 0vh

                // compare with different length/unit (relative)
                {new CssLengthValue(5, LengthType.ex), new CssLengthValue(5, LengthType.ch), false}, // 5ex != 5ch
                {new CssLengthValue(3, LengthType.ex), new CssLengthValue(5, LengthType.ex), false}, // 3ex != 5ex
        };
    }

    @Test(dataProvider = "dataProvider_testEquals")
    public void testEquals(CssLengthValue a, CssLengthValue b, boolean equals) throws Exception
    {
        assertEquals(a.equals(b), equals);
    }

    @DataProvider
    public Object[][] dataProvider_testEquals()
    {
        return new Object[][] {
                // compare with different length/units (absolute)
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(10, LengthType.mm), false}, // 1cm not equals 10mm
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(15, LengthType.mm), false}, //1cm not equals 15mm

                // compare property-equals
                {new CssLengthValue(1, LengthType.cm), new CssLengthValue(1, LengthType.cm), true}, //1cm equals 1cm
                {new CssLengthValue(5, LengthType.ex), new CssLengthValue(5, LengthType.ex), true}, //5ex equals 5ex

                // compare zero corners
                {new CssLengthValue(0, LengthType.cm), new CssLengthValue(0, LengthType.mm), false}, //0cm not equals 0mm
                {new CssLengthValue(0, LengthType.ex), new CssLengthValue(0, LengthType.vh), false}, //0ex not equals 0vh
                {new CssLengthValue(0, LengthType.ex), new CssLengthValue(0, LengthType.ex), true}, //0ex equals 0ex

                // compare with different length/unit (relative)
                {new CssLengthValue(5, LengthType.ex), new CssLengthValue(5, LengthType.ch), false}, // 5ex not equals 5ch
                {new CssLengthValue(3, LengthType.ex), new CssLengthValue(5, LengthType.ex), false}, // 3ex not equals 5ex
        };
    }

    @Test
    public void testHashCode() throws Exception
    {

    }
}