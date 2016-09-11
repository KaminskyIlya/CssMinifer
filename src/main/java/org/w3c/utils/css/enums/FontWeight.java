package org.w3c.utils.css.enums;

/**
 * Named constants of font's weights.
 *
 * Created by Home on 22.11.2015.
 */
public enum FontWeight
{
    NORMAL(400, false),
    BOLD(700, false),
    LIGHTER(0, true),
    BOLDER(0, true);

    private final int weight;
    private final boolean relative;

    FontWeight(int weight, boolean relative)
    {
        this.weight = weight;
        this.relative = relative;
    }

    public int getWeight()
    {
        return weight;
    }

    public boolean isRelative()
    {
        return relative;
    }

    public String getAlias()
    {
        return toString();
    }
}
