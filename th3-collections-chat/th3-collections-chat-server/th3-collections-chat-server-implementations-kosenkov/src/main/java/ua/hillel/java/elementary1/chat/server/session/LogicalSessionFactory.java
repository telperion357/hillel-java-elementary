package ua.hillel.java.elementary1.chat.server.session;

import ua.hillel.java.elementary1.chat.common.model.EventType;
import ua.hillel.java.elementary1.chat.server.api.LogicalSession;
import ua.hillel.java.elementary1.chat.server.api.PhysicalSession;
import ua.hillel.java.elementary1.chat.server.api.SessionFactory;
import ua.hillel.java.elementary1.chat.server.handlers.GetChatHistoryHandler;
import ua.hillel.java.elementary1.chat.server.handlers.HandlerRegistry;
import ua.hillel.java.elementary1.chat.server.handlers.HiHandler;
import ua.hillel.java.elementary1.chat.server.handlers.SendChatMessageHandler;
import ua.hillel.java.elementary1.chat.server.message.InMemoryMessageDatabase;
import ua.hillel.java.elementary1.chat.server.message.MessageDatabase;

public class LogicalSessionFactory implements SessionFactory {
    private SessionStore sessionStore;
    private MessageDatabase messageDatabase;

    public LogicalSessionFactory(){
        this.sessionStore = new InMemorySessionStore();
        this.messageDatabase = new InMemoryMessageDatabase();
    }

    @Override
    public LogicalSession createSession(long id, PhysicalSession physicalSession) {
        HandlerRegistry registry = new HandlerRegistry();
        Session session = new Session(id, physicalSession, sessionStore, registry);
        registerHandlers(session, registry);
        return session;
    }

    private void registerHandlers(Session session, HandlerRegistry registry) {
        registry.register(EventType.HI_REQUEST, new HiHandler(session, sessionStore));
        registry.register(EventType.GET_CHAT_HISTORY_REQUEST,
                new GetChatHistoryHandler(messageDatabase));
        registry.register(EventType.SET_CHAT_MESSAGE_REQUEST,
                new SendChatMessageHandler(sessionStore, messageDatabase));
    }
}
