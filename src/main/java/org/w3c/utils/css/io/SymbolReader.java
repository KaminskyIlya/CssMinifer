package org.w3c.utils.css.io;

/**
 * Created by Home on 30.11.2015.
 */
public interface SymbolReader
{
    /**
     * Return char from source and move to next.
     *
     * @return next char from source or 0, if finished.
     */
    char read();

    /**
     * Return char from source without move to next.
     *
     * @return next char from source or 0, if finished
     */
    char next();

    /**
     * Return char at some distance from current reader position.
     *
     * @param shift distance of char from current position
     * @return char value or 0, if not more chars
     */
    char next(int shift);

    /**
     *
     * @return source not being read fully?
     */
    boolean available();

    /**
     *
     * @return position of reader.
     */
    int getPos();
}
