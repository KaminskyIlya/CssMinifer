package org.w3c.utils.css.model;

/**
 * Describes simple CSS rule.
 * This rule hasn't browser-specific prefixes, it's not rules group.
 * For example: width, height, color, display, visibility, and so on.
 *
 * Created by Home on 04.11.2015.
 */
public class CssSimpleDeclaration implements CssDeclaration
{
    private final String property;
    private final String value;

    public CssSimpleDeclaration(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public DeclarationOptimizer getOptimizer()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return property + ": " + value;
    }
}
