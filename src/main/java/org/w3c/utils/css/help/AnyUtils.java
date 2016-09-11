package org.w3c.utils.css.help;

import org.w3c.utils.css.enums.BrowserPrefixes;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Temporary name for storage class of any helper functions.
 *
 * Created by Home on 22.11.2015.
 */
public final class AnyUtils
{
    // TODO: need to strongly test (already tested in ColorUtilsTest)
    public static <E extends Enum<E>> String findKeywordIn(String source, EnumSet<E> keywords)
    {
        Matcher matcher;

        for (Enum<E> keyword : keywords)
        {
            Pattern regexp = Pattern.compile("(^|[^a-z])(" + keyword.toString() + ")([^\\a-z]|$)", Pattern.CASE_INSENSITIVE);
            matcher = regexp.matcher(source);
            if (matcher.find()) return matcher.group(2);
        }

        return null;
    }

    public static <E extends Enum<E>> E getKeywordByName(Class<E> keywords, String name)
    {
        try
        {
            return Enum.valueOf(keywords, name.toUpperCase().replaceAll("-", "_"));
        }
        catch(IllegalArgumentException e)
        {
            return null;
        }
    }

    public static <E extends Enum<E>> String cssEnum2cssName(Enum<E> keyword)
    {
        return keyword.name().toLowerCase().replaceAll("_", "-");
    }

    public static String getPropertyNameFrom(String declaration)
    {
        int i = declaration.indexOf(":");
        return i > 0 ? declaration.substring(0, i).trim() : null;
    }

    public static String getPropertyValueFrom(String declaration)
    {
        int i = declaration.indexOf(":");
        return i > 0 ? declaration.substring(0, i+1).trim() : null;
    }

    public static boolean isEquals(Object a, Object b)
    {
        return a != null && a.equals(b);
    }

    public static boolean checkURL(String url)
    {
        throw new UnsupportedOperationException();
    }

    public static String getUrlFromExpression(String url)
    {
        return url.replaceFirst("^(url\\()?['\\\"]?", "").replaceFirst("['\\\"]?(\\))?$", "");
    }

    public static String removeBrowserPrefix(String identifier)
    {
        for (BrowserPrefixes prefix : BrowserPrefixes.values())
        {
            String p = prefix.toString();
            if ( identifier.startsWith(p) )
            {
                return identifier.substring(p.length());
            }
        }
        return identifier;
    }
}
