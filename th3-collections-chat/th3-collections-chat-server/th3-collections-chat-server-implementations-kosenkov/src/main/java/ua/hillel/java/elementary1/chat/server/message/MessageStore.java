package ua.hillel.java.elementary1.chat.server.message;

import java.util.List;

public interface MessageStore {
    void add(Message message);

    Message get(long id);

    Message remove(long id);

    List<Message> getHistory(int n, long lastId);

    List<Message> getTop();

    // find by?
}
