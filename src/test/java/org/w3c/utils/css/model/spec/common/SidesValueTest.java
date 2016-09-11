package org.w3c.utils.css.model.spec.common;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.enums.CssColor;
import org.w3c.utils.css.enums.SideType;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * SadesValue abstract class unit tests.
 *
 * Created by Home on 15.08.2016.
 */
public class SidesValueTest
{
    /**
     * Child class for tested abstract class. Implements minimum methods.
     */
    private static class SadesValueChild extends SidesValue
    {
        public boolean isDefined()
        {
            return !values.isEmpty();
        }

        public int countDefinedValues()
        {
            return values.size();
        }

        public Map<SideType, CssValue> getDefinedValues()
        {
            return values;
        }
    }


/*
    @Test
    public void testGetSingleValue() throws Exception
    {
        // If we defined single value direct, we can get this value
        SidesValue v = new SadesValueChild();
        CssColorValue aqua = new CssColorValue(CssColor.aqua);
        v.setSingleValue(aqua);
        assertEquals(v.getSingleValue(), aqua.toString());

        // If we set left value additional, we can get smallest value as single (now is aqua)
        CssColorValue antiquewhite = new CssColorValue(CssColor.antiquewhite);
        v.setLeftValue(antiquewhite);
        assertEquals(v.getSingleValue(), aqua.toString());

        // If we set bottom value additional, we can get smallest value as single (now is tan)
        CssColorValue tan = new CssColorValue(CssColor.tan);
        v.setBottomValue(tan);
        assertEquals(v.getSingleValue(), tan.toString());

        // If we set right value additional, we can get smallest value as single (now is tan)
        CssColorValue azure = new CssColorValue(CssColor.azure);
        v.setRightValue(azure);
        assertEquals(v.getSingleValue(), tan.toString());

        // When set double value, we can get smallest value of they (now is red)
        CssColorValue red = new CssColorValue(CssColor.red);
        CssColorValue aquamarine = new CssColorValue(CssColor.aquamarine);
        v.setDoubleValue(aquamarine, red);
        assertEquals(v.getSingleValue(), red.toString());

        // When set triple value, we can get smallest value of they (now is azure)
        v.setTripleValue(antiquewhite, aquamarine, azure);
        assertEquals(v.getSingleValue(), azure.toString());
    }
*/

    @Test
    public void testIsSingleValue() throws Exception
    {
        {
            SidesValue v = new SadesValueChild();
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // When we setup as single value, we must get a single value
            v.setSingleValue(new CssColorValue(CssColor.aqua));
            assertEquals(v.isSingleValue(), true, "It must be a single value!");

            // When we setup as double value, it's not a single value
            v.setDoubleValue(new CssColorValue(CssColor.red), new CssColorValue(CssColor.blue));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // When we setup as triple value, it's not a single value
            v.setTripleValue(new CssColorValue(CssColor.red), new CssColorValue(CssColor.blue), new CssColorValue(CssColor.black));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");
        }
        {
            // When we setup value through it's components, we must get single value only fill equals all components
            SidesValue v = new SadesValueChild();
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // only single components defined, not a single value
            v.setBottomValue(new CssColorValue(CssColor.red));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // already two components defined, not a single value
            v.setTopValue(new CssColorValue(CssColor.red));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // already three components defined, not a single value
            v.setLeftValue(new CssColorValue(CssColor.red));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");

            // now all components defined and equals, it's a single value!
            v.setRightValue(new CssColorValue(CssColor.red));
            assertEquals(v.isSingleValue(), true, "Now it must be a single value!");

            // now all components defined, but not equals, it's not a single value!
            v.setRightValue(new CssColorValue(CssColor.blue));
            assertEquals(v.isSingleValue(), false, "It must not be a single value!");
        }
    }

/*    @Test
    public void testGetDoubleValue() throws Exception
    {
        {
            SidesValue v = new SadesValueChild();
            v.setSingleValue(new CssColorValue(CssColor.aqua));
            assertEquals(v.getDoubleValue(), CssColor.aqua + " " + CssColor.aqua);

            v.setDoubleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue));
            assertEquals(v.getDoubleValue(), CssColor.black + " " + CssColor.blue);

            v.setTripleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue), new CssColorValue(CssColor.red));
            assertEquals(v.getDoubleValue(), CssColor.black + " " + CssColor.blue);
        }
        {
            SidesValue v = new SadesValueChild();
            v.setTopValue(new CssColorValue(CssColor.red));
            v.setRightValue(new CssColorValue(CssColor.yellow));
            assertEquals(v.getDoubleValue(), CssColor.red + " " + CssColor.yellow);

            v.setBottomValue(new CssColorValue(CssColor.green));
            v.setLeftValue(new CssColorValue(CssColor.blue));
            assertEquals(v.getDoubleValue(), CssColor.red + " " + CssColor.yellow);
        }
    }*/

    @Test
    public void testIsDoubleValue() throws Exception
    {
        SidesValue v = new SadesValueChild();
        v.setSingleValue(new CssColorValue(CssColor.aqua));
        assertEquals(v.isDoubleValue(), false);

        v.setDoubleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue));
        assertEquals(v.isDoubleValue(), true);

        v.setTripleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue), new CssColorValue(CssColor.red));
        assertEquals(v.isDoubleValue(), false);
    }

    @Test
    public void testIsTripleValue() throws Exception
    {
        SidesValue v = new SadesValueChild();
        v.setSingleValue(new CssColorValue(CssColor.aqua));
        assertEquals(v.isTripleValue(), true);

        v.setDoubleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue));
        assertEquals(v.isTripleValue(), true);

        v.setTripleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue), new CssColorValue(CssColor.red));
        assertEquals(v.isTripleValue(), true);

        v.setLeftValue(new CssColorValue(CssColor.tan));
        assertEquals(v.isTripleValue(), false);
    }

    @Test
    public void testIsQuadValue_Complex() throws Exception
    {
        SidesValue v = new SadesValueChild();
        v.setSingleValue(new CssColorValue(CssColor.aqua));
        assertEquals(v.isQuadValue(), false);

        v.setDoubleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue));
        assertEquals(v.isQuadValue(), false);

        v.setTripleValue(new CssColorValue(CssColor.black), new CssColorValue(CssColor.blue), new CssColorValue(CssColor.red));
        assertEquals(v.isQuadValue(), false);

        v.setLeftValue(new CssColorValue(CssColor.tan));
        assertEquals(v.isQuadValue(), true);
    }

    @Test(dataProvider = "dataProvider_isQuadValue")
    public void testIsQuadValue(boolean expected, CssColor a, CssColor b, CssColor c, CssColor d) throws Exception
    {
        SidesValue v = new SadesValueChild();
        if (a != null) v.setTopValue(new CssColorValue(a));
        if (b != null) v.setRightValue(new CssColorValue(b));
        if (c != null) v.setBottomValue(new CssColorValue(c));
        if (d != null) v.setLeftValue(new CssColorValue(d));
        assertEquals(v.isQuadValue(), expected);
    }

    @DataProvider
    public Object[][] dataProvider_isQuadValue()
    {
        return new Object[][] {
                {true, CssColor.aqua, CssColor.black, CssColor.blue, CssColor.tan},

                {false, CssColor.aqua, null, null, null}, // only one component
                {false, null, CssColor.aqua, null, null}, // only one component
                {false, null, null, CssColor.aqua, null}, // only one component
                {false, null, null, null, CssColor.aqua}, // only one component
                {false, CssColor.aqua, CssColor.aqua, CssColor.aqua, CssColor.aqua}, // single
                {false, CssColor.aqua, CssColor.red, CssColor.aqua, CssColor.red}, // double

                // 4 defined, 2 unique
                {false, CssColor.aqua, CssColor.aqua, CssColor.aqua, CssColor.red},
                {false, CssColor.aqua, CssColor.aqua, CssColor.red, CssColor.aqua}, // triple
                {false, CssColor.aqua, CssColor.red, CssColor.aqua, CssColor.aqua},
                {false, CssColor.red, CssColor.aqua, CssColor.aqua, CssColor.aqua}, // triple

                // 4 defined, 3 unique
                {true,  CssColor.aqua, CssColor.tan, CssColor.aqua, CssColor.red},
                {true,  CssColor.aqua, CssColor.tan, CssColor.red, CssColor.aqua},
                {true,  CssColor.aqua, CssColor.aqua, CssColor.tan, CssColor.red},
                {true,  CssColor.aqua, CssColor.aqua, CssColor.red, CssColor.tan},
                {true,  CssColor.tan, CssColor.aqua, CssColor.aqua, CssColor.red},
                {false, CssColor.tan, CssColor.aqua, CssColor.red, CssColor.aqua}, // triple
                {false, CssColor.red, CssColor.aqua, CssColor.tan, CssColor.aqua}, // triple
                {true,  CssColor.red, CssColor.aqua, CssColor.aqua, CssColor.tan},

                // 4 defined, 4 unique
                {true, CssColor.aqua, CssColor.tan, CssColor.teal, CssColor.red},
        };
    }

    @Test
    public void testCountUniqueValues() throws Exception
    {
        SidesValue v = new SadesValueChild();

        v.setTopValue(new CssColorValue(CssColor.red));
        assertEquals(v.countUniqueValues(), 1);

        v.setBottomValue(new CssColorValue(CssColor.red));
        assertEquals(v.countUniqueValues(), 1);

        v.setBottomValue(new CssColorValue(CssColor.teal));
        assertEquals(v.countUniqueValues(), 2);

        v.setRightValue(new CssColorValue(CssColor.yellow));
        assertEquals(v.countUniqueValues(), 3);

        v.setLeftValue(new CssColorValue(CssColor.red));
        assertEquals(v.countUniqueValues(), 3);

        v.setLeftValue(new CssColorValue(CssColor.blue));
        assertEquals(v.countUniqueValues(), 4);
    }

    @Test
    public void testIsAllSidesDefined() throws Exception
    {
        SidesValue v = new SadesValueChild();

        v.setTopValue(new CssColorValue(CssColor.red));
        assertEquals(v.isAllSidesDefined(), false);

        v.setBottomValue(new CssColorValue(CssColor.red));
        assertEquals(v.isAllSidesDefined(), false);

        v.setBottomValue(new CssColorValue(CssColor.teal));
        assertEquals(v.isAllSidesDefined(), false);

        v.setRightValue(new CssColorValue(CssColor.yellow));
        assertEquals(v.isAllSidesDefined(), false);

        v.setLeftValue(new CssColorValue(CssColor.red));
        assertEquals(v.isAllSidesDefined(), true);

        v.setLeftValue(new CssColorValue(CssColor.blue));
        assertEquals(v.isAllSidesDefined(), true);
    }
}