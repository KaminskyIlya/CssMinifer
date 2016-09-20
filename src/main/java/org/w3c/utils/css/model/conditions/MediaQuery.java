package org.w3c.utils.css.model.conditions;

import org.w3c.utils.css.enums.MediaPrefix;
import org.w3c.utils.css.filters.proc.FlowProcessorDetector;
import org.w3c.utils.css.filters.proc.MediaQueryProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.processors.TokenExtractor;

import java.util.HashSet;
import java.util.Set;

/**
 * ћедиа-запрос. ѕохоже это замена дл€ класса Media.
 * tv and (color), screen and (min-width: 1024px)
 * screen and (color), projection and (color)
 *
 * ¬ыражение screen and (max-weight: 3kg) and (color), (color) можно сократить до screen and (max-weight: 3kg), (color)
 *
 * Created by Home on 27.08.2016.
 */
public class MediaQuery
{
    private MediaPrefix prefix; // only, not
    private String mediaType; // screen, tv, projection, aural  (лучше строка, т.к. идентификаторы могут быть разные)
    private Set<Expression> expressions = new HashSet<Expression>();

    public MediaPrefix getPrefix()
    {
        return prefix;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public Set<Expression> getExpressions()
    {
        return expressions;
    }

    /**
     * Sugar
     * @param expression
     */
    public void addExpression(Expression expression)
    {
        expressions.add(expression);
    }

    public MediaQuery(String query)
    {
        // лучше применить другую тактику: сделать токенизацию по пробелам
        // пробелы не должны быть внутри скобок (). это единственное условие
        // при этом у нас могут быть такие фразы: and(...) or(...)
        // их уже можно обработать другим парсером

        MediaQueryProcessor processor = new MediaQueryProcessor();
        CharsReader reader = new CharsReader(query);
        TokenExtractor<MediaQueryProcessor> extractor = new TokenExtractor<MediaQueryProcessor>(reader, processor);

        while ( reader.available() )
        {
            if (processor.inPrefix())               processPrefix(extractor);
            else if (processor.inMediaType())       processMediaType(extractor);
            else if (processor.inConjunction())     processConjunction(extractor);
            else if (processor.inExpression())      processExpression(extractor);
            else if (processor.isInWhitespace())    processWhitespaces(extractor);
            //else if (processor.isValid(reader.next()))            processSymbol(reader, processor);
            else
            {
                throw new CssParsingException("Unexpected char in media query: " + query, reader.getPos(), EExceptionLevel.ERROR);
            }
        }
    }

    private void processPrefix(final TokenExtractor<MediaQueryProcessor> extractor)
    {
        int pos = extractor.getReader().getPos();

        String name = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().inPrefix();
            }
        });
        try
        {
            prefix = MediaPrefix.valueOf(name);
        }
        catch (IllegalArgumentException e)
        {
            throw new CssParsingException("Unexpected media query prefix: " + name, pos, extractor.getReader().getPos() - pos + 1, EExceptionLevel.ERROR);
        }
    }

    private void processMediaType(final TokenExtractor<MediaQueryProcessor> extractor)
    {
        mediaType = extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().inMediaType();
            }
        });
    }

    private void processConjunction(final TokenExtractor<MediaQueryProcessor> extractor)
    {
        // skip conjunctions
        extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().inConjunction();
            }
        });
    }

    private void processExpression(TokenExtractor<MediaQueryProcessor> extractor)
    {

    }

    private void processWhitespaces(final TokenExtractor<MediaQueryProcessor> extractor)
    {
        extractor.extractToken(new FlowProcessorDetector()
        {
            public boolean canProcess()
            {
                return extractor.getProcessor().isInWhitespace();
            }
        });
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof MediaQuery)) return false;

        MediaQuery that = (MediaQuery) o;

        return expressions.equals(that.expressions);

    }

    @Override
    public int hashCode()
    {
        return expressions.hashCode();
    }
}
