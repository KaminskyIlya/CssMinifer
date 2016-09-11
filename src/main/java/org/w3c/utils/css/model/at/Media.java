package org.w3c.utils.css.model.at;

import org.w3c.utils.css.enums.AtRules;
import org.w3c.utils.css.enums.MediaType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Css media at-rule.
 * http://www.w3.org/TR/css3-mediaqueries/
 * @media [only|not] [screen|all|...|print] and (expression) { ... }
 *
 * Created by Home on 20.11.2015.
 */
public class Media extends AtRule
{
    private static final Pattern componentValuePattern = Pattern.compile("@media[ ]*( only | not )?([a-z]+ ?)?(and )?(\\(.*\\))?");

    private final MediaType mediaType;
    private final String prefix;
    private final String join;
    private final String query;

    public Media()
    {
        this(MediaType.all.toString());
    }

    public Media(String componentValue)
    {
        super(componentValue);
        mediaType = extractMediaTypeFrom(componentValue);
        prefix = extractMediaPrefixFrom(componentValue);
        join = extractJoinWithMediaTypeFrom(componentValue);
        query = extractExpressionForm(componentValue);
    }

    private String extractExpressionForm(String componentValue)
    {
        Matcher matcher = componentValuePattern.matcher(componentValue);
        String s = matcher.matches() ? matcher.group(4) : null;
        return s != null ? s.trim() : "";
    }

    private String extractJoinWithMediaTypeFrom(String componentValue)
    {
        Matcher matcher = componentValuePattern.matcher(componentValue);
        String s = matcher.matches() ? matcher.group(3) : null;
        return s != null ? s.trim() : "";
    }

    private String extractMediaPrefixFrom(String componentValue)
    {
        Matcher matcher = componentValuePattern.matcher(componentValue);
        String s = matcher.matches() ? matcher.group(1) : null;
        return s != null ? s.trim() : "";
    }

    private MediaType extractMediaTypeFrom(String componentValue)
    {
        Matcher matcher = componentValuePattern.matcher(componentValue);
        String media = matcher.matches() ? matcher.group(2) : null;
        return media != null ? MediaType.valueOf(media.trim()) : MediaType.all;
    }

    @Override
    public AtRules getToken()
    {
        return AtRules.MEDIA;
    }

    public MediaType getMediaType()
    {
        return mediaType;
    }

    public String getQuery()
    {
        return query;
    }

    @Override
    public String toString()
    {
        //return super.toString() + (prefix != null ? " " + prefix : "") + mediaType + " " + join + (query != null ? " " + query : "");
        return getComponentValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Media media = (Media) o;

        if (mediaType != media.mediaType) return false;
        if (!prefix.equals(media.prefix)) return false;
        if (!join.equals(media.join)) return false;
        return !(query != null ? !query.equals(media.query) : media.query != null);

    }

    @Override
    public int hashCode() {
        int result = mediaType.hashCode();
        result = 31 * result + prefix.hashCode();
        result = 31 * result + join.hashCode();
        result = 31 * result + (query != null ? query.hashCode() : 0);
        return result;
    }
}
