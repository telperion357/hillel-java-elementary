package ua.hillel.java.elementary1.eventbus.impl.kosenkov;

import ua.hillel.java.elementary1.eventbus.Event;
import ua.hillel.java.elementary1.eventbus.Handle;
import ua.hillel.java.elementary1.eventbus.Handler;

public class HandlerA implements Handler {

    @Override
    @Handle(type = "A")
    public void handle(Event event) {
        // Some action
        System.out.println(event);
    }
}
