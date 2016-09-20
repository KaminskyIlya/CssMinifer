package org.w3c.utils.css.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Library for helpers functions.
 *
 * Created by Home on 03.11.2015.
 */
public final class CharUtils
{
    public static final char EOL = '\n';
    public static final Pattern UNICODE_RANGE = Pattern.compile("^U\\+(([0-9a-f]{1,6})[0|?]{0,6})(\\-[0-9a-f]{1,6})?", Pattern.CASE_INSENSITIVE);

    private CharUtils()
    {
        throw new UnsupportedOperationException("This is a static final class");
    }


    public static boolean isInlineCommentStart(char current, char next)
    {
        return current == '/' && next == '/';
    }

    public static boolean isEOL(char c)
    {
        return c == '\n' || c == '\r' || c == '\f';
    }

    public static boolean isUnixEOL(char c)
    {
        return c == '\n';
    }

    public static boolean isMacEOL(char c)
    {
        return c == '\r';
    }

    public static boolean isWindowsEOL(char c, char n)
    {
        return c == '\r' && n == '\n';
    }

    public static boolean isBlockCommentStart(char current, char next)
    {
        return (current == '/' && next == '*');
    }

    public static boolean isBlockCommendEnd(char current, char next)
    {
        return (current == '*' && next == '/');
    }

    public static boolean isWhiteSpace(char c)
    {
        return isWhiteSpace(c, true);
    }

    public static boolean isWhiteSpace(char c, boolean withEoL)
    {
        if (withEoL)
        {
            return c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f';
        }
        else
        {
            return c == ' ' || c == '\t';
        }
    }

    public static boolean isQuote(char c)
    {
        return c == '"' || c == '\'';
    }

    public static boolean isHexDigit(char c)
    {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    /**
     * Convert hexadecimal representation of unicode symbol to character.
     * See algorithm at https://www.w3.org/TR/css-syntax-3/#consume-a-unicode-range-token
     *
     * @param unicode hexadecimal representation of unicode symbol
     * @return equivalent symbol character or surrogate character
     * @throws IllegalArgumentException if unicode has out of range 16-bit and also isn't a surrogate pair
     */
    public static char getCharForUnicodeRange(String unicode)
    {
        Matcher matcher = UNICODE_RANGE.matcher(unicode);
        if ( !matcher.matches() ) throw new IllegalArgumentException("Invalid unicode fragment " + unicode);

        String left = matcher.group(1); // left part of unicode range
        String start; // start of unicode range
        String end; // end of unicode range

        if ( left.length() > 6 ) throw new IllegalArgumentException("Invalid unicode value length " + unicode);

        if (matcher.groupCount() == 4)
        {
            if (left.endsWith("?")) throw new IllegalArgumentException("Invalid unicode range " + unicode);

            start = left;

            String right = matcher.group(3);
            if ( right.length() > 6 ) throw new IllegalArgumentException("Invalid unicode value length " + unicode);
        }
        else
        {
            start = left.replaceAll("\\?", "0");
            //end = left.replaceAll("\\?", "f"); // not used
        }

        Long code = Long.valueOf(start, 16);
        char hi = (char)(code >> 16);
        char lo = (char)(code & 0xffff);

        if (hi == 0 && lo == 0) return 0xfffd;
        if (hi == 0) return lo;

        return (char) (Character.isSurrogatePair(hi, lo) ? 0xfffd : Character.toCodePoint(hi, lo));
    }

    public static boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }

    public static boolean isLetter(char c)
    {
        return isUppercaseLetter(c) || isLowercaseLetter(c);
    }

    public static boolean isLowercaseLetter(char c)
    {
        return 'a' <= c && c <= 'z';
    }

    public static boolean isUppercaseLetter(char c)
    {
        return 'A' <= c && c <= 'Z';
    }

    public static boolean isNonASCII(char c)
    {
        return c > 0x80;
    }

    public static boolean isURLStartChar(char c)
    {
        return isLetter(c) || isDigit(c) || c == '/' || c == '.' || c == '_';
    }

    //TODO: test it
    public static boolean isNmChar(char c)
    {
        return c == '-' || isNmStart(c);
    }

    //TODO: test it
    public static boolean isNmStart(char c)
    {
        return isLetter(c) || isDigit(c) || isNonASCII(c) || c == '_';
    }

    //TODO: test it
    public static boolean isIdentChar(char c)
    {
        return isNmChar(c);
    }
}
