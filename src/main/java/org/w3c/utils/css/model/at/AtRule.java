package org.w3c.utils.css.model.at;

import org.w3c.utils.css.enums.AtRules;
import org.w3c.utils.css.model.CssModel;

/**
 * At-rule prototype.
 * See specifications at
 *
 * Created by Home on 20.11.2015.
 */
public abstract class AtRule implements CssModel
{
    protected final String componentValue;

    public AtRule(String componentValue)
    {
        this.componentValue = componentValue;
    }

    public abstract AtRules getToken();

    public String getComponentValue()
    {
        return componentValue;
    }

    @Override
    public String toString()
    {
        return "@" + getToken();
    }
}
