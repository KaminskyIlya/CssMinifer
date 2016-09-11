package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.enums.SideType;
import org.w3c.utils.css.model.spec.common.CssValue;
import org.w3c.utils.css.model.spec.common.SidesValue;

import java.util.Map;

/**
 * Border width model.
 * @See specifications at
 *
 * Created by Home on 14.08.2016.
 */
public class BorderWidth extends SidesValue
{
    public BorderWidth()
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
        return String.format("border-width: %s %s %s %s", getTopValue(), getRightValue(), getBottomValue(), getLeftValue());
    }
}
