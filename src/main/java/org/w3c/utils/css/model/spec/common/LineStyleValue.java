package org.w3c.utils.css.model.spec.common;

import org.w3c.utils.css.enums.LineStyle;

/**
 * Created by Home on 22.08.2016.
 */
public class LineStyleValue implements CssValue
{
    private LineStyle style;

    public LineStyleValue(LineStyle style)
    {
        this.style = style;
    }

    public String getOptimized()
    {
        return null;
    }
}
