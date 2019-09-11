package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import java.util.Arrays;

public class MergeSortRecursive {
    // CheatMergeSort
    // Adopted from Algorithms, 4th ed

    // auxiliary array for merges
    // private static int[] aux;

    // Merge a[lo..mid] with a[mid+1..hi].
    private static void merge(int[] arr, int lo, int mid, int hi, int[] aux)
    {
        int left_itr  = lo;
        int right_itr = mid + 1;

        // Copy arr[lo..hi] to aux[lo..hi].
        for (int k = lo; k <= hi; k++) {
            aux[k] = arr[k];
        }

        // Merge back to arr[lo..hi].
        for (int k = lo; k <= hi; k++) {
            // if the left part is exhausted, take element from the right part
            if (left_itr > mid) arr[k] = aux[right_itr++];
            // if the right part is exhausted, take element from the left part
            else if (right_itr > hi) arr[k] = aux[left_itr++];
            // compare right and left element, and take the smallest one
            else if (aux[right_itr] < aux[left_itr]) arr[k] = aux[right_itr++];
            else arr[k] = aux[left_itr++];
        }

    }

    public static void sort(int[] arr)
    {
        // Auxiliary array for merges
        // Allocate space just once.
        int [] aux = new int[arr.length];

        // Recursive sort call with auxilliary array
        sort(arr, 0, arr.length - 1, aux);
    }


    // Sort arr[lo..hi].
    // Recursive sort with auxilliary array
    private static void sort(int[] arr, int lo, int hi, int[] aux)
    {
        if (hi <= lo) return;           // Check bounds
        int mid = lo + (hi - lo)/2;     // Divide in two halves
        sort(arr, lo, mid, aux);        // Sort left half.
        sort(arr, mid+1, hi, aux);  // Sort right half.
        merge(arr, lo, mid, hi, aux);   // Merge results
    }

    // print test result
    private static void  printMergeSortResult(int[] arr) {
        System.out.println("Array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Result:" + Arrays.toString(arr));
    }

    public static void main(String[] args) {
        printMergeSortResult( new int[] {1, 15, 2, 4, 3});
        printMergeSortResult( new int[] {1});
    }
}
