package ua.hillel.java.elementary1.chat.server.api;

import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.Request;

/**
 * Logical session of user within application. Holds reference of the {@link PhysicalSession} inside.
 */
public interface LogicalSession {
    /**
     * Gets unique identifier of the session.
     *
     * @return the identifier of the session.
     */
    long getId();

    /**
     * Gets physical session associated with this session.
     *
     * @return the physical session
     */
    PhysicalSession getPhysicalSession();

    /**
     * Called when message is received from physical session.
     *
     * @param request the request
     */
    void onMessage(Request request);

    /**
     * Send message to client.
     *
     * @param response the response to be sent to the client
     */
    void sendMessage(ErrorResponse response);

    /**
     * On close called when physical session is closed.
     */
    void onClose();

    /**
     * Close logical session which closes {@link PhysicalSession}.
     * NOTE! It will call {@link #onClose()} on this session. Do not close session again inside!
     */
    void close();
}
