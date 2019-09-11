package ua.hillel.java.elementary1.chat.server.handlers;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.EventType;
import ua.hillel.java.elementary1.chat.common.model.HiRequest;
import ua.hillel.java.elementary1.chat.server.api.LogicalSession;
import ua.hillel.java.elementary1.chat.server.session.Session;
import ua.hillel.java.elementary1.chat.server.session.SessionStore;


public class HiHandler implements Handler<HiRequest, ErrorResponse> {
    private Session session;
    private SessionStore sessionStore;

    public HiHandler(Session session, SessionStore sessionStore) {
        this.session = session;
        this.sessionStore = sessionStore;
    }

    @Override
    public ErrorResponse handleEvent(HiRequest hiRequest) {

        session.setUsername(hiRequest.getUsername());
        sessionStore.registerSession(session);
        return new ErrorResponse(hiRequest.getRequestId(), EventType.HI_RESPONSE.getType());
    }
}
