package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CustomArrayListTest {

    private List list;
    private final int INIT_SIZE = 5;
    private final int TEST_VALUE = 7;
    private final int TEST_INDEX = 2;

    @Before
    public void setUp() {
        list = new CustomArrayList();
        for (int i = 0; i < INIT_SIZE; i++) {
            list.add(i);
        }
    }

    @Test
    public void testToArray() {
        Object[] expected = new Object[] {0, 1, 2, 3, 4};
        Object[] actuals = list.toArray();
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void testAdd() {
        assertEquals(INIT_SIZE, list.size());
        list.add(TEST_VALUE);
        assertEquals(list.size(), INIT_SIZE + 1);
        Object[] expected = new Object[]{0, 1, 2, 3, 4, TEST_VALUE};
        Object[] actuals = list.toArray();
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void testAddByIndex() {
        assertEquals(INIT_SIZE, list.size());
        list.add(TEST_INDEX ,TEST_VALUE);
        assertEquals(list.size(), INIT_SIZE + 1);
        Object[] expected = new Object[]{0, 1, TEST_VALUE, 2, 3, 4};
        Object[] actuals = list.toArray();
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void testGet() {
        assertEquals(TEST_INDEX, list.get(TEST_INDEX));
    }

    @Test
    public void testRemove() {
        assertEquals(INIT_SIZE, list.size());
        assertEquals(TEST_INDEX, list.remove(TEST_INDEX));
        assertEquals(list.size(), INIT_SIZE - 1);
        Object[] expected = new Object[]{0, 1, 3, 4};
        Object[] actuals = list.toArray();
        assertArrayEquals(expected, actuals);
    }

    @Test
    public void testResize() {
        // Add a hundred elements to the list
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        // Now the size() is 105
        assertEquals(105, list.size());

        // Found the backing array length by reflection
        int arrayLength;
        Class clazz = list.getClass().getSuperclass();
        Field arrayField = null;
        try {
            arrayField = clazz.getDeclaredField("array");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        arrayField.setAccessible(true);
        Object array = null;
        try {
            array = arrayField.get(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        }
        arrayLength = Array.getLength(array);

        // Backing array length should be the next power of 2 after list.size()
        // size() = 105, arrayLength should be 128.
        assertEquals(128, arrayLength);

        // Remove elements to test resizing down the backing array
        for (int i = 0; i < 100; i++) {
            list.remove(0);
        }
        assertEquals(list.size(), INIT_SIZE);

        // found new array length by reflection
        try {
            array = arrayField.get(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail();
        }
        arrayLength = Array.getLength(array);

        // After removes array size get back to default value
        assertEquals(16, arrayLength);
    }
}