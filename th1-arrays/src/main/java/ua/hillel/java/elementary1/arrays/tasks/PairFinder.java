package ua.hillel.java.elementary1.arrays.tasks;

public interface PairFinder {
    /**
     * Find pair of values in the provided array that sums up to target
     * or null if nothing found.
     *
     * @param array  the array
     * @param target the target
     * @return the pair of values that sums to target.
     */
    int[] findPair(int[] array, int target);
}
