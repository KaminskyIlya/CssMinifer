package org.w3c.utils.css.enums;

/**
 * Css side types of box-model.
 *
 * Created by Home on 14.08.2016.
 */
public enum SideType
{
    Top("top"),
    Right("right"),
    Bottom("bottom"),
    Left("left"),

    TopLeft("top-left"),
    TopRight("top-right"),
    BottomRight("bottom-right"),
    BottomLeft("bottom-left");

    private final String text;

    SideType(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
