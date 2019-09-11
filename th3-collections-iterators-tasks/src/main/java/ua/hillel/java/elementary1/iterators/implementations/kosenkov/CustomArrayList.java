package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import ua.hillel.java.elementary1.iterators.AbstractArrayList;

public class CustomArrayList extends AbstractArrayList {

    private int size;
    private static final int DEFAULT_ARRAY_SIZE = 16;

    public CustomArrayList() {
        super();
        array = new Object[DEFAULT_ARRAY_SIZE];
    }

    @Override
    public boolean add(Object o) {
        // Resize if the backing array is full
        if (size() == array.length) {
            resize(size()*2);
        }
        // Add new element and increment size
        array[size++] = o;
        return true;
    }

    @Override
    public Object set(int index, Object element) {
        // Throws IndexOutOfBoundsException by spec
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        // Get the value to return
        Object previousValue = array[index];
        // Set the new value
        array[index] = element;
        return previousValue;
    }

    @Override
    public void add(int index, Object element) {
        // Throws IndexOutOfBoundsException by spec
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        // Resize if the backing array is full
        if (size() == array.length) {
            resize(size()*2);
        }
        // Shift elements of the array one position to the right, starting at index
        for (int i = size; i > index ; i--) {
            array[i] = array[i - 1];
        }
        // Insert new element on index position
        array[index] = element;
        // Increment size
        size++;
    }

    @Override
    public Object remove(int index) {
        // Throws IndexOutOfBoundsException by spec
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        // Get the value to return
        Object valueAtIndex = array[index];
        // Decrement size
        size--;
        // Shift elements one position left, starting at (index + 1)
        for (int i = index; i < size; i++) {
            array[i] = array[i+1];
        }
        // Delete the previous last element
        array[size] = 0;
        // Resize if the list size is less than 1/4 array size
        if ((size() < array.length/4) && (array.length >= DEFAULT_ARRAY_SIZE*2)) {
            resize(array.length/2);
        }
        return valueAtIndex;
    }

    @Override
    public Object get(int index) {
        // Throws IndexOutOfBoundsException by spec
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Object[] tmpArray = new Object[newSize];
        for (int i = 0; i < size(); i++) {
            tmpArray[i] = array[i];
        }
        array = tmpArray;
    }
}
