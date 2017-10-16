package org.w3c.utils.css.model;

import org.w3c.utils.css.model.at.CssAnimation;
import org.w3c.utils.css.model.at.CssFontFace;

import java.util.ArrayList;
import java.util.List;

/**
 * Css Document: set of single css pages. Each pages is single media type.
 *
 * Created by Home on 12.11.2015.
 */
public class CssDocument
{
    private List<CssPage> pages = new ArrayList<CssPage>();
    private List<CssAnimation> animations = new ArrayList<CssAnimation>();
    private List<CssFontFace> fonts = new ArrayList<CssFontFace>();

    public CssDocument()
    {

    }

    public CssPage addPage()
    {
        CssPage page = new CssPage();
        pages.add(page);
        return page;
    }

    public CssAnimation addAnimation(String name)
    {
        CssAnimation animation = new CssAnimation(name);
        animations.add(animation);
        return animation;
    }

    public CssFontFace addFont()
    {
        CssFontFace fontFace = new CssFontFace();
        fonts.add(fontFace);
        return fontFace;
    }

    public List<CssPage> getPages()
    {
        return pages;
    }

    public List<CssAnimation> getAnimations()
    {
        return animations;
    }

    public List<CssFontFace> getFonts()
    {
        return fonts;
    }
}
