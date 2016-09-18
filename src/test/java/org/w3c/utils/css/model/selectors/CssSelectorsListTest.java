package org.w3c.utils.css.model.selectors;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SelectorsList functional tests.
 *
 * Created by Home on 18.09.2016.
 */
public class CssSelectorsListTest
{
    @Test
    public void testTestModel() throws Exception
    {
        String text = "[data-text=',icon,'] ,p.article:nth-child(even) ~ font[color=\"red\"], p.footer::first-line + span, [class^='col-xs-'] > .sub-col ~ icon";
        CssSelectorsList list = new CssSelectorsList(text);

        Assert.assertEquals(list.getSelectors().size(), 4);
    }
}