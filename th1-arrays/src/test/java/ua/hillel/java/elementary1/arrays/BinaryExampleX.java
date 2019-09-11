package ua.hillel.java.elementary1.arrays;

public class BinaryExampleX {
    // indx - if found, -1 - not found
    private static int findPos(int[] array, int target) {
        int a = 0;
        int b = array.length;
        while (a < b) {
           int mid = (a + b) / 2;
           if (array[mid] == target) {
               return mid;
           }
           // what else?
           if (target < array[mid]) {
               b = mid;
           } else {
               a = mid + 1;
           }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out
                .println(
                        findPos(new int[] {1, 2, 3, 4, 5}, 9));
    }
}
