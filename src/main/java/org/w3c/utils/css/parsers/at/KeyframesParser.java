package org.w3c.utils.css.parsers.at;

import org.w3c.utils.css.parsers.CssParser;
import org.w3c.utils.css.parsers.CssTextTokenizer;

/**
 * Created by Home on 19.08.2016.
 */
public class KeyframesParser extends CssParser
{
    public KeyframesParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        throw new UnsupportedOperationException();
    }
}
