package org.w3c.utils.css.parsers;

import org.w3c.utils.css.help.CharUtils;
import org.w3c.utils.css.help.NumericUtils;
import org.w3c.utils.css.io.CssTextReader;
import org.w3c.utils.css.model.exceptions.CssTokenizerException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

/**
 * Css tokenizer.
 * Help to extractToken full css tokens.
 *
 * @See tokenize algorithm at https://www.w3.org/TR/css-syntax-3/#tokenization
 *
 * Created by Home on 18.08.2016.
 */
public class CssTextTokenizer
{
    private final CssTextReader reader;
    private boolean startProperty;

    public CssTextTokenizer(CssTextReader reader)
    {
        this.reader = reader;
    }

    public CssTextReader getReader()
    {
        return reader;
    }

    public boolean hasMoreTokens()
    {
        return reader.available();
    }

    public boolean isEOL()
    {
        return CharUtils.isEOL(reader.next());
    }

    public boolean isStartWhitespace()
    {
        return CharUtils.isWhiteSpace(reader.next());
    }

    /**
     * Test string token.
     * @return
     */
    public boolean isStartString()
    {
        return !reader.isEscaped() && CharUtils.isQuote(reader.next());
    }

    /**
     * Test number token.
     * @See rules at https://www.w3.org/TR/css-syntax-3/#check-if-three-code-points-would-start-a-number
     * @return
     */
    public boolean isStartNumber()
    {
        char c1 = reader.next(0);
        char c2 = reader.next(1);
        char c3 = reader.next(2);

        if ( CharUtils.isDigit(c1) ) return true;

        if ( c1 == '+' || c1 == '-' )
        {
            if ( CharUtils.isDigit(c2) ) return true;

            if ( c2 == '.' && CharUtils.isDigit(c3) ) return true;
        }

        return false;
    }

    /**
     * Test an identifier.
     * @See rules at https://www.w3.org/TR/css-syntax-3/#check-if-three-code-points-would-start-an-identifier
     * @return
     */
    public boolean isStartIdentifier()
    {
        char c1 = reader.next(0);
        char c2 = reader.next(1);

        return isNameStartCodePoint(c1) || c1 == '-' && isNameStartCodePoint(c2);
    }

    public boolean isStartFunction()
    {
        if (isStartIdentifier())
        {
            String name = consumeIdentifier();
            skipWhitespace(false);
            char next = reader.next();
            reader.reset();
            return next == '(';
        }
        return false;
    }

    /**
     * Test At-rule-token
     * @return
     */
    public boolean isStartAtRule()
    {
        return reader.next() == '@';
    }

    public boolean isStartUrl()
    {
        return !reader.isEscaped() && CharUtils.isURLStartChar(reader.next());
    }

    /**
     * Test start single selector or selector list
     *
     * @return
     */
    public boolean isStartSelector()
    {
        char n = reader.next();
        return (CharUtils.isLetter(n) || in("*|#.:[", n)) && !reader.isEscaped();
    }

    public boolean isStartBlock()
    {
        return reader.next() == '{' && !reader.isEscaped() && !reader.isInString();
    }

    public boolean isEndBlock()
    {
        return reader.next() == '}' && !reader.isEscaped() && !reader.isInString();
    }

    public boolean isEndDeclaration()
    {
        return reader.next() == ';' || isEOL();
    }

    public boolean isStartProperty()
    {
        reader.mark();
        boolean result = isStartIdentifier();

        if (result)
        {
            while (isStartIdentifier()) reader.next();

            skipWhitespace(false);

            result = reader.next() == ':';
        }

        reader.reset();
        return result;
    }













    public void skipWhitespace(boolean skipEoL)
    {
        while (CharUtils.isWhiteSpace(reader.next(), skipEoL))
        {
            reader.skip();
        }
    }

    public void gotoNextLine()
    {
        while ( reader.available() && !isEOL() )
        {
            reader.skip();
        }
        if ( reader.available() ) reader.skip();
    }

    public void gotoNextDeclaration()
    {
        while ( reader.available() && !isEndDeclaration() )
        {
            reader.skip();
        }
        if ( reader.available() ) reader.skip();
    }

    public void gotoEndOfAtRule()
    {
        consumeAtRuleHeader();
        if (isEndDeclaration()) reader.skip();
        if (isStartBlock()) consumeDeclarationsBlock();
    }













    public String consumeString()
    {
        reader.mark();
        reader.read();
        while ( reader.isInString() )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    /**
     * Consume a name
     * @See rules at https://www.w3.org/TR/css-syntax-3/#consume-a-name0
     * @return
     */
    public String consumeName()
    {
        reader.mark();
        while ( isNameCodePoint(reader.next()) )
        {
            reader.skip();
        }
        //if (!isStartWhitespace()) throw new CssParsingException("");
        return reader.readMarked();
    }

    /**
     * Consume identifier
     * @return
     */
    public String consumeIdentifier()
    {
        reader.mark();
        while ( isIdentifierCodePoint(reader.next()) )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    public String consumeFunction()
    {
        reader.mark();
        while ( reader.available() && !in(";}", reader.next()) && !reader.isEscaped() )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    /**
     * Consume a number.
     * Call this only after isStartNumber()
     * @See rules at https://www.w3.org/TR/css-syntax-3/#consume-a-number0
     * @return
     */
    public String consumeNumber()
    {
        reader.mark();
        while ( in("+-0123456789.eE", reader.next()) )
        {
            reader.skip();
        }
        String sample = reader.readMarked();

        if ( NumericUtils.isNumber(sample) )
        {
            return sample;
        }
        else
        {
            throw new CssTokenizerException("Invalid number " + sample, reader, EExceptionLevel.ERROR);
        }
    }

    /**
     * Consume @-rule.
     * Call this only after isStartAtRule
     * @See rules at
     *
     * @return
     */
    public String consumeAtRuleName()
    {
        reader.skip(); // skip @ symbol
        return consumeIdentifier();
    }

    public String consumeAtRuleHeader()
    {
        reader.mark();
        while ( reader.available() && (notIn(";{", reader.next()) || reader.isEscaped() || reader.isInString()) )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    /**
     * Consume selector list.
     * @return
     */
    public String consumeSelectorList()
    {
        reader.mark();
        while ( reader.available() && !isStartBlock() )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    public String consumeUrl()
    {
        reader.mark();
        while ( reader.available() && (reader.isInString() || !isStartWhitespace()) )
        {
            reader.skip();
        }
        return reader.readMarked();
    }

    /**
     * Consume declarations block {}
     */
    public String consumeDeclarationsBlock()
    {
        reader.mark();
        while ( reader.available() && (reader.next() != '}' || reader.isEscaped() || reader.isInString()) )
        {
            reader.skip();
        }
        return reader.readMarked();
    }






    private boolean isNameStartCodePoint(char c)
    {
        return CharUtils.isLetter(c) || CharUtils.isNonASCII(c) || c == '_';
    }

    private boolean isNameCodePoint(char c)
    {
        return isNameStartCodePoint(c) || (reader.isEscaped() && !isEOL());
    }

    private boolean isIdentifierCodePoint(char c)
    {
        return isNameCodePoint(c) || c == '-';
    }

    protected boolean in(String symbols, char symbol)
    {
        return symbols.contains(String.valueOf(symbol));
    }

    protected boolean notIn(String symbols, char symbol)
    {
        return !in(symbols, symbol);
    }
}
