package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

//Create RandomAccess iterator by extending
//        ```ua.hillel.java.elementary1.iterators.AbstractRandomAccessIterator```.
//        You iterator will be initialized with array and each next() call must return
//        some random element from the remaining ones.
//        For inspiration algorithm please follow https://www.rosettacode.org/wiki/Knuth_shuffle#Java
//        All details inside class.

import ua.hillel.java.elementary1.iterators.AbstractRandomAccessIterator;

import java.util.NoSuchElementException;

public class ArrayRandomIterator extends AbstractRandomAccessIterator {

    private int itr;

    public ArrayRandomIterator(int[] array) {
        super(array);
        itr = array.length;
    }


    @Override
    public boolean hasNext() {
        return itr > 0;

    }

    @Override
    public Integer next() {
        if (itr <= 0) {
            throw new NoSuchElementException();
        }
        // Random index in bounds [0, itr)
        int nextIndex = random.nextInt(itr--);
        // Put the used element to the right part of array
        swap(nextIndex, itr);
        return array[itr];
    }

    private void swap(int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
