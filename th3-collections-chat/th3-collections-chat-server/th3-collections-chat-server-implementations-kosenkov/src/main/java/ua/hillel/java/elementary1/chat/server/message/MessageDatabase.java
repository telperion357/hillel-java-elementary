package ua.hillel.java.elementary1.chat.server.message;

public interface MessageDatabase {
    MessageStore getChatStore(String chat);

    MessageStore getDirectStore(String u1, String u2);
}
