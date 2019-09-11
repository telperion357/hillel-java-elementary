package ua.hillel.java.elementary1.objects.tasks;

/**
 * Named used to get name of the accessor.
 */
public interface Named {
    /**
     * Gets name of current implementor.
     *
     * @return the name
     */
    String getName();

    /**
     * Create instance of Named object by provided name.
     *
     * @param name the name of the object.

     * @return the named object.
     * @throws IllegalArgumentException when name is not found.
     */
    Named byName(String name);
}
