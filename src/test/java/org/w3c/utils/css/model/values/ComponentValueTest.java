package org.w3c.utils.css.model.values;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Component value unit test.
 *
 * Created by Home on 22.10.2017.
 */
public class ComponentValueTest
{
    @Test
    public void testToString() throws Exception
    {
        ComponentValue value = new ComponentValue();

        value.addValue(new KeywordValue("solid"));
        assertEquals(value.toString(), "solid");
        assertEquals(value.isSingleValue(), true);

        UnitValue one_pixel = new UnitValue();
        one_pixel.setValue(1f);
        one_pixel.setUnit("px");
        value.addValue(one_pixel);
        assertEquals(value.toString(), "solid 1px");
        assertEquals(value.isDoubleValue(), true);

        value.addValue(new HashValue("#f00"));
        assertEquals(value.toString(), "solid 1px #f00");
        assertEquals(value.isTripleValue(), true);
        assertEquals(value.isSimpleGroup(), true);

        value.beginNewValuesGroup();
        assertEquals(value.toString(), "solid 1px #f00");
        assertEquals(value.isSingleValue(), false);
        assertEquals(value.isDoubleValue(), false);
        assertEquals(value.isTripleValue(), false);
        assertEquals(value.isSimpleGroup(), false);

        value.addValue(new KeywordValue("inset"));
        UnitValue zero = new UnitValue();
        zero.setValue(0f);
        value.addValue(zero);
        value.addValue(zero);
        value.addValue(one_pixel);
        value.addValue(one_pixel);
        assertEquals(value.toString(), "solid 1px #f00, inset 0 0 1px 1px");
        assertEquals(value.isSimpleGroup(), false);
        assertEquals(value.isSingleValue(), false);
        assertEquals(value.isDoubleValue(), false);
        assertEquals(value.isTripleValue(), false);
        assertEquals(value.isQuadValue(), false);

        FunctionalValue rgba = new FunctionalValue();
        rgba.setName("rgba");
        ComponentValue args = new ComponentValue();
        args.addValue(new UnitValue(255f, ""));
        args.beginNewValuesGroup();
        args.addValue(new UnitValue(139f, ""));
        args.beginNewValuesGroup();
        args.addValue(new UnitValue(50f, ""));
        args.beginNewValuesGroup();
        args.addValue(new UnitValue(0.2f, ""));
        rgba.setArguments(args);
        value.addValue(rgba);
        assertEquals(value.toString(), "solid 1px #f00, inset 0 0 1px 1px rgba(255, 139, 50, 0.2)");
    }


    @Test(expectedExceptions = {AssertionError.class})
    public void testBeginNewGroup_WhenActiveGroupIsEmpty() throws Exception
    {
        ComponentValue value = new ComponentValue();
        value.beginNewValuesGroup();
    }
}