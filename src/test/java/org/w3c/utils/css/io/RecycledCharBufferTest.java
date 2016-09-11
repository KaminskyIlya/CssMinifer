package org.w3c.utils.css.io;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Functional and unit tests for RecycledCharBuffer class.
 *
 * Created by Home on 07.11.2015.
 */
public class RecycledCharBufferTest
{

    @Test(expectedExceptions = {NullPointerException.class})
    public void testRecycledCharBuffer_WithStringBufferConstructor() throws Exception
    {
        new RecycledCharBuffer((StringBuffer)null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testRecycledCharBuffer_WithRecycledCharBufferConstructor() throws Exception
    {
        new RecycledCharBuffer((RecycledCharBuffer)null);
    }

    @Test
    public void testRead()
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.read(), 't');
        assertEquals(buffer.read(), 'e');
        assertEquals(buffer.read(), 's');
        assertEquals(buffer.read(), 't');
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void testRead_WithOverflow_MustFail()
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.read();
        buffer.read();
        buffer.read();
        buffer.read();
        buffer.read(); // the exception is excepted here
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void testSkip_WithOverflow_MustFail()
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.skip(4);
        buffer.read(); // the exception is excepted here
    }

    @Test
    public void testRead_WithSkipNext_NormalMode() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.skip();
        assertEquals(buffer.read(), 'e');
        buffer.skip();
        assertEquals(buffer.read(), 't');
    }

    @Test
    public void testRead_WithReadNext_NormalMode() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.read(), 't');
        assertEquals(buffer.next(), 'e');

        assertEquals(buffer.read(), 'e');
        assertEquals(buffer.next(), 's');

        assertEquals(buffer.read(), 's');
        assertEquals(buffer.next(), 't');

        assertEquals(buffer.read(), 't');
        assertEquals(buffer.next(), '\0');
    }

    @Test
    public void testNext_WithoutRead() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.next(), 't');
        assertEquals(buffer.next(), 't');
        assertEquals(buffer.next(), 't');
    }


    @Test
    public void testReadAndSkipAndWrite_WithoutBufferOverflow() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("This is a test string!");
        buffer.write(buffer.read());
        buffer.skip();
        buffer.write('e');
        assertEquals(buffer.read(), 'i');
        buffer.write(buffer.read());
        char c = buffer.read();
        buffer.write('t');
        buffer.skip(7);
        buffer.write(c);
        buffer.write('p');
        buffer.write('a');
        buffer.write('s');
        buffer.write('s');
        buffer.write('e');
        buffer.write('d');
        buffer.write('!');

        assertEquals(buffer.toString(), "Test passed!");
    }

    @Test
    public void testAvailable_WhenBufferIsEmpty_MustReturnFalse() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("");
        assertFalse(buffer.available());
    }

    @Test
    public void testAvailable_DueBufferReading() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.read();
        assertTrue(buffer.available());
        buffer.read();
        assertTrue(buffer.available());
        buffer.read();
        assertTrue(buffer.available());
        buffer.read();
        assertFalse(buffer.available());
    }

    @Test
    public void testReuse() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("Hello world!");
        assertEquals(buffer.getOutput(), "");

        buffer.skip(7);
        buffer.write('H');
        buffer.write('i');
        buffer.write('!');

        assertEquals(buffer.getOutput(), "Hi!");

        buffer.reuse();
        assertEquals(buffer.getOutput(), ""); // after reuse we has empty output buffer

        buffer.skip(2);
        buffer.write('1');
        buffer.write('2');
        assertEquals(buffer.getOutput(), "12");
    }

    @Test
    public void testReuse_WhenNotRead() throws Exception
    {
        String source = "Hello world!";
        RecycledCharBuffer buffer = new RecycledCharBuffer(source);
        assertEquals(buffer.getSourceSize(), source.length());
        buffer.reuse();
        assertEquals(buffer.getSourceSize(), source.length());
    }

    @Test
    public void testGetSourceSizeAndOutputSize_BeforeAndAfterRewind() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("Hello world!");
        assertEquals(buffer.getSourceSize(), 12);
        assertEquals(buffer.getOutputSize(), 0);

        buffer.skip(7);
        buffer.write('p');
        buffer.write('a');
        buffer.write('s');
        buffer.write('s');
        buffer.write('e');
        buffer.write('d');
        buffer.write('!');
        assertEquals(buffer.getSourceSize(), 12);
        assertEquals(buffer.getOutputSize(), 7);

        buffer.reuse();
        assertEquals(buffer.getSourceSize(), 7);
        assertEquals(buffer.getOutputSize(), 0);
    }

    @Test
    public void testPipe()
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("This test must be passed!");
        buffer.skip(5);
        buffer.pipe();
        buffer.pipe(4);
        buffer.skip(8);
        buffer.pipe(7);
        assertEquals(buffer.getOutput(), "test passed!");
    }

    @Test
    public void testRefill() throws Exception
    {
        String source = "This test must be passed!";

        RecycledCharBuffer buffer = new RecycledCharBuffer(source);
        assertEquals(buffer.getOutput(), "");
        buffer.refill();
        assertEquals(buffer.getOutput(), source);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void testRefill_WhenBufferIsDirty_MustFail() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("This is must be raise exception!");
        buffer.pipe(); // write some data to buffer to make it dirty
        buffer.refill();
    }

    @Test
    public void testNext() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.next(), 't');
        assertEquals(buffer.next(), 't');
        assertEquals(buffer.next(), 't');

        buffer.read();
        assertEquals(buffer.next(), 'e');
        assertEquals(buffer.next(), 'e');
    }

    @Test
    public void testAvailable() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("");
        assertFalse(buffer.available());

        buffer = new RecycledCharBuffer("test");
        assertTrue(buffer.available());

        buffer = new RecycledCharBuffer("test").refill();
        assertFalse(buffer.available());
    }

    @Test
    public void testWrite() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.read();
        buffer.write('H');
        buffer.read();
        buffer.write('i');
        buffer.read();
        buffer.write('!');
        assertEquals(buffer.getOutput(), "Hi!");
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void testWrite_InEmptySourceBuffer_MustFail() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("");
        buffer.write(' ');
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void testWrite_WithBufferOverflow_MustFail() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.read();
        buffer.read();
        buffer.write(' ');
        buffer.write(' ');
        buffer.write(' '); // the exception is excepted here
    }

    @Test
    public void testSkip() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        buffer.skip();
        assertEquals(buffer.next(), 'e');
    }

    @Test
    public void testSkip1() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test 1");
        buffer.skip(5);
        assertEquals(buffer.next(), '1');
    }

    @Test
    public void testGetSourceSize() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.getSourceSize(), 4);

        buffer = new RecycledCharBuffer("");
        assertEquals(buffer.getSourceSize(), 0);
    }

    @Test
    public void testGetOutputSize() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test");
        assertEquals(buffer.getOutputSize(), 0);

        buffer.skip(2);
        buffer.write(' ');
        buffer.write('1');
        assertEquals(buffer.getOutputSize(), 2);
    }

    @Test
    public void testGetOutput() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test passed");
        buffer.skip(5);
        buffer.pipe(6);
        assertEquals(buffer.getOutput(), "passed");
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testRecycledCharBuffer_WithStringConstructor() throws Exception
    {
        new RecycledCharBuffer((String)null);
    }

    @Test
    public void testToString() throws Exception
    {
        RecycledCharBuffer buffer = new RecycledCharBuffer("test passed");
        buffer.pipe(4);
        assertEquals(buffer.toString(), "test");
    }
}