package ua.hillel.java.elementary1.io.queue.messages;

public class Message {
    private MessageType type;

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}
