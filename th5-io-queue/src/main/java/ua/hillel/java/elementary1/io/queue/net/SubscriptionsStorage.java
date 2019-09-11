package ua.hillel.java.elementary1.io.queue.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionsStorage {
    private Map<String, List<Session>> sessions;

    public SubscriptionsStorage() {
        this.sessions = new HashMap<>();
    }

    public void subscribe(String queue, Session session) {
        sessions.computeIfAbsent(queue,
                k -> new ArrayList<>()).add(session);
    }

    public List<Session> getSubscriptions(String queue) {
        return sessions.getOrDefault(queue,
                Collections.emptyList());
    }

    public void unregisterSession(Session session) {
        sessions.forEach((k, v) -> v.remove(session));
    }
}
