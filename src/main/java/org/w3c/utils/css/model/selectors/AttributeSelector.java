package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.AttributeSelectorProcessor;
import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.help.LangUtils;
import org.w3c.utils.css.help.StringUtils;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.processors.TokenExtractor;

import java.util.regex.Pattern;


/**
 * Attribute selector in single qualifier.
 * See specifications at https://www.w3.org/TR/css3-selectors/#attribute-selectors
 * and https://www.w3.org/TR/css3-selectors/#w3cselgrammar
 *
 * Created by Home on 05.12.2015.
 */
class AttributeSelector extends AbstractSelector
{
    private String attribute;
    private char matcher = 0;
    private String value;

    private static final Pattern matcherPattern = Pattern.compile("[\\*~\\|\\^\\$]?=");

    /**
     * Build attribute matcher.
     *
     * @param selector matcher expression  [attr?=value]
     */
    public AttributeSelector(String selector)
    {
        if (LangUtils.isNullable(selector)) throw new CssParsingException("", 0, EExceptionLevel.ERROR);

        this.selector = selector;


        AttributeSelectorProcessor processor = new AttributeSelectorProcessor();
        CharsReader reader = new CharsReader(selector);
        TokenExtractor<AttributeSelectorProcessor> extractor = new TokenExtractor<AttributeSelectorProcessor>(reader, processor);

        readName(extractor);
        readMatcher(extractor);
        int pos = reader.getPos(); // remember position after matcher, but before value
        readValue(extractor);

        if (matcher != 0 && value == null) // final check
            throw new CssParsingException("Attribute value not present in selector: " + selector, pos, EExceptionLevel.ERROR);

        specificity.addSelectorExplanation();
    }



    public void readName(final TokenExtractor<AttributeSelectorProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        try
        {
            attribute = extractor.extractToken(new FlowProcessorDetector()
            {
                public boolean canProcess()
                {
                    return extractor.getProcessor().isInAttributeName();
                }
            });
        }
        catch (IllegalStateException e)
        {
            throw new CssParsingException(e.getMessage(), extractor.getReader().getPos(), EExceptionLevel.ERROR);
        }

        if (LangUtils.isNullable(attribute))
            throw new CssParsingException("Name of attribute is empty in selector: " + selector, pos, EExceptionLevel.ERROR);
    }



    public void readMatcher(final TokenExtractor<AttributeSelectorProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        String m =  extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInAttributeMatcher();
            }
        });
        if ( m != null && !matcherPattern.matcher(m).matches() )
            throw new CssParsingException(String.format("Bad matcher format %s", m), pos, m.length(), EExceptionLevel.ERROR);

        matcher = m != null ? m.charAt(0) : 0;
    }



    public void readValue(final TokenExtractor<AttributeSelectorProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        value =  extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInAttributeValue();
            }
        });

        if (value != null)
        {
            if ( StringUtils.isStringLiteralCanUnquoted(value) ) value = StringUtils.getUnquotedStringLiteral(value);

            if ( value.isEmpty() )
                throw new CssParsingException("Attribute value is empty in selector: " + selector, pos, EExceptionLevel.ERROR);
        }
    }


    /**
     *
     * @return attribute of attribute selector
     */
    public String getAttribute()
    {
        return attribute;
    }

    /**
     *
     * @return a matcher operation for value of attribute
     */
    public char getMatcher()
    {
        return matcher;
    }

    /**
     *
     * @return a sample of corners of attribute
     */
    public String getValue()
    {
        return value;
    }

    /**
     *
     * @return <b>true</b>, if it is a class matcher [class~="test"]
     */
    public boolean isClassMatcher()
    {
        return matcher == '~' && attribute.equalsIgnoreCase("class");
    }

    /**
     * @return <b>true</b>, if it is a ID matcher
     */
    public boolean isIdMatcher()
    {
        return matcher == '=' && attribute.equalsIgnoreCase("id");
    }


    @Override
    public String toString()
    {
        return "[" + selector + "]";
    }

/*
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof AttributeSelector)) return false;
        if (!super.equals(o)) return false;

        AttributeSelector that = (AttributeSelector) o;

        if (matcher != that.matcher) return false;
        if (!attribute.equals(that.attribute)) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + attribute.hashCode();
        result = 31 * result + (int) matcher;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
*/

}
