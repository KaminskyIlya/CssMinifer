package org.w3c.utils.css.filters.proc;

/**
 * Helper class for processing style sheet in a flow.
 * TODO: need tests all dynamic boolean properties; в дальнейшем придется отказаться от такого класса
 * он не правильно детектит block, поскольку не срабатывает для анимаций и т.п.
 *
 * Created by Home on 07.11.2015.
 */
public class TextProcessor extends SimpleProcessor
{
    protected boolean inMedia; // if we are in media type block
    protected boolean inBlock; // if we are in block of style definition?
    protected boolean inValue; // if we are in value of style?
    protected boolean inAttr;  // if we are in attribute value selector?
    private int inParenthesis;   // if we are in url expression

    public TextProcessor()
    {
        reset();
    }

    public void reset()
    {
        super.reset();
        inMedia = false;
        inBlock = false;
        inValue = false;
        inAttr = false;
        inParenthesis = 0;
    }

    public void before(char current)
    {
        super.before(current);

        inMedia |= prevChar == '@' && current == 'm';

        if (inMedia && isNormal() && current == '{')
        {
            inMedia = false;
        }
        else
        {
            inBlock |= isNormal() && current == '{'; // if block of styles started?
        }
        if ( isNormal() ) inBlock &= current != '}'; // if block of styles finished?

        inValue |= inBlock && current == ':' && !isInParenthesis(); // if value of style began?
        inValue &= inBlock && current != ';'; // if value of style ended?

        inAttr |= !inBlock && current == '['; // if attribute value selector began?
        inAttr &= !inBlock && current != ']'; // if attribute value selector ended?

        if ( current == '(' && isNormal() ) inParenthesis++;
        if ( current == ')' && isNormal() ) inParenthesis--;
    }

    public void after(char current)
    {
        super.after(current);
    }

    public boolean canProcess()
    {
        return true;
    }

    /**
     * Return current mode
     * @return <b>true</b>, if we inside of styles definition block.
     */
    public boolean isInBlock()
    {
        return inBlock;
    }

    /**
     * Return current mode
     * @return <b>true</b>, if we inside of style value
     */
    public boolean isInValue()
    {
        return inValue;
    }

    /**
     * Return current mode
     * @return <b>true</b>, if we inside of attribute selector
     */
    public boolean isInAttr()
    {
        return inAttr;
    }

    /**
     * Return current mode
     * @return <b>true</b>, if we inside of parenthesis expression
     */
    public boolean isInParenthesis()
    {
        return inParenthesis > 0;
    }
}
