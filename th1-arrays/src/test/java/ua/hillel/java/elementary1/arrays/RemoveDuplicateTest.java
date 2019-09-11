package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.RemoveDuplicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class RemoveDuplicateTest {
    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{}, new int[]{}},
                {new int[]{1, 3, 5}, new int[]{1, 3, 5}},
                {new int[]{1, 3, 3, 5}, new int[]{1, 3, 5}},
                {new int[]{1, 1, 1, 1, 3, 3, 5}, new int[]{1, 3, 5}},
                {new int[]{1, 1, 1, 1, 3, 3, 5, 5, 5}, new int[]{1, 3, 5}},
                });
    }

    private int[] array;
    private int[] expected;

    public RemoveDuplicateTest(int[] array, int[] expected) {
        this.array = array;
        this.expected = expected;
    }

    @Test
    public void removeDuplicates() {
        Collection<RemoveDuplicates> duplicates = Utils.implementations(RemoveDuplicates.class);
        for (RemoveDuplicates duplicate : duplicates) {
            Assert.assertArrayEquals(String.format("Failed %s [%s]", duplicate.getClass(), Arrays.toString(array)),
                                     expected,
                                     duplicate.removeDuplicates(array));
        }
    }
}