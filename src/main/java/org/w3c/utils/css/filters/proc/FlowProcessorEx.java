package org.w3c.utils.css.filters.proc;

/**
 * Flow text processor for detect status of chars in texts.
 *
 * Created by Home on 17.09.2016.
 */
public interface FlowProcessorEx
{
    /**
     * Process next char and set or prepare to set a new processor status.
     * Pre-processing.
     *
     * @param current char from input reader
     * @param next char from input reader
     */
    void before(char current, char next);

    /**
     * Process current char and finish to setup a new processor status.
     * Post-processing.
     *
     * @param current char from input reader
     * @param next char from input reader
     */
    void after(char current, char next);

}
