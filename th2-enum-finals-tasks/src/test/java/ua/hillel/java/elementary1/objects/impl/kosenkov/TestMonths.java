package ua.hillel.java.elementary1.objects.impl.kosenkov;

import org.junit.Assert;
import org.junit.Test;
import ua.hillel.java.elementary1.objects.tasks.Month;
import ua.hillel.java.elementary1.objects.tasks.Named;

public class TestMonths {

    final static int MONTHS_IN_YEAR = 12;

    final static String[] MONTH_NAME = new String[] {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    };

    final static int[] DAYS_IN_MONTH = new int[] {
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
    };
    // The number of days before the months from the beginning of the year
    final static int[] DAYS_BEFORE = new int[] {
            0,   31,  59,  90,  120, 151,
            181, 212, 243, 273, 304, 334
    };
    // The number of days from the beginning of the year to the last day of the month
    final static int[] LAST_DAY = new int[] {
            31,  59,  90,  120, 151, 181,
            212, 243, 273, 304, 334, 365
    };

    @Test
    public void testGetDaysIn() {
        for (int i = 0; i < MONTHS_IN_YEAR; i++) {
            Assert.assertEquals(DAYS_IN_MONTH[i], Months.valueOf(MONTH_NAME[i]).getDaysIn());
        }

    }

    @Test
    public void testGetLastDay() {
        for (int i = 0; i < MONTHS_IN_YEAR; i++) {
            Assert.assertEquals(LAST_DAY[i], Months.valueOf(MONTH_NAME[i]).getLastDay());
        }

    }

    @Test
    public void testGetDateOfYear() {
        int day = 5;
        for (int i = 0; i < MONTHS_IN_YEAR; i++) {
            Assert.assertEquals(DAYS_BEFORE[i] + day, Months.valueOf(MONTH_NAME[i]).getDateOfYear(day));
        }
    }

    @Test
    public void testGetName() {
        for (int i = 0; i < MONTHS_IN_YEAR; i++) {
            Assert.assertEquals(MONTH_NAME[i], Months.valueOf(MONTH_NAME[i]).getName());
        }
    }

    @Test
    public void testByName() {
        for (int i = 0; i < MONTHS_IN_YEAR; i++) {
            Assert.assertEquals(Months.valueOf(MONTH_NAME[i]), Months.MAY.byName(MONTH_NAME[i]));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidationByName() {
        Named month = Months.MAY.byName(null);
    }

    @Test
    public void printMonths() {
        for (Months month : Months.values()) {
            System.out.printf("%-10s" +"%5d" +"%5d" +"%5d" +"%n", month.getName(),
                    month.getDaysIn(), month.getLastDay(), month.getDateOfYear(3));
        }
    }

    @Test
    public void printDateOfYear() {
        int day = 5;
        System.out.println("Which day of year is the " + day + "th of " + Months.APRIL.toString() + "?");
        System.out.println("It is the " + Months.APRIL.getDateOfYear(day) + "th day of year.");
        day = 15;
        System.out.println("Which day of year is " + day + "th of " + Months.MAY.toString() + "?");
        System.out.println("It is the " + Months.MAY.getDateOfYear(day) + "th day of year.");
    }

    @Test
    public void printExceptions() {
        int day = -5; // IllegalArgumentException
        try {
            System.out.println("Which day of year is " + day + "th of " + Months.APRIL.toString() + "?");
            System.out.println("It is " + Months.APRIL.getDateOfYear(day) + "th day of year.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        day = 50; // IllegalArgumentException
        try {
            System.out.println("Which day of year is " + day + "th of " + Months.AUGUST.toString() + "?");
            System.out.println("It is " + Months.AUGUST.getDateOfYear(day) + "th day of year.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

    }
}
