package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import java.util.Arrays;

public class BubbleSort {

    public static void sort(int[] arr) {
        // Exclude trivial case
        if (arr.length > 1) {
            // Initiate the sort
            boolean sorted = false;
            int tmp = 0;
            // Iterate while not sorted
            while (!sorted) {
                // Initiate the breaking condition
                sorted = true;
                // Iterate through the array
                for (int i = 0; i < arr.length - 1; i++) {
                    // If element on the left is greater than element on the right,
                    if (arr[i] > arr[i+1]) {
                        // swap elements
                        tmp      = arr[i];
                        arr[i]   = arr[i+1];
                        arr[i+1] = tmp;
                        // Clear the breaking condition, demanding another
                        // full pass through the array
                        sorted = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // testing
        int[] array = new int[] {5, 11, 22, 12, 3, 1, 7};
        sort(array);
        System.out.println(Arrays.toString(array));

        array = new int[] {2, 1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
