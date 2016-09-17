package org.w3c.utils.css.model.processors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.*;
import org.w3c.utils.css.io.CharsReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;

/**
 * ReaderTokenizer functional tests.
 *
 *
 * Created by Home on 24.11.2015.
 */
public class ReaderTokenizerTest
{
    @Test(dataProvider = "dataProvider_NextNotEmptyToken")
    public void testNextNotEmptyToken(String source, String expected[]) throws Exception
    {
        ReaderTokenizer tokenizer = new ReaderTokenizer(new CharsReader(source));
        FlowProcessor processor = new StringOutProcessor();
        List<String> tokens = new ArrayList<String>(expected.length);

        String token;
        while ( (token = tokenizer.nextNotEmptyToken(",", processor)) != null )
        {
            tokens.add(token.trim());
        }

        String actual[] = tokens.toArray(new String[tokens.size()]);
        assertEquals(actual, expected, String.format("Not passed: %s\nexpected: %s\nactual: %s", source, Arrays.toString(expected), Arrays.toString(actual)));
    }

    @DataProvider
    private Object[][] dataProvider_NextNotEmptyToken()
    {
        return new Object[][] {
                {", .a1, .a2, .a3,,", new String[]{".a1", ".a2", ".a3"}},
                {".a1, .a2, .a3", new String[]{".a1", ".a2", ".a3"}},
                {".a1[attr=' '], .a2, .a3", new String[]{".a1[attr=' ']", ".a2", ".a3"}},
        };
    }

    private static final Pattern COMBINATORS = Pattern.compile("\\s*[\\+~>\\* ]\\s*");

    @Test(dataProvider = "dataProvider_NextTokenByRegExp")
    public void testNextTokenByRegExp(Pattern delimiter, String source, String expected[], String delims[]) throws Exception
    {
        ReaderTokenizer tokenizer = new ReaderTokenizer(source);
        FlowProcessor processor = new AdvancedProcessor();
        List<String> tokens = new ArrayList<String>(expected.length);
        List<String> delimiters = new ArrayList<String>(delims.length);

        String token;
        while ( (token = tokenizer.nextToken(delimiter, processor)) != null )
        {
            tokens.add(token.trim());
            delimiters.add(tokenizer.getLastDelimiterString());
        }

        String actual[] = tokens.toArray(new String[tokens.size()]);
        assertEquals(actual, expected, String.format("Not passed: %s\nexpected: %s\nactual:   %s", source, Arrays.toString(expected), Arrays.toString(actual)));

        String actual2[] = delimiters.toArray(new String[delimiters.size()]);
        assertEquals(actual2, delims, String.format("Not passed: %s\nexpected: %s\nactual:   %s", source, Arrays.toString(delims), Arrays.toString(actual2)));
    }

    @DataProvider
    private Object[][] dataProvider_NextTokenByRegExp()
    {
        return new Object[][] {
                {
                        COMBINATORS,
                        ".a1 + .a2  ~  .a3>.a4",
                        new String[]{".a1", ".a2", ".a3", ".a4"},
                        new String[]{" + ", "  ~  ", ">", ""}
                },
                {
                        COMBINATORS,
                        ".a1 + .a2  ~  .a3>.a4 +",
                        new String[]{".a1", ".a2", ".a3", ".a4"},
                        new String[]{" + ", "  ~  ", ">", " +"}
                },
                {
                        COMBINATORS,
                        ".a1 + * > .a2",
                        new String[]{".a1", "", "", ".a2"},
                        new String[]{" + ", "* ", "> ", ""}
                },
                {
                        COMBINATORS,
                        ".a1 .a2",
                        new String[]{".a1", ".a2"},
                        new String[]{" ", ""}
                },
        };
    }

    @Test(dataProvider = "dataProvider_ExtractQualifie")
    public void testExtractQualifier(Pattern delimiter, String source, String expected[], String delims[]) throws Exception
    {
        ReaderTokenizer tokenizer = new ReaderTokenizer(source);
        FlowProcessor processor = new CombinatorProcessor();
        List<String> tokens = new ArrayList<String>(expected.length);
        List<String> delimiters = new ArrayList<String>(delims.length);

        String token;
        while ( (token = tokenizer.nextToken(delimiter, processor)) != null )
        {
            tokens.add(token.trim());
            delimiters.add(tokenizer.getLastDelimiterString());
        }

        String actual[] = tokens.toArray(new String[tokens.size()]);
        assertEquals(actual, expected, String.format("Not passed: %s\nexpected: %s\nactual:   %s", source, Arrays.toString(expected), Arrays.toString(actual)));

        String actual2[] = delimiters.toArray(new String[delimiters.size()]);
        assertEquals(actual2, delims, String.format("Not passed: %s\nexpected: %s\nactual:   %s", source, Arrays.toString(delims), Arrays.toString(actual2)));
    }


    @DataProvider
    private Object[][] dataProvider_ExtractQualifie()
    {
        return new Object[][] {
                {
                        COMBINATORS,
                        "[href ~= img].a2  ~  .a3>.a4",
                        new String[]{"[href ~= img].a2", ".a3", ".a4"},
                        new String[]{"  ~  ", ">", ""}
                },
                {
                        COMBINATORS,
                        ".a1 + .a2  ~  .a3>.a4",
                        new String[]{".a1", ".a2", ".a3", ".a4"},
                        new String[]{" + ", "  ~  ", ">", ""}
                },
        };
    }

}