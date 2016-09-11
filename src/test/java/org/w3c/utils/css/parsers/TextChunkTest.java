package org.w3c.utils.css.parsers;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Home on 30.08.2016.
 */
public class TextChunkTest
{
    /**
     *
     * @param text sample of source text code
     * @param col column with a syntax problem
     * @param expected expected sample of source code after processing
     * @throws Exception
     */
    @Test(dataProvider = "dataProvider")
    public void testGetSourceLine(String text, int col, String expected, int shift) throws Exception
    {
        TextChunk chunk = new TextChunk(text, new TextPosition(1, col));
        String actual = chunk.getSourceLine();
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "dataProvider")
    public void testGetShift(String text, int col, String truncated, int expected) throws Exception
    {
        TextChunk chunk = new TextChunk(text, new TextPosition(1, col));
        int actual = chunk.getCol();
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_MustFail_WhenPositionAboveSourceLength() throws Exception
    {
        new TextChunk("", new TextPosition(1, 10));
    }

    @DataProvider
    public Object[][] dataProvider()
    {
        return new Object[][] {
                {"", 0, "", 0}, // empty string with zero position must return empty string and zero shift

                {"Small string with some problem in text. Not be truncated!", 15,
                 "Small string with some problem in text. Not be truncated!", 15},

                {"\tText with tab chars", 1,
                 "    Text with tab chars", 5},

                {"\tText with tab chars. So long text for display. Should be truncated at the end.", 1,
                 "    Text with tab chars. So long text for display. Should be truncated at the en...", 5},

                {"\tText with \ttab chars", 12,
                 "    Text with     tab chars", 20},

                {"This is a very long string (over 80 chars) with some syntax problem in code, but problem position near start of this string. Should be truncated only right!", 15,
                 "This is a very long string (over 80 chars) with some syntax problem in code, but...", 15},

                {"This is a very long string (over 80 chars) with some syntax problem in code, but problem position deep inside of this string. Should be truncated both!", 50,
                 "...very long string (over 80 chars) with some syntax problem in code, but problem p...", 43},

                {"This is a very long string (over 80 chars) with some syntax problem in code, but problem position near end of this string. Should be truncated only left!", 123,
                 "...oblem position near end of this string. Should be truncated only left!", 43},
        };
    }
}