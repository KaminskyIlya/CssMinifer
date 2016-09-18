package org.w3c.utils.css.model.selectors;

/**
 * Factory for pseudo classes
 *
 * Created by Home on 17.09.2016.
 */
class PseudoClassSelectorFactory
{
    public static PseudoSelector makePseudoSelectorFor(String expression)
    {
        int p = expression.indexOf("(");
        if (p < 0)
        {
            return new PseudoSelector(expression);
        }
        else
        {
            String name = expression.substring(0, p);
            if ( name.startsWith("not") ) return new NegateSelector(expression);
            if ( "nth-child nth-last-child nth-of-type nth-last-of-type".contains(name) ) return new OrderSelector(expression);
        }
        return new PseudoSelector(expression); // for unrecognized pseudo selectors - build it simple (as is)
    }
}
