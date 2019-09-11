package ua.hillel.java.elementary1.chat.common.model;

public enum EventType {
    HI_REQUEST(0),
    HI_RESPONSE(1),

    GET_CHAT_HISTORY_REQUEST(2),
    GET_CHAT_HISTORY_RESPONSE(3),

    GET_DIRECT_HISTORY_REQUEST(4),
    GET_DIRECT_HISTORY_RESPONSE(5),

    SET_CHAT_MESSAGE_REQUEST(6),
    SET_CHAT_MESSAGE_RESPONSE(7),

    SET_DIRECT_MESSAGE_REQUEST(8),
    SET_DIRECT_MESSAGE_RESPONSE(9),

    DIRECT_MESSAGE_NOTIFICATION(10),
    CHAT_MESSAGE_NOTIFICATION(11),

    REGISTER_CHAT_REQUEST(12),
    REGISTER_CHAT_RESPONSE(13)
    ;
    private int type;

    EventType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static EventType byType(int type) {
        for (EventType t : values()) {
            if (t.type == type) {
                return t;
            }
        }
        throw new IllegalArgumentException("Incorrect type :" + type);
    }
}
