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
        super.after(current);

        if (current == ')' && isNormal()) countParenthesis--;
        if (current == '}' && isNormal()) countBrackets--;
        if (current == ']' && isNormal()) countSquaredBrackets--;

        if (countParenthesis < 0 || countBrackets < 0 || countSquaredBrackets < 0) throw new IllegalStateException("Unexpected " + current);
    }

    public boolean isInParenthesis()
    {
        return countParenthesis > 0;
    }

    public boolean isInBrackets()
    {
        return countBrackets > 0;
    }

    public boolean isInSquaredBrackets()
    {
        return countSquaredBrackets > 0;
    }

    public int getParenthesisLevel()
    {
        return countParenthesis;
    }

    public int getBracketsLevel()
    {
        return countBrackets;
    }

    public int getSquaredBracketsLevel()
    {
        return countSquaredBrackets;
    }

    public boolean isNotInAnyBlock()
    {
        return !isInBrackets() && !isInParenthesis() && !isInSquaredBrackets();
    }
}
