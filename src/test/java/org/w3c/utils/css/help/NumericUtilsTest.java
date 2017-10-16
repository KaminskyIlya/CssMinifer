package org.w3c.utils.css.help;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * NumericUtils class unit test.
 *
 * Created by Home on 09.11.2015.
 */
public class NumericUtilsTest
{

    @Test(dataProvider = "dataProvider_RemoveZero")
    public void testRemoveLeadingZero(String source, String expected) throws Exception
    {
        String actual = NumericUtils.removeLeadingZero(source);
        assertEquals(actual, expected, String.format("Invalid transform for number %s to %s", source, actual));
    }

    @DataProvider
    private Object[][] dataProvider_RemoveZero()
    {
        return new Object[][]{
                {"-56.434435e-10", "-56.434435e-10"},
                {"-56.434435E+10", "-56.434435E+10"},
                {"-0.434435e-10", "-.434435e-10"},
                {"-0.434435e+10", "-.434435e+10"},
                {"0.434435E-10", ".434435E-10"},
                {"0.434435E+10", ".434435E+10"},
                {"0.555", ".555"},
                {"-0.45777", "-.45777"},
                {"0.5", ".5"},
                {"-0.5", "-.5"},
                {"6", "6"},
        };
    }

    @Test(dataProvider = "dataProvider_LossPrecision")
    public void testLossPrecision(String source, int accuracy, String expected) throws Exception
    {
        String actual = NumericUtils.lossPrecision(source, accuracy);
        assertEquals(actual, expected, String.format("Lower accuracy for number %s failed", source));
    }

    @DataProvider
    private Object[][] dataProvider_LossPrecision()
    {
        return new Object[][]{
                {"-432456.434435", 3, "-432456.434"},
                {"-56.434435", 3, "-56.434"},
                {"-56.434577", 3, "-56.435"},
                {"56.43417788998", 3, "56.434"},
                {"56.43417788998e-2", 3, "0.564"},
                {"0.555", 3, "0.555"},
                {"-0.555", 3, "-0.555"},
                {"0.5", 3, "0.5"},
                {"0.55", 3, "0.55"},
                {"-0.5", 3, "-0.5"},
                {"-0.55", 3, "-0.55"},
                {"0.0008", 3, "0.001"},
                {"8000", 3, "8000"},
        };
    }

    @Test(dataProvider = "dataProvider_CompactBloatedBigNumber")
    public void testCompactBloatedBigNumber(String source, String expected) throws Exception
    {
        String actual = NumericUtils.compactBloatedBigNumber(source);
        assertEquals(actual, expected, String.format("Number %s compact failed.", source));
    }

    @DataProvider
    private Object[][] dataProvider_CompactBloatedBigNumber()
    {
        return new Object[][]{
                {"1000", "1e3"},
                {"20000", "2e4"},
                {"300000", "3e5"},
                {"100", "100"},
                {"10", "10"},
                {"410", "410"},
                {"410000", "410000"},
                {":70000", ":7e4"},
                {"-50000", "-5e4"},
                {"+50000", "+5e4"},
                {":75000", ":75000"},
                {"-432456.434435", "-432456.434435"},
        };
    }


    @Test(dataProvider = "dataProvider_CompactBloatedLowNumber")
    public void testCompactBloatedLowNumber(String source, String expected) throws Exception
    {
        String actual = NumericUtils.compactBloatedLowNumber(source);
        assertEquals(actual, expected, String.format("Number %s compact failed.", source));
    }

    @DataProvider
    private Object[][] dataProvider_CompactBloatedLowNumber()
    {
        return new Object[][]{
                {"0.0001", "1e-4"},
                {"-0.0001", "-1e-4"},
                {"-0.0005", "-5e-4"},
                {"-0.00056", "-56e-4"},
                {"+0.003", "+3e-3"},
                {":0.003", ":3e-3"},
                {"0.07", "0.07"},
                {"0.0008", "8e-4"},
                {"0.00080", "0.00080"},
                {".0008", "8e-4"},
                {"-.0008", "-8e-4"},
                {"+.0008", "+8e-4"},
        };
    }

    @Test(dataProvider = "dataProvider_formatFloat")
    public void testFormatFloat(float number, String expected) throws Exception
    {
        String actual = NumericUtils.format(number);
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] dataProvider_formatFloat()
    {
        return new Object[][]{
                {0f, "0"},
                {0.11f, "0.11"},
                {0.1f, "0.1"},
                {0.5f, "0.5"},
                {0.6f, "0.6"},
                {0.0f, "0"},
                {1.0f, "1"},
                {1f, "1"},
                {1.324000f, "1.324"},
        };
    }

    @Test(dataProvider = "dataProvider_formatDouble")
    public void testFormatDouble(double number, String expected) throws Exception
    {
        String actual = NumericUtils.format(number);
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] dataProvider_formatDouble()
    {
        return new Object[][]{
                {0d, "0"},
                {0.11d, "0.11"},
                {0.1d, "0.1"},
                {0.5d, "0.5"},
                {0.6d, "0.6"},
                {0.0d, "0"},
                {1.0d, "1"},
                {1d, "1"},
                {1.324000d, "1.324"},
        };
    }

    @Test(dataProvider = "dataProvider_isNumber")
    public void testIsNumber(String number, boolean expected) throws Exception
    {
        boolean actual = NumericUtils.isNumber(number);
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] dataProvider_isNumber()
    {
        return new Object[][]{
                {"", false},
                {"1", true},
                {"+1", true},
                {"-1", true},
                {"+1.2", true},
                {"-1.2", true},
                {".2", true},
                {"+.2", true},
                {"-.2", true},
                {"+132323.2332323", true},
                {"-132334.2654724", true},
                {"+132323.2332323e", false},
                {"-132334.2654724e+", false},
                {"+132323.2332323e-", false},
                {"-132334.2654724e+1", true},
                {"+132323.2332323e-2", true},
                {"-132334.2654724e+10", true},
                {"+132323.2332323e-12", true},
                {"-132334.2654724E+10", true},
                {"+132323.2332323E-12", true},
                {"-132334.2654724e", false},
                {"+132323.2332.323", false},
                {"-132334.2654.724", false},
                {"1e3", true},
                {"1e+3", true},
                {"1e-2", true},
                {"1E+3", true},
                {"1E-2", true},
                {"1E+3e", false},
                {"1E-2e", false},
                {"1E+3e1", false},
                {"1E-2e1", false},
                {"1E?", false},
                {"1EM", false},
                {"-1.5e+3", true},
                {"+1.5e-2", true},
                {"1.5e-2", true},
                {"-.5e+3", true},
                {"+.5e-2", true},
                {".5e-2", true},
        };
    }
}