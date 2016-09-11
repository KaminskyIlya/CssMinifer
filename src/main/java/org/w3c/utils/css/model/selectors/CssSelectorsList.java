package org.w3c.utils.css.model.selectors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.processors.ReaderTokenizer;
import org.w3c.utils.css.model.CssDeclarationsList;
import org.w3c.utils.css.model.exceptions.CssParsingException;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * CSS selector representation.
 * p.article:nth-child(even) ~ font[color="red"], p.footer::first-line + span
 *
 * TODO: need test
 * нужно использовать powermock - который умеет подставлять свои классы.
 * самая главная задача тестирования - проверить, что класс CssSelector получает нужные строки,
 * и появляется в списке selectors; порядок их сохраняется
 *
 * Created by Home on 29.11.2015.
 */
public class CssSelectorsList
{
    private Set<CssSelector> selectors = new LinkedHashSet<CssSelector>();
    private Set<CssDeclarationsList> declarations = new LinkedHashSet<CssDeclarationsList>();

    public CssSelectorsList(String selectorList)
    {
        ReaderTokenizer tokenizer = new ReaderTokenizer(new CharsReader(selectorList));
        FlowProcessor processor = new SingleSelectorProcessor();

        String token;
        int pos = 0;
        while ((token = tokenizer.nextNotEmptyToken(",", processor)) != null)
        {
            addSelector(token.trim(), pos);

            pos = tokenizer.getPos();
        }
    }

    public void addSelector(String token, int pos)
    {
        CssSelector selector = new CssSelector(token);
        try
        {
            selector.analyze();
        }
        catch (CssParsingException e)
        {
            throw new CssParsingException(e.getMessage(), pos + e.getPosition(), e.getLevel());
        }
        selectors.add(selector);
    }

    public Set<CssSelector> getSelectors()
    {
        return selectors;
    }

    public Set<CssDeclarationsList> getDeclarations()
    {
        return declarations;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CssSelectorsList)) return false;
        if (!super.equals(o)) return false;

        CssSelectorsList that = (CssSelectorsList) o;

        return selectors.equals(that.selectors);

    }

    @Override
    public int hashCode()
    {
        return selectors.hashCode();
    }


    private static class SingleSelectorProcessor extends SelectorProcessor
    {
        @Override
        public boolean canProcess()
        {
            return !(isInConjunction() || isInQualifier() || isInParenthesis() || isInAttr() || isInPseudoClass() || isInString() || isEscaped());
        }
    }
}
