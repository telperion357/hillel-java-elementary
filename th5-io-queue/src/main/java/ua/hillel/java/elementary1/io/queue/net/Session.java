package ua.hillel.java.elementary1.io.queue.net;

import com.google.gson.Gson;
import ua.hillel.java.elementary1.io.queue.messages.Message;
import ua.hillel.java.elementary1.io.queue.messages.MessageEvent;
import ua.hillel.java.elementary1.io.queue.messages.MessageType;
import ua.hillel.java.elementary1.io.queue.messages.PublishRequest;
import ua.hillel.java.elementary1.io.queue.messages.SubscriptionRequest;
import ua.hillel.java.elementary1.io.queue.storage.ByteUtils;
import ua.hillel.java.elementary1.io.queue.storage.StorageEngine;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Session implements Closeable {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static long COUNTER = 0;

    private long id;
    private SubscriptionsStorage subscriptionsStorage;
    private StorageEngine storageEngine;
    private Gson gson;
    private SocketChannel channel;

    public Session(SubscriptionsStorage subscriptionsStorage,
                   StorageEngine storageEngine,
                   SocketChannel channel) {
        this.id = COUNTER++;
        this.subscriptionsStorage = subscriptionsStorage;
        this.storageEngine = storageEngine;
        this.channel = channel;
        this.gson = new Gson();
    }

    public void onMessage(ByteBuffer msg) {
        String str = new String(msg.array());
        try {
            Message m = gson.fromJson(str, Message.class);
            if (m == null) {
                throw new IllegalStateException();
            }
            if (m.getType() == MessageType.SUBSCRIPTION_RQ) {
                SubscriptionRequest rq = gson.fromJson(str, SubscriptionRequest.class);
                subscriptionsStorage.subscribe(rq.getQueue(), this);

                logger.info(String.format("Subscribed to %s!", rq.getQueue()));

                List<String> messages = storageEngine.getMessages(rq.getQueue());
                MessageEvent event = new MessageEvent(rq.getQueue(), messages);
                sendMessage(event);
            }
            if (m.getType() == MessageType.PUBLISH_RQ) {
                PublishRequest rq = gson.fromJson(str, PublishRequest.class);
                storageEngine.offer(rq.getQueue(), rq.getMessage());

                logger.info(String.format("Published [%s] into %s!", rq.getMessage(), rq.getQueue()));
                List<Session> subs = subscriptionsStorage.getSubscriptions(rq.getQueue());
                if (subs.isEmpty()) {
                    return;
                }

                List<String> messages = storageEngine.getMessages(rq.getQueue());
                MessageEvent event = new MessageEvent(rq.getQueue(), messages);
                Session session = subs.get((int) (Math.random() * subs.size()));
                session.sendMessage(event);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Problem occured: [%s]. Message [%s]", e.getMessage(), str), e);
        }
    }

    public void sendMessage(Object o) throws IOException {
        String message = gson.toJson(o);
        ByteUtils.writeMessage(message.getBytes(), channel);
        logger.info(String.format("Messages [%s] send!", message));
    }

    public void onClose() {
        subscriptionsStorage.unregisterSession(this);
    }

    public void close() throws IOException {
        if (channel.isOpen()) {
            channel.close();
        }
        onClose();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
