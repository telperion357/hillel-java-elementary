package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.ValuesRangeFinder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class FindValuesRangeTest {
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4, 5, 6}, 2, 4, new int[]{2, 3, 4}},
                {new int[]{1, 2, 4, 6, 8, 10}, 5, 7, new int[]{6}},
                {new int[]{1, 2, 4, 6, 8, 10}, 11, 12, new int[]{}},
                });
    }

    private int[] array;
    private int a;
    private int b;
    private int[] expected;

    public FindValuesRangeTest(int[] array, int a, int b, int[] expected) {
        this.array = array;
        this.a = a;
        this.b = b;
        this.expected = expected;
    }

    @Test
    public void runSearch() {
        Collection<ValuesRangeFinder> finders = Utils.implementations(ValuesRangeFinder.class);
        for (ValuesRangeFinder finder : finders) {
            Assert.assertArrayEquals(String.format("Failed on %s [%s] (%d, %d)", finder.getClass(),
                                                   Arrays.toString(array), a, b), expected,
                                     finder.searchRange(array, a, b));
        }
    }
}