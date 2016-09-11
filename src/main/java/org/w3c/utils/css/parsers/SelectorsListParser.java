package org.w3c.utils.css.parsers;

import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.model.exceptions.CriticalParserException;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.selectors.CssSelectorsList;

/**
 * Selectors list parser.
 * Process this tokens:  p.article:nth-child(even) ~ font[color="red"], p.footer::first-line + span, h1 > p ~ em
 *
 * Created by Home on 19.08.2016.
 */
public class SelectorsListParser extends CssParser
{
    public SelectorsListParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        if ( !processSelectorsList() ) return;

        if ( !hasDeclarationsBlock() ) return;

        CssParser parser = new DeclarationsListParser(tokenizer);
        parser.setSelectors(getSelectors());
        parser.parse();
    }

    /**
     * Process selectors list.
     *
     * @return true, if selectors list is valid
     * @throws CriticalParserException if validation fails and error could not fixed automatically
     */
    private boolean processSelectorsList()
    {
        TextPosition start = getReader().getPosition();

        boolean valid = false;
        try
        {
            setSelectors(new CssSelectorsList(tokenizer.consumeSelectorList()));
            getSheet().getSelectorsList().add(getSelectors());
            valid = true;
        }
        catch (CssParsingException e)
        {
            reportAboutBadList(e, start);
            if ( e.getLevel() == EExceptionLevel.CRITICAL ) throw new CriticalParserException(e);
        }
        return valid;
    }

    /**
     * Check block {} after selection list.
     *
     * @return true, if declaration block {} started
     * @throws CssParsingException if block {} not found
     */
    private boolean hasDeclarationsBlock()
    {
        if ( !tokenizer.hasMoreTokens() )
        {
            reportUnexpectedEOF(); // end of file
            return false; // gracefully processing (complete model)
        }

        tokenizer.skipWhitespace(true);

        if ( !tokenizer.isStartBlock() )
        {
            if ( !tokenizer.isStartProperty() ) // declaration block {} not present
            {
                reportEmptyBlock();
                throw new CssParsingException("Skipped style rules {} block.", getReader().getPos(), EExceptionLevel.CRITICAL);
            }
            else  // a css property name is follows next; possible was skipped a style's bracket {
                reportSkippedBracket();
        }

        return true;
    }

    private void reportAboutBadList(CssParsingException e, TextPosition p)
    {
        Reporter reporter = new Reporter();
        reporter.setLevel(e.getLevel());
        reporter.addMessage(String.format("%s in line <%d> at <%d>:", e.getMessage(), p.getLine(), p.getCol() + e.getPosition()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(p.getLine()));
        reporter.addAccent(p.getCol() + e.getPosition(), e.getLength(), String.format("unexpected symbol%s here", e.getLength() > 1 ? "s" : ""));
        reporter.report();
    }

    private void reportEmptyBlock()
    {
        TextPosition start = getReader().getPosition();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.CRITICAL);
        reporter.addMessage("Style block {} not found.");
        reporter.addMessage(String.format("Process stopped in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), 1, "expected { here");
        reporter.addMessage("Process stopped.");
        reporter.report();
    }

    private void reportSkippedBracket()
    {
        TextPosition start = getReader().getPosition();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.ERROR);
        reporter.addMessage(String.format("Possible skipped bracket in line <%d> at <%d>:", start.getLine(), start.getCol()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), 1, "{ was added automatically");
        reporter.report();
    }

    private void reportUnexpectedEOF()
    {
        TextPosition start = getReader().getPosition();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.WARN);
        reporter.addMessage(String.format("Unexpected end of file in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.report();
    }
}
