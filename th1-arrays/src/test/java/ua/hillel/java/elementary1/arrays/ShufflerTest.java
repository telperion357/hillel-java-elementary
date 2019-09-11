package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.Shuffler;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ShufflerTest {

    private static final int TEST_COUNTS = 1000;
    private static final int MAX_SIZE = 100;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> runs = new ArrayList<>();
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < TEST_COUNTS; i++) {
            runs.add(new Object[]{r.ints(MAX_SIZE, 0, MAX_SIZE).toArray()});
        }
        return runs;
    }

    private int[] array;
    private Collection<Shuffler> shufflers;

    public ShufflerTest(int[] array) {
        this.array = array;
        this.shufflers = Utils.implementations(Shuffler.class);
    }

    @Test
    public void testShuffle() {
        Object[][] original = copyWithPos(array);
        for (Shuffler shuffler : shufflers) {
            Object[][] copy = copyWithPos(array);
            shuffler.shuffle(copy);
            for (int i = 0; i < array.length; i++) {
                Assert.assertNotEquals("Problem happens on :" + i + " of " +
                                Arrays.deepToString(copy) + "-> " + Arrays.deepToString(original),
                        copy[i][0], original[i][0]);
            }
        }
    }

    private static Object[][] copyWithPos(int[] array) {
        Object[][] original = new Object[array.length][];
        for (int i = 0; i < array.length; i++) {
            original[i] = new Object[]{i, array[i]};
        }
        return original;
    }
}
