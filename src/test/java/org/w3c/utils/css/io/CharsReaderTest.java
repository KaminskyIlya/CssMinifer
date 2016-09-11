package org.w3c.utils.css.io;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * CharsReader unit tests.
 *
 * Created by Home on 29.11.2015.
 */
public class CharsReaderTest
{

    @Test
    public void testRead() throws Exception
    {
        CharsReader reader = new CharsReader("Test");
        assertEquals(reader.read(), 'T');
        assertEquals(reader.read(), 'e');
        assertEquals(reader.read(), 's');
        assertEquals(reader.read(), 't');
        assertEquals(reader.read(), 0);
        assertEquals(reader.read(), 0);
    }

    @Test
    public void testAvailable() throws Exception
    {
        CharsReader reader = new CharsReader("Test");
        assertTrue(reader.available());
        reader.read();
        assertTrue(reader.available());
        reader.read();
        assertTrue(reader.available());
        reader.read();
        assertTrue(reader.available());
        reader.read();
        assertFalse(reader.available());
    }

    @Test
    public void testGetPos() throws Exception
    {
        CharsReader reader = new CharsReader("Test");
        assertEquals(reader.getPos(), 0);
        reader.read();
        assertEquals(reader.getPos(), 1);
        reader.read();
        assertEquals(reader.getPos(), 2);
        reader.read();
        assertEquals(reader.getPos(), 3);
        reader.read();
        assertEquals(reader.getPos(), 4);
        reader.read();
        assertEquals(reader.getPos(), 4);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructor_WhenNullSource_MustFail() throws Exception
    {
        new CharsReader(null);
    }

    @Test
    public void testRead_WhenSourceIsEmpty() throws Exception
    {
        CharsReader reader = new CharsReader("");
        assertEquals(reader.read(), 0);
        assertEquals(reader.read(), 0);
        assertEquals(reader.read(), 0);
    }

    @Test
    public void testAvailable_WhenSourceIsEmpty() throws Exception
    {
        CharsReader reader = new CharsReader("");
        assertFalse(reader.available());
    }

    @Test
    public void testGetPos_WhenSourceIsEmpty() throws Exception
    {
        CharsReader reader = new CharsReader("");
        assertEquals(reader.getPos(), 0);
    }

    @Test
    public void testGetSource() throws Exception
    {
        CharsReader reader = new CharsReader("Test me");
        assertEquals(reader.getSource(), "Test me");
    }

    @Test
    public void testReset() throws Exception
    {
        CharsReader reader = new CharsReader("Under test");
        reader.read();
        reader.mark();
        reader.read();

        reader.reset();
        assertEquals(reader.read(), 'n');
    }

    @Test
    public void testReadMarked() throws Exception
    {
        CharsReader reader = new CharsReader("Under test");
        reader.mark();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), "Under");
    }

    @Test
    public void testReadMarked_WithoutMark_MustReturnNull() throws Exception
    {
        CharsReader reader = new CharsReader("Under test");
        reader.read();
        reader.read();
        reader.read();
        assertNull(reader.readMarked());
    }

    @Test
    public void testReadMarkedTrimmedRight() throws Exception
    {
        CharsReader reader = new CharsReader(":not(:link)");
        reader.mark();
        assertEquals(reader.readMarkedTrimmedRight(), "");

        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), ":not(");
        assertEquals(reader.readMarkedTrimmedRight(), ":not");
    }

    @Test
    public void testReadMarkedTrimmedLeft() throws Exception
    {
        CharsReader reader = new CharsReader(":not(:link)");
        reader.mark();
        assertEquals(reader.readMarkedTrimmedLeft(), "");

        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), ":not(");
        assertEquals(reader.readMarkedTrimmedLeft(), "not(");
    }

    @Test
    public void testReadMarkedTrimmedBoth() throws Exception
    {
        CharsReader reader = new CharsReader(":not(:link)");
        reader.mark();
        assertEquals(reader.readMarkedTrimmedLeft(), "");

        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), ":not(");
        assertEquals(reader.readMarkedTrimmedBoth(), "not");

    }

    @Test
    public void testMark() throws Exception
    {
        CharsReader reader = new CharsReader("Under test");

        reader.mark(); // <-- seek on a first word
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), "Under");

        reader.read(); // skip space
        reader.mark(); // <-- seed on a second word
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), "test");
    }

    @Test
    public void testMarkBackward() throws Exception
    {
        CharsReader reader = new CharsReader("P[align]");
        reader.read(); //P
        reader.read(); //[
        reader.mark(1); // marked on [
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        reader.read();
        assertEquals(reader.readMarked(), "[align]");
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testMarkBackward_WithoutMark_MustFail() throws Exception
    {
        CharsReader reader = new CharsReader("P[align]");
        reader.read();
        reader.mark(2); // exception here
    }
}