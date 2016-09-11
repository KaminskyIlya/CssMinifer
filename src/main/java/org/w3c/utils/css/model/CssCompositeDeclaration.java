package org.w3c.utils.css.model;

/**
 * Describes composite CSS rule.
 * This rule consist of many another css declarations, which formed a group.
 * For example: border-style: solid; border-width: 1px; border-color: black; is equals to: border: solid 1px black;
 *
 * Created by Home on 04.11.2015.
 */
public abstract class CssCompositeDeclaration implements CssDeclaration
{
    private final String property;

    public CssCompositeDeclaration(String property)
    {
        this.property = property;
    }

    public String getProperty()
    {
        return property;
    }

    @Override
    public String toString()
    {
        return property + ":" + getValue();
    }
}
