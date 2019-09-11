package ua.hillel.java.elementary1.chat.common.model;

import java.util.List;
import java.util.Objects;

/**
 * Get messages history response common for both direct messages and chat.
 */
public class GetMessagesHistoryResponse extends ErrorResponse {
    private List<TextMessage> messages;

    public GetMessagesHistoryResponse(EventType eventType, String requestId, List<TextMessage> messages) {
        super(requestId, eventType.getType());
        this.messages = messages;
    }

    public GetMessagesHistoryResponse(EventType eventType, Integer status, String requestId, List<TextMessage> messages) {
        super(status, requestId, eventType.getType());
        this.messages = messages;
    }

    public List<TextMessage> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetMessagesHistoryResponse that = (GetMessagesHistoryResponse) o;
        return Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), messages);
    }

    @Override
    public String toString() {
        return "GetMessagesHistoryResponse{" +
                "messages=" + messages +
                "} " + super.toString();
    }
}
