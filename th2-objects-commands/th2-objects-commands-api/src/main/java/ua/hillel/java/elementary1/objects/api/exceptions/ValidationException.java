package ua.hillel.java.elementary1.objects.api.exceptions;

public class ValidationException extends FlowException {

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
