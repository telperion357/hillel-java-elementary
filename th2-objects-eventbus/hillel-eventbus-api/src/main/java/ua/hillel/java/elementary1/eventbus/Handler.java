package ua.hillel.java.elementary1.eventbus;

/**
 * Handler used to handle events by concrete event bus.
 */
public interface Handler {
    /**
     * Handle event of the application.
     *
     * @param event the event
     */
    void handle(Event event);
}
