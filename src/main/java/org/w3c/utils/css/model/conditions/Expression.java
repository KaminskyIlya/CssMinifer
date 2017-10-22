package org.w3c.utils.css.model.conditions;

import java.util.HashSet;
import java.util.Set;

/**
 * Выражение медиазапроса.
 * and (min-width: 480px) and (max-width: 640px) and (color)
 *
 * Created by Home on 27.08.2016.
 */
public class Expression
{
    private Set<Condition> conditions = new HashSet<Condition>();

    public Expression()
    {

    }

    public Set<Condition> getConditions()
    {
        return conditions;
    }

    /**
     * Sugar
     * @param condition
     */
    public void addCondition(Condition condition)
    {
        conditions.add(condition);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Expression)) return false;

        Expression that = (Expression) o;

        return conditions.equals(that.conditions);
    }

    @Override
    public int hashCode()
    {
        return conditions.hashCode();
    }
}
