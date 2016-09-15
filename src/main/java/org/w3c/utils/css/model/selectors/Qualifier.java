package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.filters.proc.QualifierProcessor;
import org.w3c.utils.css.help.StringUtils;
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
 *
 * @See http://www.w3.org/TR/css3-selectors/#selectors
 * @See http://www.w3.org/TR/CSS2/selector.html#pattern-matching
 *
 * Created by Home on 05.12.2015.
 */
public class Qualifier extends AbstractSelector
{
    /**
     * Conjunction with previous qualifier in selector.
     * First qualifier has default conjunction ' ' (none)
     */
    private char conjunction = ' ';
    /**
     * No namespace (default)
     */
    private String namespace = "";
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
     * Attribute attributes
     */
    private Collection<AttributeSelector> attributes = new HashSet<AttributeSelector>();



    public Qualifier(String qualifier)
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

    public String getNamespace()
    {
        return namespace;
    }

    public String getType()
    {
        return type;
    }

    public Collection<String> getClasses()
    {
        return classes;
    }

    public Collection<String> getHashes()
    {
        return hashes;
    }

    public Collection<String> getPseudo()
    {
        return pseudo;
    }

    public Collection<AttributeSelector> getAttributes()
    {
        return attributes;
    }

    /**
     * Analysing css qualifier
     *
     * @throws CssParsingException
     */
    void analyze()
    {
        QualifierProcessor processor = new QualifierProcessor();
        CharsReader reader = new CharsReader(selector);
        TokenExtractor<QualifierProcessor> extractor = new TokenExtractor<QualifierProcessor>(reader, processor);

        while (reader.available())
        {
            if (processor.isInTag())                processTypeSelector(extractor);
            else if (processor.isInHash())          processIdSelector(extractor);
            else if (processor.isInClass())         processClassSelector(extractor);
            else if (processor.isInPseudo())        processPseudoExpression(extractor);
            else if (processor.isInAttr())          processAttributeSelector(extractor);
            else if (processor.isInWhitespace())    processWhitespaces(extractor);
            else if (processor.isValid(reader.next()))            processSymbol(reader, processor);
            else
            {
                throw new CssParsingException(String.format("Unrecognized part of selector %s at pos %d", selector, reader.getPos()), reader.getPos(), selector.length(), EExceptionLevel.ERROR);
            }
        }
    }

    private void processWhitespaces(final TokenExtractor<QualifierProcessor> extractor)
    {
        extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInWhitespace();
            }
        });
    }

    private void processTypeSelector(final TokenExtractor<QualifierProcessor> extractor)
    {
        String identifier = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInTag();
            }
        });

        int pos = StringUtils.findNotEscapedCharPos(identifier, '|');
        if (pos >= 0)
        {
            namespace = identifier.substring(0, pos);
            type = identifier.substring(pos + 1);
        }
        else
        {
            type = identifier;
        }

        if ( !type.equals("*") ) specificity.addQualifier();
    }

    private void processIdSelector(final TokenExtractor<QualifierProcessor> extractor)
    {
        String hash = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInHash();
            }
        });

        hashes.add(hash);
        specificity.addIdSelector();
    }

    private void processClassSelector(final TokenExtractor<QualifierProcessor> extractor)
    {
        String cls = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInClass();
            }
        });

        classes.add(cls);
        specificity.addSelectorExplanation();
    }

    private void processAttributeSelector(final TokenExtractor<QualifierProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        String matcher = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInAttr();
            }
        });

        matcher = matcher.replaceFirst("^\\[", "").replaceFirst("\\]$", "");

        try
        {
            AttributeSelector selector = new AttributeSelector(matcher);
            attributes.add(selector);
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), pos + e.getPosition(), e.getLevel());
        }

        specificity.addSelectorExplanation();
    }

    private void processPseudoExpression(final TokenExtractor<QualifierProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        String expression = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInPseudo();
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
        Qualifier q = new Qualifier(negation);
        q.analyze();

        // Adding included selector specificity to our specificity
        specificity.addSpecificity(q.getSpecificity());
    }
}
