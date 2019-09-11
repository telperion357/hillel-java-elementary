package ua.hillel.java.elementary1.iterators;

import java.util.Iterator;
import java.util.Random;

/**
 * Iterator each calling of next() will give random element from left ones.
 * Let's say income is array of [1, 2, 3].
 * By calling next(), next(), next() will can achive either 3, 1, 2 or 1, 3, 2, etc..
 */
public abstract class AbstractRandomAccessIterator implements Iterator<Integer> {
    protected int[] array;
    // Use Random.nextInt(bound) for your purposes.
    protected Random random;

    public AbstractRandomAccessIterator(int[] array) {
        this.array = array;
        this.random = new Random();
    }
}
