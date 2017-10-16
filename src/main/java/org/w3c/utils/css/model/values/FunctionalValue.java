package org.w3c.utils.css.model.values;

import java.util.ArrayList;
import java.util.List;

/**
 * Functional value of css property.
 * For example: url(image.jpg), linear-gradient(to top, yellow, red), translate(50%, 20%)
 * TODO: test it
 * Created by k002 on 12.10.2017.
 */
public class FunctionalValue extends BasicValue
{
    private String name;
    private List<Value> arguments;

    public FunctionalValue() {
        arguments = new ArrayList<Value>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        assert name != null;
        this.name = name.toLowerCase();
    }

    public List<Value> getArguments() {
        return arguments;
    }

    public void addArgument(Value argument)
    {
        arguments.add(argument);
    }

    public String getText()
    {
        return toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionalValue value = (FunctionalValue) o;

        return name.equals(value.name) && arguments.equals(value.arguments);

    }

    @Override
    public int hashCode()
    {
        return 31 * name.hashCode() + arguments.hashCode();
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(name).append('(');
        boolean first = true;

        for (Value argument : arguments)
        {
            if ( !first ) result.append(", ");
            result.append(argument.getText());
            first = false;
        }

        return result.append(')').toString();
    }
}
