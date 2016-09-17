package org.w3c.utils.css.model.processors;

import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.filters.proc.FlowProcessorEx;
import org.w3c.utils.css.io.MarkableReader;

/**
 * Advanced token extractor.
 *
 * Created by Home on 17.09.2016.
 */
public class TokenExtractorEx<T extends FlowProcessorEx>
{
    private final MarkableReader reader;
    private final T processor;

    /**
     * Create new extractor for
     * @param reader
     * @param processor
     */
    public TokenExtractorEx(MarkableReader reader, T processor)
    {
        this.reader = reader;
        this.processor = processor;
        if ( reader.getPos() == 0 ) processor.before(reader.next(), reader.next(1));
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
            processor.after(reader.read(), reader.next());
            processor.before(reader.next(), reader.next(1));
        }
        return null;
    }

    private String extractThat(MarkableReader reader, FlowProcessorEx processor, FlowProcessorDetector detector)
    {
        reader.mark();
        processor.after(reader.read(), reader.next());
        while ( reader.available() )
        {
            processor.before(reader.next(), reader.next(1));
            if ( !detector.canProcess() ) break;
            processor.after(reader.read(), reader.next());
            if ( !detector.canProcess() )
            {
                processor.before(reader.next(), reader.next(1));
                break;
            }
        }
        return reader.readMarked();
    }
}
