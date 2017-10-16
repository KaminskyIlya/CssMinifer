package org.w3c.utils.css.model.values;

import org.w3c.utils.css.model.CssModel;

/**
 * Common interface for all values of css properties
 *
 * Created by k002 on 12.10.2017.
 */
public interface Value extends CssModel
{
    /**
     * Set <b>!important</b> marker for this value
     *
     * @param flag true, if value marked as important
     */
    void setImportant(boolean flag);

    /**
     * Check <b>!important</b> marker of value
     *
     * @return true, if value marked as important
     */
    boolean isImportant();

    /**
     * @return text representation of value
     */
    String getText();
}
