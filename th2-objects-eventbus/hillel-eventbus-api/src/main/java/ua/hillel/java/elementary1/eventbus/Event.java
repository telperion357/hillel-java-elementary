package ua.hillel.java.elementary1.eventbus;

/**
 * Event which primary will be handled by application.
 */
public class Event {
    private String type;

    /**
     * Instantiates a new Event.
     *
     * @param type the type
     */
    public Event(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type='" + type + '\'' +
                '}';
    }
}
