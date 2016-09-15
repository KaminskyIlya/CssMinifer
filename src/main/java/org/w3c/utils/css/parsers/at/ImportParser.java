package org.w3c.utils.css.parsers.at;

import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.help.AnyUtils;
import org.w3c.utils.css.help.StringUtils;
import org.w3c.utils.css.io.ByteStreamLoader;
import org.w3c.utils.css.io.CssTextReader;
import org.w3c.utils.css.model.CssPage;
import org.w3c.utils.css.model.exceptions.CriticalParserException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.parsers.CssDocumentParser;
import org.w3c.utils.css.parsers.CssParser;
import org.w3c.utils.css.parsers.CssTextTokenizer;
import org.w3c.utils.css.parsers.TextPosition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Parse @import instruction.
 *
 * Created by Home on 19.08.2016.
 */
public class ImportParser extends CssParser
{
    public ImportParser(CssTextTokenizer tokenizer)
    {
        super(tokenizer);
    }

    @Override
    public void parse()
    {
        String name = parseUrl();
        if (name != null)
        {
            String media = parseMedia();
            processImportedFile(name, media);
        }
        tokenizer.gotoEndOfAtRule();
    }

    private String parseUrl()
    {
        tokenizer.skipWhitespace(false);
        tokenizer.getReader().mark();

        String url;
        if (tokenizer.isStartString())
        {
            String literal = tokenizer.consumeString();
            url = StringUtils.getUnquotedStringLiteral(literal);
            if (url.isEmpty()) reportBadUrl(literal);
        }
        else if (tokenizer.isStartUrl())
        {
            url = tokenizer.consumeUrl();
        }
        else
        {
            reportBadToken();
            return null;
        }
        if ( !AnyUtils.checkURL(url) )
        {
            reportBadUrl(url);
            return null;
        }
        else
            return url;
    }

    private String parseMedia()
    {
        tokenizer.skipWhitespace(false);
        tokenizer.getReader().mark();
        return tokenizer.consumeAtRuleHeader();
    }

    private void reportBadUrl(String url)
    {
        TextPosition start = getReader().getPosition();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.ERROR);
        reporter.addMessage(String.format("@import rule syntax error in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), url.length(), "invalid url");
        reporter.addMessage("This declaration will be skipped.");
        reporter.report();
    }

    private void processImportedFile(String name, String media)
    {
        CssParser parser = new CssDocumentParser(new CssTextTokenizer(new CssTextReader(getFileContent(name))));
        parser.setModel(getModel());
        parser.setSheet((media != null) ? new CssPage(media) : new CssPage());
        parser.parse();
    }

    private byte[] getFileContent(String file)
    {
        try
        {
            File source = new File(file);
            if (source.length() > 128*1024) throw new IOException("Imported file too large.");

            ByteStreamLoader loader = new ByteStreamLoader(new FileInputStream(source));
            return loader.toByteArray();
        }
        catch (IOException e)
        {
            TextPosition start = getReader().getPosition();

            Reporter reporter = new Reporter();
            reporter.setLevel(EExceptionLevel.CRITICAL);
            reporter.addMessage(String.format("IOException due getting a file content for @import rule in line <%d>:", start.getLine()));
            reporter.addMessage("--------------------------------------------------");
            reporter.addMessage(getReader().getSourceLine(start.getLine()));
            reporter.addAccent(start.getCol(), file.length(), "IO error for this file");
            reporter.addMessage("REASON: " + e.getMessage());
            reporter.report();

            throw new CriticalParserException(e);
        }
    }

    private void reportBadToken()
    {
        TextPosition start = getReader().getPosition();

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.ERROR);
        reporter.addMessage(String.format("@import rule syntax error in line <%d>:", start.getLine()));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage(getReader().getSourceLine(start.getLine()));
        reporter.addAccent(start.getCol(), 1, "expected string literal or valid url");
        reporter.addMessage("This declaration will be skipped.");
        reporter.report();
    }
}
