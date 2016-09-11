package org.w3c.utils.css.transform;

import org.w3c.utils.css.parsers.TextPosition;

/**
 * Created by Home on 23.08.2016.
 */
public abstract class Token
{
    private final String token;
    private final TextPosition start;
    private final TextPosition end;

    public Token(String token, TextPosition start, TextPosition end)
    {
        this.token = token;
        this.start = start;
        this.end = end;
    }

    public String getToken()
    {
        return token;
    }

    public TextPosition getStart()
    {
        return start;
    }

    public TextPosition getEnd()
    {
        return end;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        return this.token.equals(token.token);

    }

    @Override
    public int hashCode()
    {
        return token.hashCode();
    }
}
