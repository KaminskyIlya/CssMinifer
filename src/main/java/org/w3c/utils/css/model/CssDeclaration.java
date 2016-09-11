package org.w3c.utils.css.model;

/**
 * Created by Home on 04.11.2015.
 */
public interface CssDeclaration
{
    String getProperty();

    String getValue();

    DeclarationOptimizer getOptimizer();
}
