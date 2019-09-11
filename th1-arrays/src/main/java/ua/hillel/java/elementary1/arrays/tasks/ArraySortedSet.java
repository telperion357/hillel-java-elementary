package ua.hillel.java.elementary1.arrays.tasks;

/**
 * Array sorted set is data structure in which all
 * operations are made in amortized O (log (N)) time.
 */
public interface ArraySortedSet {

    /**
     * Add element into set if it does not exists.
     *
     * @param value the value
     * @return the true in case operation successfull
     */
    boolean add(int value);

    /**
     * Remove element from the set.
     *
     * @param value the value
     * @return the true in case element removed, false otherwise.
     */
    boolean remove(int value);

    /**
     * Contains element in the set.
     *
     * @param value the value
     * @return the true in case set contains element
     */
    boolean contains(int value);
}
