package org.w3c.utils.css.model.selectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.model.exceptions.CssParsingException;

import static org.testng.Assert.assertEquals;

/**
 * Attribute selector functional tests
 *
 * Created by Home on 05.12.2015.
 */
public class AttributeSelectorTest
{

    @Test(dataProvider = "selectorSamples")
    public void testSelectorAnalyze(String source, String ns, String name, char matcher, String value) throws Exception
    {
        AttributeSelector tested = new AttributeSelector(source);
        assertEquals(tested.getSelector(), source);
        assertEquals(tested.getNamespace(), ns);
        assertEquals(tested.getAttribute(), name);
        assertEquals(tested.getMatcher(), matcher);
        assertEquals(tested.getValue(), value);
    }

    @DataProvider
    private Object[][] selectorSamples()
    {
        return new Object[][] {
                {"src^='/img/portal'", "", "src", '^', "/img/portal"},
                {"|src~='/img/portal'", "", "src", '~', "/img/portal"},
                {"*|src*='/img/portal'", "*", "src", '*', "/img/portal"},
                {"foo|src|='/img/portal'", "foo", "src", '|', "/img/portal"},
                {"src^=/img/portal", "", "src", '^', "/img/portal"},
                {"  src  ^=  '/img/portal'   ", "", "src", '^', "/img/portal"},
                {"align", "", "align", (char)0, null},
                {"href", "", "href", (char)0, null},
                {"   href   ", "", "href", (char)0, null},
                {" href='a' ", "", "href", '=', "a"},
                {" href='b'", "", "href", '=', "b"},
                {"href='c' ", "", "href", '=', "c"},
                {" href ='d' ", "", "href", '=', "d"},
                {" href ^='e' ", "", "href", '^', "e"},
                {" href^= 'f' ", "", "href", '^', "f"},
                {" href^= g", "", "href", '^', "g"},
                {" href*= g", "", "href", '*', "g"},
                {" href|= g", "", "href", '|', "g"},
                {" href~= 'g'", "", "href", '~', "g"},
                {" href$= 'g'", "", "href", '$', "g"},
                {" href ^= /img/icons/", "", "href", '^', "/img/icons/"},
                {" href ^= /img/icons/ ", "", "href", '^', "/img/icons/"},
                {" href ^= '/img/ icons/' ", "", "href", '^', "'/img/ icons/'"},
                {" href ^= '/img/i\\'cons/' ", "", "href", '^', "/img/i\\'cons/"},
                {"   href   ^=   '/img/ icons/'   ", "", "href", '^', "'/img/ icons/'"},
                {"   href   ^=   '/img/i\\'cons/'   ", "", "href", '^', "/img/i\\'cons/"},
        };
    }


    @Test(dataProvider = "badSelectorSamples", expectedExceptions = {CssParsingException.class})
    public void testBadSelectors(String source) throws Exception
    {
        new AttributeSelector(source); // exception here
    }

    @DataProvider
    private Object[][] badSelectorSamples()
    {
        return new Object[][]{
                {"    "},
                {"src="},
                {"src ="},
                {"src = "},
                {"src*="},
                {"src *= "},
//                {"src * src"}, //FIXME
                {" href='"},
                {" href\\='"},
                {" href=   "},
                {" href=''  "},
                {" href =   ''  "},
        };
    }

    @Test(dataProvider = "classMatcherTest")
    public void testIsClassMatcher(String text, boolean expected) throws Exception
    {
        AttributeSelector selector = new AttributeSelector(text);
        boolean actual = selector.isClassMatcher();
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] classMatcherTest()
    {
        return new Object[][] {
                {"class~=btn-large", true},
                {"class^=col-xs-", false},
                {"class ~= btn-large", true},
        };
    }

    @Test(dataProvider = "idMatcherTest")
    public void testIsIdMatcher(String text, boolean expected) throws Exception
    {
        AttributeSelector selector = new AttributeSelector(text);
        boolean actual = selector.isIdMatcher();
        assertEquals(actual, expected);
    }

    @DataProvider
    private Object[][] idMatcherTest()
    {
        return new Object[][] {
                {"id=message", true},
                {"id ^= dialog-", false},
                {"id = dialog-window", true},
        };
    }
}