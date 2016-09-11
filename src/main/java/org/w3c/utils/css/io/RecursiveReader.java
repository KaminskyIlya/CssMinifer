package org.w3c.utils.css.io;

import java.io.IOException;
import java.io.Reader;

/**
 * Implements recursive readers stack.
 *
 * Created by Home on 16.11.2015.
 */
@Deprecated
public class RecursiveReader extends Reader
{
    private final Reader source;
    private Reader active = null;

    public RecursiveReader(Reader source)
    {
        this.source = source;
    }

    public void include(Reader next)
    {
        active = next;
    }

    @Override
    public int read(char[] chars, int i, int i1) throws IOException
    {
        if (active != null)
        {
            int n = active.read(chars, i, i1);
            checkAndCloseActive(n);
            if (n != -1) return n;
        }
        return source.read(chars, i, i1);
    }

    @Override
    public long skip(long l) throws IOException
    {
        return (active != null) ? active.skip(l) : source.skip(l);
    }

    @Override
    public void close() throws IOException
    {
        if (active != null) active.close();
        source.close();
    }

    private void checkAndCloseActive(int n) throws IOException
    {
        if (n == -1) {
            active.close();
            active = null;
        }
    }
}
