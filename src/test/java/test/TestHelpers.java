package test;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.FlowProcessorEx;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.model.CssSelectorSpecificity;

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
        if (len < 0) return "";
        //if (len == 0) return "^";

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
            char charAt = bitmap.charAt(i);
            boolean expected = (charAt != ' ' && charAt != '0' && charAt != '\t');

            processor.before(current);
            boolean actual = method.test();
            processor.after(current);

            assertEquals(actual, expected, getMessageFor(methodName, source, i));
        }
    }

    public static void testFlowByBitmapEx(String methodName, String source, String bitmap, FlowProcessorEx processor, TestBooleanMethod method)
    {
        CharsReader reader = new CharsReader(source);
        CharsReader mapper = new CharsReader(bitmap);
        
        while ( reader.available() )
        {
            char current = reader.read();
            char charAt = mapper.read();
            boolean expected = (charAt != ' ' && charAt != '0' && charAt != '\t');

            processor.before(current, reader.next());
            boolean actual = method.test();
            processor.after(current, reader.next());

            assertEquals(actual, expected, getMessageFor(methodName, source, reader.getPos()-1));
        }
    }

    public static void equalsSpecificity(CssSelectorSpecificity specificity, int a, int b, int c)
    {
        assertEquals(specificity.countOfIdSelectors(), a, "A != " + a);
        assertEquals(specificity.countOfSelectorExplanations(), b, "B != " + b);
        assertEquals(specificity.countOfQualifiers(), c, "C != " + c);
    }
}
