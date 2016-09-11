package org.w3c.utils.css.filters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.utils.css.io.RecycledCharBuffer;

import static org.testng.Assert.*;

/**
 * ColorFilter functional tests
 *
 * Created by Home on 08.11.2015.
 */
public class ColorFilterTest
{

    @Test(dataProvider = "dataProvider")
    public void testApply(String source, boolean reduceColorSpace, String expected) throws Exception
    {
        ColorFilter filter = new ColorFilter(new RecycledCharBuffer(source));
        filter.setReduceColorSpace(reduceColorSpace);
        filter.apply();

        assertEquals(filter.getBuffer().getOutput(), expected, String.format("Invalid transform for source:\n%s", source));
    }

    @DataProvider
    private Object[][] dataProvider()
    {
        return new Object[][]{
                // standard test of color simplifying
                {"body{color:#337788;margin:0;}center{max-width:1200px;background:#ffffff;box-shadow:0px 0px 4px #eeeeee;}", false,
                 "body{color:#378;margin:0;}center{max-width:1200px;background:#fff;box-shadow:0px 0px 4px #eee;}"},

                // test for reduce color space
                {"body{color:#337788;margin:0;}center{max-width:1200px;background:#ffffff;box-shadow:0px 0px 4px #f0e0d0;}", true,
                 "body{color:#378;margin:0;}center{max-width:1200px;background:#fff;box-shadow:0px 0px 4px #edc;}"},

                // test to escape selectors IDs
                {"body#337788{color:#337788;margin:0;}", false,
                 "body#337788{color:#378;margin:0;}"},

                // test to escape URLs with matched color patterns
                {"center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #eeeeee;}", false,
                 "center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #eee;}"},
                {"center{max-width:1200px;background:url(/color:#ffffff) #ff00ff;box-shadow:0px 0px 4px #eeeeee;}", false,
                "center{max-width:1200px;background:url(/color:#ffffff) #f0f;box-shadow:0px 0px 4px #eee;}"},
                {"center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #f0e0d0;}", true,
                 "center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #edc;}"},

                {"body#337788{color:#337788;margin:0;}center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #eeeeee;}", false,
                 "body#337788{color:#378;margin:0;}center{max-width:1200px;background:url(/test.png#ffffff);box-shadow:0px 0px 4px #eee;}"},

                // test rgb expressions transform
                {"body#337788{color:rgb(10,32,17);margin:0;}center{max-width:1200px;background:url(/test.png#ffffffrgb(1,1,1));box-shadow:0px 0px 4px rgb(100%,50%,25%);}", false,
                 "body#337788{color:#0a2011;margin:0;}center{max-width:1200px;background:url(/test.png#ffffffrgb(1,1,1));box-shadow:0px 0px 4px #ff7f3f;}"},

                // test hsl expressions transform
                {"body#337788{color:hsl(0,100%,25%);margin:0;}center{max-width:1200px;background:url(/test.png#ffffffhsl(1,1%,1%));box-shadow:0px 0px 4px hsl(283,64%,33%);}", false,
                 "body#337788{color:#7f0000;margin:0;}center{max-width:1200px;background:url(/test.png#ffffffhsl(1,1%,1%));box-shadow:0px 0px 4px #6b1e8a;}"},

                // test escape hsl and rgb transforms in URLs
                {"body#337788{color:rgb(255,255,255);margin:0;}center{max-width:1200px;background:url(/test.png#ffffffhsl(1,1%,1%));box-shadow:0px 0px 4px hsl(0,100%,50%);}", false,
                 "body#337788{color:#fff;margin:0;}center{max-width:1200px;background:url(/test.png#ffffffhsl(1,1%,1%));box-shadow:0px 0px 4px red;}"},
                {"body#337788{color:rgb(255,255,255);margin:0;}center{max-width:1200px;background:url(/test.png#ffffffrgb(10,10,10));box-shadow:0px 0px 4px hsl(0,100%,50%);}", true,
                 "body#337788{color:#fff;margin:0;}center{max-width:1200px;background:url(/test.png#ffffffrgb(10,10,10));box-shadow:0px 0px 4px red;}"},

                // test to named color constants replacement
                {"body{color:cornflowerblue;color:brown;color:crimson;color:darkgray;color:aqua;color:white;color:black;}", false,
                 "body{color:#6495ed;color:brown;color:crimson;color:#a9a9a9;color:aqua;color:#fff;color:#000;}"},
                {"body{color:cornflowerblue;color:brown;color:crimson;color:darkgray;color:aqua;color:white;color:black;}", true,
                 "body{color:#69e;color:#a22;color:#d14;color:#aaa;color:aqua;color:#fff;color:#000;}"},

                // test to colors replacement for shorten named constants
                {"body{color:#ff0000}h1{color:#d2b48c}h2{color:#a52a2a}", false,
                 "body{color:red}h1{color:tan}h2{color:brown}"},
                {"body{color:#ff0000}h1{color:#d2b48c}h2{color:#a52a2a}", true,
                 "body{color:red}h1{color:tan}h2{color:#a22}"},

                // test correct replacement color names
                {"h6{font-weight:black}h1{background:black;background-color:black;color:white}", false,
                 "h6{font-weight:black}h1{background:#000;background-color:#000;color:#fff}"}
        };
    }
}