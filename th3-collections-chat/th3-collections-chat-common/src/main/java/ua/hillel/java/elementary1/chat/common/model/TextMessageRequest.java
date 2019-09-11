package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * TextMessageRequest which will be transferred from server to client.
 */
public class TextMessageRequest extends Request {
    private TextMessage message;

    TextMessageRequest(int type, String requestId, TextMessage message) {
        super(requestId, type);
        this.message = message;
    }

    public TextMessage getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextMessageRequest that = (TextMessageRequest) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "{ message = " + message + '}';
    }
}
