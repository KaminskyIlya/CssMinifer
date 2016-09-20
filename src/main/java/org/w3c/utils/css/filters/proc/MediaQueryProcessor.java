package org.w3c.utils.css.filters.proc;

import org.w3c.utils.css.help.CharUtils;
import org.w3c.utils.css.io.CharsBuffer;

/**
 * Media queries text processor.
 * Determine symbol status at each moment
 *
 * Created by Home on 19.09.2016.
 */
public class MediaQueryProcessor extends AdvancedProcessor
{
    private boolean prefix;
    private boolean mediaType;
    private boolean conjunction;
    private boolean expression;
    private boolean finished;
    private CharsBuffer buffer;

    private boolean wasPrefix;

    public MediaQueryProcessor()
    {
        reset();
    }

    @Override
    public void reset()
    {
        super.reset();
        prefix = mediaType = conjunction = false;
        wasPrefix = false;
        buffer = new CharsBuffer(4);
        buffer.reset();
    }

    @Override
    public void before(char current)
    {
        super.before(current);

        if ( in(",{", current) )
        {
            finished = true;
            expression = prefix = conjunction = mediaType = false;
            wasPrefix = false;
            buffer.reset();
            return;
        }
        finished = false;

        if (in("NnOoTtLlYyAaDd", current)) buffer.append(current); else buffer.reset();
        String prev = buffer.getBuffer().toLowerCase();

        expression = isInParenthesis();

        if (prefix && current == ' ') wasPrefix = true;
        prefix = !wasPrefix && !mediaType && !conjunction && !expression && !prev.isEmpty() && ("not".startsWith(prev) || "only".startsWith(prev));

        conjunction = !mediaType && !expression && !prev.isEmpty() && ("and".startsWith(prev));
        mediaType = !prefix && !expression && !conjunction && (isEscaped() || CharUtils.isIdentChar(current));

        wasPrefix &= !conjunction && !mediaType && !expression;
    }

    @Override
    public void after(char current)
    {
        super.after(current);
    }

    public boolean inExpression()
    {
        return expression;
    }

    public boolean inPrefix()
    {
        return prefix;
    }

    public boolean inMediaType()
    {
        return mediaType;
    }

    public boolean inConjunction()
    {
        return conjunction;
    }

    public boolean isFinished()
    {
        return finished;
    }
}
