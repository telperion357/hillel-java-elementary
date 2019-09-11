package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Used to get history of direct messages between users.
 */
public class GetDirectMessagesHistoryRequest extends Request {

    private String receiver;

    public GetDirectMessagesHistoryRequest(String requestId, String receiver) {
        super(requestId, EventType.GET_DIRECT_HISTORY_REQUEST.getType());
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetDirectMessagesHistoryRequest that = (GetDirectMessagesHistoryRequest) o;
        return Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), receiver);
    }

    @Override
    public String toString() {
        return "GetDirectMessagesHistoryRequest{" +
                "receiver='" + receiver + '\'' +
                "} " + super.toString();
    }
}
