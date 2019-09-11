package ua.hillel.java.elementary1.objects.impl.kosenkov;

import ua.hillel.java.elementary1.objects.tasks.Month;
import ua.hillel.java.elementary1.objects.tasks.Named;

//  Create enumeration of Months by implementing Month interface. And implement getDays method
//  for each month saying max days in Month: Month.JANUARY.getDays() == 31. Consider February has 28 days.
//  Implement getLastDay method which return last day of month from beginning of the year.
//  Example: Month.FEBRUARY.getLastDay() == 59 (31 + 28)
//  Implement getDateOfYear method with generally convert day on month to day of year.
//  Validate provided day as parameter and throw unchecked exception in case of invalid data.

// Simplified version of enum Months.
// The number of days from the beginning of the year
// to the beginning of the month is stored into each month.
// This gives the possibility to implement getDay methods
// in a simple constant-time way, and to share the implementation
// between all instances
enum Months implements Month, Named {

    JANUARY(31, 0),
    FEBRUARY(28, 31) ,
    MARCH(31, 59),
    APRIL(30, 90),
    MAY(31, 120),
    JUNE(30, 151),
    JULY(31, 181) ,
    AUGUST(31, 212),
    SEPTEMBER(30, 243),
    OCTOBER(31, 273),
    NOVEMBER(30, 304),
    DECEMBER(31, 334);

    // The number of days in this month
    private final int numberOfDays;
    // The number of days before this month from the beginning of the year
    private final int daysBefore;

    Months(int numberOfDays, int daysBefore) {
        this.numberOfDays = numberOfDays;
        this.daysBefore = daysBefore;
    }

    @Override
    public int getDaysIn() {
        return this.numberOfDays;
    }

    @Override
    // Returns the number of days from the beginning of the year
    // to the last day of this month
    public int getLastDay() {
        return this.numberOfDays + this.daysBefore;
    }

    @Override
    // Returns the number of days from the beginning of the year
    // to the specified day of this month
    public int getDateOfYear(int day) {
        validate(day);
        return day + this.daysBefore;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public Named byName(String name) {
        if(name == null) throw new IllegalArgumentException("Name is null!");
        return Months.valueOf(name.toUpperCase());
    }

    // validation in getDateOfYear(int)
    private void validate(int day) {
        if(day < 1) throw new IllegalArgumentException("Your days should be positive!");
        if(day > this.getDaysIn()) throw new IllegalArgumentException("Day number in " + this.getName()
                + " can't be greater than " + this.getDaysIn());
    }
}
