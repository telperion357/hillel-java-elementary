package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Register chat request to register user in the chat.
 */
public class RegisterChatRequest extends Request {
    private String chatName;

    /**
     * Instantiates a new Register chat request.
     *
     * @param requestId the request id
     * @param chatName  the chat name
     */
    public RegisterChatRequest(String requestId, String chatName) {
        super(requestId, EventType.REGISTER_CHAT_REQUEST.getType());
        this.chatName = chatName;
    }

    /**
     * Gets chat name.
     *
     * @return the chat name
     */
    public String getChatName() {
        return chatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterChatRequest that = (RegisterChatRequest) o;
        return Objects.equals(chatName, that.chatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatName);
    }
}
