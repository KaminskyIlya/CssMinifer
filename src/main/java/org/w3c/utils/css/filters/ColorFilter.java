package org.w3c.utils.css.filters;

import org.w3c.utils.css.enums.CssColor;
import org.w3c.utils.css.filters.proc.TextProcessor;
import org.w3c.utils.css.io.ProcessedBuffer;
import org.w3c.utils.css.io.RecycledCharBuffer;
import org.w3c.utils.css.help.ColorUtils;

import java.awt.*;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Color filter.</p>
 *
 * <ol>
 * <li>Convert rgb/hsl expressions to a hexadecimal color string.</li>
 * <li>Simplify 6-digits hex colors to 3-digits color, for doublets.</li>
 * <li>Reduce color space from 16M to 4K.</li>
 * <li>Translates color constants with long name into its hexadecimal value.</li>
 * <li>Convert hexadecimal corners of colors to its shorten names.</li>
 * </ol>
 *
 * <p>NOTE: Must be a called after WhiteSpaceFilter.</p>
 *
 * Created by Home on 08.11.2015.
 */
public class ColorFilter extends AbstractFilter
{
    private boolean reduceColorSpace = false;
    private final ProcessedBuffer processedBuffer;

    public ColorFilter(RecycledCharBuffer buffer)
    {
        super(buffer);
        processedBuffer = new ProcessedBuffer(buffer, new ColorProcessor());
    }

    /**
     * Setter for manipulation of filter settings.
     * @param reduceColorSpace are needs to reduce a color space from 16M to 4K?
     */
    public void setReduceColorSpace(boolean reduceColorSpace)
    {
        this.reduceColorSpace = reduceColorSpace;
    }

    public void apply()
    {
        translateRgbExpressions();
        translateHslExpressions();
        translateNames2Colors();
        simplifyColors();
        translateColor2Names();
        if (reduceColorSpace) reduceColors();

        buffer.refill();
    }

    /**
     * Convert color #668877 to #687
     */
    private void simplifyColors()
    {
        processedBuffer.replaceAll(ColorUtils.HEX_COLOR_PATTERN_6, new StringTransformer()
        {
            public String transform(String color, Matcher matcher)
            {
                return ColorUtils.simplifyColor(color);
            }
        });
        processedBuffer.reuse();
    }


    /**
     * Convert 16M to 4K color model.
     */
    private void reduceColors()
    {
        processedBuffer.replaceAll(ColorUtils.HEX_COLOR_PATTERN_6, new StringTransformer()
        {
            public String transform(String color, Matcher matcher)
            {
                return ColorUtils.reduceColorSpace(color);
            }
        });
        processedBuffer.reuse();
    }

    /**
     * Translate hsl expressions (such as hls(40, 100%, 63%)) to rgb hexadecimal corners of color (as #f634ee)
     */
    private void translateHslExpressions()
    {
        processedBuffer.replaceAll(ColorUtils.HSL_PATTERN, new StringTransformer()
        {
            public String transform(String expr, Matcher matcher)
            {
                return ColorUtils.fromHSL(expr);
            }
        });
        processedBuffer.reuse();
    }

    /**
     * Translate rgb expressions (such as rgb(255, 255, 255)) to rgb hexadecimal corners of color (as #ffffff)
     */
    private void translateRgbExpressions()
    {
        processedBuffer.replaceAll(ColorUtils.RGB_PATTERN_NUMERIC, new StringTransformer()
        {
            public String transform(String expr, Matcher matcher)
            {
                return ColorUtils.fromRGB(expr);
            }
        });
        processedBuffer.reuse();

        processedBuffer.replaceAll(ColorUtils.RGB_PATTERN_PERCENT, new StringTransformer()
        {
            public String transform(String expr, Matcher matcher)
            {
                return ColorUtils.fromRGB(expr);
            }
        });
        processedBuffer.reuse();
    }

    /**
     * Translate named colors (such as lightseagreen) to rgb hexadecimal corners of color (as #20B2AA)
     */
    private void translateNames2Colors()
    {
        for (CssColor color : EnumSet.allOf(CssColor.class))
        {
            String name = color.getName();
            final String value = reduceColorSpace ? ColorUtils.reduceColorSpace(color.getColor()) : ColorUtils.simplifyColor(color.getColor());

            if (name.length() > value.length())
            {
                Pattern regexp = Pattern.compile("(background|color)\\:" + name, Pattern.CASE_INSENSITIVE);
                processedBuffer.replaceAll(regexp, new StringTransformer()
                {
                    public String transform(String source, Matcher matcher)
                    {
                        return matcher.group(1) + ":" + value;
                    }
                });
                processedBuffer.reuse();
            }
        }
    }

    /**
     * Translate colors to its shorten names (for example: #ff0000 = #f00 = red)
     */
    private void translateColor2Names()
    {
        int max = reduceColorSpace ? 3 : 6; // maximum available length for color's constant
        for (CssColor color : EnumSet.allOf(CssColor.class))
        {
            final String name = color.getName();
            String value = ColorUtils.simplifyColor(color.getColor());

            if (name.length() <= max && name.length() < value.length())
            {
                Pattern regexp = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
                processedBuffer.replaceAll(regexp, new StringTransformer()
                {
                    public String transform(String source, Matcher matcher)
                    {
                        return name;
                    }
                });
                processedBuffer.reuse();
            }
        }
    }



    private static class ColorProcessor extends TextProcessor
    {
        @Override
        public boolean canProcess()
        {
            return isInBlock() && !isInParenthesis() && !isInString();
        }
    }

}
