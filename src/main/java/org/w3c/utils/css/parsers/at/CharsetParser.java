package org.w3c.utils.css.parsers.at;

import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.parsers.CssParser;
import org.w3c.utils.css.parsers.CssTextTokenizer;
import org.w3c.utils.css.parsers.TextPosition;

import java.io.UnsupportedEncodingException;

/**
 * Created by Home on 19.08.2016.
 */
public class CharsetParser extends CssParser
{
    public CharsetParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        tokenizer.skipWhitespace(false);
        tokenizer.getReader().mark();

        if (tokenizer.isStartString())
        {
            changeCodePage(tokenizer.consumeString());
        }
        else
        {
            reportBadString();
        }
    }

    private void reportBadString()
    {
        TextPosition start = getReader().getPosition();
        tokenizer.gotoNextDeclaration();
        int length = getReader().readMarked().length();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.ERROR);
        reporter.addMessage(String.format("@charset rule syntax error in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), length, "expected string literal");
        reporter.report();
    }

    private void changeCodePage(String codePage)
    {
        try
        {
            getReader().setCharset(codePage);
        }
        catch (UnsupportedEncodingException e)
        {
            TextPosition start = getReader().getPosition();
            tokenizer.gotoNextDeclaration();

            Reporter reporter = new Reporter();
            reporter.setLevel(EExceptionLevel.ERROR);
            reporter.addMessage(String.format("Unsupported charset rule in line <%d>:", start.getLine()));
            reporter.addMessage("--------------------------------------------------");
            reporter.addMessage(getReader().getSourceLine(start.getLine()));
            reporter.addAccent(start.getCol(), codePage.length(), "this codepage is unsupported");
            reporter.report();
        }
    }
}
