package org.w3c.utils.css.enums;

/**
 * Created by Home on 22.08.2016.
 */
public enum Position
{
    STATIC("static"),
    RELATIVE("relative"),
    ABSOLUTE("absolute"),
    FIXED("fixed"),
    INHERIT("inherit");

    private final String name;

    Position(String name)
    {
        this.name = name;
    }
}
