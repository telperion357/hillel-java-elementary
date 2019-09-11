package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.SortedArraysMerger;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class MergeSortedArraysTest {
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{}, new int[]{}, new int[]{}},
                {new int[]{1, 2}, new int[]{}, new int[]{1, 2}},
                {new int[]{1, 2}, new int[]{3, 4}, new int[]{1, 2, 3, 4}},
                {new int[]{4, 5}, new int[]{1, 2}, new int[]{1, 2, 4, 5}},
                {new int[]{1, 3, 5}, new int[]{2, 4, 6}, new int[]{1, 2, 3, 4, 5, 6}},
                {new int[]{1, 10}, new int[]{2, 3, 4}, new int[]{1, 2, 3, 4, 10}},
                });
    }

    private int[] a;
    private int[] b;
    private int[] expected;

    public MergeSortedArraysTest(int[] a, int[] b, int[] expected) {
        this.a = a;
        this.b = b;
        this.expected = expected;
    }

    @Test
    public void testMerge() {
        Collection<SortedArraysMerger> mergers = Utils.implementations(SortedArraysMerger.class);
        for (SortedArraysMerger merger : mergers) {
            Assert.assertArrayEquals(String.format("Failed on %s [%s, %s]", merger.getClass(), Arrays.toString(a),
                                                   Arrays.toString(b)), expected, merger.merge(a, b));
        }
    }
}