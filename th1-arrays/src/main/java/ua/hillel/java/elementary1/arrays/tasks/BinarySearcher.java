package ua.hillel.java.elementary1.arrays.tasks;

public interface BinarySearcher {
    /**
     * Find position of the element in the sorted array or return -1-pos to inserted.
     *
     * @param array  the array
     * @param target the target
     * @return the pos of element in the array of return -1-pos where to insert element.
     */
    int findPos(int[] array, int target);
}
