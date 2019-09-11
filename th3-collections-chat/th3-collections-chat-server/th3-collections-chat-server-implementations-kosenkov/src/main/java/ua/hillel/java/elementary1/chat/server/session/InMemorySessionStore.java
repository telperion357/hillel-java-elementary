package ua.hillel.java.elementary1.chat.server.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemorySessionStore implements SessionStore {
    private Map<String, Session> sessions;

    public InMemorySessionStore() {
        this.sessions = new HashMap<>();
    }

    @Override
    public Session findSession(String username) {
        return sessions.get(username);
    }

    @Override
    public void registerSession(Session session) {
        sessions.put(session.getUsername(), session);
    }

    @Override
    public void closeSession(Session session) {
        sessions.remove(session.getUsername());
    }

    @Override
    public Collection<Session> getSessions() {
        return sessions.values();
    }
}
