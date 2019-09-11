package ua.hillel.java.elementary1.objects.api.exceptions;

public class FlowException extends Exception {

    public FlowException() {
    }

    public FlowException(String message) {
        super(message);
    }

    public FlowException(String message, Throwable cause) {
        super(message, cause);
    }
}
