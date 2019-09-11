// Homework_2 Task_4
// 4. (оптиционально) interface SortedArraysMerger.
// Нужно написать метод (блок кода) который соединяет 2 отсортированных массива в один тоже отсортированный.
// Нужно сделать за O(N) время

package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import ua.hillel.java.elementary1.arrays.tasks.SortedArraysMerger;
import java.util.Arrays;

public class ArrayMerge implements SortedArraysMerger {

    /**
     * Merge two sorted arrays and return array with all elements in sorted order
     *
     * @param a the first array
     * @param b the second array
     * @return the array with full elements in the sorted order.
     */
    public int[] merge(int[] a, int[] b) {

        // Initialise the resulting array with combined length
        int[] result = new int[a.length + b.length];
        
        // Setup iterator for b
        int b_itr = 0;

        // Iterate through a one by one
        for (int a_itr = 0; a_itr < a.length; a_itr++) {

            // If there are elements in b that are less or equal to the
            // current element of a, add them to the resulting array
            while ((b_itr < b.length) && (b[b_itr] <= a[a_itr])) {
                result[a_itr + b_itr] = b[b_itr];
                b_itr++;
            }

            // Add the current element of a to the resulting array
            result[a_itr + b_itr] = a[a_itr];
        }

        // If any elements are left in b, add them to the resulting array
        while ((b_itr < b.length)) {
            result[a.length + b_itr] = b[b_itr];
            b_itr++;
        }

        return result;
    }

    // Print method for convenient testing
    public void printResultArraysMerger(int[] a, int[] b) {
        System.out.println("Sorted Arrays Merge");
        System.out.println("array a: " + Arrays.toString(a));
        System.out.println("array b: " + Arrays.toString(b));
        System.out.println("result:  " + Arrays.toString(merge(a, b)));
        System.out.println();
    }

    // Testing
    public static void main(String args[]) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {1, 3, 5, 7, 8};
        ArrayMerge am = new ArrayMerge();
        am.printResultArraysMerger(a , b);

        a = new int[] {1};
        b = new int[] {3};
        am.printResultArraysMerger(a , b);


    }
}
