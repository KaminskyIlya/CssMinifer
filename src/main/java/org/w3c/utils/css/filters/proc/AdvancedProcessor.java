package org.w3c.utils.css.filters.proc;

/**
 * Advanced text processor to control this blocks: () [] {}
 *
 * Created by Home on 13.09.2016.
 */
public class AdvancedProcessor extends SimpleProcessor
{
    private int countParenthesis;   // if we are in () expression
    private int countBrackets;      // if we are in {}
    private int countSquaredBrackets; // if we are in []

    public AdvancedProcessor()
    {
        reset();
    }

    @Override
    public void reset()
    {
        super.reset();
        countParenthesis = 0;
        countBrackets = 0;
        countSquaredBrackets = 0;
    }

    @Override
    public void before(char current)
    {
        super.before(current);

        if (current == '(' && isNormal()) countParenthesis++;
        if (current == '{' && isNormal()) countBrackets++;
        if (current == '[' && isNormal()) countSquaredBrackets++;
    }

    /**
     *
     * @param current
     * @throws IllegalStateException when closing overflow
     */
    @Override
    public void after(char current)
    {
        if ( isNormal() )
        {
            if (current == ')') countParenthesis--;
            if (current == '}') countBrackets--;
            if (current == ']') countSquaredBrackets--;

            if (countParenthesis < 0 || countBrackets < 0 || countSquaredBrackets < 0)
                throw new IllegalStateException("Unexpected " + current);
        }

        super.after(current);
    }

    /**
     * @return <b>true</b>, if we inside in a block ()
     */
    public boolean isInParenthesis()
    {
        return countParenthesis > 0;
    }

    /**
     * @return <b>true</b>, if we inside in a block {}
     */
    public boolean isInBrackets()
    {
        return countBrackets > 0;
    }

    /**
     * @return <b>true</b>, if we inside in a block []
     */
    public boolean isInSquaredBrackets()
    {
        return countSquaredBrackets > 0;
    }

    /**
     * @return level of block ()
     */
    public int getParenthesisLevel()
    {
        return countParenthesis;
    }

    /**
     * @return level of block {}
     */
    public int getBracketsLevel()
    {
        return countBrackets;
    }

    /**
     * @return level of block []
     */
    public int getSquaredBracketsLevel()
    {
        return countSquaredBrackets;
    }

    /**
     * @return <b>true</b>, if we outside of any block () {} []
     */
    public boolean isNotInAnyBlock()
    {
        return !isInBrackets() && !isInParenthesis() && !isInSquaredBrackets();
    }

    /**
     * Sugar for
     * @return <b>true</b>, if we inside of any block () {} []
     */
    public boolean inAnyBlock()
    {
        return isInBrackets() || isInParenthesis() || isInSquaredBrackets();
    }
}
