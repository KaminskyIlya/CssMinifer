package org.w3c.utils.css.filters.proc;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.TestBooleanMethod;
import test.TestHelpers;

/**
 * CombinatorProcessor functional tests.
 *
 * Created by Home on 17.09.2016.
 */
public class CombinatorProcessorTest
{

    @Test(dataProvider = "dataProvider_CanProcess")
    public void testCanProcess(String selector, String expectedFlags) throws Exception
    {
        final FlowProcessor processor = new CombinatorProcessor();

        TestHelpers.testFlowByBitmap("canProcess", selector, expectedFlags, processor, new TestBooleanMethod()
        {
            public boolean test()
            {
                return processor.canProcess();
            }
        });
    }


    @DataProvider
    private Object[][] dataProvider_CanProcess()
    {
        return new Object[][]{
                {"*", " "},
                {
                        "* ~ H1 + P .selector#id > .wrapper > a:hover ~ p::before",
                        " 111  111 1            111        111       111         "
                },
                {
                        "* ~ H1 + P * .wrapper > a:hover ~ p::before",
                        " 111  111 1 1        111       111         "
                },
                {
                        "*.active",
                        "        "
                },
                {
                        ".active ~ .wrapper > .prefix + DIV",
                        "       111        111       111   "
                },
                {
                        ".active.active + P:pseudo",
                        "              111        "
                },
                {
                        "#hash-code.active",
                        "                 "
                },
                {
                        "#hash-code:nth-child(2n+1):not(:nth-child(3n))[href='test ~ > + for.class'].selected  >  div",
                        "                                                                                    11111   "
                },
                {
                        "html > body > p.article +  p.pages:first-line ~ span[ color ~= red ]",
                        "    111    111         1111                  111                    "
                },
        };
    }

}