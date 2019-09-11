package ua.hillel.java.elementary1.objects.impl.kosenkov.executor;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.executors.ValidationExecutor;
import ua.hillel.java.elementary1.objects.impl.kosenkov.exception.CommandExecutionException;

import java.util.Arrays;

public class PrintExecutor extends ValidationExecutor {

    @Override
    protected Object validatedExecute(Command command) {
        // Printing all command parameters to standard output

        Object[] params = command.getParameters();

        for (int i = 0; i < params.length; i++) {

            // If the i-th parameter is not null, print it
            if (params[i] != null) {
                // If i-th parameter is an array, print it as an array
                if (params[i] instanceof  Object[]) {
                    System.out.println(Arrays.toString((Object[]) params[i]));
                }
                else {
                    System.out.println(params[i]);
                }
            }
            else {
                throw new IllegalArgumentException("One of the print parameters is null");
            }
        }

        // This method has only side effects (printing) and returns null
        return null;
    }

    @Override
    public String supportedCommand() {
        return "print";
    }
}
