package org.w3c.utils.css.io;

import org.testng.annotations.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for RecursiveReader class.
 *
 * Created by Home on 18.11.2015.
 */
public class RecursiveReaderTest
{

    @Test(enabled = false)
    public void testInclude() throws Exception
    {
        StringReader source = new StringReader("This is the first line of test.\n@This is the second line of test.");
        StringReader inject = new StringReader("This is the imported line.\n");
        StringWriter output = new StringWriter();
        String expected = "This is the first line of test.\nThis is the imported line.\nThis is the second line of test.";

        RecursiveReader underTest = new RecursiveReader(source);
        int n;
        while ((n = underTest.read()) != -1)
        {
            if (n == '@') underTest.include(inject); else output.append((char)n);
        }
        output.close();

        assertEquals(output.toString(), expected);
    }

    @Test
    public void testInclude_ReadToFullCharsArray() throws Exception
    {
        StringReader source = new StringReader("This is the first line of test.\nThis is the second line of test.");
        StringReader inject = new StringReader("This is the imported line.\n");
        StringWriter output = new StringWriter();
        String expected = "This is the imported line.\nThis is the first line of test.\nThis is the second line of test.";

        RecursiveReader underTest = new RecursiveReader(source);
        underTest.include(inject);
        char chars[] = new char[256];
        int n;
        while ((n = underTest.read(chars)) != -1)
        {
            output.write(chars, 0, n);
        }
        output.close();

        assertEquals(output.toString(), expected);
    }

    @Test
    public void testInclude_ReadToCharsArrayPartial() throws Exception
    {
        StringReader source = new StringReader("This is the first line of test.\nThis is the second line of test.");
        StringReader inject = new StringReader("This is the imported line.\n");
        StringWriter output = new StringWriter();
        String expected = "This is the imported line.\nThis is the first line of test.\nThis is the second line of test.";

        RecursiveReader underTest = new RecursiveReader(source);
        underTest.include(inject);
        char chars[] = new char[32];
        int n;
        while ((n = underTest.read(chars, 10, 22)) != -1)
        {
            output.write(chars, 10, n);
        }
        output.close();

        assertEquals(output.toString(), expected);
    }

}