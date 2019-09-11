package ua.hillel.java.elementary1.objects.tasks;

/**
 * Time offset of object instance.
 */
public interface TimeOffset {
    /**
     * Gets offset hours from UTC (consider not day saving hour).
     *
     * @return the offset of country from UTC.
     */
    int getUtcOffset();

    /**
     * Diff in hours between time offsets.
     * For example UA.diff(UTC) == UA.getUtcOffset()
     *
     * @param other the other time-zoned object.
     *
     * @return the number of hours difference between time-zones.
     */
    int diff(TimeOffset other);
}
