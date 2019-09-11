package ua.hillel.java.elementary1.chat.common.model;

import java.util.EnumMap;

import static ua.hillel.java.elementary1.chat.common.model.EventType.GET_CHAT_HISTORY_REQUEST;
import static ua.hillel.java.elementary1.chat.common.model.EventType.GET_DIRECT_HISTORY_REQUEST;
import static ua.hillel.java.elementary1.chat.common.model.EventType.HI_REQUEST;
import static ua.hillel.java.elementary1.chat.common.model.EventType.REGISTER_CHAT_REQUEST;
import static ua.hillel.java.elementary1.chat.common.model.EventType.SET_CHAT_MESSAGE_REQUEST;
import static ua.hillel.java.elementary1.chat.common.model.EventType.SET_DIRECT_MESSAGE_REQUEST;

/**
 * Event types mapping between {@link EventType} and class of the request.
 */
public class EventTypesMapping {
    private static final EnumMap<EventType, Class<? extends Request>> MAPPING = new EnumMap<>(EventType.class);

    static {
        MAPPING.put(HI_REQUEST, HiRequest.class);
        MAPPING.put(GET_CHAT_HISTORY_REQUEST, GetChatHistoryRequest.class);
        MAPPING.put(SET_CHAT_MESSAGE_REQUEST, SendChatMessageRequest.class);
        MAPPING.put(GET_DIRECT_HISTORY_REQUEST, GetDirectMessagesHistoryRequest.class);
        MAPPING.put(SET_DIRECT_MESSAGE_REQUEST, SendDirectMessageRequest.class);
        MAPPING.put(REGISTER_CHAT_REQUEST, RegisterChatRequest.class);
    }

    /**
     * Gets request class mapping for type of the event.
     *
     * @param type the type
     * @return the mapping of type of the event or null if not mapping exists.
     */
    public static Class<? extends Request> getMapping(int type) {
        EventType et = EventType.byType(type);
        return MAPPING.get(et);
    }
}
