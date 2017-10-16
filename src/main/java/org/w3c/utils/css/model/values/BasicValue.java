package org.w3c.utils.css.model.values;

/**
 * Prototype for all values of css-properties.
 * Mutable object.
 *
 * Created by k002 on 12.10.2017.
 */
abstract class BasicValue implements Value
{
    private boolean important;

    public void setImportant(boolean flag)
    {
        important = flag;
    }

    public boolean isImportant()
    {
        return important;
    }
}
