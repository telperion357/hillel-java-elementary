// Homework_2 Task_1
// 1. interface RemoveFirstElement. Нужно написать метод
// (или просто создать и вывести результат в консоль)
// который удаляет :
// (1) элемент с массива (его первое вхождение) и возвращает результатом новый массив,
// (2) все найденные элементы с массива и возвращает массив.
// Массив и заданный элемент просто просто забить в код - считывать его не нужно с консоли
//
// https://gitlab.com/djandrewd/hillel-elementary-java01/blob/master/
// th1-arrays/src/main/java/ua/hillel/java/elementary1/arrays/tasks/RemoveFirstElement.java

package ua.hillel.java.elementary1.arrays.implementations.olegkosenkov;

import ua.hillel.java.elementary1.arrays.tasks.RemoveFirstElement;
import java.util.Arrays;

public class RemoveElements implements RemoveFirstElement {

    /**
     * Remove first discovery of element 'e' int the array and return result array instead.
     *
     * @param array the array
     * @param e  the element to remove
     * @return the array without first occurance of element 'e'.
     */
    public int[] removeFirstElement(int[] array, int e) {
        int[] result = array;

        // found the position of first occurance of e in array
        int ePosition = 0;
        while ((ePosition < array.length) && (array[ePosition] != e)) {
            ePosition++;
        }

        // If e is present in the array, copy elements into the new array
        // without the first occurance of e
        if (ePosition < array.length) {
            result = new int[array.length - 1];
            for (int i = 0 ; i < ePosition; i++) {
                result[i] = array[i];
            }
            for (int i = ePosition ; i < array.length - 1; i++) {
                result[i] = array[i+1];
            }
        }

        return result;
    }

    /**
     * Remove all occurances of element 'e' int the array and return result array instead.
     *
     * @param array the array
     * @param e  the element to remove
     * @return the array without all occurances of element 'e'.
     */
    public int[] removeAllElements(int[] array, int e) {

        // found the number of elements e in the input array
        int eCtr = 0;
        for (int i = 0; i < (array.length); i++) {
            if (array[i] == e) {
                eCtr++;
            }
        }

        // initialise the new array with the new length
        int[] result = new int[array.length - eCtr];

        // Iterate through the input array.
        // Copy all the elements except e to the new array
        eCtr = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != e) {
                result[i - eCtr] = array[i];
            }
            else {
                eCtr++;
            }
        }

        return result;
    }

    // Print for convenient testing removeFirstElement()
    public void printResultRemoveFirst(int[] arr, int e) {
        System.out.println("Remove First");
        System.out.println("array:  " + Arrays.toString(arr));
        System.out.println("e: " + e);
        System.out.println("result: " + Arrays.toString(removeFirstElement(arr, e)));
        System.out.println();
    }

    // Print for convenient testing removeAllElements()
    public void printResultRemoveAll(int[] arr, int e) {
        System.out.println("Remove All");
        System.out.println("array:  " + Arrays.toString(arr));
        System.out.println("e: " + e);
        System.out.println("result: " + Arrays.toString(removeAllElements(arr, e)));
        System.out.println();
    }

    // testing
    public static void main (String args[]) {

        int[] arr =  {1, 2, 3, 4, 5};
        int[] arr1 = {1, 2, 3, 4, 3};

        RemoveElements re = new RemoveElements();

        re.printResultRemoveFirst(arr, 1);
        re.printResultRemoveFirst(arr, 3);
        re.printResultRemoveFirst(arr, 5);
        re.printResultRemoveFirst(arr, 7);
        re.printResultRemoveFirst(arr1,3);

        re.printResultRemoveAll(arr, 1);
        re.printResultRemoveAll(arr, 3);
        re.printResultRemoveAll(arr, 5);
        re.printResultRemoveAll(arr1, 3);
        re.printResultRemoveAll(arr1, 7);
    }
}


