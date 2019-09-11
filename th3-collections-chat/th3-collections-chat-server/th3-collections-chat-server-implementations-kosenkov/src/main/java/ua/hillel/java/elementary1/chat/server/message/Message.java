package ua.hillel.java.elementary1.chat.server.message;

import ua.hillel.java.elementary1.chat.common.model.TextMessage;

public class Message {
    private long id;
    private String sender;
    private String text;
    private long time;

    public Message(long id, String sender, String text, long time) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.time = time;
    }

    public Message(long id, TextMessage txtMsg) {
        this.id = id;
        this.sender = txtMsg.getAuthor();
        this.text = txtMsg.getText();
        this.time = txtMsg.getTime();
    }

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

}
