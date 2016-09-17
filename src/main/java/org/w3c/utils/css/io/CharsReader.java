package org.w3c.utils.css.io;

/**
 * Simple reader from string.
 * Without any exceptions due reading.
 *
 * Created by Home on 29.11.2015.
 */
public class CharsReader implements MarkableReader
{
    private final String source;
    private int pos = 0;
    private int mark = -1;

    /**
     * Default constructor. Build reader for specified string.
     *
     * @param source source of chars.
     * @throws NullPointerException
     */
    public CharsReader(String source)
    {
        if (source == null) throw new NullPointerException();
        this.source = source;
    }

    public char read()
    {
        return available() ? source.charAt(pos++) : 0;
    }

    public char next()
    {
        return available() ? source.charAt(pos) : 0;
    }

    public char next(int shift)
    {
        assert shift >= 0;
        return (pos + shift < source.length()) ? source.charAt(pos + shift) : 0;
    }

    public boolean available()
    {
        return pos < source.length();
    }

    public int getPos()
    {
        return pos;
    }

    /**
     *
     * @return source string.
     */
    public String getSource()
    {
        return source;
    }

    public void mark()
    {
        mark = pos;
    }

    public void reset()
    {
        if (mark >= 0) pos = mark;
        mark = -1;
    }

    public String readMarked()
    {
        return (mark >= 0) ? source.substring(mark, pos) : null;
    }

    /**
     * Rewind reader to backward
     * @param shift number of chars to move
     */
    public void backward(int shift)
    {
        pos -= shift; if (pos < 0) pos = 0;

        mark = (mark < pos) ? mark : -1;
    }

    /**
     * Sugar for mark operation. Moves a marker backward.
     * @param shift number of chars to move
     */
    public void mark(int shift)
    {
        mark = pos - shift;
        if (mark < 0) throw new IllegalArgumentException();
    }


    /**
     * Skip some symbols
     * @param count
     */
    public void skip(int count)
    {
        pos += count;
    }


    /**
     * Sugar for readMarked
     *
     * @return a marked string without last symbol
     */
    public String readMarkedTrimmedRight()
    {
        return (mark >= 0) ? (mark < pos-1) ? source.substring(mark, pos-1) : "" : null;
    }

    /**
     * Sugar for readMarked
     *
     * @return a marked string without first symbol
     */
    public String readMarkedTrimmedLeft()
    {
        return (mark >= 0) ? (mark+1 < pos) ? source.substring(mark+1, pos) : "" : null;
    }

    /**
     * Sugar for readMarked
     *
     * @return a marked string without first and last symbols
     */
    public String readMarkedTrimmedBoth()
    {
        return (mark >= 0) ? (mark+1 < pos-1) ? source.substring(mark+1, pos-1) : "" : null;
    }
}
