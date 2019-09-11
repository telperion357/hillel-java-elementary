package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import java.util.Arrays;

// Selection sort in ascending order
public class MinSort {

    public static void sort(int[] arr) {
        if (arr.length > 1) {
            for (int i = 0; i < arr.length - 1; i++) {
                int indexMin = findIndexMin(arr, i, arr.length - 1);
                swap(arr, i, indexMin);
            }
        }
    }

    // return the index of minimum element in the subarray arr[lo...hi]
    private static int findIndexMin(int[] arr, int lo, int hi) {
        int min = lo;
        for (int j = lo + 1; j <= hi ; j++) {
            if (arr[j] < arr[min]) {
                min = j;
            }
        }
        return min;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 17, 2, 100, 1, 35, 7};
        sort(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[] {5, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
