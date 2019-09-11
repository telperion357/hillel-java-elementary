package ua.hillel.java.elementary1.chat.server.handlers;

import ua.hillel.java.elementary1.chat.common.model.*;
import ua.hillel.java.elementary1.chat.server.message.Message;
import ua.hillel.java.elementary1.chat.server.message.MessageDatabase;
import ua.hillel.java.elementary1.chat.server.message.MessageStore;

import java.util.ArrayList;
import java.util.List;

public class GetChatHistoryHandler implements Handler<GetChatHistoryRequest, GetMessagesHistoryResponse>{
    private MessageDatabase messageDatabase;

    public GetChatHistoryHandler(MessageDatabase messageDatabase) {
        this.messageDatabase = messageDatabase;
    }

    @Override
    public GetMessagesHistoryResponse handleEvent(GetChatHistoryRequest request) {
        String chatName = request.getChatName();
        MessageStore messageStore = messageDatabase.getChatStore(chatName);
        List<Message> messages = messageStore.getTop();
        List<TextMessage> textMessages = new ArrayList<>(messages.size());
        // Transform messages from storage format into TextMessage protocol format
        for(Message msg : messages) {
            TextMessage txtMsg = new TextMessage(
                    msg.getId(),
                    msg.getText(),
                    msg.getTime(),
                    msg.getSender(),
                    true
            );
            textMessages.add(txtMsg);
        }

        return new GetMessagesHistoryResponse(
                EventType.GET_CHAT_HISTORY_RESPONSE,
                request.getRequestId(),
                textMessages
        );
    }
}
