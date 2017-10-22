package org.w3c.utils.css.model.values;

import org.w3c.utils.css.model.CssModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Component value of css property. May be a single token, or a token group, or groups list.
 * TODO: Strong test all methods
 * <p>
 *     See <a href="https://www.w3.org/TR/css-syntax-3/#component-value">component value</a>
 * </p>
 *
 * Created by k002 on 12.10.2017.
 */
public class ComponentValue implements CssModel
{
    private List<List<Value>> groups;

    private List<Value> activeGroup;

    public ComponentValue()
    {
        groups = new ArrayList<List<Value>>();
        beginNewValuesGroup();
    }

    /**
     * Start filling a next values group.
     */
    public void beginNewValuesGroup()
    {
        assert activeGroup == null || !activeGroup.isEmpty();

        activeGroup = new ArrayList<Value>();
        groups.add(activeGroup);
    }

    /**
     * Add new value in current values group.
     *
     * @param value new single value
     */
    public void addValue(Value value)
    {
        activeGroup.add(value);
    }

    /**
     * Get full component value.
     *
     * @return list of values groups.
     */
    public List<List<Value>> getGroups()
    {
        return groups;
    }

    /**
     * Get a single group.
     *
     * @return as single group if it a single group
     * @throws IllegalStateException when it is not a single group.
     */
    public List<Value> getValues()
    {
        if ( isSimpleGroup() )
            return activeGroup;
        else
            throw new IllegalStateException();
    }

    /**
     * Get a single value.
     *
     * @return as single value if it a single value
     * @throws IllegalStateException when it is not a single value.
     */
    public Value getValue()
    {
        if ( isSingleValue() )
            return activeGroup.get(0);
        else
            throw new IllegalStateException();
    }

    /**
     *
     * @return true, if composite value consist of single group
     */
    public boolean isSimpleGroup()
    {
        return groups.size() == 1;
    }

    /**
     *
     * @return true, if composite value consist of single value only
     */
    public boolean isSingleValue()
    {
        return isSimpleGroup() && activeGroup.size() == 1;
    }

    public boolean isDoubleValue()
    {
        return isSimpleGroup() && activeGroup.size() == 2;
    }

    public boolean isTripleValue()
    {
        return isSimpleGroup() && activeGroup.size() == 3;
    }

    public boolean isQuadValue()
    {
        return isSimpleGroup() && activeGroup.size() == 4;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        boolean firstList = true;

        for (List<Value> list : groups)
        {
            if ( !firstList && !list.isEmpty() ) builder.append(", ");

            boolean firstItem = true;

            for (Value item : list)
            {
                if ( !firstItem ) builder.append(' ');

                builder.append(item);

                firstItem = false;
            }

            firstList = false;
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentValue value = (ComponentValue) o;

        return groups.equals(value.groups);

    }

    @Override
    public int hashCode() {
        return groups.hashCode();
    }

}
