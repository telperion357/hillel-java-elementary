package ua.hillel.java.elementary1.objects.impl.kosenkov.executor;

import ua.hillel.java.elementary1.objects.impl.kosenkov.exception.CommandExecutionException;

public class SubtrCommandExecutor extends AbstractBiNumberExecutor{

    @Override
    protected double execute(double a, double b)  {
        return a - b;
    }

    @Override
    public String supportedCommand() {
        return "subtr";
    }
}
