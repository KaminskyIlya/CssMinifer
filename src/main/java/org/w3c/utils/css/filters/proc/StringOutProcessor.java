package org.w3c.utils.css.filters.proc;


/**
 * To control process outside of strings.
 *
 * Created by Home on 29.11.2015.
 */
public class StringOutProcessor extends TextProcessor
{
    @Override
    public boolean canProcess()
    {
        return !isInString();
    }
}
