package ua.hillel.java.elementary1.chat.server.session;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.Request;
import ua.hillel.java.elementary1.chat.server.api.LogicalSession;
import ua.hillel.java.elementary1.chat.server.api.PhysicalSession;
import ua.hillel.java.elementary1.chat.server.handlers.Handler;
import ua.hillel.java.elementary1.chat.server.handlers.HandlerRegistry;

public class Session implements LogicalSession {
    private long id;
    private String username;
    private SessionStore sessionStore;
    private PhysicalSession physicalSession;
    private HandlerRegistry handlerRegistry;

    public Session(long id,  PhysicalSession physicalSession,
                   SessionStore sessionStore, HandlerRegistry handlerRegistry) {
        this.id = id;
        this.physicalSession = physicalSession;
        this.sessionStore = sessionStore;
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void sendMessage(ErrorResponse response) {
        physicalSession.sendMessage(response);
    }

    @Override
    public PhysicalSession getPhysicalSession() {
        return physicalSession;
    }

    @Override
    public void onMessage(Request request) {
        Handler<Request,ErrorResponse> handler = handlerRegistry.getHandler(request.getType());
        ErrorResponse response = handler.handleEvent(request);
        sendMessage(response);
    }

    @Override
    public void onClose() {
        sessionStore.closeSession(this);
    }

    @Override
    public void close() {
        physicalSession.close();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
