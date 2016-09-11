package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.model.CssSelectorSpecificity;

/**
 * Common interface for all types of selectors.
 *
 * Created by Home on 05.12.2015.
 */
public interface Selector
{
    /**
     *
     * @return initial value of selector
     */
    String getSelector();

    /**
     * @See http://www.w3.org/TR/css3-selectors/#specificity
     * @return single qualifier specificity
     */
    CssSelectorSpecificity getSpecificity();
}
