package org.w3c.utils.css.filters.proc;

/**
 * Attribute matcher processor.
 * Used for parsing attribute selectors.
 * For example, [attr=value]
 *
 * Created by Home on 05.09.2016.
 */
public class AttributeSelectorProcessor extends SimpleProcessor
{
    private boolean inAttr = false;  // if we are in attribute name of matcher now?
    private boolean inMatcher = false;  // if we are matcher sign now?
    private boolean inValue = false; // if we are in value of matcher now?
    private boolean wasAttr = false; // if attribute's name already processed or in processing now
    private boolean wasMatcher = false; // if matcher already processed or in processing now
    private boolean wasValue = false; // if value already processed or in processing now

    @Override
    public void reset() {
        super.reset();
        inAttr = inMatcher = inValue = false;
        wasAttr = wasMatcher = wasValue = false;
    }

    public void before(char current)
    {
        super.before(current);

        if (current == 0)
        {
            inAttr = inMatcher = inValue = false;
            return;
        }

        if (!wasAttr && !inWhitespace)
        {
            inAttr = wasAttr = true;
        }
        if (inAttr && inWhitespace) inAttr = false;


        if (wasAttr && !wasMatcher && isNormal() && in("=*~|^$", current) )
        {
            inAttr = false;
            inMatcher = wasMatcher = true;
        }
        inMatcher &= in("*~|^$", current) || ((current == '=') && prevChar != '=');


        if (wasMatcher && !inMatcher && !wasValue && !inWhitespace)
        {
            inMatcher = false;
            inValue = wasValue = true;
        }
        if (inValue && inWhitespace) inValue = false;
    }

    public void after(char current)
    {
        super.after(current);
    }

    public boolean canProcess()
    {
        return true;
    }

    public boolean isInAttributeName()
    {
        return inAttr;
    }

    public boolean isInAttributeMatcher()
    {
        return inMatcher;
    }

    public boolean isInAttributeValue()
    {
        return inValue;
    }


}
