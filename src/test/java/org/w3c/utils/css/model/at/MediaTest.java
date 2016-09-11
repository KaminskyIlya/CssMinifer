package org.w3c.utils.css.model.at;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Functional tests of Media class.
 *
 * Created by Home on 21.11.2015.
 */
public class MediaTest
{

    @Test(dataProvider = "dataProvider_GetMediaTypes")
    public void testGetMediaType(String source, String expected) throws Exception
    {
        Media media = new Media(source);
        assertEquals(media.getMediaType().toString(), expected);
    }

    @DataProvider
    private Object[][] dataProvider_GetMediaTypes()
    {
        return new Object[][]{
                {"@media print", "print"},
                {"@media screen", "screen"},
                {"@media only tv and (color)", "tv"},
                {"@media screen and (max-width: 550px)", "screen"},
                {"@media only screen and (color)", "screen"},
                {"@media not tv and (color)", "tv"},
                {"@media screen and (max-width: 550px)", "screen"},
                {"@media (max-width: 550px)", "all"},
                {"@media(max-width: 550px)", "all"},
                {"@media tv and (max-width: 550px) and (min-width: 480px)", "tv"},
        };
    }

    @Test(dataProvider = "dataProvider_GetMediaQueries")
    public void testGetQuery(String source, String expected) throws Exception
    {
        Media media = new Media(source);
        assertEquals(media.getQuery(), expected);
    }

    @DataProvider
    private Object[][] dataProvider_GetMediaQueries()
    {
        return new Object[][]{
                {"@media print", ""},
                {"@media screen", ""},
                {"@media only screen and (color)", "(color)"},
                {"@media not screen and (color)", "(color)"},
                {"@media screen and (max-width: 550px)", "(max-width: 550px)"},
                {"@media (max-width: 550px)", "(max-width: 550px)"},
                {"@media(max-width: 550px)", "(max-width: 550px)"},
                {"@media screen and (max-width: 550px) and (min-width: 480px)", "(max-width: 550px) and (min-width: 480px)"},
        };
    }
}