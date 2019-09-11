package ua.hillel.java.elementary1.chat.server.handlers;

import ua.hillel.java.elementary1.chat.common.model.*;
import ua.hillel.java.elementary1.chat.server.message.Message;
import ua.hillel.java.elementary1.chat.server.message.MessageDatabase;
import ua.hillel.java.elementary1.chat.server.session.Session;
import ua.hillel.java.elementary1.chat.server.session.SessionStore;

import java.util.Collection;

public class SendChatMessageHandler implements Handler<SendChatMessageRequest, ErrorResponse> {
    private SessionStore sessionStore;
    private MessageDatabase messageDatabase;

    private static long idCounter = 0;

    public SendChatMessageHandler(SessionStore sessionStore, MessageDatabase messageDatabase) {
        this.sessionStore = sessionStore;
        this.messageDatabase = messageDatabase;
    }

    @Override
    public ErrorResponse handleEvent(SendChatMessageRequest request) {

        // Get the arrived message to work with it.
        TextMessage txtMsg = request.getMessage();
        String chatName = request.getChatName();
        // Get the new incremental id for the arrived message.
        long newId = idCounter++;
        // Transform the arrived message into the database format and store it
        // in the corresponding chat storage.
        Message msg = new Message(newId, txtMsg);
        messageDatabase.getChatStore(chatName).add(msg);
        // Overwrite the id of arrived message, i.e. create the new message
        // of the protocol format with the new id and the same content;
        // Notify all "online" users, subscribed for the chat.
        TextMessage newTxtMsg = new TextMessage(newId, txtMsg.getText(),
                txtMsg.getTime(), txtMsg.getAuthor(), txtMsg.isRead());
        ChatMessageNotification notification = new ChatMessageNotification(
                request.getRequestId(), chatName, newTxtMsg);
        Collection<Session> activeSessions = sessionStore.getSessions();
        for (Session session : activeSessions) {
            session.sendMessage(notification);
        }
        //
        return new ErrorResponse(request.getRequestId(), EventType.SET_CHAT_MESSAGE_RESPONSE.getType());
    }
}
