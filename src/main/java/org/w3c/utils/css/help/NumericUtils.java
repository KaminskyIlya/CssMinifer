package org.w3c.utils.css.help;

import org.w3c.utils.css.enums.LengthType;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Numeric work library.
 *
 * Created by Home on 09.11.2015.
 */
public final class NumericUtils
{
    public static final Pattern INTEGER_NUMBER = Pattern.compile("[+|\\-]?\\d+(e[+|\\-]?\\d+)?", Pattern.CASE_INSENSITIVE);
    public static final Pattern FLOAT_NUMBER_WITH_INTEGER_PART = Pattern.compile("[+|\\-]?\\d+(\\.\\d+(e[+|\\-]?\\d+)?)?", Pattern.CASE_INSENSITIVE);
    public static final Pattern FLOAT_NUMBER_ONLY_FRACTIONAL = Pattern.compile("[+|\\-]?\\.\\d+(e[+|\\-]?\\d+)?", Pattern.CASE_INSENSITIVE);

    public static final Pattern FLOAT_NUMBER = Pattern.compile("((([-|+]?)(\\d+)\\.(\\d+))(e([+|\\-]?\\d+))?)", Pattern.CASE_INSENSITIVE);
    public static final Pattern BLOATED_BIG_NUMBER = Pattern.compile("([^\\d])?([1-9])(0{3,})");
    public static final Pattern BLOATED_LOW_NUMBER = Pattern.compile("([^\\d])?0?\\.(0{2,})([1-9]+)");


    public static String removeLeadingZero(String source)
    {
        Matcher m = FLOAT_NUMBER.matcher(source);
        if (m.matches())
        {
            if (m.group(4).equals("0")) return m.group(3) + "." + m.group(5) + ((m.group(6) != null) ? m.group(6) : "");
        }
        return source;
    }

    public static String lossPrecision(String source, int accuracyScale)
    {
        if (!source.contains(".")) return source;

        Double d = Double.valueOf(source);

        int f = 1;
        for (int i = 0; i < accuracyScale; i++) { f *= 10; }

        d = ((double)Math.round(d * f) / f);
        String result = (Math.abs(d - d.intValue()) < 1.0d/f) ? String.valueOf(d.intValue()) : d.toString();

        f = result.indexOf('.');
        if (result.length() - f - 1 > accuracyScale) result = result.substring(0, f + accuracyScale + 1);

        return result;
    }

    public static String compactBloatedBigNumber(String source)
    {
        Matcher m = BLOATED_BIG_NUMBER.matcher(source);
        if (m.matches())
        {
            return (m.group(1) != null ? m.group(1) : "") + m.group(2) + "e" + m.group(3).length();
        }
        return source;
    }

    public static String compactBloatedLowNumber(String source)
    {
        Matcher m = BLOATED_LOW_NUMBER.matcher(source);
        if (m.matches())
        {
            return (m.group(1) != null ? m.group(1) : "") + m.group(3) + "e-" + (m.group(2).length()+1);
        }
        return source;
    }

    public static String format(float number)
    {
        String result = Float.toString(number);
        return result.replaceAll("(\\.[^0])?0+$", "$1").replaceAll("\\.$", "");
    }

    public static String format(double number)
    {
        String result = Double.toString(number);
        return result.replaceAll("(\\.[^0])?0+$", "$1").replaceAll("\\.$", "");
    }

    public static boolean isNumber(String sample)
    {
        return INTEGER_NUMBER.matcher(sample).matches()
                || FLOAT_NUMBER_ONLY_FRACTIONAL.matcher(sample).matches()
                || FLOAT_NUMBER_WITH_INTEGER_PART.matcher(sample).matches();
    }

    public static String extractNumberFrom(String source, EnumSet<LengthType> keywords)
    {
        Matcher matcher;

        for (Enum<LengthType> keyword : keywords)
        {
            Pattern regexp = Pattern.compile(FLOAT_NUMBER.pattern() + "\\s*(" + keyword + ")([^a-z]|$)", Pattern.CASE_INSENSITIVE);
            matcher = regexp.matcher(source);
            if (matcher.find()) return matcher.group(1) + matcher.group(8);
        }

        return null;
    }
}
