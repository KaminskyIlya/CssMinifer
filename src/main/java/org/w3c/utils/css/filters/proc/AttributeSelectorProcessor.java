package org.w3c.utils.css.filters.proc;

import org.w3c.utils.css.help.CharUtils;

/**
 * Attribute matcher processor.
 * Used for parsing attribute selectors.
 * For example, [attr=value]
 *
 * Created by Home on 05.09.2016.
 */
public class AttributeSelectorProcessor extends SimpleProcessor implements FlowProcessorEx
{
    private boolean inAttr = false;  // if we are in attribute name of matcher now?
    private boolean inMatcher = false;  // if we are matcher sign now?
    private boolean inValue = false; // if we are in value of matcher now?
    private boolean wasAttr = false; // if attribute's name already processed or in processing now
    private boolean wasMatcher = false; // if matcher already processed or in processing now
    private boolean doubleMatcher = false; // if matcher symbol is double: *= ^= ~= $= |=
    private boolean wasValue = false; // if value already processed or in processing now

    @Override
    public void reset() {
        super.reset();
        inAttr = inMatcher = inValue = false;
        wasAttr = wasMatcher = doubleMatcher = wasValue = false;
    }

    public void before(char current, char next)
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
        if ( inAttr && inWhitespace ) inAttr = false;

        checkAttrSymbol(current);

        if ( !wasMatcher )
        {
            doubleMatcher = isNormal() && in("*|~^$", current) && next == '=';
        }

        if (wasAttr && !wasMatcher && (doubleMatcher || current == '='))
        {
            inAttr = false;
            inMatcher = wasMatcher = true;
        }
        if (doubleMatcher)
        {
            inMatcher &= in("*|~^$", current) || (current == '=' && in("*|~^$", prevChar));
        }
        else
            inMatcher &= current == '=';

        if (wasMatcher && !inMatcher && !wasValue && !inWhitespace)
        {
            inMatcher = doubleMatcher = false;
            inValue = wasValue = true;
        }
        if (inValue && inWhitespace) inValue = false;
    }

    public void checkAttrSymbol(char current)
    {
        if ( inAttr )
        {
            boolean valid = match("[_a-z0-9\\-]", current) || CharUtils.isNonASCII(current) || in("=*~|^$\\", current);
            if ( !valid ) throw new IllegalStateException("Unexpected char");
        }
    }

    public void after(char current, char next)
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
