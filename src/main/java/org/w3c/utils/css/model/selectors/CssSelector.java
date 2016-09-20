package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.CombinatorProcessor;
import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.processors.ReaderTokenizer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

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
    private static final Pattern COMBINATORS = Pattern.compile(  "(\\s*[\\+~> ]\\s*)|( +\\* +)"  );

    private Collection<Qualifier> qualifiers = new LinkedHashSet<Qualifier>();

    public CssSelector(String selector)
    {
        this.selector = selector;
    }

    public void analyze()
    {
        FlowProcessor processor = new CombinatorProcessor();
        ReaderTokenizer tokenizer = new ReaderTokenizer(selector);

        String qualifier;
        int pos = 0;

        while ( (qualifier = tokenizer.nextToken(COMBINATORS, processor)) != null )
        {
            if (qualifier.isEmpty()) throw new CssParsingException("Bad selector " + selector, tokenizer.getPos(), EExceptionLevel.ERROR);

            String s = tokenizer.getLastDelimiterString().trim();
            char d = s.isEmpty() ? ' ' : s.charAt(0);

            addQualifier(qualifier.trim(), d, pos);

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

    public Collection<Qualifier> getQualifiers()
    {
        return qualifiers;
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
