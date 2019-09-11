package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Send chat message request which will be used to transfer message to chat.
 */
public class SendChatMessageRequest extends TextMessageRequest {
    private String chatName;

    public SendChatMessageRequest(String requestId, TextMessage message, String chatName) {
        super(EventType.SET_CHAT_MESSAGE_REQUEST.getType(), requestId, message);
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
        SendChatMessageRequest that = (SendChatMessageRequest) o;
        return Objects.equals(chatName, that.chatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatName);
    }

    @Override
    public String toString() {
        return "SendChatMessageRequest{" +
                "chatName='" + chatName + '\'' +
                "} " + super.toString();
    }
}
