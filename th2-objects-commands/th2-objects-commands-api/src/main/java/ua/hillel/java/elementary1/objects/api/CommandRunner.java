package ua.hillel.java.elementary1.objects.api;

import ua.hillel.java.elementary1.objects.api.exceptions.FlowException;

/**
 * Runner which used to run commands from command line.
 */
public abstract class CommandRunner {
    /**
     * Execute command from command line arguments.
     *
     * @param args the arguments from the console
     *
     * @return the returning object.
     */
    public abstract Object execute(String[] args) throws FlowException;

    /**
     * Checks if provided command is supported.
     *
     * @param command the command
     *
     * @return the true in case command is supported, false otherwise.
     */
    public abstract boolean supportCommand(String command);

    @Override
    public String toString() {
        String a = "1";
        a += super.toString();
        return a + "B";
    }
}
