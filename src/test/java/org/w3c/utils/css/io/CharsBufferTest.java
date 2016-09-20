package org.w3c.utils.css.io;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CharsBuffer functional tests.
 *
 * Created by Home on 19.09.2016.
 */
public class CharsBufferTest
{
    @Test
    public void testGetBuffer() throws Exception
    {
        CharsBuffer buffer = new CharsBuffer(4);
        buffer.append('n');
        assertEquals(buffer.getBuffer(), "n");
        buffer.append('o');
        assertEquals(buffer.getBuffer(), "no");
        buffer.append('t');
        assertEquals(buffer.getBuffer(), "not");
        buffer.append(' ');
        assertEquals(buffer.getBuffer(), "not ");
        buffer.append('a');
        assertEquals(buffer.getBuffer(), "ot a");
        buffer.append('n');
        assertEquals(buffer.getBuffer(), "t an");
        buffer.append('d');
        assertEquals(buffer.getBuffer(), " and");
        buffer.append('(');
        assertEquals(buffer.getBuffer(), "and(");
        buffer.reset();
        assertEquals(buffer.getBuffer(), "");
    }
}