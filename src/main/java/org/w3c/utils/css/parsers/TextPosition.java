package org.w3c.utils.css.parsers;

/**
 * Selection position in text.
 *
 * Created by Home on 23.08.2016.
 */
public class TextPosition
{
    private final int line;
    private final int col;
    private final int length;

    public TextPosition(int line, int col)
    {
        this(line, col, 0);
    }

    public TextPosition(int line, int col, int length)
    {
        this.line = line;
        this.col = col;
        this.length = length;
    }

    /**
     * @return the line number of text.
     */
    public int getLine()
    {
        return line;
    }

    /**
     * @return the column number in line of first char of selected text.
     */
    public int getCol()
    {
        return col;
    }

    /**
     * @return length of selected text in.
     */
    public int getLength()
    {
        return length;
    }
}
