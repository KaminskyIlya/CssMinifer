package org.w3c.utils.css.filters;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.TextProcessor;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.w3c.utils.css.help.CharUtils.*;


/**
 * Filter for removing inline comments.
 *
 * Created by Home on 03.11.2015.
 */
public class InlineCommentsFilter extends AbstractFilter
{
    public InlineCommentsFilter(RecycledCharBuffer buffer)
    {
        super(buffer);
    }

    public RecycledCharBuffer getBuffer() {
        return buffer;
    }

    public void apply()
    {
        while (buffer.available())
        {
            if (isInlineCommentFounded()) skipInlineComment();
        }
    }


    private boolean isInlineCommentFounded()
    {
        FlowProcessor processor = new InlineCommentProcessor();

        while (buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            processor.before(current);
            if (isInlineCommentStart(current, next) && processor.canProcess()) return true;

            buffer.write(current);
            processor.after(current);
        }
        return false;
    }

    private void skipInlineComment()
    {
        while (buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            if (isWindowsEOL(current, next)) buffer.skip();

            if (isEOL(current)) return;
        }
    }



    private static class InlineCommentProcessor extends TextProcessor
    {
        @Override
        public boolean canProcess()
        {
            return !isInString() && ((inValue && !isInParenthesis()) || !inValue);
        }
    }
}
