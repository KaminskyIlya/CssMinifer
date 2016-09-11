package org.w3c.utils.css.model.exceptions;

import org.w3c.utils.css.io.SourceTextReader;

/**
 * Created by Home on 17.08.2016.
 */
public class CssTokenizerException extends RuntimeException
{
    private final String message;
    private final int line;
    private final int pos;
    SourceTextReader reader;
    private EExceptionLevel level;

    public CssTokenizerException(String message, SourceTextReader reader, EExceptionLevel level)
    {
        this.message = message;
        this.reader = reader;
        this.line = reader.getLine();
        this.pos = reader.getPos();
        this.level = level;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public EExceptionLevel getLevel()
    {
        return level;
    }

    public int getLine()
    {
        return line;
    }

    public int getPos()
    {
        return pos;
    }

    public SourceTextReader getReader()
    {
        return reader;
    }
}
