package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.enums.SideType;
import org.w3c.utils.css.model.spec.common.CssValue;
import org.w3c.utils.css.model.spec.common.SidesValue;

import java.util.Map;

/**
 * Border style model.
 * @See specifications as https://www.w3.org/TR/css3-background/#border-style
 *
 * Created by Home on 14.08.2016.
 */
public class BorderStyle extends SidesValue
{
    public BorderStyle()
    {
        super();
        //setSingleValue(LineStyle.None);
    }
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

/*
    public boolean isDefined()
    {
        return (getTopValue() != LineStyle.None) || (getRightValue() != LineStyle.None) || (getBottomValue() != LineStyle.None) || (getLeftValue() != LineStyle.None);
    }

    public int countDefinedValues()
    {
        int count = 0;
        for (CssValue style : corners.corners())
        {
            if (style != null && style != LineStyle.None) count++;
        }
        return count;
    }

    public Map<SideType, CssValue> getDefinedValues()
    {
        Map<SideType, CssValue> map = new HashMap<SideType, CssValue>(4);

        for (SideType side : corners.keySet())
        {
            LineStyle value = (LineStyle) corners.get(side);
            if (value != LineStyle.None) map.put(side, value);
        }

        return  map;
    }
*/

    @Override
    public String toString()
    {
        return String.format("border-style: %s %s %s %s", getTopValue(), getRightValue(), getBottomValue(), getLeftValue());
    }
}
