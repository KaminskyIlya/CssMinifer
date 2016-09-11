package org.w3c.utils.css.model.exceptions;

/**
 * Runtime CSS parsing exception.
 *
 * Created by Home on 29.11.2015.
 */
public class CssParsingException extends RuntimeException
{
    private final EExceptionLevel level;
    private final int position;
    private final int length;

    public CssParsingException(String message, int position)
    {
        this(message, position, EExceptionLevel.ERROR);
    }

    public CssParsingException(String message, int position, EExceptionLevel level)
    {
        this(message, position, 1, level);
    }

    public CssParsingException(String message, int position, int length, EExceptionLevel level)
    {
        super(message);
        this.position = position;
        this.level = level;
        this.length = length;
    }

    public EExceptionLevel getLevel()
    {
        return level;
    }

    public int getPosition()
    {
        return position;
    }

    public int getLength()
    {
        return length;
    }
}
