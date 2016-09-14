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
    private boolean tag; // we are in a tag name or universal element (*) or namespace|
    private boolean hash; // we are in ID #hash
    private boolean cls; // we are in a .class
    private boolean pseudo; // we are in a :pseudo element and it's expression (an+b) or :not(qualifier)
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

        if ( isNormal() ) // skip escaped and string symbols
        {
            if ( !isInParenthesis() && in(".#:[({", current) ) // common break points for items
            {
                attr = (current == '['); // started attr block
                if (current != '(') pseudo = false; // started expression in pseudo
                tag = hash = cls = false;
            }
        }
        if (current == 0)
        {
            tag = hash = cls = pseudo = attr = false;
        }
    }

    @Override
    public void after(char current)
    {
        if ( isNormal() ) // skip escaped and string symbols
        {
            if ( !pseudo && attr && current == ']' ) attr = false; // finished attr block

            if ( pseudo && current == ')' && getParenthesisLevel() == 0 ) pseudo = false; // finished pseudo expression

            if ( isNotInAnyBlock() ) // we must not be in [], {} or ()
            {
                if (current == '#') hash = true; // start hash
                if (current == '.') cls = true; // start class
                if (current == ':') pseudo = true; // start pseudo class
            }
        }

        super.after(current);
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

    public boolean isInPseudo()
    {
        return pseudo;
    }

    public boolean isValid(char next)
    {
        return in("*#.:[]()", next);
    }
}
