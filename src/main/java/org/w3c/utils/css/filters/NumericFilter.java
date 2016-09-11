package org.w3c.utils.css.filters;

import org.w3c.utils.css.filters.proc.TextProcessor;
import org.w3c.utils.css.help.NumericUtils;
import org.w3c.utils.css.io.ProcessedBuffer;
import org.w3c.utils.css.io.RecycledCharBuffer;

import java.util.regex.Matcher;

/**
 * Numeric filter.
 *
 * 1. Remove trailing zeroes.
 * 2. Downward a numbers accuracy (to 3-digits after comma).
 * 3. Change representation form for bloated numbers (for example, 1000 = 1e3, 0.0005 = 5e-4)
 *
 * <p>NOTE: Must be a called after WhiteSpaceFilter.</p>
 *
 * Created by Home on 09.11.2015.
 */
public class NumericFilter extends AbstractFilter
{
    private boolean lossAccuracy = true;
    private int accuracyScale = 3; // 3 digits after comma
    private boolean compactBloated = false;
    private final ProcessedBuffer processedBuffer;

    public NumericFilter(RecycledCharBuffer buffer)
    {
        super(buffer);
        processedBuffer = new ProcessedBuffer(buffer, new NumberProcessor());
    }

    public void setLossAccuracy(boolean lossAccuracy)
    {
        this.lossAccuracy = lossAccuracy;
    }

    public void setCompactBloated(boolean compactBloated)
    {
        this.compactBloated = compactBloated;
    }

    public void setAccuracyScale(int accuracyScale)
    {
        if (accuracyScale < 0 || accuracyScale > 6)
            throw new IllegalArgumentException(String.format("Invalid value (%d) for accuracyScale property.", accuracyScale));

        this.accuracyScale = accuracyScale;
    }

    public void apply()
    {
        if (compactBloated) compactBloatNumbers();
        if (lossAccuracy) roundFloatNumbers();
        removeLeadingZeros();

        buffer.refill();
    }

    private void compactBloatNumbers()
    {
        processedBuffer.replaceAll(NumericUtils.BLOATED_BIG_NUMBER, new StringTransformer()
        {
            public String transform(String source, Matcher matcher)
            {
                return NumericUtils.compactBloatedBigNumber(source);
            }
        });
        processedBuffer.reuse();

        processedBuffer.replaceAll(NumericUtils.BLOATED_LOW_NUMBER, new StringTransformer()
        {
            public String transform(String source, Matcher matcher)
            {
                return NumericUtils.compactBloatedLowNumber(source);
            }
        });
        processedBuffer.reuse();
    }

    private void removeLeadingZeros()
    {
        processedBuffer.replaceAll(NumericUtils.FLOAT_NUMBER, new StringTransformer()
        {
            public String transform(String source, Matcher matcher)
            {
                return NumericUtils.removeLeadingZero(source);
            }
        });
        processedBuffer.reuse();
    }

    private void roundFloatNumbers()
    {
        processedBuffer.replaceAll(NumericUtils.FLOAT_NUMBER, new StringTransformer()
        {
            public String transform(String source, Matcher matcher)
            {
                try
                {
                    return NumericUtils.lossPrecision(source, accuracyScale);
                }
                catch (NumberFormatException e)
                {
                    return source;
                }
            }
        });
        processedBuffer.reuse();
    }

    private static class NumberProcessor extends TextProcessor
    {
        @Override
        public boolean canProcess()
        {
            return !isInString();
        }
    }
}
