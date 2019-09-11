package ua.hillel.java.elementary1.chat.common.model;

public class DirectMessageNotification extends ErrorResponse {

    private String sender;
    private TextMessage message;

    public DirectMessageNotification(String requestId, String sender,
                                     TextMessage message) {
        super(requestId, EventType.DIRECT_MESSAGE_NOTIFICATION.getType());
        this.sender = sender;
        this.message = message;
    }

    public DirectMessageNotification(Integer status, String requestId, String sender,
                                     TextMessage message) {
        super(status, requestId, EventType.DIRECT_MESSAGE_NOTIFICATION.getType());
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public TextMessage getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DirectMessageNotification{" +
                "sender='" + sender + '\'' +
                ", message=" + message +
                "} " + super.toString();
    }
}
