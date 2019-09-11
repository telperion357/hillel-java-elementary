package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayReverseIteratorTest {

    private Integer[] array;

    @Before
    public void setup() {
       array = new Integer[] {1, 2, 3, 4, 5};
    }

    @Test
    public void testNext() {
        ArrayReverseIterator<Integer> itr = new ArrayReverseIterator<>(array);
        Integer[] expected = new Integer[]{5, 4, 3, 2, 1};
        Integer[] actual = new Integer[expected.length];
        int i = 0;
        while (itr.hasNext()) {
            actual[i] = itr.next();
            i++;
        }
        assertArrayEquals(expected, actual);
    }
}