package org.w3c.utils.css.filters.proc;

/**
 * Helper class for selectors parsing.
 *
 * @See specifications at http://www.w3.org/TR/css3-selectors/
 *
 * Created by Home on 29.11.2015.
 */
public class SelectorProcessor extends AdvancedProcessor
{
    private boolean conjunction; // we are between of qualifiers
    private boolean qualifier; // we are in a qualifier

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
    }

    @Override
    public void before(char current)
    {
        super.before(current);

        conjunction = in("+>~", current) && isNormal() && isNotInAnyBlock();
        qualifier = (!isInWhitespace() && !conjunction) || inAnyBlock();
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

}
