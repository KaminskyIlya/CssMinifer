package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.LengthType;
import org.w3c.utils.css.help.NumericUtils;

/**
 * Length value model.
 * @See specifications at https://www.w3.org/TR/css3-values/#length-value
 *
 * Created by Home on 14.08.2016.
 */
public class CssLengthValue implements CssValue
{
    private double length;
    private LengthType units;
    private String optimized;

    public CssLengthValue(double length, LengthType units)
    {
        this.length = length;
        this.units = units;
    }

    public double getLength()
    {
        return length;
    }

    public LengthType getUnits()
    {
        return units;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public void setUnits(LengthType units)
    {
        this.units = units;
    }

    public String getOptimized()
    {
        return optimized;
    }

    public void setOptimized(String value)
    {
        optimized = value;
    }

    public boolean isZero()
    {
        return length == 0;
    }

    /**
     * For absolute length units - test that absolute length of this equals another.
     * For relative length units - compare length and units together.
     *
     * @param that
     * @return
     */
    public boolean isEquals(CssLengthValue that)
    {
        if (this.units.isAbsolute() && that.units.isAbsolute())
        {
            double px1 = LengthType.px.convertFrom(this.units, this.length);
            double px2 = LengthType.px.convertFrom(that.units, that.length);
            return Math.abs(px1 - px2) < 0.001;
        }
        else
            return (this.isZero() && that.isZero()) || isSomeAs(that);
    }

    private boolean isSomeAs(CssLengthValue that)
    {
        return Double.compare(that.length, this.length) == 0 && this.units == that.units;
    }

    @Override
    public String toString()
    {
        return isZero() && units.isAbsolute() ? "0" : String.format("%s%s", NumericUtils.format(length), units);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return isSomeAs((CssLengthValue) o);
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(length);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + units.hashCode();
        return result;
    }
}
