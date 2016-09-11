package org.w3c.utils.css.enums;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 16.08.2016.
 */
public class LengthTypeTest
{
    @Test(dataProvider = "textConvertToPixels_DataProvider")
    public void testConvertToPixels(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.px.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToPixels_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 1d, 96/2.54d},
                {LengthType.mm, 10d, 96/2.54d},
                {LengthType.q, 40d, 96/2.54d},
                {LengthType.In, 1d, 96d},
                {LengthType.pc, 6d, 96d},
                {LengthType.pt, 72d, 96d},
                {LengthType.px, 96d, 96d},
        };
    }

    @Test(dataProvider = "textConvertToPoints_DataProvider")
    public void testConvertToPoints(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.pt.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToPoints_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 2.54d, 72d},
                {LengthType.mm, 25.4d, 72d},
                {LengthType.q, 40*2.54d, 72d},
                {LengthType.In, 1d, 72d},
                {LengthType.pc, 6d, 72d},
                {LengthType.pt, 72d, 72d},
                {LengthType.px, 96d, 72d},
        };
    }

    @Test(dataProvider = "textConvertToPicas_DataProvider")
    public void testConvertToPicas(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.pc.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToPicas_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 2.54d, 6d},
                {LengthType.mm, 25.4d, 6d},
                {LengthType.q, 40*2.54d, 6d},
                {LengthType.In, 1d, 6d},
                {LengthType.pc, 6d, 6d},
                {LengthType.pt, 72d, 6d},
                {LengthType.px, 96d, 6d},
        };
    }

    @Test(dataProvider = "textConvertToInches_DataProvider")
    public void testConvertToInches(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.In.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToInches_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 2.54d, 1d},
                {LengthType.mm, 25.4d, 1d},
                {LengthType.q, 40*2.54d, 1d},
                {LengthType.In, 1d, 1d},
                {LengthType.pc, 6d, 1d},
                {LengthType.pt, 72d, 1d},
                {LengthType.px, 96d, 1d},
        };
    }

    @Test(dataProvider = "textConvertToQmm_DataProvider")
    public void testConvertToQmm(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.q.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToQmm_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 1d, 40d},
                {LengthType.mm, 10d, 40d},
                {LengthType.q, 1d, 1d},
                {LengthType.In, 1d, 2.54d/40},
                {LengthType.pc, 6d, 2.54d/40},
                {LengthType.pt, 72d, 2.54d/40},
                {LengthType.px, 96d, 2.54d/40},
        };
    }

    @Test(dataProvider = "textConvertToMillimeters_DataProvider")
    public void testConvertToMillimeters(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.mm.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToMillimeters_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 1d, 10d},
                {LengthType.mm, 1d, 1d},
                {LengthType.q, 40d, 10d},
                {LengthType.In, 1d, 25.4d},
                {LengthType.pc, 6d, 25.4d},
                {LengthType.pt, 72d, 25.4d},
                {LengthType.px, 96d, 25.4d},
        };
    }

    @Test(dataProvider = "textConvertToCentimeters_DataProvider")
    public void testConvertToCentimeters(LengthType source, double value, double expected) throws Exception
    {
        double actual = LengthType.cm.convertFrom(source, value);
        assertEquals(actual, expected, 0.001);
    }

    @DataProvider
    public Object[][] textConvertToCentimeters_DataProvider()
    {
        return new Object[][]{
                {LengthType.cm, 1d, 1d},
                {LengthType.mm, 10d, 1d},
                {LengthType.q, 40d, 1d},
                {LengthType.In, 1d, 2.54d},
                {LengthType.pc, 6d, 2.54d},
                {LengthType.pt, 72d, 2.54d},
                {LengthType.px, 96d, 2.54d},
        };
    }
}