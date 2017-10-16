package org.w3c.utils.css.model.values;

/**
 * Hashed value of property.
 * For example, #34f.
 * Used in properties like as color, background-color and another.
 * TODO: test it
 * <p>
 *     See <a href="https://www.w3.org/TR/css-syntax-3/#hash-token-diagram">hash token</a>
 * </p>
 *
 * Created by k002 on 12.10.2017.
 */
public class HashValue extends BasicValue
{
    private String hash;

    public HashValue(String hash)
    {
        this.hash = hash;
    }

    /**
     * @return value with # prefix
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash value with # prefix
     */
    public void setHash(String hash)
    {
        assert hash != null;
        this.hash = hash;
    }

    public String getText()
    {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashValue hashValue = (HashValue) o;

        return hash.equals(hashValue.hash);

    }

    @Override
    public int hashCode() {
        return hash.hashCode();
    }

    @Override
    public String toString()
    {
        return hash;
    }
}
