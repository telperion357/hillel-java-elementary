package ua.hillel.java.elementary1.iterators;

import java.util.AbstractList;

/**
 * Abstract array list with basic implementations
 */
public abstract class AbstractArrayList extends AbstractList<Object> {

    protected Object[] array;

    /////////////////////////////
    // Implement the following methods below.
    /////////////////////////////

    @Override
    public abstract boolean add(Object o);

    @Override
    public abstract Object set(int index, Object element);

    @Override
    public abstract void add(int index, Object element);

    @Override
    public abstract Object remove(int index);
}
