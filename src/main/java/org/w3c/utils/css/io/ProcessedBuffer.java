package org.w3c.utils.css.io;

import org.w3c.utils.css.filters.StringTransformer;
import org.w3c.utils.css.filters.proc.TextProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProcessedBuffer provides search and replace blocks of text.
 *
 * Created by Home on 08.11.2015.
 */
public class ProcessedBuffer
{
    private final TextProcessor processor;
    private final RecycledCharBuffer buffer;

    /**
     *
     * @param source
     * @param processor
     */
    public ProcessedBuffer(RecycledCharBuffer source, TextProcessor processor)
    {
        this.buffer = source;
        this.processor = processor;
    }

    /**
     *
     * @return
     */
    public RecycledCharBuffer getBuffer()
    {
        return buffer;
    }

    /**
     * Transform all strings matched given pattern by specific transformer
     *
     * @param regexp pattern to find
     * @param transformer matched strings transformer
     */
    public void replaceAll(Pattern regexp, StringTransformer transformer)
    {
        Matcher matcher = regexp.matcher(buffer.buffer);
        String source;

        while ((source = find(matcher)) != null)
        {
            String transformed = processor.canProcess() ? transformer.transform(source, matcher) : source;

            write(transformed);
        }

        while (buffer.available()) buffer.pipe();
    }

    /**
     * Replace all strings equals sample to
     *
     * @param sample to find what
     * @param replacement for founded parts
     */
/*
    public void replaceAll(String sample, String replacement)
    {
        int pos = 0, n = sample.length();
        while ((pos = buffer.buffer.indexOf(sample, pos)) >= 0)
        {
            int end = pos + n;
            if (end > buffer.size) break;

            for (int i = buffer.read_index ; i < pos; i++)
            {
                char current = buffer.read();
                processor.before(current);

                buffer.write(current);
                processor.after(current);
            }

            buffer.read_index = pos = end;

            write(replacement);
        }
    }
*/

    /**
     *
     */
    public void reuse()
    {
        processor.reset();
        buffer.reuse();
    }

    /**
     *
     * @param replacement
     */
    private void write(String replacement)
    {
        if (replacement != null)
        {
            for (int i = 0, n = replacement.length(); i < n; i++)
            {
                buffer.write(replacement.charAt(i));
            }
        }
    }

    /**
     * Search next match by given pattern.
     *
     * @param matcher
     * @return matched string which was found
     */
    private String find(Matcher matcher)
    {
        if (matcher.find())
        {
            int pos = matcher.start();
            int end = matcher.end();

            if (end > buffer.size) return null;

            for (int i = buffer.read_index ; i < pos; i++)
            {
                char current = buffer.read();
                processor.before(current);

                buffer.write(current);
                processor.after(current);
            }

            buffer.read_index = end;

            return buffer.buffer.substring(pos, end);
        }
        return null;
    }

}
