package org.w3c.utils.css;

/**
 * Global singleton of program runtime configuration.
 *
 * Created by Home on 15.08.2016.
 */
public class Configuration
{
    private static final Configuration instance = new Configuration();

    // whitespaces
    private boolean removeWhiteSpaces = true;
    private boolean removeBlockComments = true;
    private boolean removeInlineComments = true;
    private boolean removeCopyrightInfo = true;

    // colors
    private boolean reduceColorSpace = true;
    private boolean reduceColorSpaceForBackgrounds = false;

    // numbers
    private boolean lossAccuracy = true;
    private int accuracyScale = 3;
    private boolean compactBloated = true;

    // texts
    private boolean makeAliasesForFonst = false;
    private boolean makeAliasesForAnimations = true;

    public static Configuration getInstance()
    {
        return instance;
    }

    public boolean isRemoveWhiteSpaces()
    {
        return removeWhiteSpaces;
    }

    public void setRemoveWhiteSpaces(boolean removeWhiteSpaces)
    {
        this.removeWhiteSpaces = removeWhiteSpaces;
    }

    public boolean isRemoveBlockComments()
    {
        return removeBlockComments;
    }

    public void setRemoveBlockComments(boolean removeBlockComments)
    {
        this.removeBlockComments = removeBlockComments;
    }

    public boolean isRemoveInlineComments()
    {
        return removeInlineComments;
    }

    public void setRemoveInlineComments(boolean removeInlineComments)
    {
        this.removeInlineComments = removeInlineComments;
    }

    public boolean isRemoveCopyrightInfo()
    {
        return removeCopyrightInfo;
    }

    public void setRemoveCopyrightInfo(boolean removeCopyrightInfo)
    {
        this.removeCopyrightInfo = removeCopyrightInfo;
    }

    public boolean isReduceColorSpace()
    {
        return reduceColorSpace;
    }

    public void setReduceColorSpace(boolean reduceColorSpace)
    {
        this.reduceColorSpace = reduceColorSpace;
    }

    public boolean isReduceColorSpaceForBackgrounds()
    {
        return reduceColorSpaceForBackgrounds;
    }

    public void setReduceColorSpaceForBackgrounds(boolean reduceColorSpaceForBackgrounds)
    {
        this.reduceColorSpaceForBackgrounds = reduceColorSpaceForBackgrounds;
    }

    public boolean isLossAccuracy()
    {
        return lossAccuracy;
    }

    public void setLossAccuracy(boolean lossAccuracy)
    {
        this.lossAccuracy = lossAccuracy;
    }

    public int getAccuracyScale()
    {
        return accuracyScale;
    }

    public void setAccuracyScale(int accuracyScale)
    {
        this.accuracyScale = accuracyScale;
    }

    public boolean isCompactBloated()
    {
        return compactBloated;
    }

    public void setCompactBloated(boolean compactBloated)
    {
        this.compactBloated = compactBloated;
    }
}
