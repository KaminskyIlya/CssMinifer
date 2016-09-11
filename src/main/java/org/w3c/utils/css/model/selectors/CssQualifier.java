package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.processors.TokenExtractor;

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
        TokenExtractor<SelectorProcessor> extractor = new TokenExtractor<SelectorProcessor>(reader, processor);

        while (reader.available())
        {
            if (processor.isInTag())                 processTypeSelector(extractor);
            else if (processor.isInHash())           processIdSelector(extractor);
            else if (processor.isInClassName())      processClassSelector(extractor);
            else if (processor.isInPseudoClass())    processPseudoExpression(extractor);
            else if (processor.isInAttr())           processAttributeSelector(extractor);
            else if (processor.isInWhitespace())     processWhitespaces(extractor);
            else if (processor.isValid(reader.next()))            processSymbol(reader, processor);
            else// if ( !processor.isValid() )
            {
                throw new CssParsingException(String.format("Unrecognized part of selector %s at pos %d", selector, reader.getPos()), reader.getPos(), selector.length(), EExceptionLevel.ERROR);
            }
        }
    }

    private void processWhitespaces(final TokenExtractor<SelectorProcessor> extractor)
    {
        extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInWhitespace();
            }
        });
    }

    private void processTypeSelector(final TokenExtractor<SelectorProcessor> extractor)
    {
        String type = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInTag();
            }
        });
        if ( !type.equals("*") ) specificity.addQualifier();
        // TODO: analyze namespaces
    }

    private void processIdSelector(final TokenExtractor<SelectorProcessor> extractor)
    {
        specificity.addIdSelector();

        String hash = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInHash();
            }
        });
        // TODO: check hash syntax
        hashes.add(hash);
    }

    private void processClassSelector(final TokenExtractor<SelectorProcessor> extractor)
    {
        specificity.addSelectorExplanation();

        String cls = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInClassName();
            }
        });
        classes.add(cls);
    }

    private void processAttributeSelector(final TokenExtractor<SelectorProcessor> extractor)
    {
        specificity.addSelectorExplanation();

        int pos = extractor.getReader().getPos();

        String matcher = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInAttr();
            }
        });

        matcher = matcher.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
        // TODO: format check

        try
        {
            AttributeSelector selector = new AttributeSelector(matcher);
            matchers.add(selector);
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), pos + e.getPosition(), e.getLevel());
        }
    }

    private void processPseudoExpression(final TokenExtractor<SelectorProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        String expression = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInPseudoClass();
            }
        });

        try
        {
            if (expression.toLowerCase().startsWith("not("))
            {
                processNegation(expression);
            }
            else
            {
                processPseudo(expression);
            }
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), pos + e.getPosition(), e.getLevel());
        }
    }

    private void processPseudo(String expression)
    {
        specificity.addSelectorExplanation();
        //TODO: need to check syntax
        pseudo.add(expression); // TODO: need make true model
    }

    /**
     * Calculates specificity for negate expression (for included selector)
     */
    private void processNegation(String expression)
    {
        String negation = expression.replaceFirst("^not\\(", "").replaceFirst("\\)$", "");

        // Check expression syntax
        if ( negation.length() < 2)
        {
            throw new CssParsingException("Empty negation in " + expression, 5, EExceptionLevel.ERROR);
        }

        // Calculate included selector specificity
        CssQualifier q = new CssQualifier(negation);
        q.analyze();

        // Adding included selector specificity to our specificity
        specificity.addSpecificity(q.getSpecificity());
    }





}
