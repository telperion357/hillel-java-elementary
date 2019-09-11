package ua.hillel.java.elementary1.objects.impl.kosenkov.exception;

import java.util.Arrays;

public class CommandExecutionException extends Exception{
    private Object[] args;
    private String command;

    public CommandExecutionException(Object[] args, String command) {
        this.args = args;
        this.command = command;
    }

    public CommandExecutionException(String message, Object[] args, String command) {
        super(message);
        this.args = args;
        this.command = command;
    }

    public CommandExecutionException(String message, Throwable cause, Object[] args, String command) {
        super(message, cause);
        this.args = args;
        this.command = command;
    }

    @Override
    public String toString() {
        return "CommandExecutionException{" +
                "args=" + Arrays.toString(args) +
                ", command='" + command + '\'' +
                '}' + super.toString();
    }
}
