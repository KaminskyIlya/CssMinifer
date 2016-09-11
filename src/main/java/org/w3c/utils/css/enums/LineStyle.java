package org.w3c.utils.css.enums;

/**
 * Line style enumeration.
 * @See specifications at https://www.w3.org/TR/css3-background/#ltline-stylegt
 *
 * For example, used in border-style property.
 *
 * Created by Home on 14.08.2016.
 */
public enum LineStyle
{
    None("none"),
    Hidden("hidden"),
    Dotted("dotted"),
    Dashed("dashed"),
    Solid("solid"),
    Double("double"),
    Groove("groove"),
    Ridge("ridge"),
    Inset("inset"),
    Outset("outset");

    private final String name;

    LineStyle(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
