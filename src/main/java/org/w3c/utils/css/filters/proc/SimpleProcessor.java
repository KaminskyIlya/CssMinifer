package org.w3c.utils.css.filters.proc;

import org.w3c.utils.css.help.CharUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple flow text processor.
 * Is prototype for all subclasses.
 * Control state only for strings and escape chars.
 *
 *
 * Created by Home on 05.09.2016.
 */
public abstract class SimpleProcessor implements FlowProcessor
{
    private char quote = 0;

    private boolean inString = false; // if we are in string literal?
    private int escaped = 0; // count sequential of escaped chars
    protected boolean inWhitespace = false; // if we are in whitespace symbol?
    private char curChar = 0; // current char
    protected char prevChar = 0; // previous char

    public void reset()
    {
        quote = 0;
        inString = false;
        escaped = 0;
        curChar = 0;
        prevChar = 0;
    }

    public void before(char current)
    {
        if ( !inString && !isEscaped() && CharUtils.isQuote(current) )
        {
            inString = true;
            quote = current;
        }
        else if ( inString && !isEscaped() && current == quote )
        {
            quote = 0;
        }
        curChar = current;

        inWhitespace = !inString && CharUtils.isWhiteSpace(current);
    }

    public void after(char current)
    {
        if ( current == '\\' ) escaped++; else escaped = 0;
        prevChar = current;
        inString &= quote != 0;
    }

    public boolean canProcess()
    {
        return true;
    }

    /**
     * @return <b>true</b>, if we inside a string literal, <b>false</b> if outside.
     */
    public boolean isInString()
    {
        return inString;
    }

    /**
     * @return <b>true</b>, if this symbol escaped
     */
    public boolean isEscaped()
    {
        return escaped % 2 == 1;
    }

    /**
     * @return  <b>true</b>, if current symbol not inside a string literal, and was not escaped.
     */
    public boolean isNormal()
    {
        return !isInString() && !isEscaped() && curChar != 0;
    }

    /**
     * @return  <b>true</b>, if current symbol not inside a string literal, and was not escaped, and not is a whitespace.
     */
    public boolean isSymbol()
    {
        return isNormal() && !inWhitespace;
    }

    /**
     * @return <b>true</b>, if this symbol is whitespace and not inside a string literal
     */
    public boolean isInWhitespace()
    {
        return inWhitespace;
    }

    /**
     * @return previous processed symbol
     */
    public char getPreviousChar()
    {
        return prevChar;
    }

    /**
     * @return character what used for string literal quoting.
     * @throws IllegalStateException when processor not in string
     */
    public char getStringQuoteChar()
    {
        if (inString)
        {
            return quote;
        }
        throw new IllegalStateException("TextProcessor not in string literal.");
    }

    protected boolean in(String symbols, char symbol)
    {
        return symbols.contains(String.valueOf(symbol));
    }

    protected boolean not(String symbols, char symbol)
    {
        return !in(symbols, symbol);
    }

    protected boolean match(String regexp, char symbol)
    {
        Matcher matcher = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(symbol));
        return matcher.matches();
    }
}
