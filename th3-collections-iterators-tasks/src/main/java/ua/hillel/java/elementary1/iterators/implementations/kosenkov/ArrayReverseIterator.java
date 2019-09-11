package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayReverseIterator<T> implements Iterator<T> {

    private T[] arr;
    private int itr;
    private boolean removeIsAllowed;

    public ArrayReverseIterator(T[] array) {
        this.arr = array;
        itr = arr.length - 1;
    }

    @Override
    public boolean hasNext() {
        return itr >= 0;
    }

    @Override
    public T next() {
        if (itr < 0) {
            throw new NoSuchElementException();
        }
        return arr[itr--];
    }

    // Removes the element, returned by the preceding call to next().
    // As it appears to be by specification, remove has no meaning with arrays,
    // because arrays are not resizeable.
    @Override
    public void remove() {
        // By specification remove() can only be called after next(),
        // and only once after each next();
        if (!removeIsAllowed) {
            throw new IllegalStateException();
        }
        remove(itr + 1);
        removeIsAllowed = false;
    }

    private void remove(int i) {
        int newLength = arr.length - 1;
        T[] newArr = (T[]) new Object[newLength];
        // copy[0, i)
        for (int j = 0; j < i; j++) {
            newArr[j] = arr[j];
        }
        // copy(i, newLength)
        for (int j = i; j < newLength; j++) {
            newArr[j] = arr[j + 1];
        }
    }
}
