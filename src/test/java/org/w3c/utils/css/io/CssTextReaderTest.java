package org.w3c.utils.css.io;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.FlowProcessor;
import test.TestReaderProcessor;
import test.TestBooleanMethod;
import test.TestHelpers;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 27.08.2016.
 */
public class CssTextReaderTest
{
    @Test
    public void testSkip() throws Exception
    {
        CssTextReader reader = new CssTextReader("test<!-- DATA -->me");
        for (int i = 0; i < 4; i++) reader.skip();

        assertEquals(reader.read(), ' ');
        assertEquals(reader.read(), 'm');
        assertEquals(reader.read(), 'e');
    }

    @Test(dataProvider = "dataProvider_read")
    public void testRead(String source, int skip, int read, String expected) throws Exception
    {
        CssTextReader reader = new CssTextReader(source);
        for (int i = 0; i < skip; i++) reader.skip();
        reader.mark();

        for (int i = 0; i < read; i++) reader.read();

        String actual = reader.readMarked();
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dataProvider_read()
    {
        return new Object[][] {
                // test unicode conversion
                {"Code U+26 B", 5, 3, "& B"},
                // test short escaped hexadecimal conversion with space in last
                {"Code \\26 B", 5, 3, "&B"},
                {"Code \\026 B", 5, 3, "&B"},
                {"Code \\0026 B", 5, 3, "&B"},
                {"Code \\00026 B", 5, 3, "&B"},
                {"Code \\000026B", 5, 3, "&B"},
                {"Code \\\\000026B", 5, 3, "\\\\0"},
                {"Code \\\\\\000026B", 5, 4, "\\\\&B"},
                // test full escaped hexadecimal conversion without space in last
                {"Code \\00A B", 5, 3, "\nB"},
                // test string splitter by escaped new line
                {"content: 'string\\\n test'", 9, 13, "'string test'"},
                // test removed CDO token
                {"content: <!-- DATA -->'\\26';", 0, 18, "content:  '&';"},
                // test removed block comment
                {"content: /* this is long comment */'test';", 0, 17, "content:  'test';"},
                // test escaped symbol
                {"test\\{5\\}", 0, 9, "test\\{5\\}"},
                // test escaped special char
                {"test\\t(color)", 0, 13, "test\\t(color)"},
                // test escaped quote in string
                {"content: '\\'' attr(title) '\\''", 0, 30, "content: '\\'' attr(title) '\\''"},
                // test skip windows EOL
                {"a\r\nb", 0, 3, "a\nb"},
        };
    }

    @Test(dataProvider = "dataProvider_isInString")
    public void testIsInString(String source, String expectedFlags) throws Exception
    {
        final CssTextReader reader = new CssTextReader(source);
        FlowProcessor processor = new TestReaderProcessor(reader);

        TestHelpers.testFlowByBitmap("isInString", source, expectedFlags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return reader.isInString();
            }
        });
    }
    @DataProvider
    public Object[][] dataProvider_isInString()
    {
        return new Object[][] {
                // test simple string, quote '
                {"content: ' attr(title) ' ;",
                 "         11111111111111   "},
                // test simple string, quote "
                {"content: \" attr(title) \" ;",
                 "         11111111111111   "},
                // test quote ' in string ""
                {"content: \" attr'(title) \" ;",
                  "         111111111111111    "},
                // test quote " in string ''
                {"content: ' attr\"(title) ' ;",
                 "         111111111111111   "},
                // test escaped quote ' in string ''
                {"content: '\\'' attr(title) '\\''",
                 "         111              111 "},
                // test escaped quote " in string ""
                {"content: \"\\\"\" attr(title) \"\\\"\"",
                 "         111              111 "},
        };
    }

}