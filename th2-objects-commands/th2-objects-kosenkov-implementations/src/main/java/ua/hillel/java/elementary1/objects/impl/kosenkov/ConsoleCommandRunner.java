package ua.hillel.java.elementary1.objects.impl.kosenkov;

import ua.hillel.java.elementary1.objects.api.CommandRunner;
import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.exceptions.FlowException;
import ua.hillel.java.elementary1.objects.api.executors.CommandExecutor;
import ua.hillel.java.elementary1.objects.impl.kosenkov.executor.AddCommandExecutor;
//import ua.hillel.java.elementary1.objects.impl.kosenkov.executor.SubtrCommandExecutor;

public class ConsoleCommandRunner extends CommandRunner{

    @Override
    public Object execute(String[] args)
    {
        CommandExecutor executor = new AddCommandExecutor();
        Command command = new Command("add", new Object[] {1.0, 2.0});

        // try-catch for compilation reasons
        try {
            return executor.execute(command);
        } catch (FlowException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean supportCommand(String command) {
        return false;
    }

    public static void main(String[] args) {
        ConsoleCommandRunner commandRunner = new ConsoleCommandRunner();
        System.out.println(commandRunner.execute(null).toString());
    }
}
