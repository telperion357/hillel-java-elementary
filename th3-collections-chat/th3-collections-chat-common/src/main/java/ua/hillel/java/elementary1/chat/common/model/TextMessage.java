package ua.hillel.java.elementary1.chat.common.model;

import java.util.Objects;

/**
 * Text message holds information about message and additional information with it.
 */
public class TextMessage {
    private final long id;
    private final String text;
    private final long time;
    private final String author;
    private final boolean read;

    public TextMessage(long id, String text, long time) {
        this(id, text, time, null, false);
    }

    public TextMessage(long id, String text, long time, String author, boolean read) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.author = author;
        this.read = read;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextMessage that = (TextMessage) o;
        return id == that.id &&
                time == that.time &&
                read == that.read &&
                Objects.equals(text, that.text) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, time, author, read);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time=" + time +
                ", author='" + author + '\'' +
                ", read=" + read +
                '}';
    }
}
