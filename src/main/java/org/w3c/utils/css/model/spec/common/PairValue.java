package org.w3c.utils.css.model.spec.common;

import java.util.Arrays;

/**
 * Pair css value.
 *
 * For example,
 *   border-top-left-radius: 10px 5px;
 *
 * Created by Home on 16.08.2016.
 */
public class PairValue implements CssValue
{
    private CssValue values[];

    public PairValue()
    {
        values = new CssValue[2];
    }

    public CssValue getFirstValue()
    {
        return values[0];
    }

    public void setFirstValue(CssValue value)
    {
        values[0] = value;
    }

    public CssValue getSecondValue()
    {
        return values[1];
    }

    public void setSecondValue(CssValue value)
    {
        values[1] = value;
    }

    public CssValue getCssValue(int index)
    {
        return values[index];
    }

    public void setCssValue(int index, CssValue value)
    {
        values[index] = value;
    }

    boolean isDefined()
    {
        return values[0] != null || values[1] != null;
    }

    public boolean isSingle()
    {
        return isDefined() && values[0].equals(values[1]);
    }

    public boolean isDouble()
    {
        return !isSingle();
    }

    public String getOptimized()
    {
        throw new UnsupportedOperationException();
    }

    public void setOptimized(String value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        return isDouble() ? (isSingle() ? getFirstValue().toString() : getFirstValue() + " " + getSecondValue()) : "";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof PairValue)) return false;

        PairValue pairValue = (PairValue) o;

        return Arrays.equals(values, pairValue.values);

    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(values);
    }
}
