package org.w3c.utils.css.filters;

import org.w3c.utils.css.filters.proc.SemicolonProcessor;
import org.w3c.utils.css.io.RecycledCharBuffer;
import org.w3c.utils.css.filters.proc.WhiteSpaceProcessor;

import static org.w3c.utils.css.help.CharUtils.*;

/**
 * Removes redundant spaces.
 * InlineCommentsFilter must be applied before.
 *
 * Created by Home on 06.11.2015.
 */
public class WhiteSpacesFilter extends AbstractFilter
{
    private boolean removeEOLs = true;

    public WhiteSpacesFilter(RecycledCharBuffer buffer) {
        super(buffer);
    }

    public void apply()
    {
        normalizeWhitespaces();
        removeConsecutiveSpaces();
        removeAnotherSpaces();
        replaceRedundantSemicolons();

        buffer.refill();
    }

    public void setRemoveEOLs(boolean value)
    {
        removeEOLs = value;
    }

    /**
     * Replaces all whitespace chars to space symbol.
     */
    private void normalizeWhitespaces()
    {
        while (buffer.available())
        {
            char current = buffer.read();

            buffer.write(isWhiteSpace(current, removeEOLs) ? ' ' : current);
        }
        buffer.reuse();
    }

    /**
     * Removes redundant semicolons.
     * Removes semicolon in a tail of blocks of style definition.
     */
    private void replaceRedundantSemicolons()
    {
        SemicolonProcessor processor = new SemicolonProcessor();

        while (buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            processor.before(current);

            if (processor.canWriteIt(current) || processor.isItSingleSemicolon(current, next) || processor.isItLastSeparator(current, next)) buffer.write(current);

            processor.after(current);
        }
        buffer.reuse();
    }

    /**
     * Collapse all consecutive whitespaces to single whitespace.
     */
    private void removeConsecutiveSpaces()
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();

        while (buffer.available())
        {
            char current = buffer.read();

            processor.before(current);

            if (current == ' ' && processor.canWriteSpaceNow()) buffer.write(' ');

            if (current != ' ') buffer.write(current);

            processor.after(current);
        }
        buffer.reuse();
    }

    /**
     * Remove another redundant whitespaces.
     */
    private void removeAnotherSpaces()
    {
        WhiteSpaceProcessor processor = new WhiteSpaceProcessor();

        while (buffer.available())
        {
            char current = buffer.read();
            char next = buffer.next();

            processor.before(current);

            if (processor.isMustSkipNext(current, next)) buffer.skip();

            boolean canWriteIt = processor.canWriteIt(current);
            if (canWriteIt) buffer.write(current);

            boolean spaceWasWrote = canWriteIt && current == ' ';
            if (!spaceWasWrote && processor.canWriteSpace(current, next)) buffer.write(' ');

            processor.after(current);
        }
        buffer.reuse();
    }

}
