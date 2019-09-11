package ua.hillel.java.elementary1.arrays.tasks;

/**
 * Create queue based on heap structure.
 * Basically you have to create priority queue.
 *
 * https://en.wikipedia.org/wiki/Heap_(data_structure)
 */
public interface HeapQueue {
    /**
     * Poll element from the queue. Always poll the smallest
     *
     * @return the smallest element in the queue or Integer.MIN_VALUE if nothing found.
     */
    int poll();

    /**
     * Offer element in the queue.
     *
     * @param value the value
     */
    void offer(int value);
}
