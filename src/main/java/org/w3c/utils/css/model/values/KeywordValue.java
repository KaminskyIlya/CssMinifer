package org.w3c.utils.css.model.values;

/**
 * Keyword property value.
 * For example: 'inherit', 'solid', 'block', and etc.
 * TODO: test it
 * See <a href="https://www.w3.org/TR/css-syntax-3/#ident-token-diagram">Ident token</a>
 * Created by k002 on 12.10.2017.
 */
public class KeywordValue extends BasicValue
{
    private String keyword;

    public KeywordValue(String keyword)
    {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Checks whether the property is vendor-specific.
     * For example, <i>-ms- mso- -o- -webkit-</i> and so on (about 15 prefixes).
     * See <a href="https://www.w3.org/TR/CSS2/syndata.html#vendor-keyword-history">Informative Historical Notes</a>
     *
     * @return true, if it is vendor-specific value.
     */
    public boolean isPrefixed()
    {
        return keyword.startsWith("-") || keyword.indexOf('-') <= 7;
    }

    public String getText()
    {
        return keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordValue that = (KeywordValue) o;

        return keyword.equals(that.keyword);

    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

    @Override
    public String toString()
    {
        return keyword;
    }
}
