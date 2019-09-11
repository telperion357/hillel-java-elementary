package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

//Create circular infinitive iterator. Iterator will be initialized with array.
//        When last element of the array is reached then pointer should point to first one.
//        Extends class ua.hillel.java.elementary1.iterators.AbstractCircularIterator
//        Example:
//
//        CIter t = new CIter({1, 2, 3});
//        t.next() returns 1
//        t.next() returns 2
//        t.next() returns 3
//        t.next() returns 1
//        t.next() returns 2
//        t.next() returns 3

import ua.hillel.java.elementary1.iterators.AbstractCircularIterator;

public class CircularIterator extends AbstractCircularIterator {

    // By default initialization itr = 0.
    // We don't have to reinitialize it.
    private int itr;

    public CircularIterator(Object[] array) {
        super(array);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        Object item = array[itr++];
        // Close the loop of iteration
        if (itr >= array.length) {
            itr = 0;
        }
        return item;
    }
}
