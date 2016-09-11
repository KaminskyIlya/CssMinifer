package org.w3c.utils.css.enums;

/**
 * Created by Home on 21.08.2016.
 */
public enum BrowserPrefixes
{
    OPERA("o"),
    MICROSOFT("ms"),
    FIREFOX("moz"),
    WEBKIT("webkit");

    private final String prefix;

    BrowserPrefixes(String prefix)
    {
        this.prefix = prefix;
    }

    public String getPrefix()
    {
        return prefix;
    }

    @Override
    public String toString()
    {
        return "-" + prefix + "-";
    }
}
