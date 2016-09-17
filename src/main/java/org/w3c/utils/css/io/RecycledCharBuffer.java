package org.w3c.utils.css.io;

/**
 * Recycled characters buffer. For the flow-line processing text with using a single buffer.
 *
 * Created by Home on 06.09.2015.
 */
public class RecycledCharBuffer implements SymbolReader
{
    protected final StringBuffer buffer;
    protected int size;
    protected int read_index = 0;
    protected int write_index = 0;


    public RecycledCharBuffer(String source)
    {
        buffer = new StringBuffer(source);
        size = buffer.length();
    }

    public RecycledCharBuffer(StringBuffer source)
    {
        buffer = source;
        size = buffer.length();
    }

    public RecycledCharBuffer(RecycledCharBuffer source)
    {
        buffer = source.buffer;
        size = source.getOutputSize();
    }

    /**
     * @return returns next symbol without reading source buffer.
     */
    public char next()
    {
        return read_index < size ? buffer.charAt(read_index) : 0;
    }

    public char next(int shift)
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Check where available symbols in the source buffer.
     * @return <b>true</b>, if source buffer has more symbols.
     */
    public boolean available()
    {
        return read_index < size;
    }

    public int getPos()
    {
        return read_index;
    }

    /**
     * Reads a one char from source buffer.
     * @return a single char
     * @throws IllegalStateException when buffer is empty or read operation takes overflow
     */
    public char read()
    {
        if (read_index < size)
        {
            return buffer.charAt(read_index++);
        }
        throw new IllegalStateException("RecycledCharBuffer is empty.");
    }

    /**
     * Writes a one char to output buffer.
     * @param c the char to output
     */
    public void write(char c)
    {
        if (write_index < read_index)
        {
            buffer.setCharAt(write_index++, c);
        }
        else
        {
            throw new IllegalStateException("RecycledCharBuffer is full.");
        }
    }

    /**
     * Skips one symbol from the read buffer.
     */
    public void skip()
    {
        read();
    }

    /**
     * Skip a some symbols from the read buffer.
     * @param i count of symbols for skip.
     */
    public void skip(int i)
    {
        for (; i > 0; i--)
        {
            read();
        }
    }

    /**
     * Copies one char from current source place to destination place.
     * Sugar for .write(.read()) operation.
     *
     */
    public void pipe()
    {
        write(read());
    }

    /**
     * Copies some chars from source place to destination place.
     *
     * @param i chars to copy
     */
    public void pipe(int i)
    {
        for (; i > 0; i--)
        {
            pipe();
        }
    }

    /**
     * Prepares for buffer reuse:
     * source buffer takes a written data;
     * output buffer is cleared.
     */
    public RecycledCharBuffer reuse()
    {
        if (write_index != 0)
        {
            buffer.setLength(write_index);
            size = write_index;
        }
        read_index = write_index = 0;
        return this;
    }

    /**
     * Sets output buffer as equivalent to source buffer.
     */
    public RecycledCharBuffer refill()
    {
        if (write_index == 0)
        {
            read_index = write_index = size;
            return this;
        }
        throw new IllegalStateException("Buffer refilling valid only for newest buffer.");
    }

    /**
     *
     * @return length of source buffer
     */
    public int getSourceSize()
    {
        return size;
    }

    /**
     *
     * @return length of written data
     */
    public int getOutputSize()
    {
        return write_index;
    }

    /**
     *
     * @return written data in buffer at this moment
     */
    public String getOutput()
    {
        return buffer.substring(0, write_index);
    }

    /**
     *
     * @return written data
     */
    @Override
    public String toString() {
        return getOutput();
    }
}
