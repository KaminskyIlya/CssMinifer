package org.w3c.utils.css.model.exceptions;

/**
 * Level of exceptions.
 *
 * Created by Home on 29.11.2015.
 */
public enum EExceptionLevel
{
    /**
     * Information level.
     */
    INFO,


    /**
     * Warning level.
     * Optimizations, analysing not applicable for this.
     */
    WARN,


    /**
     * Error level.
     * Processor may skip invalid chunk of source.
     */
    ERROR,


    /**
     * Critical exception for fatal error.
     * Source processing must be stopped.
     */
    CRITICAL;
}
