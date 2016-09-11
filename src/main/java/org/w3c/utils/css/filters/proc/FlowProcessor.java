package org.w3c.utils.css.filters.proc;

/**
 * Flow text processor for detect status of chars in texts.
 *
 * Created by Home on 27.08.2016.
 */
public interface FlowProcessor
{
    /**
     * Reset processor state.
     */
    void reset();

    /**
     * Process next char and set or prepare to set a new processor status.
     * Pre-processing.
     *
     * @param current next char (from input reader, for example)
     */
    void before(char current);

    /**
     * Process current char and finish to setup a new processor status.
     * Post-processing.
     *
     * @param current last read char (from input reader, for example)
     */
    void after(char current);

    /**
     * Abstract method for descendant implementations.
     * Used for detect moment when we can make some operation.
     * For example, we can delete all redundant whitespaces
     * except whitespaces in string literals.
     * This method in descendant implementation may say to us: can we delete or not
     * current char.
     *
     * @return true, if current symbol can processed
     */
    boolean canProcess();
}
