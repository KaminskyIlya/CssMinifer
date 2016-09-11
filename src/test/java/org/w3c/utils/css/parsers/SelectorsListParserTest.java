package org.w3c.utils.css.parsers;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.w3c.utils.css.Reporter;
import org.w3c.utils.css.io.CssTextReader;
import org.w3c.utils.css.model.CssPage;
import org.w3c.utils.css.model.exceptions.CssParsingException;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;
import org.w3c.utils.css.model.selectors.CssSelectorsList;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Home on 28.08.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SelectorsListParser.class/*, CssSelectorsList.class, DeclarationsListParser.class, Reporter.class*/})
public class SelectorsListParserTest
{
    @Mock
    private CssTextReader reader;
    @Mock
    private CssTextTokenizer tokenizer;
    @Mock
    private CssPage sheet;
    @Mock
    private Collection<CssSelectorsList> collection;


    @Before
    public void setUp() throws Exception
    {
        EasyMock.reset(reader, tokenizer, sheet, collection);
    }

    /**
     * Test ProcessSelectorsList private method for detection result.
     * Important rule: CssSelectorsList constructor call no produce any exceptions.
     */
    @Test
    public void testProcessSelectorsList_WhenValidSelector() throws Exception
    {
        //
        // Prepare internal mocks
        //
        String partialOfCssText = "p.article:nth-child(even) ~ font[color='red'], p.footer::first-line + span, h1 > p ~ em"; // anything data

        // this variable created in tested private method
        CssSelectorsList selectorsList = PowerMock.createMockAndExpectNew(CssSelectorsList.class, partialOfCssText);
        PowerMock.replay(CssSelectorsList.class);

        EasyMock.expect(reader.getPosition()).andReturn(new TextPosition(4, 0));

        EasyMock.expect(tokenizer.consumeSelectorList()).andReturn(partialOfCssText);
        EasyMock.expect(tokenizer.getReader()).andReturn(reader);

        EasyMock.expect(collection.add(selectorsList)).andReturn(true);
        EasyMock.expect(sheet.getSelectorsList()).andReturn(collection);

        EasyMock.replay(reader, tokenizer, collection, sheet);

        //
        // Prepare tested class
        //

        SelectorsListParser parser = new SelectorsListParser(tokenizer);
        parser.setSheet(sheet);

        //
        // Tests
        //

        // When CssSelectorsList not thrown exception
        Boolean valid = Whitebox.invokeMethod(parser, "processSelectorsList");

        // we MUST get TRUE
        assertEquals(valid, true);

        // and "selectors" property MUST setup
        assertEquals(parser.getSelectors(), selectorsList);

        // and CssSelectorsList was added in collection of sheet
        EasyMock.verify(collection);
    }

    /**
     * Test ProcessSelectorsList private method for detection result.
     * Important rule: CssSelectorsList constructor call produce CssParsingException
     * with not CRITICAL level.
     */
    @Test
    public void testProcessSelectorsList_WhenInvalidSelector_NotCritical() throws Exception
    {
        //
        // Prepare internal mocks
        //
        String partialOfCssText = "p.article:nth-child(even) ~ font[color='red'], p.footer::first-line + span, h1 > p ~ em"; // anything data

        EasyMock.expect(reader.getPosition()).andReturn(new TextPosition(4, 0));
        EasyMock.expect(reader.getSourceLine(4)).andReturn(partialOfCssText);
        EasyMock.expect(tokenizer.consumeSelectorList()).andReturn(partialOfCssText);
        EasyMock.expect(tokenizer.getReader()).andReturn(reader).times(2);
        EasyMock.replay(reader, tokenizer);

        // this variable created in tested private method
        CssSelectorsList selectorsList = PowerMock.createMock(CssSelectorsList.class);
        PowerMock.expectNew(CssSelectorsList.class, partialOfCssText).andThrow(new CssParsingException("Invalid selector", 0, EExceptionLevel.ERROR));
        PowerMock.replay(CssSelectorsList.class);

        // this variable created in tested private method
        Reporter reporter = PowerMock.createMockAndExpectNew(Reporter.class);
        PowerMock.replay(Reporter.class);

        // set scenario of repoter using
        //reporter.setLevel(EExceptionLevel.ERROR); // tested method must call that with specified arguments
        /*reporter.addMessage(null);
        PowerMock.expectLastCall().times(3);
        reporter.addAccent(0, 0, null);
        PowerMock.expectLastCall().times(1);
        reporter.report(); // and that
        PowerMock.expectLastCall().times(1);*/
        //PowerMock.replay(reporter);

        //
        // Prepare tested class
        //
        SelectorsListParser parser = new SelectorsListParser(tokenizer);

        //
        // Tests
        //

        // When CssSelectorsList thrown exception with normal level
        Boolean valid = Whitebox.invokeMethod(parser, "processSelectorsList");

        // we MUST get FALSE
        assertEquals(valid, false);

        //PowerMock.verify(reporter);
    }
}