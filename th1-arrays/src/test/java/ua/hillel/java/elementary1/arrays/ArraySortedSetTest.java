package ua.hillel.java.elementary1.arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.hillel.java.elementary1.arrays.tasks.ArraySortedSet;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ArraySortedSetTest {
    private static final MethodType TYPE = MethodType.methodType(boolean.class, int.class);

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {
                        new Object[][]{
                                {"add", 4, true},
                                {"add", 3, true},
                                {"add", 2, true},
                                {"add", 1, true},
                                {"contains", 1, true},
                                {"remove", 1, true},
                                {"contains", 1, false},
                                {"remove", 1, false},
                                },
                        },
                {
                        new Object[][]{
                                {"add", 4, true},
                                {"add", 1, true},
                                {"add", 2, true},
                                {"add", 3, true},
                                {"add", 5, true},
                                {"add", 1, false},
                                {"contains", 1, true},
                                {"remove", 1, true},
                                {"remove", 3, true},
                                {"remove", 5, true},
                                {"remove", 2, true},
                                {"contains", 2, false},
                                {"remove", 1, false},
                                {"contains", 4, true},
                                },
                        }
        });
    }

    private Object[][] operations;

    public ArraySortedSetTest(Object[][] operations) {
        this.operations = operations;
    }

    @Test
    public void testSet() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Collection<ArraySortedSet> sets = Utils.implementations(ArraySortedSet.class);
        for (Object[] row : operations) {
            String operation = (String) row[0];
            int number = (int) row[1];
            boolean result = (boolean) row[2];

            for (ArraySortedSet set : sets) {
                MethodHandle handle = lookup.findVirtual(ArraySortedSet.class, operation, TYPE);
                assertEquals(String.format("Failed with %s [%s]", set.getClass(), Arrays.toString(row)), result,
                             handle.invoke(set, number));
            }
        }
    }
}