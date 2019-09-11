package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Chat history request used to get list of last entries.
 */
public class GetChatHistoryRequest extends Request {
    private String chatName;

    public GetChatHistoryRequest(String requestId, String chatName) {
        super(requestId, EventType.GET_CHAT_HISTORY_REQUEST.getType());
        this.chatName = chatName;
    }

    public String getChatName() {
        return chatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetChatHistoryRequest that = (GetChatHistoryRequest) o;
        return Objects.equals(chatName, that.chatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatName);
    }

    @Override
    public String toString() {
        return "GetChatHistoryRequest{" +
                "chatName='" + chatName + '\'' +
                "} " + super.toString();
    }
}
