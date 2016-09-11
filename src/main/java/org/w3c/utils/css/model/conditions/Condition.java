package org.w3c.utils.css.model.conditions;

import org.w3c.utils.css.model.CssSimpleDeclaration;

/**
 * Условие для @media или @condition.
 * Хранит выражение вида (property:value).
 *
 * Created by Home on 27.08.2016.
 */
public class Condition extends CssSimpleDeclaration
{
    public Condition(String property, String value)
    {
        super(property, value);
    }

    //TODO: необходимы правильные equals & hashcode для сравнения условий.  учесть, что (min-width: 960px) и (min-width: 10in) могут быть эквивалентны
    //а значит, CssSimpleDeclaration должен уметь возвращать CssValue, которое умеет себя оптимизировать и приводить к другому
}
