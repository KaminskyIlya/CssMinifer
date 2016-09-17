package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.model.CssSelectorSpecificity;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

/**
 * Negation expression selector model.
 * Model for pseudo class :not
 *
 * Created by Home on 17.09.2016.
 */
public class NegateSelector extends PseudoSelector
{
    private Qualifier negation;

    /**
     * Default constructor.
     *
     * @param selector pseudo expression, for example :not(input[disabled].red)
     */
    public NegateSelector(String selector)
    {
        super(selector);
        init();
    }

    private void init()
    {
        this.name = selector.substring(0, selector.indexOf("(")).trim();
    }

    public Qualifier getNegation()
    {
        return negation;
    }

    @Override
    public CssSelectorSpecificity getSpecificity()
    {
        return negation.getSpecificity();
    }

    /**
     * Analysing negation expression
     *
     * @throws CssParsingException
     */
    void analyze()
    {
        int start = selector.indexOf("(") + 1;
        String expression = selector.substring(start, selector.length()-1).trim();
        checkExpression(expression, start);

        negation = new Qualifier(expression);
        try
        {
            negation.analyze();
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), e.getPosition() + name.length(), e.getLength(), e.getLevel());
        }
    }

    private void checkExpression(String expression, int start)
    {
        if (expression.isEmpty())
            throw new CssParsingException("Empty negation in " + expression, 5, start, EExceptionLevel.ERROR);

        if (expression.startsWith(":not"))
            throw new CssParsingException("Forbidden recursive negation " + selector, start, selector.length() - start, EExceptionLevel.ERROR);
    }
}
