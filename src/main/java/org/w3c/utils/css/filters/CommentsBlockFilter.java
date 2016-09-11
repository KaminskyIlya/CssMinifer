package org.w3c.utils.css.filters;

import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.w3c.utils.css.help.CharUtils.*;

/**
 * Filter for removing block comments.
 *
 * Created by Home on 06.11.2015.
 */
public class CommentsBlockFilter extends AbstractFilter
{
    public CommentsBlockFilter(RecycledCharBuffer buffer) {
        super(buffer);
    }

    public void apply()
    {
        while (buffer.available())
        {
            if (isBlockCommentFounded()) skipBlockComment();
        }
    }

    private boolean isBlockCommentFounded()
    {
        while (buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            if (isBlockCommentStart(current, next)) return true;

            buffer.write(current);
        }
        return false;
    }

    private void skipBlockComment()
    {
        while(buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            if (isBlockCommendEnd(current, next)) {
                buffer.skip();
                return;
            }
        }
    }

}
