package ua.hillel.java.elementary1.arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.HeapQueue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HeapQueueTest {

    @Parameterized.Parameters
    public static List<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {new Object[][]{
                        {"offer", 1},
                        {"offer", 4},
                        {"offer", 2},
                        {"offer", 5},
                        {"poll", 1},
                        {"poll", 2},
                        {"poll", 4},
                        {"poll", 5},
                        {"poll", Integer.MIN_VALUE},
                        }},
                {new Object[][]{
                        {"offer", 3},
                        {"offer", 2},
                        {"offer", 1},
                        {"offer", 0},
                        {"poll", 0},
                        {"poll", 1},
                        {"poll", 2},
                        {"poll", 3},
                        {"poll", Integer.MIN_VALUE},
                        }},
                {new Object[][]{
                        {"poll", Integer.MIN_VALUE},
                        }},
                {new Object[][]{
                        {"offer", -1},
                        {"offer", -4},
                        {"offer", -2},
                        {"offer", -5},
                        {"poll", -5},
                        {"poll", -4},
                        {"poll", -2},
                        {"poll", -1},
                        {"poll", Integer.MIN_VALUE},
                        }},
        });
    }

    private Object[][] operations;

    public HeapQueueTest(Object[][] operations) {
        this.operations = operations;
    }

    @Test
    public void testQueue() {
        Collection<HeapQueue> queues = Utils.implementations(HeapQueue.class);
        for (HeapQueue queue : queues) {
            for (Object[] row : operations) {
                String operation = (String) row[0];
                if ("offer".equalsIgnoreCase(operation)) {
                    int number = (int) row[1];
                    queue.offer(number);
                } else {
                    int number = (int) row[1];
                    assertEquals(String.format("Failed with %s [%s]", queue.getClass(), Arrays.toString(row)), number,
                                 queue.poll());
                }

            }
        }
    }
}