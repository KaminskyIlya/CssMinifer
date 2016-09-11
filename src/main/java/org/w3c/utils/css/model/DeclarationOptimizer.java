package org.w3c.utils.css.model;

import org.w3c.utils.css.Configuration;
import org.w3c.utils.css.io.PropertyWriter;

/**
 * Created by Home on 22.08.2016.
 */
public interface DeclarationOptimizer
{
    void optimize();

    void render(PropertyWriter writer);

    void setConfig(Configuration config);
}
