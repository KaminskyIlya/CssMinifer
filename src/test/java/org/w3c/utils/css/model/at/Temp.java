package org.w3c.utils.css.model.at;

import org.testng.annotations.Test;
import org.w3c.utils.css.enums.CssColor;

import static org.testng.Assert.*;

/**
 * Created by Home on 28.11.2015.
 */
public class Temp
{
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testEnumValueOf()
    {
        CssColor color = CssColor.valueOf("lime2");
        assertNull(color); // throws java.lang.IllegalArgumentException
    }
}
