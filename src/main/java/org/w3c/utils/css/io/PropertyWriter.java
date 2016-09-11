package org.w3c.utils.css.io;

import sun.plugin.dom.exception.InvalidStateException;

/**
 * Пока временный класс для удобной записи цепочки свойств.
 * Контроллирует растановку точек с запятой, двоеточий, пробелов между свойствами и т.п.
 *
 * Created by Home on 14.08.2016.
 */
public class PropertyWriter
{
    private StringBuffer buffer = new StringBuffer();

    boolean inStyle = false;
    boolean inProperty = false;
    boolean inValue = false;

    public PropertyWriter beginStyle(String qualifier)
    {
        if (inStyle) buffer.append("}");
        buffer.append(qualifier).append("{");

        return this;
    }

    public PropertyWriter beginProperty(String name)
    {
        if (inProperty) buffer.append(";");
        buffer.append(name);
        inValue = false;

        return this;
    }

    public PropertyWriter addPropertyName(String name)
    {
        if (inProperty)
        {
            buffer.append("-").append(name);
        }
        else
        {
            throw new InvalidStateException("Could not append part of property name, because we are not in property name definition.");
        }

        return this;
    }

    public PropertyWriter addValue(String value)
    {
        if (inProperty)
        {
            buffer.append(":");
            inProperty = false;

        }
        if (inValue) buffer.append(" ");
        buffer.append(value);
        inValue = true;

        return this;
    }

    public PropertyWriter close()
    {
        if (inValue)
        {
            buffer.append(";");
        }
        else
            throw new InvalidStateException("Could not close that, because value of last style property not present.");

        return this;
    }

    @Override
    public String toString()
    {
        if (inStyle || inProperty || inValue) close();

        return buffer.toString();
    }
}
