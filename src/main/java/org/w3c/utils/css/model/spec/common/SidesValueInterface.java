package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.SideType;

import java.util.Map;

/**
 * Common interface for all properties that define corners of box sides
 *
 * For example:
 *  border-style: solid dashed dot none;
 *  margin: 0 0 5px;
 *  padding: 2px 2px;
 *
 *
 * Created by Home on 14.08.2016.
 */
public interface SidesValueInterface
{
    boolean isDefined();

    boolean isSingleValue();

    boolean isDoubleValue();

    boolean isTripleValue();

    boolean isQuadValue();

    String getSingleValue();

    String getDoubleValue();

    String getTripleValue();

    String getQuadValue();

    int countUniqueValues();

    int countDefinedValues();

    boolean isAllSidesDefined();

    Map<SideType, CssValue> getDefinedValues();

    CssValue getTopValue();

    CssValue getRightValue();

    CssValue getBottomValue();

    CssValue getLeftValue();

    void setTopValue(CssValue top);

    void setRightValue(CssValue right);

    void setBottomValue(CssValue bottom);

    void setLeftValue(CssValue left);

    void setSingleValue(CssValue single);

    void setDoubleValue(CssValue vertical, CssValue horizontal);

    void setTripleValue(CssValue top, CssValue horizontal, CssValue bottom);
}
