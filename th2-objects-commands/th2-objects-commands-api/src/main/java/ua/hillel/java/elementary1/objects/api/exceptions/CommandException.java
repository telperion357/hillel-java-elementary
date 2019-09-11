package ua.hillel.java.elementary1.objects.api.exceptions;

public class CommandException extends FlowException {

    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
