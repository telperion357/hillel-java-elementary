package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Send direct message request which must be transferred as direct message to another client.
 */
public class SendDirectMessageRequest extends TextMessageRequest {
    private String receiver;

    public SendDirectMessageRequest(String requestId, TextMessage message, String receiver) {
        super(EventType.SET_DIRECT_MESSAGE_REQUEST.getType(), requestId, message);
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendDirectMessageRequest that = (SendDirectMessageRequest) o;
        return Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver);
    }

    @Override
    public String toString() {
        return "SendDirectMessageRequest{" +
                "receiver='" + receiver + '\'' +
                "} " + super.toString();
    }
}
