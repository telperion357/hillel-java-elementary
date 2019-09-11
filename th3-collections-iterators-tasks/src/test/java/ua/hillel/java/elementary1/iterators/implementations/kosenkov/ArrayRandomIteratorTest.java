package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayRandomIteratorTest {

    @Test
    public void testRandomIterator() {
        ArrayRandomIterator randomIterator = new ArrayRandomIterator(new int[] {1, 2, 3, 4, 5});
        while (randomIterator.hasNext()) {
            System.out.print(randomIterator.next() + " ");
        }
    }

}