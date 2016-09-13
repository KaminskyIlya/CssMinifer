package org.w3c.utils.css.filters.proc;

/**
 * Qualifier processor.
 * Determine a part of css qualifiers.
 * CSS qualifier is a string as: P.article:nth-child(even)
 *
 * Created by Home on 13.09.2016.
 */
public class QualifierProcessor extends AdvancedProcessor
{
    private boolean tag; // we are in a tag name or universal element (*) or namespace
    private boolean hash; // we are in ID hash #
    private boolean cls; // we are in a class .
    private boolean pseudo; // we are in a pseudo element : and it's expression
    private boolean attr; // we are in attr expression []

    public QualifierProcessor()
    {
        reset();
    }

    @Override
    public void reset()
    {
        super.reset();
        tag = true;
    }

    @Override
    public void before(char current)
    {
        super.before(current);

        if ( !isInParenthesis() && in(".#:[", current) ) // break point for all items
        {
            attr = (current == '['); // started attr block
            tag = hash = cls = pseudo = false;
        }
    }

    @Override
    public void after(char current)
    {
        super.after(current);

        if ( isNormal() ) // skip escaped and string symbols
        {
            if ( !pseudo && attr && current == ']' ) attr = false; // finished attr block

            if ( pseudo && current == ')' && getParenthesisLevel() == 0 ) pseudo = false;

            if ( !isInParenthesis() ) // we must not are in [], {} or ()
            {
                if (current == '#') hash = true; // start hash
                if (current == '.') cls = true; // start class
                if (current == ':') pseudo = true; // start pseudo class
            }
        }
    }

    public boolean isInTag()
    {
        return tag;
    }

    public boolean isInHash()
    {
        return hash;
    }

    public boolean isInClass()
    {
        return cls;
    }

    public boolean isInAttr()
    {
        return attr;
    }
}
