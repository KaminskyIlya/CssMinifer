package org.w3c.utils.css.parsers.at;

import org.w3c.utils.css.enums.AtRules;
import org.w3c.utils.css.help.AnyUtils;
import org.w3c.utils.css.parsers.CssParser;
import org.w3c.utils.css.parsers.CssTextTokenizer;

/**
 * Created by Home on 24.08.2016.
 */
public class AtRuleParser extends CssParser
{
    public AtRuleParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        String name = AnyUtils.removeBrowserPrefix(tokenizer.consumeAtRuleName());
        AtRules rule = AnyUtils.getKeywordByName(AtRules.class, name);
        CssParser parser = getCssParserFor(tokenizer, rule);

        parser.setModel(getModel());
        parser.setSheet(getSheet());
        //parser.setSelectors(getSelectorsList());
        parser.parse();
    }

    private CssParser getCssParserFor(CssTextTokenizer tokenizer, AtRules rule)
    {
        switch (rule)
        {
            case MEDIA:
                return new MediaQueryParser(tokenizer);
            case FONT_FACE:
                return new FontFaceParser(tokenizer);
            case KEYFRAMES:
                return new KeyframesParser(tokenizer);
            case PAGE:
                return new PrinterPageParser(tokenizer);
            case CHARSET:
                return new CharsetParser(tokenizer);
            case IMPORT:
                return new ImportParser(tokenizer);
            default:
                return new UnknownAtRuleParser(tokenizer);
        }
    }
}
