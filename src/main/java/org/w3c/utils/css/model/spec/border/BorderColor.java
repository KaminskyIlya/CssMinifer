package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.enums.SideType;
import org.w3c.utils.css.model.spec.common.CssValue;
import org.w3c.utils.css.model.spec.common.SidesValue;

import java.util.Map;

/**
 * Border color model.
 * @See specifications at https://www.w3.org/TR/css3-background/#border-color
 *
 * Created by Home on 14.08.2016.
 */
public class BorderColor extends SidesValue
{
    public BorderColor()
    {
        super();
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

    @Override
    public String toString()
    {
        return String.format("border-color: %s %s %s %s", getTopValue(), getRightValue(), getBottomValue(), getLeftValue());
    }
}
