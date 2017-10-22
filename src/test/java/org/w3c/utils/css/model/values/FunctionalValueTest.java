package org.w3c.utils.css.model.values;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Functional value unit tests.
 *
 * Created by Home on 22.10.2017.
 */
public class FunctionalValueTest
{

    @Test
    public void testSetName() throws Exception
    {
        FunctionalValue func = new FunctionalValue();
        func.setName("rGBa");
        assertEquals(func.getName(), "rgba");
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetName_WhenNullName() throws Exception
    {
        FunctionalValue func = new FunctionalValue();
        func.setName(null);
    }

    @Test(expectedExceptions = {AssertionError.class})
    public void testSetName_WhenEmptyName() throws Exception
    {
        FunctionalValue func = new FunctionalValue();
        func.setName("");
    }

    @Test
    public void testToString() throws Exception
    {
        FunctionalValue func = new FunctionalValue();
        func.setName("linear-gradient");

        ComponentValue args = new ComponentValue();
        args.addValue(new KeywordValue("to"));
        args.addValue(new KeywordValue("bottom"));
        args.beginNewValuesGroup();
        args.addValue(new KeywordValue("orange"));
        args.beginNewValuesGroup();
        args.addValue(new KeywordValue("red"));
        args.addValue(new UnitValue(60f, "%"));
        args.beginNewValuesGroup();
        args.addValue(new KeywordValue("cyan"));

        func.setArguments(args);
        assertEquals(func.toString(), "linear-gradient(to bottom, orange, red 60%, cyan)");
    }
}