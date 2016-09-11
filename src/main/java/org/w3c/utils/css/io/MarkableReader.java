package org.w3c.utils.css.io;

/**
 * Markable symbol reader.
 *
 * Created by Home on 05.12.2015.
 */
public interface MarkableReader extends SymbolReader
{
    /**
     * Mark next symbol.
     * Remember current reader position for future restoring (via reset) or get last read string (readMarked).
     */
    void mark();

    /**
     * Restore reader position by marked symbol.
     */
    void reset();

    /**
     *
     * @return marked chunk of string source
     */
    String readMarked();
}
