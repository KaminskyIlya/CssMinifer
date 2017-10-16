package org.w3c.utils.css.parsers;

import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.model.CssDocument;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.parsers.at.AtRuleParser;

/**
 * Css document parser.
 *
 * Created by Home on 17.08.2016.
 */
public class CssDocumentParser extends CssParser
{
    public CssDocumentParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    public void parse()
    {
        if (model == null) model = new CssDocument(); // create empty css document
        if (sheet == null) model.addPage(); // create a new empty stylesheet with default media type

        while (tokenizer.hasMoreTokens())
        {
            boolean processed  = processWhiteSpaces();
                    processed |= processAtRules();
                    processed |= processSelectorsList();
                    processed |= processUnknownToken();

            assert processed;
        }
    }


    private boolean processWhiteSpaces()
    {
        if ( !tokenizer.isStartWhitespace() ) return false;

        tokenizer.skipWhitespace(true);

        return true;
    }


    private boolean processSelectorsList()
    {
        if ( !tokenizer.isStartSelector() ) return false;

        CssParser parser = new SelectorsListParser(tokenizer);
        parser.setSheet(sheet);
        parser.parse();

        return true; // we processed it
    }


    private boolean processAtRules()
    {
        if ( !tokenizer.isStartAtRule() ) return false;

        CssParser parser = new AtRuleParser(tokenizer);
        parser.setModel(model);
        parser.setSheet(sheet);
        parser.parse();

        return true; // we processed it
    }


    private boolean processUnknownToken()
    {
        TextPosition start = getReader().getPosition();

        int length = 0;
        while ( tokenizer.hasMoreTokens() )
        {
            if ( !tokenizer.isStartWhitespace() && !tokenizer.isStartAtRule() && !tokenizer.isStartSelector() )
            {
                getReader().skip();
                length++;
            }
        }

        //TODO: создать специальный класс, он должен формировать сам такие сообщения (они все стандартизированы)
        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.ERROR);
        reporter.addMessage(String.format("Unrecognized token in line <%d> at <%d>:", start.getLine(), start.getCol()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), length, String.format("unexpected symbol%s here", length > 1 ? "s" : ""));
        reporter.report();

        return true; // we processed it
    }

}
