package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.enums.SideType;
import org.w3c.utils.css.model.spec.common.CornerValueInterface;
import org.w3c.utils.css.model.spec.common.CssValue;
import org.w3c.utils.css.model.spec.common.PairValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.w3c.utils.css.help.AnyUtils.isEquals;

/**
 * Border radius model.
 * @See specifications as https://www.w3.org/TR/css3-background/#the-border-radius
 *
 * Created by Home on 16.08.2016.
 */
public class BorderRadius implements CornerValueInterface
{
    protected Map<SideType, PairValue> corners;

    public BorderRadius()
    {
        corners = new HashMap<SideType, PairValue>(4);
    }

    public boolean isDefined()
    {
        return !corners.isEmpty();
    }

    public boolean isSingleValue()
    {
        CssValue tl = getTopLeft();
        return isDefined() && isEquals(tl, getTopRight()) && isEquals(tl, getBottomRight()) && isEquals(tl, getBottomLeft());
    }

    public boolean isDoubleValue()
    {
        CssValue tl = getTopLeft();
        CssValue tr = getTopRight();
        return isDefined() && isEquals(tl, getBottomRight()) && isEquals(tr, getBottomLeft()) && !isEquals(tl, tr);
    }

    public boolean isTripleValue()
    {
        return isDefined() && isEquals(getBottomLeft(), getTopRight());
    }

    public boolean isQuadValue()
    {
        return isAllCornersDefined() && countUniqueValues() > 2 && !isSingleValue() && !isDoubleValue() && !isTripleValue();
    }

    public boolean hasVerticalValues()
    {
        for (PairValue corner: corners.values())
        {
            if (corner.isDouble()) return true;
        }
        return false;
    }

    public PairValue getTopLeft()
    {
        return corners.get(SideType.TopLeft);
    }

    public PairValue getTopRight()
    {
        return corners.get(SideType.TopRight);
    }

    public PairValue getBottomRight()
    {
        return corners.get(SideType.BottomRight);
    }

    public PairValue getBottomLeft()
    {
        return corners.get(SideType.BottomLeft);
    }

    public void setTopLeft(CssValue value)
    {
        corners.put(SideType.TopLeft, (PairValue) value);
    }

    public void setTopRight(CssValue value)
    {
        corners.put(SideType.TopRight, (PairValue) value);
    }

    public void setBottomRight(CssValue value)
    {
        corners.put(SideType.BottomLeft, (PairValue) value);
    }

    public void setBottomLeft(CssValue value)
    {
        corners.put(SideType.BottomRight, (PairValue) value);
    }

    public Map<SideType, CssValue> getDefinedValues()
    {
        Map<SideType, CssValue> map = new HashMap<SideType, CssValue>();
        map.putAll(corners);
        return map;
    }

    public int countUniqueValues()
    {
        return getValuesSet().size();
    }

    public int countDefinedValues()
    {
        return corners.size();
    }

    public boolean isAllCornersDefined()
    {
        return countDefinedValues() == 4;
    }

    private Set<CssValue> getValuesSet()
    {
        Set<CssValue> set = new HashSet<CssValue>(4);
        set.addAll(corners.values());
        return set;
    }

    @Override
    public String toString()
    {
        return hasVerticalValues() ?
            String.format(
                "border-radius: %s %s %s %s / %s %s %s %s",
                getTopLeft().getFirstValue(), getTopRight().getFirstValue(), getBottomRight().getFirstValue(), getBottomLeft().getFirstValue(),
                getTopLeft().getSecondValue(), getTopRight().getSecondValue(), getBottomRight().getSecondValue(), getBottomLeft().getSecondValue()
            )
        :
            String.format(
                "border-radius: %s %s %s %s",
                getTopLeft().getFirstValue(), getTopRight().getFirstValue(), getBottomRight().getFirstValue(), getBottomLeft().getFirstValue()
            );
    }
}
