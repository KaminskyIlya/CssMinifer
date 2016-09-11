package org.w3c.utils.css.model.processors;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.io.MarkableReader;

/**
 * Token extractor based on flow processor detector.
 * Extract specified token from source reader what
 * parsed via processor and when determined by detector.
 * Use only when you know token's order.
 *
 * Created by Home on 11.09.2016.
 */
public class TokenExtractor<T extends FlowProcessor>
{
    private final MarkableReader reader;
    private final T processor;

    /**
     * Create new extractor for
     * @param reader
     * @param processor
     */
    public TokenExtractor(MarkableReader reader, T processor)
    {
        this.reader = reader;
        this.processor = processor;
        if ( reader.getPos() == 0 ) processor.before(reader.next());
    }

    public MarkableReader getReader()
    {
        return reader;
    }

    public T getProcessor()
    {
        return processor;
    }

    /**
     * Extract next token from reader source.
     * My call sequential and with different detectors.
     *
     * @param detector for determine a moment when we read a need token
     * @return string with interest token
     */
    public String extractToken(FlowProcessorDetector detector)
    {
        while ( reader.available() )
        {
            if ( detector.canProcess() )
            {
                return extractThat(reader, processor, detector);
            }
            processor.after(reader.read());
            processor.before(reader.next());
        }
        return null;
    }

    private String extractThat(MarkableReader reader, FlowProcessor processor, FlowProcessorDetector detector)
    {
        reader.mark();
        processor.after(reader.read());
        while ( true )
        {
            processor.before(reader.next());
            if ( !detector.canProcess() ) break;
            processor.after(reader.read());
            if ( !detector.canProcess() )
            {
                processor.before(reader.next());
                break;
            }
        }
        return reader.readMarked();
    }

}
