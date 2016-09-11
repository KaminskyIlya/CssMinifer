package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.CssColor;
import org.w3c.utils.css.help.ColorUtils;
import org.w3c.utils.css.help.NumericUtils;

import java.awt.*;
import java.util.Locale;

/**
 * Color value model.
 * @See specifications at https://www.w3.org/TR/css3-values/#colors
 *
 * Created by Home on 14.08.2016.
 */
public class CssColorValue implements CssValue
{
    private int red;
    private int green;
    private int blue;
    private float alpha;
    private CssColor name;
    private String optimized;

    public CssColorValue(int red, int green, int blue, float alpha)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.name = null;
    }

    public CssColorValue(CssColor name)
    {
        this.name = name;
    }

    public static CssColorValue fromRGB(int red, int green, int blue)
    {
        return new CssColorValue(red, green, blue, 1);
    }

    public static CssColorValue fromRGBA(int red, int green, int blue, float alpha)
    {
        return new CssColorValue(red, green, blue, alpha);
    }

    public static CssColorValue fromHSL(float h, float s, float l)
    {
        Color color = ColorUtils.hslToRgb(h, s, l);
        return new CssColorValue(color.getRed(), color.getGreen(), color.getBlue(), 1);
    }

    public static CssColorValue fromHSLA(float h, float s, float l, float a)
    {
        Color color = ColorUtils.hslToRgb(h, s, l);
        return new CssColorValue(color.getRed(), color.getGreen(), color.getBlue(), a);
    }

    public boolean isTransparent()
    {
        return name == null && alpha < 1;
    }

    public boolean isSolid()
    {
        return name != null || alpha == 1;
    }

    public boolean isInvisible() { return name == null && alpha == 0; }

    public boolean isNamed()
    {
        return name != null;
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public float getAlpha()
    {
        return alpha;
    }

    public CssColor getName()
    {
        return name;
    }

    public String getOptimized()
    {
        return optimized;
    }

    public void setOptimized(String value)
    {
        optimized = value;
    }

    @Override
    public String toString()
    {
        if (name != null)
        {
            return name.getName();
        }
        else
        {
            return isSolid() ? ColorUtils.toColorStr(red, green, blue) : String.format(Locale.getDefault(), "rgba(%d, %d, %d, %s)", red, green, blue, NumericUtils.format(alpha));
        }
    }

    public String toHex()
    {
        if (name != null)
        {
            return name.getColor();
        }
        else
        {
            return isSolid() ? ColorUtils.toColorStr(red, green, blue) : String.format(Locale.getDefault(), "rgba(%d, %d, %d, %s)", red, green, blue, NumericUtils.format(alpha));
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CssColorValue)) return false;

        CssColorValue that = (CssColorValue) o;

        return this.toHex().equals(that.toHex());

    }

    @Override
    public int hashCode()
    {
        return this.toHex().hashCode();
    }
}
