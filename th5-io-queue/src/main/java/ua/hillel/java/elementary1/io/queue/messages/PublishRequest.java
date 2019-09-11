package ua.hillel.java.elementary1.io.queue.messages;

public class PublishRequest extends Message {
    private String queue;
    private String message;

    public PublishRequest(String queue, String message) {
        super(MessageType.PUBLISH_RQ);
        this.queue = queue;
        this.message = message;
    }

    public String getQueue() {
        return queue;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PublishRequest{" +
                "queue='" + queue + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
