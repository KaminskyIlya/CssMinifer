package org.w3c.utils.css.model.values;

/**
 * Numeric value of property.
 * For example: 0, 1em, 50%, 6ch -.56e10mm, 3.1415rad
 * TODO: test it
 * <p>See <a href="https://www.w3.org/TR/css-syntax-3/#dimension-token-diagram">dimension token</a>,
 * <a href="https://www.w3.org/TR/css-syntax-3/#percentage-token-diagram">percentage token</a> and
 * <a href="https://www.w3.org/TR/css-syntax-3/#number-token-diagram">number token</a>.</p>
 *
 * Created by k002 on 12.10.2017.
 */
public class UnitValue extends BasicValue
{
    private Float value = 0f;
    private String unit = "";

    public UnitValue() {
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value)
    {
        assert value != null;
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit)
    {
        assert unit != null;
        this.unit = unit.toLowerCase();
    }

    public String getText()
    {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitValue unitValue = (UnitValue) o;

        return value.equals(unitValue.value) && unit.equals(unitValue.unit);

    }

    @Override
    public int hashCode() {
        return 31 * value.hashCode() + unit.hashCode();
    }

    @Override
    public String toString()
    {
        return value + unit;
    }
}
