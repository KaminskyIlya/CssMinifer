package test;

import org.w3c.utils.css.filters.proc.FlowProcessor;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 27.08.2016.
 */
public class TestHelpers
{
    public static String getMessageFor(String method, String css, int len)
    {
        return String.format("%s not passed.\n%s\n%s at here", method, css.substring(0, len+1), getMarker(len));
    }

    public static String getMarker(int len)
    {
        if (len <= 0) return "";

        char spaces[] = new char[len+1];
        Arrays.fill(spaces, ' ');
        spaces[len] = '^';
        return new String(spaces);
    }

    public static void testFlowByBitmap(String methodName, String source, String bitmap, FlowProcessor processor, TestBooleanMethod method)
    {
        for (int i = 0, n = source.length(); i < n; i++)
        {
            char current = source.charAt(i);
            boolean expected = bitmap.charAt(i) == '1';

            processor.before(current);
            boolean actual = method.test();
            processor.after(current);

            assertEquals(actual, expected, getMessageFor(methodName, source, i));
        }
    }
}
