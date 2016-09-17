package org.w3c.utils.css.model.processors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.io.MarkableReader;

import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for extractToken tokens in readers in flow.
 *
 * Created by Home on 24.11.2015.
 */
public class ReaderTokenizer
{
    private final String source;
    private final MarkableReader reader;

    private Matcher matcher = null;
    private char lastDelimiterChar = ' ';
    private String lastDelimiterString = "";

    /**
     * Create tokenized reader for extract tokens by delimiter chars
     *
     * @param reader source of chars (<b>NOTE:</b> source must support the {@link Reader#mark} operation!)
     */
    public ReaderTokenizer(MarkableReader reader)
    {
        this.reader = reader;
        this.source = null;
    }

    /**
     * Create tokenized reader for extract tokens by regexp patterns.
     *
     * @param source
     */
    public ReaderTokenizer(String source)
    {
        this.reader = new CharsReader(source);
        this.source = source;
    }

    /**
     * Find and return next token in source.
     *
     * @param delims string with expected delimiters chars (any chars)
     * @param processor inflow text processor, to control exceptions (for example: control that we are not in string)
     * @return <b>token string</b> if we founded,<br/>
     *         <b>empty</b> string for empty tokens (if we a founded token immediately),<br/>
     *         and <b>null</b>, when source is finished read.
     */
    public String nextToken(String delims, FlowProcessor processor)
    {
        reader.mark();
        lastDelimiterChar = ' ';

        char symbol;
        while ((symbol = reader.read()) != 0)
        {
            processor.before(symbol);
            boolean canProcess = processor.canProcess();
            processor.after(symbol);
            lastDelimiterChar = symbol;


            if ( canProcess && delims.indexOf((int)symbol) >= 0) // we founded token
            {
                return extractToken(true);
            }
        }

        // not founded (reader is empty)
        return extractToken(false);
    }


    private String extractToken(boolean truncate)
    {
        String token = reader.readMarked();

        if (truncate)
        {
            if (!token.isEmpty()) token = token.substring(0, token.length()-1);
        }

        return token;
    }


    /**
     * Find next not empty token in source.
     *
     * @param delims string with expected delimiters chars (any chars)
     * @param processor inflow text processor, to control exceptions (for example: control that we are not in string)
     * @return <b>token string</b> if we founded,<br/>
     *         or <b>null</b>, when source is finished read.
     */
    public String nextNotEmptyToken(String delims, FlowProcessor processor)
    {
        String s;
        do
        {
            if ( !reader.available() ) return null;
            s = nextToken(delims, processor);
        }
        while ( s != null && s.isEmpty() );

        return s;
    }

    /**
     * Extract token by regexp pattern
     *
     * @param delimiter regexp pattern
     * @param processor detector for valid delimiter
     * @return read token or <b>null</b>, if no more tokens founded
     */
    public String nextToken(Pattern delimiter, FlowProcessor processor)
    {
        if (matcher == null) matcher = delimiter.matcher(source);
        lastDelimiterString = "";

        int tokenStart = reader.getPos();
        while ( reader.available() && matcher.find() )
        {
            moveBy(matcher.start(), processor); // moves to next delimiter
            String token = source.substring(tokenStart, matcher.start());

            moveBy(matcher.end(), processor); // skip founded delimiter
            if ( !processor.canProcess() ) continue; // skip not correct delimiter

            lastDelimiterString = source.substring(matcher.start(), matcher.end());

            return token;
        }
        matcher = null;

        if ( reader.available() )
        {
            reader.mark();
            moveBy(source.length(), processor);
            return reader.readMarked();
        }
        return null; // not founded
    }

    private void moveBy(int pos, FlowProcessor processor)
    {
        for (int i = reader.getPos(); i < pos; i++)
        {
            processor.before(reader.next());
            processor.after(reader.read());
        }
    }

    public char getLastDelimiterChar()
    {
        return lastDelimiterChar;
    }

    /**
     * @return last delimiter string or empty
     */
    public String getLastDelimiterString()
    {
        return lastDelimiterString;
    }

    public int getPos()
    {
        return reader.getPos();
    }

    public boolean available()
    {
        return reader.available();
    }
}
