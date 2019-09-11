package ua.hillel.java.elementary1.objects.api.executors;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.exceptions.CommandException;
import ua.hillel.java.elementary1.objects.api.exceptions.FlowException;
import ua.hillel.java.elementary1.objects.api.exceptions.ValidationException;

/**
 * Validation executor which validates the command.
 */
public abstract class ValidationExecutor implements CommandExecutor {
    // Last and final paradigm called inheritance.
    // Inheritance is allow child class aggregation of all properties and methods of the parent.
    // When class A inherits class B in Java it written: A extends B
    // With interfaces situation is bit different - you have to implement all methods (because interface have no state).
    // Multiple inheritance is not allowed in java. A cannot extends classes B and C
    // But multiple implementation is possible class A can implement interfaces B and C.
    // Class A can extend 1 superclass and many interfaces at the same time.
    //
    //
    // Special 'abstract' classes exists in Java. This means that class can have 0 or more abstract (without realization) methods.
    //
    // In class in abstract then it might not implement interface methods.
    @Override
    public Object execute(Command command) throws FlowException {
        try {
            validate(command);
            try {
                return validatedExecute(command);
            } catch (Exception e) {
                throw new CommandException("Command error!", e);
            }
        } catch (Exception e) {
            throw new ValidationException("Validation error!", e);
        }
    }

    protected abstract Object validatedExecute(Command command);

    protected void validate(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Command is null!");
        }
        if (command.getName() == null || command.getName().isEmpty()) {
            throw new IllegalArgumentException("Command name is empty!");
        }
        Object[] cmd = command.getParameters();
        if (cmd == null) {
            throw new IllegalArgumentException("Command params are null!");
        }
    }

    @Override
    public String supportedCommand() {
        return null;
    }
}
