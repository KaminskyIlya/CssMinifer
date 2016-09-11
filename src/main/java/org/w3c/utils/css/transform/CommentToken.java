package org.w3c.utils.css.transform;

import org.w3c.utils.css.parsers.TextPosition;

/**
 * Created by Home on 27.08.2016.
 */
public class CommentToken extends Token
{
    public CommentToken(String token, TextPosition start, TextPosition end)
    {
        super(token, start, end);
    }
}
