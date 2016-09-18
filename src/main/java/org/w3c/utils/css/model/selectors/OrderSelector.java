package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Order selector.
 * Model for expression nth-child(an+b)
 *
 * Created by Home on 17.09.2016.
 */
public class OrderSelector extends PseudoSelector
{
    private int a = 0;
    private int b = 0;

    /**
     * Constructor for order selector.
     *
     * @param selector expression nth-child(2n+1)
     */
    public OrderSelector(String selector)
    {
        super(selector);
        init();
    }

    private void init()
    {
        int pos = selector.indexOf("(");
        this.name = selector.substring(0, pos).trim().toLowerCase();
        this.selector = selector.substring(pos+1, selector.length()-1).trim();
    }

    public int getA()
    {
        return a;
    }

    public int getB()
    {
        return b;
    }

    // See grammar at https://www.w3.org/TR/css3-selectors/#nth-child-pseudo
    private static final Pattern AnB = Pattern.compile("([+-])?(\\d+)n(\\s*([+-])?\\s*(\\d+))?");
    private static final Pattern B = Pattern.compile("([+-])?(\\d+)");

    /**
     * Analysing order expression
     *
     * @throws CssParsingException
     */
    void analyze()
    {
        if (selector.equalsIgnoreCase("odd"))
        {
            a = 2; b = 1; return;
        }
        if (selector.equalsIgnoreCase("even"))
        {
            a = 2; b = 0; return;
        }
        Matcher matcher;

        matcher = AnB.matcher(selector);
        if (matcher.matches())
        {
            a = getNum(matcher.group(1), matcher.group(2));
            b = getNum(matcher.group(4), matcher.group(5));
            return;
        }

        matcher = B.matcher(selector);
        if (matcher.matches())
        {
            b = getNum(matcher.group(1), matcher.group(2));
            return;
        }

        throw new CssParsingException("Bad order expression " + selector, 0, EExceptionLevel.ERROR);
    }

    private int getNum(String sign, String digits)
    {
        if (digits == null) return 0;

        int result = Integer.valueOf(digits);
        return "-".equals(sign) ? -result : result;
    }
}
