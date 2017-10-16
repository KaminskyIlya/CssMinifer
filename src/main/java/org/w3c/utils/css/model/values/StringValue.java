package org.w3c.utils.css.model.values;

import org.w3c.utils.css.help.StringUtils;

/**
 * String value of property.
 * For example, 'Times New Roman', 'attr(title)'.
 * Used in properties like as content, font-name.
 * TODO: test it
 * <p>
 *     See <a href="https://www.w3.org/TR/css-syntax-3/#string-token-diagram">string token</a>
 * </p>
 *
 * Created by k002 on 12.10.2017.
 */
public class StringValue extends BasicValue
{
    private String value;

    public StringValue() {
    }

    /**
     * @return value with outer quotes
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value value with outer quotes
     */
    public void setValue(String value) {
        assert value != null;
        this.value = value;
    }

    public String getUnquotedValue()
    {
        return StringUtils.getUnquotedStringLiteral(value);
    }

    public String getText() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringValue that = (StringValue) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
