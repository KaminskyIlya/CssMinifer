package org.w3c.utils.css.filters.proc;

/**
 * Flow text processor for detect qualifier combinators and whitespaces surround this.
 * For example, in selector a[download] > em this detected the " > " string.
 * See more specifications about combinators at https://www.w3.org/TR/css3-selectors/#combinators
 *
 * Created by Home on 17.09.2016.
 */
public class CombinatorProcessor extends SelectorProcessor
{
    @Override
    public boolean canProcess()
    {
        return isInConjunction() || (!isInQualifier() && inWhitespace);
    }
}
