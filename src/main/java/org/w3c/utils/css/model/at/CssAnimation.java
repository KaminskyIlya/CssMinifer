package org.w3c.utils.css.model.at;


import org.w3c.utils.css.enums.AtRules;
import org.w3c.utils.css.model.CssDeclaration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSS animations block.
 * '@keyframes' token model
 *
 * Created by Home on 21.08.2016.
 * @see  <a href="http://www.w3.org/TR/css3-animations/">animations</a>
  */
public class CssAnimation extends AtRule
{
    private Map<Integer, List<CssDeclaration>> keyframes = new HashMap<Integer, List<CssDeclaration>>(2);
    private String name;

    public CssAnimation(String name)
    {
        super(name);
        this.name = name;
    }

    @Override
    public AtRules getToken()
    {
        return AtRules.KEYFRAMES;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Map<Integer, List<CssDeclaration>> getKeyframes()
    {
        return keyframes;
    }
}
