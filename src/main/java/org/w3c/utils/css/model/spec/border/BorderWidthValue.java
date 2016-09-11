package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.enums.LengthType;
import org.w3c.utils.css.enums.LineWidth;
import org.w3c.utils.css.model.spec.common.CssLengthValue;

/**
 * Border width value model.
 * @See specifications at https://www.w3.org/TR/css3-background/#border-width
 * <line-width> = <length> | thin | medium | thick
 *
 * Created by Home on 14.08.2016.
 */
public class BorderWidthValue extends CssLengthValue
{
    private LineWidth width;

    public BorderWidthValue(double value, LengthType units)
    {
        super(value, units);
        this.width = null;
    }

    public BorderWidthValue(LineWidth width)
    {
        super(0, null);
        this.width = width;
    }

    public LineWidth getWidth()
    {
        return width;
    }


    public boolean isNamed()
    {
        return width != null;
    }

    @Override
    public String toString()
    {
        return isNamed() ? getWidth().toString() : super.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BorderWidthValue)) return false;
        if (!super.equals(o)) return false;

        BorderWidthValue that = (BorderWidthValue) o;

        return width == that.width;

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (width != null ? width.hashCode() : 0);
        return result;
    }
}
