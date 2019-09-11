package ua.hillel.java.elementary1.objects.impl.kosenkov.executor;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.executors.CommandExecutor;
import ua.hillel.java.elementary1.objects.api.executors.ValidationExecutor;
import ua.hillel.java.elementary1.objects.impl.kosenkov.exception.CommandExecutionException;

public abstract class AbstractBiNumberExecutor extends ValidationExecutor {

    @Override
    protected Object validatedExecute(Command command) {

        Object[] params = command.getParameters();
        try {
            return execute(convert(params[0]), convert(params[1]));
        }
        catch (CommandExecutionException e) {
            throw new RuntimeException("Execution error", e);
        } catch (Exception e) {
            throw new RuntimeException("General error", e);
        }
    }

    @Override
    protected void validate(Command command) {
        super.validate(command);

        Object[] params = command.getParameters();

        if(params.length != 2) {
            throw new IllegalArgumentException("There should be exactly 2 parameters");
        }

        if(params[0] == null || params[1] == null) {
            throw new IllegalArgumentException("One of parameters is null");
        }

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

    protected abstract double execute(double a, double b)
            throws CommandExecutionException;
}

