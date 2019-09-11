package ua.hillel.java.elementary1.chat.server.api;

/**
 * Session factory for creation of {@link LogicalSession} instances.
 */
public interface SessionFactory {
    /**
     * Create logical session with provided identifier and physical session.
     *
     * @param id              the identifier of the session.
     * @param physicalSession the physical session
     * @return the logical session returned by this factory.
     */
    LogicalSession createSession(long id, PhysicalSession physicalSession);
}
