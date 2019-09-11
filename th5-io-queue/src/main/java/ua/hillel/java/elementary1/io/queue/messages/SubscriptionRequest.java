package ua.hillel.java.elementary1.io.queue.messages;

public class SubscriptionRequest extends Message {
    private String queue;

    public SubscriptionRequest(String queue) {
        super(MessageType.SUBSCRIPTION_RQ);
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "SubscriptionRequest{" +
                "queue='" + queue + '\'' +
                "} " + super.toString();
    }
}
