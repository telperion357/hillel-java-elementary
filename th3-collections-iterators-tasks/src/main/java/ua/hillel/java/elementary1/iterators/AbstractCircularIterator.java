package ua.hillel.java.elementary1.iterators;

import java.util.Iterator;

/**
 * Iterator which link last element of the array to the beginning and therefore
 * calling next() will be infinitive.
 * <p>
 * Example :
 * It (1, 2) =>
 * 1. next() = 1
 * 2. next() = 2
 * 3. next() = 1
 * 4. next() = 2
 * ...
 */
public abstract class AbstractCircularIterator implements Iterator<Object> {
    protected Object[] array;

    public AbstractCircularIterator(Object[] array) {
        this.array = array;
    }
}
