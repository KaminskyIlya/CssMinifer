package org.w3c.utils.css.enums;

import org.w3c.utils.css.help.AnyUtils;

/**
 * Css @-Rules enumeration.
 * @See enum declaration at
 *
 * Created by Home on 12.11.2015.
 */
public enum AtRules
{
    MEDIA,
    FONT_FACE,
    IMPORT,
    KEYFRAMES,
    PAGE,
    CHARSET;

    @Override
    public String toString()
    {
        return AnyUtils.cssEnum2cssName(this);
    }
}
