package ua.hillel.java.elementary1.objects.api.executors;

import ua.hillel.java.elementary1.objects.api.commands.Command;
import ua.hillel.java.elementary1.objects.api.exceptions.FlowException;

/**
 * Command executors.
 */
public interface CommandExecutor {
    // Another important part (paradigm) is polymorphism.
    // Is basically means that under same interface (as method of manipulation) you can hide lots of realization.
    // In general case this like : car gears -> cars are different but method of manipulation is same.
    //
    // In Java in additional to classes exists another construction called interfaces.
    // This special class which consist only with abstract (without implementation) public methods.

    /// We can omit public as it is always public
    Object execute(Command command) throws FlowException;

    String supportedCommand();

}
