package ua.hillel.java.elementary1.objects.impl.kosenkov.executor;

import ua.hillel.java.elementary1.objects.api.commands.Command;

public class DivCommandExecutor extends AbstractBiNumberExecutor{

    @Override
    protected double execute(double a, double b) {
        return a/b;
    }

    @Override
    public String supportedCommand() {
        return "div";
    }

    @Override
    protected void validate(Command command) {
        super.validate(command);
        // Check division by zero
        Object[] params = command.getParameters();
        if((double) params[1] == 0){
            throw new IllegalArgumentException("Division by zero is not allowed!");
        }
    }
}
