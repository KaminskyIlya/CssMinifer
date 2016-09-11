package org.w3c.utils.css.transform;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Home on 23.08.2016.
 */
public class CssDocumentTransformer
{
    private StringBuffer source;

    private Map<Integer, Token> tokenMap;
    private StringBuilder out;

    public CssDocumentTransformer(String source)
    {
        this.source = new StringBuffer(source);
    }

    public String getTransformed()
    {
        return out.toString();
    }

    public void transform()
    {
        tokenMap = new HashMap<Integer, Token>();
        out = new StringBuilder();


    }

}
