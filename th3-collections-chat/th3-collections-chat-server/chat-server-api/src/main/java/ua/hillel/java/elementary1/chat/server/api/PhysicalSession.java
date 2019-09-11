package ua.hillel.java.elementary1.chat.server.api;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;

/**
 * Physical session is communication channel with client allowing sending messages thought directed link.
 */
public interface PhysicalSession {
    /**
     * Send message via physical link.
     *
     * @param message the message
     */
    void sendMessage(ErrorResponse message);

    /**
     * Close physical session.
     */
    void close();
}
