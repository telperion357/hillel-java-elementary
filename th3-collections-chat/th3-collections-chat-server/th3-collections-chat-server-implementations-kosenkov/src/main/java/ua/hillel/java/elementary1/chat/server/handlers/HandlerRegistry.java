package ua.hillel.java.elementary1.chat.server.handlers;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.EventType;
import ua.hillel.java.elementary1.chat.common.model.Request;

import java.util.EnumMap;
import java.util.Map;

public class HandlerRegistry {

    private Map<EventType, Handler<? extends Request, ? extends ErrorResponse>>
            handlers;

    public HandlerRegistry() {
        handlers = new EnumMap<>(EventType.class);

    }

    public Handler<Request, ErrorResponse> getHandler(int type) {
        EventType eventType = EventType.byType(type);
        return (Handler<Request, ErrorResponse>) handlers.get(eventType);
    }

    public void register(EventType eventType, Handler<? extends Request, ? extends ErrorResponse> handler) {
        handlers.put(eventType, handler);
    }
}
