package org.w3c.utils.css.model.selectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.filters.proc.SelectorProcessor;
import org.w3c.utils.css.io.CharsReader;
import org.w3c.utils.css.io.SymbolReader;
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
    public void testSelectorAnalyze(String source, String name, char matcher, String value) throws Exception
    {
        AttributeSelector tested = new AttributeSelector(source);
        assertEquals(tested.getSelector(), source);
        assertEquals(tested.getAttribute(), name);
        assertEquals(tested.getMatcher(), matcher);
        assertEquals(tested.getValue(), value);
    }

    @DataProvider
    private Object[][] selectorSamples()
    {
        return new Object[][] {
                {"src^='/img/portal'", "src", '^', "/img/portal"},
                {"'src'^='/img/portal'", "src", '^', "/img/portal"},
                {"  src  ^=  '/img/portal'   ", "src", '^', "/img/portal"},
                {"align", "align", (char)0, null},
                {"href", "href", (char)0, null},
                {"   href   ", "href", (char)0, null},
                {" href='a' ", "href", '=', "a"},
                {" href='b'", "href", '=', "b"},
                {"href='c' ", "href", '=', "c"},
                {" href ='d' ", "href", '=', "d"},
                {" href ^='e' ", "href", '^', "e"},
                {" href^= 'f' ", "href", '^', "f"},
                {" href^= g", "href", '^', "g"},
                {" href*= g", "href", '*', "g"},
                {" href|= g", "href", '|', "g"},
                {" href~= 'g'", "href", '~', "g"},
                {" href$= 'g'", "href", '$', "g"},
                {" href ^= /img/icons/", "href", '^', "/img/icons/"},
                {" href ^= /img/icons/ ", "href", '^', "/img/icons/"},
                {" href ^= '/img/ icons/' ", "href", '^', "'/img/ icons/'"},
                {" href ^= '/img/i\\'cons/' ", "href", '^', "/img/i\\'cons/"},
                {"   href   ^=   '/img/ icons/'   ", "href", '^', "'/img/ icons/'"},
                {"   href   ^=   '/img/i\\'cons/'   ", "href", '^', "/img/i\\'cons/"},
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
                {" href='"},
                {" href=   "},
                {" href=''  "},
                {" href =   ''  "},
        };
    }

    protected void processSymbol(SymbolReader reader, SelectorProcessor processor)
    {
        char current = reader.read();
        processor.before(current);
        processor.after(current);
    }
}