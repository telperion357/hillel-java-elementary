package ua.hillel.java.elementary1.io.queue.messages;

import java.util.List;

public class MessageEvent extends Message {
    private String queue;
    private List<String> messages;

    public MessageEvent(String queue,
                        List<String> messages) {
        super(MessageType.MESSAGE_EVENT);
        this.queue = queue;
        this.messages = messages;
    }

    public String getQueue() {
        return queue;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "queue='" + queue + '\'' +
                ", messages=" + messages +
                '}';
    }
}
