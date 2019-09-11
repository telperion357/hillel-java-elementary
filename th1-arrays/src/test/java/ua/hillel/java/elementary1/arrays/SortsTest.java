package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.Sorts;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class SortsTest {

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 5, 2, 6, 3, 7, 9, 8, 4}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}},
                {new int[]{1, 2, 1, 3, 1, 4}, new int[]{1, 1, 1, 2, 3, 4}},
                {new int[]{1, 1}, new int[]{1, 1}},
                {new int[]{}, new int[]{}}
        });
    }

    private int[] example;
    private int[] expected;

    public SortsTest(int[] example, int[] expected) {
        this.example = example;
        this.expected = expected;
    }

    private Collection<Sorts> sorts;

    @Before
    public void setUp() {
        sorts = Utils.implementations(Sorts.class);
    }

    @Test
    public void bubbleSort() {
        for (Sorts sort : sorts) {
            int[] array = Arrays.copyOf(example, example.length);
            sort.bubbleSort(array);
            Assert.assertArrayEquals(String.format("Failed %s [%s]", sort.getClass(), Arrays.toString(example)), array,
                                     expected);
        }
    }

    @Test
    public void minSort() {
        for (Sorts sort : sorts) {
            int[] array = Arrays.copyOf(example, example.length);
            sort.minSort(array);
            Assert.assertArrayEquals(String.format("Failed %s [%s]", sort.getClass(), Arrays.toString(example)), array,
                                     expected);
        }
    }

    @Test
    public void mergeSort() {
        for (Sorts sort : sorts) {
            int[] array = Arrays.copyOf(example, example.length);
            sort.mergeSort(array);
            Assert.assertArrayEquals(String.format("Failed %s [%s]", sort.getClass(), Arrays.toString(example)), array,
                                     expected);
        }
    }
}