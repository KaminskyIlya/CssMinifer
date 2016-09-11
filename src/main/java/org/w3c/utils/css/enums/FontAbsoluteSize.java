package org.w3c.utils.css.enums;

/**
 * @See enum definition at https://www.w3.org/TR/css-fonts-3/#propdef-font-size
 *
 * Created by Home on 21.08.2016.
 */
public enum FontAbsoluteSize
{
    XX_SMALL("xx-small"),
    X_SMALL("x-small"),
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large"),
    X_LARGE("x-large"),
    XX_LARGE("xx-large");

    private final String size;

    FontAbsoluteSize(String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return size;
    }
}
