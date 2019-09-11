package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

public class ChatMessageNotification extends ErrorResponse {

    private String chatName;
    private TextMessage message;

    public ChatMessageNotification(String requestId,
                                   String chatName,
                                   TextMessage message) {
        super(requestId, EventType.CHAT_MESSAGE_NOTIFICATION.getType());
        this.chatName = chatName;
        this.message = message;
    }

    public ChatMessageNotification(Integer status,
                                   String requestId,
                                   String chatName,
                                   TextMessage message) {
        super(status, requestId, EventType.CHAT_MESSAGE_NOTIFICATION.getType());
        this.chatName = chatName;
        this.message = message;
    }

    public String getChatName() {
        return chatName;
    }

    public TextMessage getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChatMessageNotification that = (ChatMessageNotification) o;
        return Objects.equals(chatName, that.chatName) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatName, message);
    }

    @Override
    public String toString() {
        return "ChatMessageNotification{" +
                "chatName='" + chatName + '\'' +
                ", message=" + message +
                "} " + super.toString();
    }
}
