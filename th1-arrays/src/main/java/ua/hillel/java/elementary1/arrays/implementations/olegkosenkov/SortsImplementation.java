package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import ua.hillel.java.elementary1.arrays.tasks.Sorts;

// The implementation of Sorts interface
public class SortsImplementation implements Sorts {

    @Override
    public void bubbleSort(int[] array) {
        if (array != null) {
            BubbleSort.sort(array);
        }
    }

    @Override
    public void minSort(int[] array) {
        if (array != null) {
            MinSort.sort(array);
        }
    }

    @Override
    public void mergeSort(int[] array) {
        if (array != null) {
            MergeSortRecursive.sort(array);
        }
    }
}
