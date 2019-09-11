package ua.hillel.java.elementary1.objects.tasks;

/**
 * Month with method to access data.
 */
public interface Month {
    /**
     * Gets number of the days in current month.
     *
     * @return the days in month.
     */
    int getDaysIn();

    /**
     * Gets last day of the month from the beginning of the year.
     *
     * @return the last day of month from the beginning of the year.
     */
    int getLastDay();

    /**
     * Gets date of year. Given day in current month return day in year from 01.01.
     * Example:
     *    FEBRUARY.getDateOfYear(10) => 41: 31 days on JANUARY + 10 days in FEBRUARY.
     *
     * @param day the day number in the month.
     *
     * @return the date of year for this day.
     */
    int getDateOfYear(int day);
}
