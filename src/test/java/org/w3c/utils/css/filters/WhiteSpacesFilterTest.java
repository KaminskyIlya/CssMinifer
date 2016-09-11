package org.w3c.utils.css.filters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.testng.Assert.*;

/**
 * WhiteSpaceFilter functional tests.
 *
 * Created by Home on 07.11.2015.
 */
public class WhiteSpacesFilterTest
{

    @Test(dataProvider = "dataProvider")
    public void testApply(String source, String expected) throws Exception
    {
        WhiteSpacesFilter filter = new WhiteSpacesFilter(new RecycledCharBuffer(source));
        filter.apply();
        assertEquals(filter.getBuffer().getOutput(), expected);
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][]{

            // Common test for remove first spaces, repeated spaces, with skip in spaces strings and url-corners
            {"   H1.member[font='Arial   sans serif']    .wrapped { border :  1px   solid   black ! important;     color:#white; font-size: 12pt; background: url( /test space.png ); }",
             "H1.member[font='Arial   sans serif'] .wrapped{border:1px solid black!important;color:#white;font-size:12pt;background:url(/test space.png)}"},

            // Test for remove alternative space symbols
            {"H1.member + H2 { font-family: \"PT  Sans  Narrow\";\r\n font-size: \t\t\f10px; }",
             "H1.member+H2{font-family:\"PT  Sans  Narrow\";font-size:10px}"},

            // Test for remove spaces between selectors
            {"H1.member > H2 ,  H3 +  H4  , H5 ~  P { font-family: \"PT  Sans  Narrow\"; font-size: 10px; }",
             "H1.member>H2,H3+H4,H5~P{font-family:\"PT  Sans  Narrow\";font-size:10px}"},

            {".col-xs-1, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9, .col-xs-10, .col-xs-11, .col-xs-12{float:left}",
             ".col-xs-1,.col-xs-2,.col-xs-3,.col-xs-4,.col-xs-5,.col-xs-6,.col-xs-7,.col-xs-8,.col-xs-9,.col-xs-10,.col-xs-11,.col-xs-12{float:left}"},

            // Test for remove spaces into attribute selector
            {"H1.member  [class  *  = \"selected\" ] { color: blue; }",
             "H1.member[class*=\"selected\"]{color:blue}"},
            {"H1 : focus > * , H2 *  [class  *  = \"selected\" ] { color: blue; }",
             "H1:focus>*,H2 *[class*=\"selected\"]{color:blue}"},

            // Test what spaces do not be removed between composite property corners
            {"p{margin:0 0 10px}",
             "p{margin:0 0 10px}"},

            // Test for remove redundant semicolons
            {"p{margin:10px;padding:10px;;border:0;;;}",
             "p{margin:10px;padding:10px;border:0}"},

            {"H1.member[font='Arial   sans serif'] .wrapped{border:1px solid black!important;color:#white;font-size:12pt;background:url(/test space.png);}",
             "H1.member[font='Arial   sans serif'] .wrapped{border:1px solid black!important;color:#white;font-size:12pt;background:url(/test space.png)}"},

            {"H1.member+H2{font-family:\"PT  Sans  Narrow\";font-size:10px;}H3.member+H4{font-family:\"PT  Sans  Narrow\";font-size:10px;color:blue;}",
             "H1.member+H2{font-family:\"PT  Sans  Narrow\";font-size:10px}H3.member+H4{font-family:\"PT  Sans  Narrow\";font-size:10px;color:blue}"},

            {"H1.member+H2{font-family:\"PT  Sans {font-size:10px;} ' \\\" Narrow\";font-size:10px;}H3.member+H4{font-family:\"PT  Sans  Narrow\";font-size:10px;color:blue;}",
             "H1.member+H2{font-family:\"PT  Sans {font-size:10px;} ' \\\" Narrow\";font-size:10px}H3.member+H4{font-family:\"PT  Sans  Narrow\";font-size:10px;color:blue}"},

            //TODO: должны ли мы удалять лишние пробелы перед attr и после него?
            {"a[href]:after{content:' (' attr(href)')'}abbr[title]:after{content:' (' attr(title)')'}",
             "a[href]:after{content:' (' attr(href)')'}abbr[title]:after{content:' (' attr(title)')'}"},

            // Fixed bug: test into @media block; Before fixed it, removes space between .dl-corners dt
            {"@media (min-width:768px){.dl-horizontal dt{float:left;width:160px;clear:left;text-align:right;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}",
            "@media(min-width:768px){.dl-horizontal dt{float:left;width:160px;clear:left;text-align:right;overflow:hidden;text-overflow:ellipsis;white-space:nowrap}"},

            // Fixed bug: leave space in rgba/hsla and other functions
            {".form-control:focus{border-color:#6ae;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6)}",
             ".form-control:focus{border-color:#6ae;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)}"},

            // Fixed bug: leave space between = sign
            {".disabled>a:focus{text-decoration:none;background-color:transparent;background-image:none;filter:progid:DXImageTransform.Microsoft.gradient(enabled = false);cursor:not-allowed}",
             ".disabled>a:focus{text-decoration:none;background-color:transparent;background-image:none;filter:progid:DXImageTransform.Microsoft.gradient(enabled=false);cursor:not-allowed}"}
        };
    }
}