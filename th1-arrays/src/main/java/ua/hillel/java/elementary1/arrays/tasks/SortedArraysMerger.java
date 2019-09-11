package ua.hillel.java.elementary1.arrays.tasks;

/**
 * Sorted arrays merger.
 */
public interface SortedArraysMerger {
    /**
     * Merge two sorted array and return array with all elements in sorted order
     *
     * @param a the first array
     * @param b the second array
     * @return the array with full elements in the sorted order.
     */
    int[] merge(int[] a, int[] b);
}
