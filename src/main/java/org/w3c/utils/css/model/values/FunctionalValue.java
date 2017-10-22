package org.w3c.utils.css.model.values;

/**
 * Functional value of css property.
 * For example: url(image.jpg), linear-gradient(to top, yellow, red), translate(50%, 20%)
 * <p></p>
 * Created by k002 on 12.10.2017.
 */
public class FunctionalValue extends BasicValue
{
    private String name;
    private ComponentValue arguments;

    public FunctionalValue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        assert name != null && !name.isEmpty();
        this.name = name.toLowerCase();
    }

    public ComponentValue getArguments() {
        return arguments;
    }

    public void setArguments(ComponentValue arguments)
    {
        this.arguments = arguments;
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
        return name + '(' + arguments.toString() + ')';
    }
}
