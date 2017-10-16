package org.w3c.utils.css.parsers;

/**
 *
 *
 * Created by k002 on 09.10.2017.
 */
public class ParserMarker
{
    private final ParserMarker parent;

    private final int sourceLine;

    private final int startPos;

    private final int finalPos;

    public ParserMarker(ParserMarker parent, int sourceLine, int startPos, int finalPos)
    {
        this.parent = parent;
        this.sourceLine = sourceLine;
        this.startPos = startPos;
        this.finalPos = finalPos;
    }

    public ParserMarker getParent() {
        return parent;
    }

    public int getSourceLine() {
        return sourceLine;
    }

    public int getStartPos() {
        return startPos;
    }

    public int getFinalPos() {
        return finalPos;
    }
}
