package org.w3c.utils.css.filters.proc;

/**
 * Helper class for WhiteSpaceFilter.
 * Was extracted from parent class for helping unit test.
 *
 * Created by Home on 06.11.2015.
 */
public class WhiteSpaceProcessor extends TextProcessor
{
    private boolean prevWasSpace = true; // if previous symbol was space?
    //TODO: может быть добавить флаги inComment?  ведь иногда нужно удалять пробельные символы, без удаления комментариев. или нет?

    public WhiteSpaceProcessor()
    {
    }

    @Override
    public void reset()
    {
        super.reset();
        prevWasSpace = true;
    }

    @Override
    public void after(char current)
    {
        super.after(current);
        prevWasSpace = current == ' '; // last char was space?
    }

    public boolean isPreviousCharWasWhitespace()
    {
        return prevWasSpace;
    }

    public boolean isMustSkipNext(char current, char next)
    {
        if (!isInString() && next == ' ')
        {
            if (current == ' ') return true;

            if (inBlock)
            {
                return (!inValue || current == ':') || (in("%,=()!", current));
            }
            else
            {
                if (in("}*,+>:([=~^$|\"'", current)) return true;
            }
        }
        return false;
    }

    public boolean canWriteSpace(char current, char next)
    {
        if (!isInString() && !prevWasSpace && current == ' ')
        {
            if (inBlock)
            {
                return inValue && not(":;%!})=", next);
            }
            else
            {
                return !(inAttr && next == '*') && (not("{,+>:()[]=~^$|\"'", next));

            }
        }
        return isInString() && current == ' ';
    }

    public boolean canWriteSpaceNow()
    {
        return isInString() || !prevWasSpace;
    }

    public boolean canWriteIt(char current)
    {
        return ( isInString() || current != ' ' );
    }

}
