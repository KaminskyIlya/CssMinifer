package org.w3c.utils.css.io;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.StringTransformer;
import org.w3c.utils.css.filters.proc.TextProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Functional and unit tests for ProcessedBuffer class.
 *
 * Created by Home on 08.11.2015.
 */
public class ProcessedBufferTest
{

    @Test(dataProvider = "dataProvider")
    public void testReplaceAll(String source, final String replacement, String expected) throws Exception
    {
        ProcessedBuffer buffer = new ProcessedBuffer(new RecycledCharBuffer(source), new TextProcessor());
        Pattern pattern = Pattern.compile("test");

        buffer.replaceAll(pattern, new StringTransformer()
        {
            public String transform(String source, Matcher matcher)
            {
                return replacement;
            }
        });

        assertEquals(buffer.getBuffer().getOutput(), expected);
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][]{

                {"this is the test string where 'test' word must be removed for checking test",
                 "1",
                 "this is the 1 string where '1' word must be removed for checking 1"},

                {"this is the test with advanced text",
                 "2222",
                 "this is the 2222 with advanced text"},
        };
    }
}