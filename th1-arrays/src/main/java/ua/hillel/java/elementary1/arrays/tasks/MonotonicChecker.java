package ua.hillel.java.elementary1.arrays.tasks;

public interface MonotonicChecker {

    /**
     * Check if array is creating monotonically increasing or decreasing seq.
     * 1. 1, 2, 3, 4 -> true
     * 2. 2, 3, 5 -> true
     * 3. 1, 4, 2 -> false,
     * 5. 4, 3, 2, 1 -> true,
     * 6. 1, 1, 1, 2 -> true,
     * 7. 1, 1, 1, 1 -> false
     *
     * @param array the array
     * @return true in case array is monotonic sequence.
     */
    boolean checkIfMonotonic(int[] array);
}
