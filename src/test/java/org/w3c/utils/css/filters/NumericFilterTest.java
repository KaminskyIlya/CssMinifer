package org.w3c.utils.css.filters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.testng.Assert.*;

/**
 * Numeric Filter functional test.
 *
 * Created by Home on 09.11.2015.
 */
public class NumericFilterTest
{

    @Test(dataProvider = "dataProvider")
    public void testApply_WithoutCompactBloatedNumbers(String source, String expected) throws Exception
    {
        NumericFilter filter = new NumericFilter(new RecycledCharBuffer(source));
        filter.setAccuracyScale(3);
        filter.setLossAccuracy(true);
        filter.setCompactBloated(false);
        filter.apply();
        assertEquals(filter.getBuffer().getOutput(), expected);
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][] {

                {".span[class*='-']{width:46.154424777%;height:18.7685444%}.col-xs-11{width:0.7777777777777%;height:88.9999999%}",
                 ".span[class*='-']{width:46.154%;height:18.769%}.col-xs-11{width:.778%;height:89%}"},

                {"@media screen and (max-width:46.7777px){.style{width:-0.50em}}",
                 "@media screen and (max-width:46.778px){.style{width:-.5em}}"},

                // test to round numbers
                {".style{color:rgba(0, 0, 0, 0.0008)}",
                 ".style{color:rgba(0, 0, 0, .001)}"},

                {".form-control:focus{border-color:#66afe9;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6);" +
                        "box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6)}",
                 ".form-control:focus{border-color:#66afe9;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);" +
                         "box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)}"},

                {".carousel-control.left{background-image:-webkit-linear-gradient(left,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);" +
                        "background-image:-o-linear-gradient(left,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);" +
                        "background-image:-webkit-gradient(linear,left top,right top,color-stop(0,rgba(0,0,0,.5)),to(rgba(0,0,0,0)));" +
                        "background-image:linear-gradient(to right,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);background-repeat:repeat-x;" +
                        "filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#80000000',endColorstr='#00000000',GradientType=1)}",

                 ".carousel-control.left{background-image:-webkit-linear-gradient(left,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);" +
                        "background-image:-o-linear-gradient(left,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);" +
                        "background-image:-webkit-gradient(linear,left top,right top,color-stop(0,rgba(0,0,0,.5)),to(rgba(0,0,0,0)));" +
                        "background-image:linear-gradient(to right,rgba(0,0,0,.5)0,rgba(0,0,0,0)100%);background-repeat:repeat-x;" +
                        "filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#80000000',endColorstr='#00000000',GradientType=1)}",
                }
        };
    }


    @Test(dataProvider = "dataProvider2")
    public void testApply_WithCompactBloatedNumbers(String source, String expected) throws Exception
    {
        NumericFilter filter = new NumericFilter(new RecycledCharBuffer(source));
        filter.setAccuracyScale(3);
        filter.setLossAccuracy(true);
        filter.setCompactBloated(true);
        filter.apply();
        assertEquals(filter.getBuffer().getOutput(), expected);
    }


    @DataProvider
    private Object[][] dataProvider2()
    {
        return new Object[][] {
                // test to compact bloated numbers
                {".style{width:1000px}",
                 ".style{width:1e3px}"},
                {".style{color:rgba(0, 0, 0, 0.0008)}",
                 ".style{color:rgba(0, 0, 0, 8e-4)}"},
       };
    }
}