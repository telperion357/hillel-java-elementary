package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.BinarySearcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class BinarySearchElementsTest {

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4, 5, 6}, 4, 3},
                {new int[]{4, 5, 6}, 5, 1},
                {new int[]{1, 2, 3}, 4, -4},
                {new int[]{}, 2, -1},
                {new int[]{1, 2, 3, 4, 5, 6}, 6, 5},
                {new int[]{1, 4, 10, 20, 100}, 10, 2}
        });
    }

    private int[] array;
    private int target;
    private int expected;

    public BinarySearchElementsTest(int[] array, int target, int expected) {
        this.array = array;
        this.target = target;
        this.expected = expected;
    }

    @Test
    public void runSearch() {
        Collection<BinarySearcher> implementations = Utils.implementations(BinarySearcher.class);
        for (BinarySearcher searcher : implementations) {
            Assert.assertEquals(String.format("Failed on %s [%s]", searcher.getClass(), Arrays.toString(array)),
                                expected,
                                searcher.findPos(array, target));
        }
    }
}