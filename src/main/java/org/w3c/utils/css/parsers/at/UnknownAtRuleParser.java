package org.w3c.utils.css.parsers.at;

import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.parsers.CssParser;
import org.w3c.utils.css.parsers.CssTextTokenizer;
import org.w3c.utils.css.parsers.TextPosition;

/**
 * Unknown rule parser.
 * Only skip rule declaration.
 *
 * Created by Home on 23.08.2016.
 */
public class UnknownAtRuleParser extends CssParser
{
    public UnknownAtRuleParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        String name = getReader().readMarked();
        getReader().reset();
        TextPosition start = getReader().getPosition();
        tokenizer.gotoEndOfAtRule();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.WARN);
        reporter.addMessage(String.format("Unknown rule in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), name.length(), "rule declaration will not be optimized");
        reporter.report();
    }
}
