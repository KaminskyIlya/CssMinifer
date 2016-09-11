package org.w3c.utils.css.filters.proc;

/**
 * Helper class for removing semicolons tails.
 *
 * Created by Home on 08.11.2015.
 */
public class SemicolonProcessor extends TextProcessor
{
    public boolean isItSingleSemicolon(char current, char next)
    {
        return prevChar != current && current == ';' && next != '}' && next != current && isNormal();
    }

    public boolean isItLastSeparator(char current, char next)
    {
        return current == ';' && next != '}' && next != current && isNormal();
    }

    public boolean canWriteIt(char current)
    {
        return current != ';' || !isNormal();
    }
}
