package org.w3c.utils.css.help;

import java.util.regex.Pattern;

/**
 * String utilities helper class.
 *
 * Created by Home on 15.09.2016.
 */
public final class StringUtils
{
    private StringUtils()
    {
        throw new UnsupportedOperationException("This is a static final class");
    }


    /**
     * Remove in css string literal all escaped new lines \n \r \f
     * @param literal css string literal
     * @return solid string
     */
    public static String getSolidCssStringLiteral(String literal)
    {
        return literal.replaceAll("\\\\([\\n\\r\\f]||(\\r\\n))", "");
    }

    /**
     * Remove quotes in string literal
     *
     * @param literal string literal
     * @return unquoted string
     */
    public static String getUnquotedStringLiteral(String literal)
    {
        return literal.replaceFirst("^['|\"]", "").replaceFirst("['|\"]$", "");
    }

    /**
     * Test what this css literal can be unquoted.
     *
     * @param literal source css literal
     * @return true, if this literal may be written without quotes
     */
    public static boolean isStringLiteralCanUnquoted(String literal)
    {
        return !Pattern.compile("\\s").matcher(literal).find();
    }

    /**
     * Find in string the specified char and return it's position.
     * Skip escaped chars.
     *
     * @param source source string
     * @param searched specified char
     * @return position of searched char
     */
    public static int findNotEscapedCharPos(String source, char searched)
    {
        int escapes = 0;
        for (int i = 0, n = source.length(); i < n; i++)
        {
            char c = source.charAt(i);
            if (c == '\\') escapes++;
            boolean escaped = (escapes & 1) == 1;
            if ( !escaped && c == searched ) return i;
        }
        return -1;
    }
}
