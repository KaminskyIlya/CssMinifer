package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

import java.util.Collection;
import java.util.HashSet;

/**
 * Simple selectors group representation.
 * p.article:nth-child(even) - is a qualifier (single selector).
 * Qualifier group construct a css selector.
 * TODO: need test
 *
 * @See http://www.w3.org/TR/css3-selectors/#selectors
 * @See http://www.w3.org/TR/CSS2/selector.html#pattern-matching
 *
 * Created by Home on 05.12.2015.
 */
public class CssQualifier extends AbstractSelector
{
    /**
     * Conjunction with previous qualifier in selector.
     * First qualifier has default conjunction ' ' (none)
     */
    private char conjunction = ' ';
    /**
     * Qualifier type.
     * A, H1, DIV - is valid qualifier types.
     * Default type is '*' (all)
     */
    private String type = "*";
    /**
     * All classes in qualifier. Often is one or none.
     * HasSet used for automatically removing duplicates.
     */
    private Collection<String> classes = new HashSet<String>();
    /**
     * Hashes list. Often is only one or none.
     * HasSet used for automatically removing duplicates.
     */
    private Collection<String> hashes = new HashSet<String>();
    /**
     * All declared pseudo-classes.
     */
    private Collection<String> pseudo = new HashSet<String>();
    /**
     * Attribute matchers
     */
    private Collection<AttributeSelector> matchers = new HashSet<AttributeSelector>();



    public CssQualifier(String qualifier)
    {
        this.selector = qualifier;
    }

    public char getConjunction()
    {
        return conjunction;
    }

    public void setConjunction(char conjunction)
    {
        this.conjunction = conjunction;
    }

    /**
     * Analysing css qualifier
     *
     * @throws CssParsingException
     */
    void analyze()
    {
        SelectorProcessor processor = new SelectorProcessor();
        CharsReader reader = new CharsReader(selector);

        while (reader.available())
        {

            if (processor.isInTag())                 processTypeSelector(reader, processor);
            else if (processor.isInHash())           processIdSelector(reader, processor);
            else if (processor.isInClassName())      processClassSelector(reader, processor);
            else if (processor.isInPseudoClass())    processPseudoExpression(reader, processor);
            else if (processor.isInAttr())           processAttributeSelector(reader, processor);
            else if ( processor.isInWhitespace() )
            {
                processSymbol(reader, processor);
            }
            // Without any conjunctions!
            else
            {
                throw new CssParsingException(String.format("Unrecognized part of selector %s at pos %d", selector, reader.getPos()), reader.getPos(), selector.length(), EExceptionLevel.ERROR);
            }
        }
    }

    private void processTypeSelector(CharsReader reader, SelectorProcessor processor)
    {
        if ('*' != processor.getPreviousChar()) specificity.addQualifier();

        reader.mark();
        while (reader.available() && processor.isInTag())
        {
            processSymbol(reader, processor);
        }
        type = reader.readMarked();
    }

    private void processIdSelector(CharsReader reader, SelectorProcessor processor)
    {
        specificity.addIdSelector();

        reader.mark();
        while (reader.available() && processor.isInHash())
        {
            processSymbol(reader, processor);
        }
        hashes.add(reader.readMarked());
    }

    private void processClassSelector(CharsReader reader, SelectorProcessor processor)
    {
        specificity.addSelectorExplanation();

        reader.mark();
        while ( reader.available() && processor.isInClassName() && (reader.next() != '.') )
        {
            processSymbol(reader, processor);
        }
        classes.add(reader.readMarked());
    }

    private void processAttributeSelector(CharsReader reader, SelectorProcessor processor)
    {
        specificity.addSelectorExplanation();

        //matchers.add(new AttributeSelector(reader, processor));

        reader.mark();
        while ( reader.available() && processor.isInAttr() && (reader.next() != '[') )
        {
            processSymbol(reader, processor);
        }
        matchers.add(new AttributeSelector(reader.readMarkedTrimmedBoth()));
    }

    private void processPseudoExpression(CharsReader reader, SelectorProcessor selector)
    {
        reader.mark();
        while (reader.available() && !selector.isInParenthesis())
        {
            processSymbol(reader, selector);
        }
        String pseudoName = reader.readMarkedTrimmedRight();

        if (pseudoName.toLowerCase().endsWith("not"))
        {
            processNegation(reader, selector);
        }
        else
        {
            processPseudo(reader, selector);
        }
    }

    private void processPseudo(CharsReader reader, SelectorProcessor processor)
    {
        specificity.addSelectorExplanation();

        while (reader.available() && processor.isInPseudoClass())
        {
            processSymbol(reader, processor);
        }
    }



    /**
     * Calculates specificity for negate expression (for included selector)
     */
    private void processNegation(CharsReader reader, SelectorProcessor selector)
    {
        // Extract expression (simple selector)
        reader.mark(1);
        while (reader.available() && selector.isInPseudoClass())
        {
            processSymbol(reader, selector);
        }
        String expression = reader.readMarked();

        // Check expression syntax
        if ( expression.length() < 2)
        {
            throw new CssParsingException("Expected pseudo-class or pseudo-element name", reader.getPos() - expression.length(), EExceptionLevel.ERROR);
        }
        expression = expression.substring(1, expression.length() - 1);

        // Calculate included selector specificity
        CssQualifier q = new CssQualifier(expression);
        q.analyze();

        // Adding included selector specificity to our specificity
        specificity.addSpecificity(q.getSpecificity());
    }





}
