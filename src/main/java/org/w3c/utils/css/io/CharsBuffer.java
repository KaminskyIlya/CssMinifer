package org.w3c.utils.css.io;

/**
 * Simple chars windowed buffer
 * Created by Home on 19.09.2016.
 */
public class CharsBuffer
{
    private final char buffer[];
    private int index;

    public CharsBuffer(int size)
    {
        buffer = new char[size];
        reset();
    }

    public void reset()
    {
        index = 0;
    }

    public void append(char c)
    {
        if (index < buffer.length)
        {
            buffer[index++] = c;
        }
        else
        {
            System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);
            buffer[buffer.length-1] = c;
        }
    }

    public String getBuffer()
    {
        return new String(buffer, 0, index);
    }

    @Override
    public String toString()
    {
        return getBuffer();
    }
}
