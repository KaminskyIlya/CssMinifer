package org.w3c.utils.css.model;

import java.util.List;

/**
 * Hierarchy object
 *
 * Created by Home on 03.11.2015.
 */
public class Hierarchy<T>
{
    private T parent;

    private List<T> children;

    public T getParent() {
        return parent;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
