package ua.hillel.java.elementary1.chat.server.session;

import java.util.Collection;

public interface SessionStore {
    Session findSession(String username);

    void registerSession(Session session);

    void closeSession(Session session);

    Collection<Session> getSessions();

}
