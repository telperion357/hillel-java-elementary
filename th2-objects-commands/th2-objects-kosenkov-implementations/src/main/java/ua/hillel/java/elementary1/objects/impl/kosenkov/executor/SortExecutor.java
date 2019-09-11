package ua.hillel.java.elementary1.objects.impl.kosenkov.executor;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.executors.ValidationExecutor;

import java.util.Arrays;
import java.util.Comparator;

public class SortExecutor extends ValidationExecutor {

    private static enum SortingOrder  {ASCENDING, DESCENDING};

    @Override
    protected Object validatedExecute(Command command) {
        // Sorts parameters array in ascending or descending order

        Object[] params = command.getParameters();

        // Set the sorting order according to the first parameter
        // The order is ascending by default
        // If the first parameter is command option -d, exclude it from the array
        // And set the sorting order to descending;
        SortingOrder sortingOrder = SortingOrder.ASCENDING;
        if ((params[0] != null) && params[0].equals("-d")) {
            params =  Arrays.copyOfRange(params, 1, params.length);
            sortingOrder = SortingOrder.DESCENDING;
        }

        // Cast the array to double before sorting
        Double[] arr = new Double[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                arr[i] = convert(params[i]);
            } else {
                arr[i] = null;
            }
        }

        // Sort depending on the sorting order
        // Sort null-pointers to the left
        switch (sortingOrder) {
            case ASCENDING:
                Arrays.sort(arr, Comparator.nullsFirst(Comparator.naturalOrder()));
                break;
            case DESCENDING:
                Arrays.sort(arr, Comparator.nullsFirst(Comparator.reverseOrder()));
        }

        return arr;
    }

    @Override
    public String supportedCommand() {
        return "sort";
    }

    private double convert(Object x) {
        if (x instanceof Double) {
            return (double) x;
        }
        try {
            String s = x.toString();
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to convert argument into double: " + x, e);
        }
    }


    public static void main(String[] args) {
        Command sortCommand = new Command("sort", new Object[] {"-d", 3, 11, null, 17.5, null, 7.0, 5.0});
        SortExecutor sortExecutor = new SortExecutor();
        System.out.println(Arrays.toString((Object[]) sortExecutor.validatedExecute(sortCommand)));
    }
}
