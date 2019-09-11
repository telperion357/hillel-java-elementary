package ua.hillel.java.elementary1.arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.EvenNumbersCounter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CountEvenNumbersTest {

    @Parameterized.Parameters
    public static List<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 4}, 2},
                {new int[]{1, 3, 5}, 0},
                {new int[]{}, 0},
                {null, 0}
        });
    }

    private int[] array;
    private int count;

    public CountEvenNumbersTest(int[] array, int count) {
        this.array = array;
        this.count = count;
    }

    @Test
    public void evenCounterTest() {
        Collection<EvenNumbersCounter> counters = Utils.implementations(EvenNumbersCounter.class);
        for (EvenNumbersCounter counter : counters) {
            Assert.assertEquals(String.format("Failed on %s [%s]", counter.getClass(), Arrays.toString(array)), count,
                    counter.countEven(array));
        }
    }


}