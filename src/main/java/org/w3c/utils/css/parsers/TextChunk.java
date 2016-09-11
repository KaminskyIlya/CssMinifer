package org.w3c.utils.css.parsers;

import java.util.Arrays;

/**
 * Describes chunk of source code: single line and problem position.
 * If source line so long (for example, a big minimized css file in single line),
 * it may store and describe chunk of source line.
 *
 * Created by Home on 30.08.2016.
 */
public class TextChunk
{
    private static final int SOURCE_LINE_MAX_LENGTH = 80;
    private static final int MAX_POSITION_DISTANCE = 40;
    private static final int TAB_LEN = 4;
    private static final String TAB_PAD;

    static
    {
        char pad[] = new char[TAB_LEN];
        Arrays.fill(pad, ' ');
        TAB_PAD = new String(pad);
    }

    private final String source;
    private final int col;
    private final int length;

    /**
     * Make chunk of source text with specified position.
     * Truncate long text, and recalculate relative position.
     *
     * @param text source text.
     * @param position of text cursor.
     * @throws IllegalArgumentException when pos less then source length
     */
    public TextChunk(String text, TextPosition position)
    {
        if ( position.getCol() > text.length() ) throw new IllegalArgumentException();

        int tabs = getTabCharsCount(text, position.getCol());
        int pos = position.getCol() + tabs * TAB_LEN;

        length = position.getLength();
        text = text.replaceAll("\t", TAB_PAD);

        if ( pos > MAX_POSITION_DISTANCE )
        {
            int start = pos - MAX_POSITION_DISTANCE;
            int finish = Math.min(start + SOURCE_LINE_MAX_LENGTH, text.length());

            source = "..." + text.substring(start, finish) + (finish < text.length() ? "..." : "");
            col = MAX_POSITION_DISTANCE + (start > 0 ? 3 : 0);
        }
        else
        {
            int finish = Math.min(SOURCE_LINE_MAX_LENGTH, text.length());

            source = text.substring(0, finish) + (finish < text.length() ? "..." : "");
            col = pos;
        }
    }

    private int getTabCharsCount(String text, int finish)
    {
        int count = 0;
        for (int i = 0, n = text.length(); i < n && i < finish; i++)
        {
            if (text.charAt(i) == '\t') count++;
        }
        return count;
    }

    /**
     * Single line of source text.
     * May be truncated, if very long (over 255 chars).
     */
    public String getSourceLine()
    {
        return source;
    }

    /**
     * Relative position of truncated text,
     * where we has a problem. Calculated automatically
     * decencies of size length and initial position.
     * @return cursor position in truncated text
     */
    public int getCol()
    {
        return col;
    }

    /**
     * @return length of selected text in source string
     */
    public int getLength()
    {
        return length;
    }
}
