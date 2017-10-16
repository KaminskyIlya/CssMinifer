package org.w3c.utils.css.parsers;

import org.w3c.utils.css.model.CssModel;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.values.*;

/**
 * Created by k002 on 12.10.2017.
 */
public class ValueParser extends CssParser
{
    public ValueParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CssModel parseModel()
    {
        if ( tokenizer.isStartNumber() )
        {
            return extractNumericValue(tokenizer);
        }

        if ( tokenizer.isStartIdentifier() )
        {
            return new KeywordValue(tokenizer.consumeIdentifier());
        }

        if ( tokenizer.isStartHash() )
        {
            return new HashValue(tokenizer.consumeHash());
        }

        if ( tokenizer.isStartFunction() )
        {
            return extractFunctionalValue(tokenizer);
        }

        throw new CssParsingException("Unknown token", tokenizer.getReader().getPos()); //TODO: Make unknown token error.
    }

    /**
     * Extract functional value like as 'url(...)', 'linear-gradient(...), 'translate(...)'
     *
     * @param tokenizer
     * @return
     */
    private CssModel extractFunctionalValue(CssTextTokenizer tokenizer)
    {
        FunctionalValue value = new FunctionalValue();
        value.setName(tokenizer.consumeIdentifier());

        tokenizer.skipWhitespace(false);
        tokenizer.getReader().skip(); // skip '('

        while ( tokenizer.hasMoreTokens()  )
        {
            if ( tokenizer.isClosedParentheses() ) break;
            tokenizer.skipWhitespace(false);

            if ( tokenizer.getReader().next() == ',' ) continue;

            //String argument = consumeFunctionArgument(tokenizer);
            //value.addArgument();
        }

        return value;
    }

    /**
     * Extract numeric value from source text like as '1', '2%', '1em' '.5ch', '-.5em', '+6px', '+6.7mm', '+.6e-2cm'
     *
     * @param tokenizer
     * @return unit value with number and unit or only number.
     */
    private Value extractNumericValue(CssTextTokenizer tokenizer)
    {
        UnitValue value = new UnitValue();

        String number = tokenizer.consumeNumber();
        try
        {
            value.setValue(Float.valueOf(number));
        }
        catch (NumberFormatException e)
        {
            throw new CssParsingException("Number parsing error for " + number, getReader().getPos() - number.length(), number.length(), EExceptionLevel.WARN);
        }

        if ( tokenizer.isStartWhitespace() )
        {
            return value;
        }
        else if ( tokenizer.isStartIdentifier() )
        {
            value.setUnit(tokenizer.consumeIdentifier());
            return value;
        }
        else
            throw new CssParsingException("Unknown token", tokenizer.getReader().getPos()); //TODO:

    }


}
