package ua.hillel.java.elementary1.chat.server.handlers;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.hillel.java.elementary1.chat.common.model.ErrorResponse;
import ua.hillel.java.elementary1.chat.common.model.EventType;
import ua.hillel.java.elementary1.chat.common.model.HiRequest;

import ua.hillel.java.elementary1.chat.server.session.Session;
import ua.hillel.java.elementary1.chat.server.session.SessionStore;

import static org.junit.Assert.*;

public class HiHandlerTest {
    private final String USERNAME = "Test username";
    private final String REQUEST_ID = "Test id";

    private Session session;
    private SessionStore sessionStore;
    private HiRequest hiRequest;
    private HiHandler hiHandler;

    @Before
    public void setup() {
        // Mock session and sessionStore
        session = Mockito.mock(Session.class);
        sessionStore = Mockito.mock(SessionStore.class);
        // Instantiate hiHandler with mocked session and sessionStore
        hiHandler = new HiHandler(session, sessionStore);
        // Mock hiRequest and its methods
        hiRequest = Mockito.mock(HiRequest.class);
        Mockito.when(hiRequest.getUsername()).thenReturn(USERNAME);
        Mockito.when(hiRequest.getRequestId()).thenReturn(REQUEST_ID);
        Mockito.when(hiRequest.getType()).thenReturn(EventType.HI_REQUEST.getType());
    }

    @Test
    public void handleEvent() {
        // Call handle event
        ErrorResponse response = hiHandler.handleEvent(hiRequest);
        // Verify that the method setUsername on object session
        // was called exactly once with argument USERNAME
        Mockito.verify(session, Mockito.times(1)).setUsername(USERNAME);
        // Verify that the method registerSession on object sessionStore
        // was called exactly once with argument session
        Mockito.verify(sessionStore, Mockito.times(1)).registerSession(session);
        // Check that the object ErrorResponse is created with the proper parameters
        assertEquals(REQUEST_ID, response.getRequestId());
        assertEquals(new Integer(EventType.HI_RESPONSE.getType()), response.getType());
    }
}