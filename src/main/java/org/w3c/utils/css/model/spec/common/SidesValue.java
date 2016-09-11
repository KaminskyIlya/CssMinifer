package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.SideType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.w3c.utils.css.help.AnyUtils.isEquals;

/**
 * FIXME: getSingleValue должны возвращать строки или объекты CssValue?
 * FIXME: убрать из класса getSingle, getDouble, getTriple, getQuad - поскольку это задача оптимизатора, а не модели
 * Created by Home on 14.08.2016.
 */
public abstract class SidesValue implements SidesValueInterface
{
    protected Map<SideType, CssValue> values;

    public SidesValue()
    {
        values = new HashMap<SideType, CssValue>(4);
    }

    public String getSingleValue()
    {
        if (isSingleValue())
        {
            return values.get(SideType.Top).toString();
        }
        else
        {
            return getSmallerValue();
        }
    }

    private String getSmallerValue()
    {
        String small = null;
        for (CssValue v : getValuesSet())
        {
            String o = v.toString();
            if ( small == null || small.length() > o.length() ) small = o;
        }
        return small;
    }

    public boolean isSingleValue()
    {
        CssValue t = getTopValue();
        return isDefined() && isEquals(t, getLeftValue()) && isEquals(t, getRightValue()) && isEquals(t, getBottomValue());
    }

    public String getDoubleValue()
    {
        return values.get(SideType.Top) + " " + values.get(SideType.Right);
    }

    public boolean isDoubleValue()
    {
        CssValue t = getTopValue();
        CssValue r = getRightValue();
        return isDefined() && isEquals(t, getBottomValue()) && isEquals(r, getLeftValue()) && !isEquals(t, r);
    }

    public String getTripleValue()
    {
        return values.get(SideType.Top) + " " + values.get(SideType.Right) + " " + values.get(SideType.Bottom);
    }

    public boolean isTripleValue()
    {
        return isDefined() && isEquals(getRightValue(), getLeftValue());
    }

    public boolean isQuadValue()
    {
        return isAllSidesDefined() && countUniqueValues() > 2 && !isSingleValue() && !isDoubleValue() && !isTripleValue();
    }

    public String getQuadValue()
    {
        return values.get(SideType.Top) + " " + values.get(SideType.Right) + " " + values.get(SideType.Bottom) + " " + values.get(SideType.Left);
    }

    public int countUniqueValues()
    {
        return getValuesSet().size();
    }

    public boolean isAllSidesDefined()
    {
        return countDefinedValues() == 4;
    }

    public CssValue getTopValue()
    {
        return values.get(SideType.Top);
    }

    public CssValue getRightValue()
    {
        return values.get(SideType.Right);
    }

    public CssValue getBottomValue()
    {
        return values.get(SideType.Bottom);
    }

    public CssValue getLeftValue()
    {
        return values.get(SideType.Left);
    }

    public void setTopValue(CssValue top)
    {
        values.put(SideType.Top, top);
    }

    public void setRightValue(CssValue right)
    {
        values.put(SideType.Right, right);
    }

    public void setBottomValue(CssValue bottom)
    {
        values.put(SideType.Bottom, bottom);
    }

    public void setLeftValue(CssValue left)
    {
        values.put(SideType.Left, left);
    }

    public void setSingleValue(CssValue single)
    {
        values.put(SideType.Top, single);
        values.put(SideType.Right, single);
        values.put(SideType.Bottom, single);
        values.put(SideType.Left, single);
    }

    public void setDoubleValue(CssValue vertical, CssValue horizontal)
    {
        values.put(SideType.Top, vertical);
        values.put(SideType.Right, horizontal);
        values.put(SideType.Bottom, vertical);
        values.put(SideType.Left, horizontal);
    }

    public void setTripleValue(CssValue top, CssValue horizontal, CssValue bottom)
    {
        values.put(SideType.Top, top);
        values.put(SideType.Right, horizontal);
        values.put(SideType.Bottom, bottom);
        values.put(SideType.Left, horizontal);
    }

    protected Set<CssValue> getValuesSet()
    {
        Set<CssValue> set = new HashSet<CssValue>(4);
        set.addAll(values.values());
        return set;
    }
}
