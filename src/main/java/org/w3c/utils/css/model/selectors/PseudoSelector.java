package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.model.exceptions.CssParsingException;

/**
 * Basic class for all pseudo selectors.
 *
 * Created by Home on 17.09.2016.
 */
public class PseudoSelector extends AbstractSelector
{
    protected String name;

    public PseudoSelector(String selector)
    {
        this.selector = selector;
        this.name = selector.toLowerCase();
        this.specificity.addSelectorExplanation();
    }

    public String getName()
    {
        return name;
    }

    /**
     * Analysing selector and build model
     *
     * @throws CssParsingException
     */
    void analyze()
    {
        // for simple pseudo classes do nothing
    }
}
