package ua.hillel.java.elementary1.arrays.tasks;

/**
 * Create Shuffler service which shuffle array and guarantee elements will take diff places.
 */
public interface Shuffler {

    /**
     * Shuffle the array which guarantee elements wont be on the same places.
     *
     * @param array the array to be shuffle.
     */
    void shuffle(Object[] array);
}
