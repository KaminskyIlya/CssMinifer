package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sun.jdbc.odbc.ee.ObjectPool;
import test.TestBooleanMethod;
import test.TestHelpers;

import static org.testng.Assert.*;

/**
 * Simple Processor Tests
 *
 * Created by k002 on 08.09.2016.
 */
public class SimpleProcessorTest {

    @Test(dataProvider = "dataProvider_IsInString")
    public void testIsInString(String text, String expectedFlags) throws Exception
    {
        final SimpleProcessor processor = new SimpleProcessorChild();

        TestHelpers.testFlowByBitmap("IsInString", text, expectedFlags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isInString();
            }
        });
    }

    @DataProvider
    public Object[][] dataProvider_IsInString()
    {
        return new Object[][] {
                {" this is 'simple' test ",
                 "         11111111       "},
                {" this is 'sim\\'ple' test ",
                 "         1111111111       "},
                {" this is 'sim\"ple' test ",
                 "         111111111       "},
                {" this is \"sim'ple\" test ",
                  "         111111111       "},

                {"H1.member+H2{font-family:\"PT  Sans {font-size:10px;} ' \\\" Narrow\";font-size:10px;}H3.member+H4{font-family:\"PT  Sans  Narrow\";font-size:10px;color:blue;}",
                      "                         1111111111111111111111111111111111111111                                          111111111111111111                             "},
        };
    }

    @Test(dataProvider = "dataProvider_IsEscaped")
    public void testIsEscaped(String text, String expectedFlags) throws Exception
    {
        final SimpleProcessor processor = new SimpleProcessorChild();

        TestHelpers.testFlowByBitmap("isEscaped", text, expectedFlags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isEscaped();
            }
        });
    }

    @DataProvider
    public Object[][] dataProvider_IsEscaped()
    {
        return new Object[][] {
                {
                        new String(new char[]{'t','e','s','m','e'}),
                        new String(new char[]{'0','0','0','0','0'}),
                },
                {
                        new String(new char[]{'t','e','\\','m','e'}),
                        new String(new char[]{'0','0','0' ,'1','0'}),
                },
                {
                        new String(new char[]{'t','\\','\\','m','e'}),
                        new String(new char[]{'0','0' ,'1' ,'0','0'}),
                },
                {
                        new String(new char[]{'t','\\','\\','\\','e'}),
                        new String(new char[]{'0','0' ,'1' ,'0', '1'}),
                },
        };
    }

    @Test(dataProvider = "dataProvider_IsInWhitespace")
    public void testIsInWhitespace(String text, String expectedFlags) throws Exception
    {
        final SimpleProcessor processor = new SimpleProcessorChild();

        TestHelpers.testFlowByBitmap("isInWhitespace", text, expectedFlags, processor, new TestBooleanMethod() {
            public boolean test() {
                return processor.isInWhitespace();
            }
        });
    }

    @DataProvider
    public Object[][] dataProvider_IsInWhitespace()
    {
        return new Object[][] {
                {
                        new String(new char[]{'\\',' ','s','m','e'}),
                        new String(new char[]{'0' ,'1','0','0','0'}),
                },
                {
                        new String(new char[]{' ','"',' ',' ','"'}),
                        new String(new char[]{'1' ,'0','0','0','0'}),
                },
                {
                        new String(new char[]{'\\','"',' ',' ','"'}),
                        new String(new char[]{'0' ,'0','1','1','0'}),
                },
        };
    }




    /**
     * Descendant class of abstract class for test only
     */
    private static class SimpleProcessorChild extends SimpleProcessor
    {

    }
}