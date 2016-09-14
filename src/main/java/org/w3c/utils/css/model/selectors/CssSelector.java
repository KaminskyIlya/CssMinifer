package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.help.CharUtils;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.processors.ReaderTokenizer;
import org.w3c.utils.css.model.exceptions.CssParsingException;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Simple selectors group representation.
 * p.article:nth-child(even) ~ font[color="red"] - is a selector.
 *
 * @See http://www.w3.org/TR/css3-selectors/#selectors
 * @See http://www.w3.org/TR/CSS2/selector.html#pattern-matching
 *
 * Created by Home on 29.11.2015.
 */
public class CssSelector extends AbstractSelector
{
    private Collection<Qualifier> qualifiers = new LinkedHashSet<Qualifier>();

    public CssSelector(String selector)
    {
        this.selector = selector;
    }

    public void analyze()
    {
        ReaderTokenizer tokenizer = new ReaderTokenizer(new CharsReader(selector));
        FlowProcessor processor = new ConjunctionProcessor();

        String token;
        int pos = 0;

        while ( (token = tokenizer.nextNotEmptyToken("\r\n\f\t +>~", processor)) != null )
        {
            char d = tokenizer.getLastDelimiter();
            if (CharUtils.isWhiteSpace(d)) d = ' ';

            addQualifier(token, d, pos);

            pos = tokenizer.getPos();
        }
    }

    private void addQualifier(String token, char conjunction, int pos)
    {
        Qualifier qualifier = new Qualifier(token);
        qualifier.setConjunction(conjunction);
        try
        {
            qualifier.analyze();
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), pos + e.getPosition(), e.getLevel());
        }
        qualifiers.add(qualifier);
        specificity.addSpecificity(qualifier.getSpecificity());
    }


    private static class ConjunctionProcessor extends SelectorProcessor
    {
        @Override
        public boolean canProcess()
        {
            return isInConjunction();
        }
    }

}
