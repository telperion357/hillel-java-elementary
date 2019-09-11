package ua.hillel.java.elementary1.eventbus;

/**
 * Event BUS main object for managing events and handlers together.
 */
public interface EventBus {
    /**
     * Fire event into event bus which can be handled by handlers.
     *
     * @param event the event to be fired.
     */
    void fire(Event event);

    /**
     * Subscribe with handler on the event bus type.
     *
     * @param type    the type of the event to handle.
     * @param handler the handler for the concrete event type.
     */
    void subscribe(String type, Handler handler);

    /**
     * Add current handle methods to be handle by events.
     *
     * @param handler the handler used in event-bus
     */
    void subscribe(Object handler);
}
