package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class LinkedQueueTest {
    private LinkedQueue queue;

    // Test add()
    @Test
    public void testAdd() {
        queue = new LinkedQueue();
        assertEquals(queue.size(), 0);
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }
        assertEquals(queue.size(), 5);
        assertArrayEquals(queue.toArray(), new Object[] {0, 1, 2, 3, 4});
    }

    // Test remove()
    @Test
    public void testRemove() {
        queue = new LinkedQueue(Arrays.asList(new Object[] {0, 1, 2, 3, 4}));
        assertEquals(queue.size(), 5);
        assertArrayEquals(queue.toArray(), new Object[] {0, 1, 2, 3, 4});
        assertEquals(0, queue.remove());
        assertEquals(1, queue.remove());
        assertEquals(queue.size(), 3);
        assertArrayEquals(queue.toArray(), new Object[] {2, 3, 4});
        assertEquals(2, queue.remove());
        assertEquals(3, queue.remove());
        assertEquals(4, queue.remove());
        assertEquals(queue.size(), 0);
        Iterator itr = queue.iterator();
        assertEquals(itr.hasNext(), false);
    }

}