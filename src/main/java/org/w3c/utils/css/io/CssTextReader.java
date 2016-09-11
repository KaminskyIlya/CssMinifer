package org.w3c.utils.css.io;

import org.w3c.utils.css.help.CharUtils;
import org.w3c.utils.css.model.exceptions.CssTokenizerException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

import java.util.Arrays;

/**
 * CSS text reader.
 * Help read unicode symbols, escape symbols, whitespaces, block comments, and CDO/CDC blocks
 *
 * @See tokenization algorithm at https://www.w3.org/TR/css-syntax-3/#tokenization
 *
 * Created by Home on 17.08.2016.
 */
public class CssTextReader extends SourceTextReader
{
    private char quote = 0; // last quote symbol (then we are in string)
    //private boolean escaped = true;

    private boolean marked = false;
    private char buffer[] = new char[32];
    private int bufpos = 0;

    /**
     * Build css text reader from string.
     * Internal using only (due test).
     *
     * @param source string
     */
    public CssTextReader(String source)
    {
        super(source);
    }

    /**
     * Default constructor.
     * Build css text reader from bytes buffer.
     *
     * @param source bytes buffer
     */
    public CssTextReader(byte[] source)
    {
        super(source);
    }

    @Override
    public char read()
    {
        char c = processChar();

        markCurrentChar(c);

        if (available() && c == 0) c = read();

        return c;
    }

    public void markCurrentChar(char c)
    {
        if (marked && c != 0)
        {
            if (bufpos == buffer.length)
            {
                char[] newbuf = new char[buffer.length * 2];
                System.arraycopy(buffer, 0, newbuf, 0, bufpos);
                buffer = newbuf;
            }
            buffer[bufpos++] = c;
        }
    }

    private char processChar()
    {
        char c = super.read();
        char n = super.next();

        // U+00002B u+35
        if (isUnicodeRangeToken(c, n)) // probably need to cancel (delete) this code
        {
            return getUnicodeChar();
        }
        // &#245; - unicode symbols

        if ( isEscape(c) )
        {
            return isEscaped() ? '\\' : getEscapeChar();
        }
        if ( isEscaped() )
        {
            return c; // as is
        }
        if ( CharUtils.isQuote(c) || isInString() )
        {
            return getStringChar(c);
        }
        if ( CharUtils.isEOL(c) )
        {
            return CharUtils.EOL;
        }
        if ( CharUtils.isBlockCommentStart(c, n) ) // && !inString() = true
        {
            return skipCommentBlock();
        }
        if ( isCDOToken(c, n) ) // && !inString() = true
        {
            return skipCDOToken();
        }

        return c;
    }

    public boolean isInString()
    {
        return quote != 0;
    }

    public void skip()
    {
        read();
    }

    @Override
    public void mark()
    {
        //super.mark();
        marked = true;
        bufpos = 0;
    }

    @Override
    public void reset()
    {
        //super.reset();
        marked = false;
    }

    @Override
    public String readMarked()
    {
        marked = false;
        return new String(Arrays.copyOf(buffer, bufpos));
    }







    private boolean isUnicodeRangeToken(char c, char n)
    {
        return (c == 'U' || c == 'u') && n == '+';
    }

    private boolean isEscape(char c)
    {
        return c == '\\';
    }

    private boolean isCDOToken(char c, char n)
    {
        return c == '<' && n == '!';
    }




    
    private char getUnicodeChar()
    {
        char n;
        super.mark(); // mark reader position

        do
        {
            super.read();
            n = next();
        }
        while (CharUtils.isHexDigit(n) || n == '-' || n == '?');


        String unicode = super.readMarked();
        try
        {
            return CharUtils.getCharForUnicodeRange("U" + unicode);
        }
        catch (IllegalArgumentException e)
        {
            throw new CssTokenizerException(e.getMessage(), this, EExceptionLevel.ERROR);
        }
    }

    /**
     * @See rules at https://www.w3.org/TR/css-syntax-3/#consume-an-escaped-code-point0
     * @return
     */
    private char getEscapeChar()
    {
        char n = next();

        // Test EOF
        if (!available()) return 0xfffd;

        // Test escaped newline
        if (CharUtils.isEOL(n))
        {
            if (!isInString()) throw new CssTokenizerException("Invalid escape symbol \\", this, EExceptionLevel.WARN);
            super.skip(); // skip escaped new line
            return 0;
        }

        // Test hexadecimal code
        if ( CharUtils.isHexDigit(n) )
        {
            super.mark();
            int counter = 0;
            while ( available() && CharUtils.isHexDigit(next()) && counter++ < 6 )
            {
                super.skip();
            }

            String code = super.readMarked();
            if (code.length() < 6 && CharUtils.isWhiteSpace(next())) super.skip();

            try
            {
                return CharUtils.getCharForUnicodeRange("U+" + code);
            }
            catch (IllegalArgumentException e)
            {
                throw new CssTokenizerException(e.getMessage(), this, EExceptionLevel.ERROR);
            }
        }

        return '\\'; // return escape symbol \ as is
    }

    /**
     *
     */
    private char getStringChar(char c)
    {
        if ( !isEscaped() && CharUtils.isQuote(c) )
        {
            quote = (quote == 0) ? c : (quote == c ? 0 : quote);
        }
        return c;
    }

    /**
     *
     */
    private char skipCDOToken()
    {
        // CDO token validation
        super.mark();

        char c1 = super.read(); // !  must be, skip that
        char c2 = super.read(); // -  expect
        char c3 = super.read(); // -  expect

        if (c1 != '!' || c2 != '-' || c3 != '-')
        {
            super.reset();
            return '<'; // it is not a valid CDO token, skip that
        }

        // search CDC token
        super.mark();
        do
        {
            if ( !available() ) throw new CssTokenizerException("Unclosed CDO-token <!--. Expected --> but founded EOF.", this, EExceptionLevel.CRITICAL);

            super.reset();
            c1 = super.read();
            super.mark();
            c2 = super.read();
            c3 = super.read();
        }
        while ( !(c1 == '-' && c2 == '-' && c3 == '>') );

        return ' ';
    }

    /**
     *
     */
    private char skipCommentBlock()
    {
        while (available())
        {
            char c = super.read(); //  *
            char n = super.next(); //  /

            if (CharUtils.isBlockCommendEnd(c, n))
            {
                super.skip(); // skip symbols '/'
                return ' ';
            }
        }
        throw new CssTokenizerException("Unclosed comment block /*. Expected */ but founded EOF.", this, EExceptionLevel.CRITICAL);
    }

}
