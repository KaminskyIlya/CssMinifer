package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.io.SymbolReader;
import org.w3c.utils.css.model.CssSelectorSpecificity;

/**
 * The common prototype all types of selectors.
 *
 * Created by Home on 05.12.2015.
 */
abstract class AbstractSelector implements Selector
{
    protected String selector;
    protected CssSelectorSpecificity specificity = new CssSelectorSpecificity();

    public String getSelector()
    {
        return selector;
    }

    /**
     * @See http://www.w3.org/TR/css3-selectors/#specificity
     * @return selector specificity
     */
    public CssSelectorSpecificity getSpecificity()
    {
        return specificity;
    }

    /**
     *
     * @param reader
     * @param start
     */
//    protected void readSelector(CharsReader reader, int start)
//    {
//        reader.mark(reader.getPos() - start);
//        selector = reader.readMarked();
//        reader.skip(selector.length());
//    }

    /**
     * Process one symbol from reader.
     *
     * @param reader source symbols reader
     * @param processor selector processor for check status
     */
    protected void processSymbol(SymbolReader reader, FlowProcessor processor)
    {
        char current = reader.read();
        processor.before(current);
        processor.after(current);
    }

    @Override
    public String toString()
    {
        return selector;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSelector that = (AbstractSelector) o;

        return selector.equals(that.selector);

    }

    @Override
    public int hashCode()
    {
        return selector.hashCode();
    }
}
