package org.w3c.utils.css.model.spec.common;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.enums.CssColor;

import static org.testng.Assert.assertEquals;

/**
 * CssColorValue class unit test.
 *
 * Created by Home on 15.08.2016.
 */
public class CssColorValueTest
{
    @Test(dataProvider = "dataProvider_IsInvisible")
    public void testIsInvisible(boolean expected, int r, int g, int b, float a, CssColor n) throws Exception
    {
        CssColorValue color = (n == null) ? new CssColorValue(r, g, b, a) : new CssColorValue(n);
        boolean actual = color.isInvisible();
        assertEquals(actual, expected, String.format("Invalid color invisibly for arguments (%d, %d, %d, %.2g, %s).", r, g, b, a, n));
    }

    @DataProvider
    public Object[][] dataProvider_IsInvisible()
    {
        return new Object[][]{
                {false, 255, 0, 14, 1f, null},
                {false, 255, 0, 14, .5f, null},
                {true, 255, 0, 14, 0f, null},
                {false, 255, 0, 14, 0f, CssColor.azure},
                {false, 255, 0, 14, 0.5f, CssColor.azure},
                {false, 255, 0, 14, 1f, CssColor.azure},
        };
    }

    @Test(dataProvider = "dataProvider_IsTransparent")
    public void testIsTransparent(boolean expected, int r, int g, int b, float a, CssColor n) throws Exception
    {
        CssColorValue color = (n == null) ? new CssColorValue(r, g, b, a) : new CssColorValue(n);
        boolean actual = color.isTransparent();
        assertEquals(actual, expected, String.format("Invalid color transparency for arguments (%d, %d, %d, %.2g, %s).", r, g, b, a, n));
    }

    @DataProvider
    public Object[][] dataProvider_IsTransparent()
    {
        return new Object[][]{
                {false, 255, 0, 14, 1f, null},
                {true, 255, 0, 14, .5f, null},
                {true, 255, 0, 14, 0f, null},
                {false, 255, 0, 14, 0f, CssColor.azure},
                {false, 255, 0, 14, 0.5f, CssColor.azure},
                {false, 255, 0, 14, 1f, CssColor.azure},
        };
    }

    @Test(dataProvider = "dataProvider_IsSolid")
    public void testIsSolid(boolean expected, int r, int g, int b, float a, CssColor n) throws Exception
    {
        CssColorValue color = (n == null) ? new CssColorValue(r, g, b, a) : new CssColorValue(n);
        boolean actual = color.isSolid();
        assertEquals(actual, expected, String.format("Invalid solid color for arguments (%d, %d, %d, %.2g, %s).", r, g, b, a, n));
    }

    @DataProvider
    public Object[][] dataProvider_IsSolid()
    {
        return new Object[][]{
                {true, 255, 0, 14, 1f, null},
                {false, 255, 0, 14, .5f, null},
                {false, 255, 0, 14, 0f, null},
                {true, 255, 0, 14, 0f, CssColor.azure},
                {true, 255, 0, 14, 0.5f, CssColor.azure},
                {true, 255, 0, 14, 1f, CssColor.azure},
        };
    }

    @Test(dataProvider = "dataProvider_IsNamed")
    public void testIsNamed(boolean expected, int r, int g, int b, float a, CssColor n) throws Exception
    {
        CssColorValue color = (n == null) ? new CssColorValue(r, g, b, a) : new CssColorValue(n);
        boolean actual = color.isNamed();
        assertEquals(actual, expected, String.format("Invalid named color for arguments (%d, %d, %d, %.2g, %s).", r, g, b, a, n));
    }

    @DataProvider
    public Object[][] dataProvider_IsNamed()
    {
        return new Object[][]{
                {false, 255, 0, 14, 1f, null},
                {false, 255, 0, 14, .5f, null},
                {false, 255, 0, 14, 0f, null},
                {true, 255, 0, 14, 0f, CssColor.azure},
                {true, 255, 0, 14, 0.5f, CssColor.azure},
                {true, 255, 0, 14, 1f, CssColor.azure},
        };
    }

    @Test(dataProvider = "dataProvider_fromHSL")
    public void testMakeFromHSL(float h, float s, float l, int r, int g, int b, float a)
    {
        CssColorValue color = CssColorValue.fromHSL(h, s, l);
        assertEquals(color.getRed(), r, String.format("Invalid red(%d) component calculation for hsl(%.2g, %.2g, %.2g)", r, h, s, l));
        assertEquals(color.getGreen(), g, String.format("Invalid green(%d) component calculation for hsl(%.2g, %.2g, %.2g)", g, h, s, l));
        assertEquals(color.getBlue(), b, String.format("Invalid blue(%d) component calculation for hsl(%.2g, %.2g, %.2g)", b, h, s, l));

        color = CssColorValue.fromHSLA(h, s, l, a);
        assertEquals(color.getRed(), r, String.format("Invalid red(%d) component calculation for hsla(%.2g, %.2g, %.2g)", r, h, s, l));
        assertEquals(color.getGreen(), g, String.format("Invalid green(%d) component calculation for hsla(%.2g, %.2g, %.2g)", g, h, s, l));
        assertEquals(color.getBlue(), b, String.format("Invalid blue(%d) component calculation for hsla(%.2g, %.2g, %.2g)", b, h, s, l));
        float actual = color.getAlpha();
        assertEquals(actual, a, String.format("Invalid color transparency(%.2g) for hsla(%.2g, %.2g, %.2g, %.2g)", actual, h, s, l, a));
    }

    @DataProvider
    public Object[][] dataProvider_fromHSL()
    {
        return new Object[][]{
                {0.0f, 0.1f, 1.0f, 255, 255, 255, 1.0f},
                {0.0f, 0.5f, 0.5f, 0xbf, 0x3f, 0x3f, 0.5f},
                {0.0f, 0.0f, 1.0f, 255, 255, 255, 0.25f},
                {0.0f, 1.0f, 0.5f, 255, 0, 0, 0.0f},
        };
    }

    @Test
    public void testMakeFromRGB()
    {
        CssColorValue color = CssColorValue.fromRGB(255, 128, 30);
        assertEquals(color.getRed(), 255, "Wrong color constructed!");
        assertEquals(color.getGreen(), 128, "Wrong color constructed!");
        assertEquals(color.getBlue(), 30, "Wrong color constructed!");

        color = CssColorValue.fromRGBA(254, 120, 37, 0.26f);
        assertEquals(color.getRed(), 254, "Wrong color constructed!");
        assertEquals(color.getGreen(), 120, "Wrong color constructed!");
        assertEquals(color.getBlue(), 37, "Wrong color constructed!");
        assertEquals(color.getAlpha(), 0.26f, 0.001f, "Wrong color constructed!");
    }

    @Test
    public void testColorConstructor()
    {
        CssColorValue color = new CssColorValue(CssColor.aqua);
        assertEquals(color.getName(), CssColor.aqua, "Wrong color constructed!");

        color = new CssColorValue(240, 17, 13, 0.51f);
        assertEquals(color.getRed(), 240, "Wrong color constructed!");
        assertEquals(color.getGreen(), 17, "Wrong color constructed!");
        assertEquals(color.getBlue(), 13, "Wrong color constructed!");
        assertEquals(color.getAlpha(), 0.51f, 0.001f, "Wrong color constructed!");
    }

    @Test
    public void testEquals()
    {
        CssColorValue color1 = new CssColorValue(CssColor.aqua);

        CssColorValue color2 = new CssColorValue(0, 255, 255, 1f);
        assertEquals(color1.equals(color2), true, String.format("Color must be equals (%s, %s)!", color1.toHex(), color2.toHex()));

        CssColorValue color3 = new CssColorValue(0, 255, 255, 0.5f);
        assertEquals(color1.equals(color3), false, String.format("Color must not be equals (%s, %s)!", color1.toHex(), color3.toHex()));
    }

    @Test
    public void testToString()
    {
        CssColorValue color1 = new CssColorValue(CssColor.aqua);
        assertEquals(color1.toString(), "aqua");

        CssColorValue color2 = new CssColorValue(0, 255, 255, 1f);
        assertEquals(color2.toString(), "#00ffff");

        CssColorValue color3 = new CssColorValue(0, 255, 255, 0f);
        assertEquals(color3.toString(), "rgba(0, 255, 255, 0)");

        CssColorValue color4 = new CssColorValue(0, 255, 255, 0.5f);
        assertEquals(color4.toString(), "rgba(0, 255, 255, 0.5)");

        CssColorValue color5 = new CssColorValue(0, 255, 255, 0.88f);
        assertEquals(color5.toString(), "rgba(0, 255, 255, 0.88)");
    }
}