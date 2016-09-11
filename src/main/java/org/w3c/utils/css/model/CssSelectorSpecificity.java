package org.w3c.utils.css.model;

/**
 * Calculator weight of selector (in original documentation as specificity).
 *
 * @See algorithm description at http://www.w3.org/TR/CSS2/cascade.html#specificity (6.4.3 Calculating a selector's specificity)
 *      and at http://www.w3.org/TR/css3-selectors/#specificity (9. Calculating a selector's specificity)
 * Created by Home on 29.11.2015.
 */
public class CssSelectorSpecificity implements Comparable<CssSelectorSpecificity>
{
    private int a = 0;
    private int b = 0;
    private int c = 0;

    public CssSelectorSpecificity()
    {
    }

    public CssSelectorSpecificity(CssSelectorSpecificity specificity)
    {
        this.a = specificity.a;
        this.b = specificity.b;
        this.c = specificity.c;
    }

    public void addIdSelector()
    {
        a++;
    }

    public void addSelectorExplanation()
    {
        b++;
    }

    public void addQualifier()
    {
        c++;
    }

    public void addSpecificity(CssSelectorSpecificity specificity)
    {
        this.a += specificity.a;
        this.b += specificity.b;
        this.c += specificity.c;
    }

    public int countOfIdSelectors()
    {
        return a;
    }

    public int countOfSelectorExplanations()
    {
        return b;
    }

    public int countOfQualifiers()
    {
        return c;
    }

    public int compareTo(CssSelectorSpecificity o)
    {
        int a = o.a - this.a;
        int b = o.b - this.b;
        int c = o.c - this.c;

        return a != 0 ? a : b != 0 ? b : c != 0 ? c : 0;
    }

    @Override
    public String toString()
    {
        return "specificity = 0," + a + "," + b + "," + c;
    }
}
