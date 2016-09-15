package org.w3c.utils.css.model.processors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.io.MarkableReader;

import java.io.Reader;

/**
 * Helper class for extractToken tokens in readers in flow.
 *
 * Created by Home on 24.11.2015.
 */
public class ReaderTokenizer
{
    private final MarkableReader source;
    private char lastDelimiter;

    /**
     * Create tokenized reader.
     *
     * @param source source of chars (<b>NOTE:</b> source must support the {@link Reader#mark} operation!)
     */
    public ReaderTokenizer(MarkableReader source)
    {
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
        source.mark();

        char symbol;
        while ((symbol = source.read()) != 0)
        {
            processor.before(symbol);
            boolean canProcess = processor.canProcess();
            processor.after(symbol);
            lastDelimiter = source.available() ? symbol : 0;


            if (canProcess && delims.contains(symbol + "")) // we founded token
            {
                return extractToken(true);
            }
        }

        // not founded (source is empty)
        return extractToken(false);
    }


    private String extractToken(boolean truncate)
    {
        String token = source.readMarked();

        if (truncate)
        {
            if (!token.isEmpty()) token = token.substring(0, token.length()-1);
        }

        return !token.isEmpty() ? token : null;
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
            s = nextToken(delims, processor);
        }
        while (s != null && s.isEmpty());

        return s;
    }

    public char getLastDelimiter()
    {
        return lastDelimiter;
    }

    public int getPos()
    {
        return source.getPos();
    }

    public boolean available()
    {
        return source.available();
    }
}
