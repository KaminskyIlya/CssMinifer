package org.w3c.utils.css.enums;

/**
 * <p><a href="http://www.w3.org/TR/css3-values/">css3-values</a></p>
 *
 * Created by Home on 22.11.2015.
 */
public enum AngleType
{
    DEG, GRAD, RAD, TURN;

    @Override
    public String toString()
    {
        return name().toLowerCase();
    }
}
