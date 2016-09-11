package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.SideType;

import java.util.Map;

/**
 * Common interface for all properties that define corners of box corners.
 *
 * For example:
 *  border-radius: 1px 2px 3px 4px / 2px 3px 4px 5px;
 *  border-top-left-radius: 25px 15px;
 *
 * Created by Home on 16.08.2016.
 */
public interface CornerValueInterface
{
    boolean isDefined();

    boolean isSingleValue();

    boolean isDoubleValue();

    boolean isTripleValue();

    boolean isQuadValue();

    CssValue getTopLeft();

    CssValue getTopRight();

    CssValue getBottomRight();

    CssValue getBottomLeft();

    void setTopLeft(CssValue value);

    void setTopRight(CssValue value);

    void setBottomRight(CssValue value);

    void setBottomLeft(CssValue value);

    Map<SideType, CssValue> getDefinedValues();

    int countUniqueValues();

    int countDefinedValues();

    boolean isAllCornersDefined();
}
