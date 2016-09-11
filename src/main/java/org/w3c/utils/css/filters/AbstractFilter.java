package org.w3c.utils.css.filters;

import org.w3c.utils.css.io.RecycledCharBuffer;

/**
 * Prototype of all css filters.
 *
 * Created by Home on 06.11.2015.
 */
public abstract class AbstractFilter implements StyleFilter
{
    protected final RecycledCharBuffer buffer;

    public AbstractFilter(RecycledCharBuffer buffer)
    {
        this.buffer = buffer;
    }

    public RecycledCharBuffer getBuffer() {
        return buffer;
    }
}
