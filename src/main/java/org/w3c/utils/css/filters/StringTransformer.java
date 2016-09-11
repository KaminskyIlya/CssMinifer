package org.w3c.utils.css.filters;

import java.util.regex.Matcher;

/**
 * Created by Home on 08.11.2015.
 */
public interface StringTransformer
{
    String transform(String source, Matcher matcher);
}
