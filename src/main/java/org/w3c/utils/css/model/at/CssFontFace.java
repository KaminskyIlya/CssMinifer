package org.w3c.utils.css.model.at;

import org.w3c.utils.css.enums.AtRules;
import org.w3c.utils.css.enums.FontStyle;
import org.w3c.utils.css.enums.FontWeight;
import org.w3c.utils.css.model.CssDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Font-face rule.
 * Created by Home on 21.08.2016.
 *
 * @see  <a href="https://www.w3.org/TR/css-fonts-3/#at-font-face-rule">ront-face rule</a>
 */
public class CssFontFace extends AtRule
{
    private String fontFamily;
    private FontStyle fontStyle;
    private FontWeight fontWeight;
    private List<CssDeclaration> declarations = new ArrayList<CssDeclaration>();

    public CssFontFace(String componentValue)
    {
        super(componentValue);
    }

    @Override
    public AtRules getToken()
    {
        return AtRules.FONT_FACE;
    }

    public String getFontFamily()
    {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily)
    {
        this.fontFamily = fontFamily;
    }

    public FontStyle getFontStyle()
    {
        return fontStyle;
    }

    public void setFontStyle(FontStyle fontStyle)
    {
        this.fontStyle = fontStyle;
    }

    public FontWeight getFontWeight()
    {
        return fontWeight;
    }

    public void setFontWeight(FontWeight fontWeight)
    {
        this.fontWeight = fontWeight;
    }

    public List<CssDeclaration> getDeclarations()
    {
        return declarations;
    }
}
