package org.w3c.utils.css.io;

import org.w3c.utils.css.help.CharUtils;
import org.w3c.utils.css.parsers.TextChunk;
import org.w3c.utils.css.parsers.TextPosition;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Read source text and remember its position
 *
 * Created by Home on 29.11.2015.
 */
public class SourceTextReader implements MarkableReader
{
    private String charset = Charset.defaultCharset().name();
    private byte buffer[] = null;
    private String source;
    private int row = 1; // reading line number
    private int col = 0; // reading column number
    private int pos = 0; // position in source
    private Mark mark = null; // mark position
    private char last = 0; // last symbol
    private char prev = 0; // previous symbol
    private List<Integer> lines; // start positions of source text lines
    private int escapes = 0; // count of '\' repeat

    /**
     * Default constructor. Build reader for specified string.
     *
     * @param source source of chars.
     * @throws NullPointerException
     */
    public SourceTextReader(String source)
    {
        if (source == null) throw new NullPointerException();
        this.source = source;
        this.lines = new ArrayList<Integer>();
        lines.add(0);
    }

    /**
     * Default constructor. Build reader from bytes with default charset.
     *
     * @param source byte buffer
     */
    public SourceTextReader(byte[] source)
    {
        if (source == null) throw new NullPointerException();
        this.buffer = source;
        this.source = new String(buffer);
        this.lines = new ArrayList<Integer>();
        lines.add(0);
    }

    /**
     * Advanced constructor. Build reader from bytes with specified charset.
     *
     * @param source byte buffer
     * @param charset charset
     * @throws UnsupportedEncodingException
     */
    public SourceTextReader(byte[] source, String charset) throws UnsupportedEncodingException
    {
        if (source == null) throw new NullPointerException();
        this.charset = charset;
        this.buffer = source;
        this.source = new String(buffer, charset);
        this.lines = new ArrayList<Integer>();
        lines.add(0);
    }

    public int getLine()
    {
        return row;
    }

    public int getPos()
    {
        return col;
    }

    public TextPosition getPosition()
    {
        return new TextPosition(row, col);
    }

    public char getLast()
    {
        return last;
    }

    /**
     * @return true, if next symbol is escaped
     */
    public boolean isEscaped()
    {
        return escapes % 2 == 1;
    }

    public boolean available()
    {
        return pos < source.length();
    }

    public char read()
    {
        return internalRead();
    }

    private char internalRead()
    {
        prev = last;
        if (prev == '\\') escapes++; else escapes = 0;

        if (available())
        {
            char c = source.charAt(pos++);
            char n = next();
            if (CharUtils.isWindowsEOL(c, n) || CharUtils.isEOL(c))
            {
                if (CharUtils.isWindowsEOL(c, n)) pos++;
                addNewLinePosition(row, pos);
                row++; col = 0;
                return (last = CharUtils.EOL);
            }
            else
            {
                col++;
                return (last = c);
            }
        }
        return 0;
    }

    private void addNewLinePosition(int r, int p)
    {
        int n = lines.size();
        assert r <= n;
        if (r == n) lines.add(p); else lines.set(r, p);
    }

    public char next()
    {
        return available() ? source.charAt(pos) : 0;
    }

    public void skip()
    {
        internalRead();
    }

    public void mark()
    {
        mark = new Mark(pos, row, col);
    }

    public void reset()
    {
        if (mark != null)
        {
            pos = mark.pos;
            row = mark.row;
            col = mark.col;
        }
        mark = null;
    }

    public String readMarked()
    {
        return (mark != null) ? source.substring(mark.pos, pos) : null;
    }

    public String getSourceLine(int number)
    {
        int n = lines.size();
        if (number > n || (number == n && available()) || number <= 0) return "";

        int start_pos = lines.get(number-1);
        int finish_pos = (number < n) ? lines.get(number) : source.length();

        return source.substring(start_pos, finish_pos);
    }

    public String getCurrentLine()
    {
        int start = lines.get(row-1);
        int end = start;
        int len = source.length();
        while (end < len && !CharUtils.isEOL(source.charAt(end))) end++;
        return source.substring(start, end);
    }

    public TextChunk getTextChunk()
    {
        return new TextChunk(getCurrentLine(), getPosition());
    }

    public TextChunk getTextChunk(TextPosition position)
    {
        return (position.getLine() == getLine()) ? new TextChunk(getCurrentLine(), getPosition()) : new TextChunk(getSourceLine(position.getLine()), position);
    }

    public int countReadLines()
    {
        return available() ? lines.size() - 1 : lines.size();
    }

    @Deprecated
    public char getNextCharAt(int shiftPos)
    {
        return next(shiftPos);
    }

    public char next(int shift)
    {
        assert shift >= 0;
        return (pos + shift < source.length()) ? source.charAt(pos + shift) : 0;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setCharset(String charset) throws UnsupportedEncodingException
    {
        if ( buffer == null ) throw new IllegalStateException("Operation not supported for object created from string.");

        if ( ! this.charset.equalsIgnoreCase(charset.replaceAll("-", "")) )
        {
            this.charset = charset;
            this.source = new String(buffer, charset);
        }
    }


    private static class Mark
    {
        int pos;
        int row;
        int col;

        public Mark(int pos, int row, int col)
        {
            this.pos = pos;
            this.row = row;
            this.col = col;
        }
    }
}
