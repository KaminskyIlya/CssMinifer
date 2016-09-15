package org.w3c.utils.css.model.processors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.filters.proc.StringOutProcessor;
import org.w3c.utils.css.io.CharsReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * ReaderTokenizer functional tests.
 * TODO: test getLastDelimiter and more strings
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
        while ((token = tokenizer.nextNotEmptyToken(",", processor)) != null)
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
                {".a1, .a2, .a3", new String[]{".a1", ".a2", ".a3"}},
                {".a1[attr=' '], .a2, .a3", new String[]{".a1[attr=' ']", ".a2", ".a3"}},
        };
    }
}