package ua.hillel.java.elementary1.objects.api.factories;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.executors.CommandExecutor;

/**
 * Executors factory used to create executor for given command.
 */
public interface ExecutorsFactory {
    /**
     * Create executor.
     *
     * @param command the command used to create executor
     *
     * @return the executor of the command.
     */
    CommandExecutor createExecutor(Command command);
}
