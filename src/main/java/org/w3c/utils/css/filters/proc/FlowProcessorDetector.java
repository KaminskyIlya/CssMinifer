package org.w3c.utils.css.filters.proc;

/**
 * Detector for flow processors.
 * Determine a moment, when we can process some part of text.
 *
 * Created by Home on 11.09.2016.
 */
public interface FlowProcessorDetector
{
    boolean canProcess();
}
