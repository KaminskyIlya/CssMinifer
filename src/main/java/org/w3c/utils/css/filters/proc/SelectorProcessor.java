package org.w3c.utils.css.filters.proc;

/**
 * Helper class for selectors parsing.
 *
 * @See specifications at http://www.w3.org/TR/css3-selectors/
 *
 * Created by Home on 29.11.2015.
 */
public class SelectorProcessor extends TextProcessor
{
    private boolean conjunction; // we are between of names
    private boolean qualifier; // we are in a qualifier
    private boolean tag; // we are in a tag
    private boolean hash; // we are in ID hash
    private boolean cls; // we are in a class
    private boolean pseudo; // we are in a pseudo element
    private boolean matcher; // we are in a attribute matcher ?= [name?="value"]
    private boolean a_name; // we are in a name of attribute to match
    private boolean a_value; // we are in value of attribute (in string)
    private boolean expr; // we are in pseudo expression
    private boolean wasMatcher;

    public SelectorProcessor()
    {
        reset();
    }

    @Override
    public void reset()
    {
        super.reset();

        conjunction = false;
        qualifier = false;

        // qualifier chunks
        tag = false;
        hash = false;
        cls = false;
        pseudo = false;

        // inAttr []
        matcher = false;
        a_name = false;
        a_value = false;
        wasMatcher = false;

        // inParenthesis ()
        expr = false;
    }

    @Override
    public void before(char current)
    {
        super.before(current);

        boolean in_brackets = isInParenthesis() || (current == ')');

        inAttr |= isNormal() && current == '[';
        inAttr |= inAttr && !isInString() && prevChar == ']';

        qualifier = !inBlock && !in_brackets && !inAttr && !isInString() && not("\r\n\f\t ", current);
        if ( qualifier && in("+>~])},", current) ) qualifier = isEscaped();
        conjunction = in("\r\n\f\t +>~", current) && !inBlock && !isInString() && !inAttr && !isInParenthesis() && !qualifier;

        if (current == '.' && qualifier)
        {
            cls = true; hash = false; pseudo = false;
        }
        if (current == '#' && qualifier)
        {
            hash = true; cls = false; pseudo = false;
        }
        if (current == ':' && qualifier)
        {
            pseudo = true; cls = false; hash = false;
        }


        tag = qualifier && !cls && !hash && !pseudo;
        cls &= qualifier;
        hash &= qualifier;
        pseudo &= qualifier || in_brackets;


        matcher = inAttr && in("=~^$*|", current);
        wasMatcher |= matcher;
        wasMatcher &= inAttr;

        a_name |= !isInString() && inAttr;
        a_name &= !wasMatcher && match("[\\w\\-]", current);
        a_value = inAttr && wasMatcher && (isInString() || match("[\\w\\-'\"]", current)); //
        //a_value = inAttr && wasMatcher && ( inString || match("[\\w\\-]", current) || (wasString && isQuote(current)) ); // <-- for future, if errors will be discovered

        expr = pseudo && in_brackets;
    }

    @Override
    public void after(char current)
    {
        super.after(current);
    }

    public boolean isInQualifier()
    {
        return qualifier;
    }

    public boolean isInConjunction()
    {
        return conjunction;
    }

    public boolean isInTag()
    {
        return tag;
    }

    public boolean isInHash()
    {
        return hash;
    }

    public boolean isInClassName()
    {
        return cls;
    }

    public boolean isInPseudoClass()
    {
        return pseudo;
    }

    public boolean isInAttributeMatcher()
    {
        return matcher;
    }

    public boolean isInAttributeName()
    {
        return a_name;
    }

    public boolean isInAttributeValue()
    {
        return a_value;
    }

    public boolean isInPseudoExpression()
    {
        return expr;
    }

}
