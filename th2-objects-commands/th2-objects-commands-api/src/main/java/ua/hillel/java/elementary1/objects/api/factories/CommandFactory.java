package ua.hillel.java.elementary1.objects.api.factories;

import ua.hillel.java.elementary1.objects.api.commands.Command;

/**
 * Factory class for creation of {@link Command}
 */
public interface CommandFactory {
    /**
     * Create Command instance from command line arguments.
     *
     * @param args the arguments
     *
     * @return the command used for execution.
     */
    Command createFromArgs(String[] args);

    /**
     * Extends existed Command instance with result arguments
     *
     * @param command the argument command
     * @param rs result object of the execution
     *
     * @return the command used for execution.
     */
    Command extendsWithResult(Command command, Object rs);
}
