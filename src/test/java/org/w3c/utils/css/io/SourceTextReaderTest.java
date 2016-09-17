package org.w3c.utils.css.io;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.parsers.TextChunk;
import org.w3c.utils.css.parsers.TextPosition;
import test.TestReaderProcessor;
import test.TestBooleanMethod;
import test.TestHelpers;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 24.08.2016.
 */
public class SourceTextReaderTest
{
    private static final String data1 = "This is first line.\rThis is second line.\nThis is third line.\r\nThis is fourth line.";
    private static final String data2 = ".test\\{15}";
    private static final String data3 = "a\rb\nc\r\nd";
    // 'ab_тест' в Window-1251 кодировке
    private static final byte test_win1251[] = {(byte)0x61, (byte)0x62, (byte)0x5f, (byte)0xf2, (byte)0xe5, (byte)0xf1, (byte)0xf2};
    // 'ab_тест' в UTF-8 кодировке
    private static final byte test_utf8[] = {(byte)0x61, (byte)0x62, (byte)0x5f, (byte)0xd1, (byte)0x82, (byte)0xd0, (byte)0xb5, (byte)0xd1, (byte)0x81, (byte)0xd1, (byte)0x82};


    @Test
    public void testGetLine() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        assertEquals(reader.getLine(), 1);

        for (int i = 0; i < 20; i++) reader.read();
        assertEquals(reader.getLine(), 2);

        for (int i = 0; i < 21; i++) reader.read();
        assertEquals(reader.getLine(), 3);

        for (int i = 0; i < 20; i++) reader.read();
        assertEquals(reader.getLine(), 4);
        assertEquals(reader.read(), 'T');
        assertEquals(reader.read(), 'h');
        assertEquals(reader.read(), 'i');
        assertEquals(reader.read(), 's');
    }

    @Test
    public void testGetPos() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        assertEquals(reader.getPos(), 0);

        reader.read(); // T
        assertEquals(reader.getLast(), 'T');
        assertEquals(reader.getPos(), 1);
        reader.read(); // h
        assertEquals(reader.getLast(), 'h');
        assertEquals(reader.getPos(), 2);

        for (int i = 0; i < 18; i++) reader.read(); // until '\r'
        assertEquals(reader.getLast(), '\n');
        assertEquals(reader.getLine(), 2); //
        assertEquals(reader.getPos(), 0); // position of '\r' char

        reader.read(); // move to new line
        assertEquals(reader.getLast(), 'T');
        assertEquals(reader.getPos(), 1);
    }

    @Test
    public void testGetPosition() throws Exception
    {

    }

    @Test
    public void testGetLast() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        assertEquals(reader.getLast(), 0);

        reader.read();
        assertEquals(reader.getLast(), 'T');
    }

    @Test(dataProvider = "dataProvider_isEscaped")
    public void testIsEscaped(String source, String expectedFlags) throws Exception
    {
        final SourceTextReader reader = new SourceTextReader(source);
        FlowProcessor processor = new TestReaderProcessor(reader);

        TestHelpers.testFlowByBitmap("isEscaped", source, expectedFlags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return reader.isEscaped();
            }
        });
    }

    @DataProvider
    public Object[][] dataProvider_isEscaped()
    {
        // where is '1' - symbol MUST be defined as escaped char
        return new Object[][] {
                {new String(new byte[]{'.','t','e','s','t','\\','{','1','5','}'}), // for example, symbol { is escaped in this string
                 new String(new byte[]{' ',' ',' ',' ',' ',' ', '1',' ',' ',' '})}, // because flag '1' marked this char

                {new String(new byte[]{'.','t','e','s','t','\\','\\','{','1','5','}'}),
                 new String(new byte[]{' ',' ',' ',' ',' ',' ', '1', ' ',' ',' ',' '})},

                {new String(new byte[]{'.','t','e','s','t','\\','{','1','5','}','\\','\\','\\','\\','k','n'}),
                 new String(new byte[]{' ',' ',' ',' ',' ',' ', '1',' ',' ',' ',' ', '1', ' ', '1', ' ',' '})},

                {new String(new byte[]{'.','t','e','s','t','\\','\\','{','1','5','}','\\','\\','\\','\\','\\','m','s'}),
                 new String(new byte[]{' ',' ',' ',' ',' ',' ', '1', ' ',' ',' ',' ',' ', '1', ' ', '1', ' ', '1',' '})},
        };
    }

    @Test
    public void testAvailable() throws Exception
    {
        {
            SourceTextReader reader = new SourceTextReader(data2);
            assertEquals(reader.available(), true);

            int i;

            for (i = 0; i < data2.length() / 2; i++) reader.read();
            assertEquals(reader.available(), true);

            for (; i < data2.length(); i++) reader.read();
            assertEquals(reader.available(), false);
        }
        {
            SourceTextReader reader = new SourceTextReader(data3);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), true);
            reader.read(); assertEquals(reader.available(), false);
        }
    }

    @Test
    public void testRead() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data3);
        assertEquals(reader.read(), 'a');
        assertEquals(reader.read(), '\n');
        assertEquals(reader.read(), 'b');
        assertEquals(reader.read(), '\n');
        assertEquals(reader.read(), 'c');
        assertEquals(reader.read(), '\n');
        assertEquals(reader.read(), 'd');
        assertEquals(reader.read(), 0);
        assertEquals(reader.read(), 0);
    }

    @Test
    public void testNext() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data2);
        assertEquals(reader.next(), '.'); reader.read();
        assertEquals(reader.next(), 't'); reader.read();
        assertEquals(reader.next(), 'e'); reader.read();
        assertEquals(reader.next(), 's'); reader.read();
        assertEquals(reader.next(), 't'); reader.read();
        assertEquals(reader.next(), '\\'); reader.read();
        assertEquals(reader.next(), '{'); reader.read();
        assertEquals(reader.next(), '1'); reader.read();
        assertEquals(reader.next(), '5'); reader.read();
        assertEquals(reader.next(), '}'); reader.read();
        assertEquals(reader.next(), 0); reader.read();
        assertEquals(reader.next(), 0);
    }

    @Test
    public void testMark() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        while (reader.available() && reader.getLine() != 2) reader.read();

        reader.mark(); // now we test it!
        while (reader.available() && reader.getLine() != 3) reader.read();

        assertEquals(reader.readMarked(), "This is second line.\n");
    }

    @Test
    public void testReset() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);

        reader.mark(); // remember our position at start of first line

        while (reader.available() && reader.getLine() != 2) reader.read(); // read a first line
        assertEquals(reader.readMarked(), "This is first line.\r");

        ////////////////////////
        reader.reset(); // now we test it!  : we MUST to moving at start of first line  (on marked position)
        ////////////////////////

        while (reader.available() && reader.getLine() != 2) reader.read(); // read a first line again
        // not we MUST be at start of second line
        assertEquals(reader.readMarked(), null); // BUT after reset, readMarked return NULL, because mark was deleted

        reader.mark(); // remember out position at start of second line

        while (reader.available() && reader.getLine() != 3) reader.read(); // read a second line
        assertEquals(reader.readMarked(), "This is second line.\n"); // we must get a second line content
    }

    @Test
    public void testReadMarked() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data2);
        for (int i = 0; i < 6; i++) reader.read();
        assertEquals(reader.readMarked(), null); // not marked source

        reader.mark();
        for (int i = 0; i < 4; i++) reader.read();
        assertEquals(reader.readMarked(), "{15}"); // marked source

        assertEquals(reader.available(), false);

        for (int i = 0; i < 10; i++) reader.read();
        assertEquals(reader.readMarked(), "{15}"); // if source is done, return last marked
    }

    @Test
    public void testGetSourceLine() throws Exception
    {
        {
            SourceTextReader reader = new SourceTextReader(data1);
            assertEquals(reader.getSourceLine(0), "");
            assertEquals(reader.getSourceLine(1), "");
            assertEquals(reader.getSourceLine(2), "");
            assertEquals(reader.getSourceLine(3), "");
            assertEquals(reader.getSourceLine(4), "");
            assertEquals(reader.getSourceLine(5), "");

            while (reader.available() && reader.getLine() != 1) reader.read();
            assertEquals(reader.getSourceLine(1), "");

            while (reader.available() && reader.getLine() != 2) reader.read();
            assertEquals(reader.getSourceLine(1), "This is first line.\r");

            while (reader.available() && reader.getLine() != 6) reader.read();
            assertEquals(reader.getSourceLine(1), "This is first line.\r");
            assertEquals(reader.getSourceLine(2), "This is second line.\n");
            assertEquals(reader.getSourceLine(3), "This is third line.\r\n");
            assertEquals(reader.getSourceLine(4), "This is fourth line.");
            assertEquals(reader.getSourceLine(5), "");
        }
        {
            SourceTextReader reader = new SourceTextReader(data1);
            while (reader.available() && reader.getLine() != 3) reader.read();
            assertEquals(reader.getSourceLine(1), "This is first line.\r");
            assertEquals(reader.getSourceLine(2), "This is second line.\n");
            assertEquals(reader.getSourceLine(3), "");
            assertEquals(reader.getSourceLine(4), "");
        }
    }

    @Test
    public void testGetCurrentLine() throws Exception
    {
        {
            SourceTextReader reader = new SourceTextReader(data1);
            assertEquals(reader.getCurrentLine(), "This is first line.");

            while (reader.available() && reader.getLine() != 2) reader.read();
            assertEquals(reader.getCurrentLine(), "This is second line.");

            for (int i = 0; i < 24; i++) reader.read();
            assertEquals(reader.getCurrentLine(), "This is third line.");
        }
        {
            SourceTextReader reader = new SourceTextReader("\n\n");
            assertEquals(reader.getCurrentLine(), "");
        }

    }

    @Test
    public void testCountReadLines() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        assertEquals(reader.countReadLines(), 0);

        while (reader.available() && reader.getLine() != 1) reader.read();
        assertEquals(reader.countReadLines(), 0);

        while (reader.available() && reader.getLine() != 2) reader.read();
        assertEquals(reader.countReadLines(), 1);

        while (reader.available() && reader.getLine() != 3) reader.read();
        assertEquals(reader.countReadLines(), 2);

        while (reader.available() && reader.getLine() != 6) reader.read();
        assertEquals(reader.countReadLines(), 4);
    }

    @Test
    public void testGetNextCharAt() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data2);
        assertEquals(reader.next(0), '.');
        assertEquals(reader.next(1), 't');
        assertEquals(reader.next(2), 'e');
        assertEquals(reader.next(3), 's');
        assertEquals(reader.next(4), 't');
        assertEquals(reader.next(5), '\\');
        assertEquals(reader.next(6), '{');
        assertEquals(reader.next(7), '1');
        assertEquals(reader.next(8), '5');
        assertEquals(reader.next(9), '}');
        assertEquals(reader.next(10), 0);
        assertEquals(reader.next(11), 0);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testGetNextCharAt_WhenIndexBelowZero() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data2);
        assertEquals(reader.next(-1), 0);
    }

    @Test
    public void testGetCharset() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(test_win1251);
        reader.setCharset("Windows-1251");
        assertEquals(reader.getCharset(), "Windows-1251");
    }

    @Test
    public void testSetCharset() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(test_win1251);

        reader.mark();
        reader.read(); reader.read(); reader.read();
        assertEquals(reader.readMarked(), "ab_");

        reader.setCharset("Windows-1251");
        while (reader.available() && reader.getLine() != 2) reader.read();
        assertEquals(reader.readMarked(), "ab_тест");
    }

    @Test
    public void testSetCharset_fromUTF8() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(test_utf8);

        reader.mark();
        reader.read(); reader.read(); reader.read();
        assertEquals(reader.readMarked(), "ab_");

        reader.setCharset("UTF-8");
        while (reader.available() && reader.getLine() != 2) reader.read();
        assertEquals(reader.readMarked(), "ab_тест");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testSetCharset_WhenConstructFromString_MustException() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);
        reader.setCharset("window-1251");
    }

    @Test
    public void testGetTextChunk() throws Exception
    {
        {
            SourceTextReader reader = new SourceTextReader(data1);

            TextChunk chunk = reader.getTextChunk();
            assertEquals(chunk.getSourceLine(), "This is first line.");
            assertEquals(chunk.getCol(), 0);

            for (int i = 0; i < 4; i++) reader.read();
            chunk = reader.getTextChunk();
            assertEquals(chunk.getSourceLine(), "This is first line.");
            assertEquals(chunk.getCol(), 4);

            for (int i = 0; i < 16; i++) reader.read();
            chunk = reader.getTextChunk();
            assertEquals(chunk.getSourceLine(), "This is second line.");
            assertEquals(chunk.getCol(), 0);
        }
        {
            SourceTextReader reader = new SourceTextReader("\tThis is \ttabbed source line.");

            TextChunk chunk = reader.getTextChunk();
            assertEquals(chunk.getSourceLine(), "    This is     tabbed source line.");
            assertEquals(chunk.getCol(), 0);

            reader.read();
            chunk = reader.getTextChunk();
            assertEquals(reader.getLast(), '\t');
            assertEquals(reader.next(), 'T');
            assertEquals(chunk.getCol(), 5);

            for (int i = 0; i < 8; i++) reader.read();
            chunk = reader.getTextChunk();
            assertEquals(reader.getLast(), ' ');
            assertEquals(reader.next(), '\t');
            assertEquals(chunk.getCol(), 13);

            reader.read();
            chunk = reader.getTextChunk();
            assertEquals(reader.getLast(), '\t');
            assertEquals(reader.next(), 't');
            assertEquals(chunk.getCol(), 18);
        }
    }

    @Test
    public void testGetTextChunk_WithArguments() throws Exception
    {
        SourceTextReader reader = new SourceTextReader(data1);

        for (int i = 0; i < 4; i++) reader.read();

        TextPosition position = reader.getPosition();
        TextChunk chunk = reader.getTextChunk(position);

        assertEquals(chunk.getSourceLine(), "This is first line.");
        assertEquals(chunk.getCol(), 4);

        for (int i = 0; i < 20; i++) reader.read();

        position = reader.getPosition();
        chunk = reader.getTextChunk(position);

        assertEquals(chunk.getSourceLine(), "This is second line.");
        assertEquals(chunk.getCol(), 4);
    }
}