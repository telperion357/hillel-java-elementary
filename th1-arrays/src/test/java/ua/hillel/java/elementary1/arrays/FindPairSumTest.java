package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.PairFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class FindPairSumTest {

    @Parameterized.Parameters
    public static List<Object[]> params() {
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new int[]{1, 3, 5}, 6, new int[]{1, 5}});
        params.add(new Object[]{new int[]{1, 5}, 6, new int[]{1, 5}});
        params.add(new Object[]{new int[]{1, 5}, 10, null});
        params.add(new Object[]{new int[]{1, 1, 1, 1, 1}, 3, null});
        params.add(new Object[]{new int[]{}, 0, null});

        int[] array = new int[Short.MAX_VALUE];
        for (int i = 0; i < array.length; i++) {
            array[i] = i << 1;
        }
        int target = array[array.length >> 1] + array[array.length - 1];
        params.add(new Object[]{array, target, new int[]{array[array.length >> 1], array[array.length - 1]}});
        return params;
    }

    private int[] array;
    private int target;
    private int[] expected;

    public FindPairSumTest(int[] array, int target, int[] expected) {
        this.array = array;
        this.target = target;
        this.expected = expected;
    }

    @Test
    public void pairsTest() {
        Collection<PairFinder> finders = Utils.implementations(PairFinder.class);
        for (PairFinder finder : finders) {
            Assert.assertArrayEquals(
                    String.format("Failed on %s [%s] %d", finder.getClass(), Arrays.toString(array), target), expected,
                    finder.findPair(array, target));
        }
    }
}