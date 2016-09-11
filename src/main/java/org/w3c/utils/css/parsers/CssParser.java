package org.w3c.utils.css.parsers;

import org.w3c.utils.css.io.CssTextReader;
import org.w3c.utils.css.model.CssDocument;
import org.w3c.utils.css.model.CssPage;
import org.w3c.utils.css.model.selectors.CssSelectorsList;

/**
 * Created by Home on 18.08.2016.
 */
public abstract class CssParser
{
    protected CssTextTokenizer tokenizer;
    protected CssDocument model; // document model
    protected CssPage sheet; // current style sheet
    private CssSelectorsList selectors; // current selectors block

    public CssParser(CssTextTokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
    }

    public abstract void parse();

    public CssDocument getModel()
    {
        return model;
    }

    public void setModel(CssDocument model)
    {
        this.model = model;
    }

    public void setSheet(CssPage sheet)
    {
        this.sheet = sheet;
    }

    public CssPage getSheet()
    {
        return sheet;
    }

    public CssTextReader getReader()
    {
        return tokenizer.getReader();
    }

    public void setSelectors(CssSelectorsList selectors)
    {
        this.selectors = selectors;
    }

    public CssSelectorsList getSelectors()
    {
        return selectors;
    }
}
