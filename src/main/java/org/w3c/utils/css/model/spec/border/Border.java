package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.model.CssCompositeDeclaration;
import org.w3c.utils.css.model.DeclarationOptimizer;

/**
 * Border property model.
 * @See specifications at http://www.w3.org/TR/CSS2/box.html#propdef-border
 *
 * Created by Home on 22.11.2015.
 */
public class Border extends CssCompositeDeclaration
{
    private BorderStyle style;
    private BorderWidth width;
    private BorderColor color;
    private BorderRadius radius;

    public Border()
    {
        super("border");
        style = new BorderStyle();
        width = new BorderWidth();
        color = new BorderColor();
        radius = new BorderRadius();
    }

    public DeclarationOptimizer getOptimizer()
    {
        return new BorderOptimizer(this);
    }

    /*private CssDeclaration parseColor(String declaration)
    {
        String value = ColorUtils.extractColorFrom(declaration);
        return value != null ? new CssSimpleDeclaration("color", value) : null;
    }

    private CssDeclaration parseSize(String declaration)
    {
        String value = AnyUtils.findKeywordIn(declaration, EnumSet.allOf(LineWidth.class));
        if (value != null) return new CssSimpleDeclaration("width", value);

        value = AnyUtils.extractNumberFrom(declaration, EnumSet.allOf(LengthType.class)); //необходимо не только знать, какое это значение, но и единицу измерения
        return value != null ? new CssSimpleDeclaration("width", value) : null; // предлагаю: width = new CssNumberValue(declaration)
    }

    private BorderStyle parseStyle(String declaration)
    {
        String value = AnyUtils.findKeywordIn(declaration, EnumSet.allOf(LineStyle.class));
        return value != null ? new CssSimpleDeclaration("style", value) : null;
    }*/

    public String getValue()
    {
        //return ((style != null ? style.toString() + " " : "") + (width != null ? width.toString() + " " : "") + (color != null ? color.toString() : "")).trim();
        return null; //FIXME: необходимо отменить эту функцию
    }

    public BorderStyle getStyle()
    {
        return style;
    }

    public BorderWidth getWidth()
    {
        return width;
    }

    public BorderColor getColor()
    {
        return color;
    }

    public BorderRadius getRadius()
    {
        return radius;
    }


}
