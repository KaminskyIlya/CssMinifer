package org.w3c.utils.css.help;

import org.w3c.utils.css.enums.CssColor;

import java.awt.*;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Color work library.
 *
 * Created by Home on 06.09.2015.
 */
public final class ColorUtils
{
    public static final Pattern RGB_PATTERN_NUMERIC = Pattern.compile("rgb\\((\\d+),(\\d+),(\\d+)\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern RGB_PATTERN_PERCENT = Pattern.compile("rgb\\((\\d+)%,(\\d+)%,(\\d+)%\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern RGBA_PATTERN_NUMERIC = Pattern.compile("rgba\\((\\d+),(\\d+),(\\d+),(\\d+)\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern RGBA_PATTERN_PERCENT = Pattern.compile("rgba\\((\\d+)%,(\\d+)%,(\\d+)%,((0\\.)?\\d+)\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern HSL_PATTERN = Pattern.compile("hsl\\((\\d+),(\\d+)%,(\\d+)%\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern HSLA_PATTERN = Pattern.compile("hsla\\((\\d+),(\\d+)%,(\\d+)%,((0\\.)?\\d+)\\)", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEX_COLOR_PATTERN_6 = Pattern.compile("#([0-9a-f]){6}", Pattern.CASE_INSENSITIVE);
    public static final Pattern HEX_COLOR_PATTERN_3 = Pattern.compile("#([0-9a-f]){3}", Pattern.CASE_INSENSITIVE);

    /**
     * Translate color defined by RGB corners to hex string.
     * Used to translate color to valid css color string.
     *
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return valid css hexadecimal color
     */
    public static String toColorStr(int r, int g, int b)
    {
        return  "#" + toHex(clamp(r))
                    + toHex(clamp(g))
                    + toHex(clamp(b));
    }

    /**
     * Translate color to valid css hexadecimal color
     * @param color source color to translate
     * @return valid css hexadecimal color
     */
    public static String toColorStr(Color color)
    {
        return toColorStr(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Make shortened version of css color when the high and low byte of each
     * color components are equals.
     *
     * @param color css hexadecimal color string as "#rrggbb"
     * @return color shortcut as "#rgb"
     */
    public static String simplifyColor(String color)
    {
        char    r1 = color.charAt(1),
                r2 = color.charAt(2),
                g1 = color.charAt(3),
                g2 = color.charAt(4),
                b1 = color.charAt(5),
                b2 = color.charAt(6);

        if (r1 == r2 && g1 == g2 && b1 == b2 && match(r1) && match(g1) && match(b1))
        {
            return "#" + r1 + g1 + b1;
        }
        return color;
    }

    public static char[] simplifyColor(char color[])
    {
        if (color[0] == color[1] && color[2] == color[3] && color[4] == color[5] && match(color[0]) && match(color[2]) && match(color[4]))
        {
            char result[] = new char[3];
            result[0] = color[0];
            result[1] = color[2];
            result[2] = color[4];
            return result;
        }
        else
        {
            return color;
        }
    }

    //TODO: need test
    public static boolean isLongHexColor(char color[])
    {
        boolean matched = color.length == 6;
        for (int i = 0; i < 6 && matched; i++) matched = match(color[i]);
        return matched;
    }

    //TODO: need test
    public static boolean isHexColor6(String color)
    {
        return HEX_COLOR_PATTERN_6.matcher(color).matches();
    }

    //TODO: need test
    public static boolean isShortHexColor(char color[])
    {
        return color.length == 3 && match(color[0]) && match(color[1]) && match(color[2]);
    }

    //TODO: need test
    public static boolean isHexColor3(String color)
    {
        return HEX_COLOR_PATTERN_3.matcher(color).matches();
    }

    //TODO: need test
    public static boolean isRGBColorExpr(String expr)
    {
        return RGB_PATTERN_NUMERIC.matcher(expr).matches() || RGB_PATTERN_PERCENT.matcher(expr).matches();
    }

    //TODO: need test
    public static boolean isRGBAColorExpr(String expr)
    {
        return RGBA_PATTERN_NUMERIC.matcher(expr).matches() && RGBA_PATTERN_PERCENT.matcher(expr).matches();
    }

    //TODO: need test
    public static boolean isHSLColorExpr(String expr)
    {
        return HSL_PATTERN.matcher(expr).matches();
    }

    //TODO: need test
    public static boolean isHSLAColorExpr(String expr)
    {
        return HSLA_PATTERN.matcher(expr).matches();
    }

    /**
     * Reduce color space from 16K color model to 4K (12 bites) color model.
     * Each color component shift on 4 bites right with rounding mode.
     *
     * @param color css hexadecimal color string as "#rrggbb"
     * @return color shortcut as "#rgb" with reduced color space
     */
    public static String reduceColorSpace(String color)
    {
        int c[] = new int[3];

        for (int j = 0, i = 1; j < 3; j++, i += 2)
        {
            int b = Integer.valueOf(color.substring(i, i+2), 16);
            int h = b >> 4; // high byte of b; if b = #37, h = 3;

            int midpoint = h << 4 | h; // when b = #37, midpoint = #33
            if (b < midpoint)
            {
                if (midpoint - b >= 8) h--;
            }
            else
            {
                if (b - midpoint > 8) h++;
            }

            c[j] = h;
        }

        return "#" + Integer.toHexString(c[0]) + Integer.toHexString(c[1]) + Integer.toHexString(c[2]);
    }


    /**
     * Convert rgb color representation to hexadecimal css color.
     * Used due translating one css color notation to another.
     *
     * @param expr css color in rgb color model rgb(0..255, 0..255, 0..255) or rgb(0..100%, 0..100%, 0..100%)
     * @return hexadecimal css color
     */
    public static String fromRGB(String expr)
    {
        Matcher m = RGB_PATTERN_NUMERIC.matcher(expr);
        if (m.matches())
        {
            int r = Integer.valueOf(m.group(1));
            int g = Integer.valueOf(m.group(2));
            int b = Integer.valueOf(m.group(3));

            return ColorUtils.toColorStr(r, g, b);
        }
        m = RGB_PATTERN_PERCENT.matcher(expr);
        if (m.matches())
        {
            int r = 255 * Integer.valueOf(m.group(1)) / 100;
            int g = 255 * Integer.valueOf(m.group(2)) / 100;
            int b = 255 * Integer.valueOf(m.group(3)) / 100;

            return ColorUtils.toColorStr(r, g, b);
        }
        return expr;
    }


    /**
     * Convert hsl color representation to hexadecimal css color.
     * Used due translating one css color notation to another.
     *
     * @param expr css color in hsl color model hsl(0..359, 0..100%, 0..100%)
     * @return hexadecimal css color
     */
    public static String fromHSL(String expr)
    {
        Matcher m = HSL_PATTERN.matcher(expr);
        if (m.matches())
        {
            float h = Float.valueOf(m.group(1));
            float s = Float.valueOf(m.group(2));
            float l = Float.valueOf(m.group(3));

            //Color c = new Color(Color.HSBtoRGB(h, s/100, l/100)); // unfortunately, it's not work (another color model)

            // Used algorithm described at {@link https://ru.wikipedia.org/wiki/HSL}
            Color c = hslToRgb(h, s/100, l/100);

            return ColorUtils.toColorStr(c.getRed(), c.getGreen(), c.getBlue());
        }
        return expr;
    }


    public static String extractColorFrom(String source)
    {
        Matcher matcher;

        matcher = RGB_PATTERN_NUMERIC.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = RGB_PATTERN_PERCENT.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = RGBA_PATTERN_NUMERIC.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = RGBA_PATTERN_PERCENT.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = HSL_PATTERN.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = HSLA_PATTERN.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = HEX_COLOR_PATTERN_6.matcher(source);
        if (matcher.find()) return matcher.group(0);

        matcher = HEX_COLOR_PATTERN_3.matcher(source);
        if (matcher.find()) return matcher.group(0);

        return AnyUtils.findKeywordIn(source, EnumSet.allOf(CssColor.class));
    }


    /**
     * See algorithm description at https://ru.wikipedia.org/wiki/HSL
     * @param h hue
     * @param s saturation
     * @param l luminance
     * @return color in RGB color model
     */
    public static Color hslToRgb(float h, float s, float l)
    {
        float Q = (l < 0.5f) ? l  * (1.0f + s) : l + s - (l * s);
        float P = 2.0f * l - Q;
        float H = clamp(h / 360);
        float Tr = hslClamp(H + 1f/3);
        float Tg = hslClamp(H);
        float Tb = hslClamp(H - 1f/3);

        int r = (int) (255 * hslColor(P, Q, Tr));
        int g = (int) (255 * hslColor(P, Q, Tg));
        int b = (int) (255 * hslColor(P, Q, Tb));

        return new Color(r, g, b);
    }

    private static float hslClamp(float Tc)
    {
        return (Tc < 0) ? Tc + 1.0f : (Tc > 1) ? Tc - 1.0f : Tc;
    }

    private static float hslColor(float P, float Q, float Tc)
    {
        if (Tc < 1f/6) {
            return P + ((Q - P) * 6.0f * Tc);
        }

        if (1f/6 <= Tc && Tc < 1f/2) {
            return Q;
        }

        if (1f/2 <= Tc && Tc <= 2f/3) {
            return P + ((Q - P) * (2f/3 - Tc) * 6.0f);
        }

        return P;
    }

    /**
     * Convert byte to its hexadecimal representation with leading zero, if less then 0xF.
     * @param value
     * @return byte hexadecimal representation
     */
    private static String toHex(int value)
    {
        String result = Integer.toHexString(value);
        return result.length() > 1 ? result : "0" + result;
    }

    /**
     * Clamp color value to range 0..255
     * @param value
     * @return clamped value
     */
    private static int clamp(int value)
    {
        return value >= 0 ? value <= 255 ? value : 255 : 0;
    }

    /**
     * Clamp color value to real range 0..1
     * @param value
     * @return clamped value
     */
    private static float clamp(float value) { return value >= 1 ? 1 : value <= 0 ? 0 : value; }

    /**
     * Check its param as color digit
     * @param c symbol to check
     * @return <b>true</b>, its a valid color digit
     */
    private static boolean match(char c)
    {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }
}
