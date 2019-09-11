package ua.hillel.java.elementary1.arrays.tasks;

/**
 * The interface Values range finder.
 */
public interface ValuesRangeFinder {
    /**
     * Search range of values which match [a, b] range - both inclusive. Return empty array in case it is not found.
     * Array can be sorted.
     *
     * @param nums the array of value.
     * @param a    the lower bound, inclusive
     * @param b    the upper bound, inclusive
     * @return range of values which match [a, b] range, or empty array if nothing found.
     */
    int[] searchRange(int[] nums, int a, int b);
}
