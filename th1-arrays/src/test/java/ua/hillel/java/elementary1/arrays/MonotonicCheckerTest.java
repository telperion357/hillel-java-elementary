package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.MonotonicChecker;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class MonotonicCheckerTest {

    @Parameterized.Parameters
    public static List<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {null, false},
                {new int[0], false},
                {new int[] {1, 2}, true},
                {new int[] {1, 1, 2}, true},
                {new int[] {1, 1, 1, 2}, true},
                {new int[] {1, 1, 1, 2, 1}, false},
                {new int[] {1, 2, 3, 2}, false},
                {new int[] {5, 3, 2}, true},
                {new int[] {3, 3, 2, 1}, true},
                {new int[] {4, 3, 1, 2}, false}
        });
    }

    private int[] array;
    private boolean expected;

    public MonotonicCheckerTest(int[] array, boolean expected) {
        this.array = array;
        this.expected = expected;
    }

    @Test
    public void checkIfMonotonic() {
        Collection<MonotonicChecker> checkers = Utils.implementations(MonotonicChecker.class);
        for (MonotonicChecker checker : checkers) {
            Assert.assertEquals(String.format("Failed on %s [%s]", checker.getClass(), Arrays.toString(array)),
                                expected, checker.checkIfMonotonic(array));
        }
    }
}