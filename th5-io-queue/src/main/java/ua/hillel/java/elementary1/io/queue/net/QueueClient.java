package ua.hillel.java.elementary1.io.queue.net;

import com.google.gson.Gson;
import ua.hillel.java.elementary1.io.queue.messages.Message;
import ua.hillel.java.elementary1.io.queue.messages.MessageEvent;
import ua.hillel.java.elementary1.io.queue.messages.MessageType;
import ua.hillel.java.elementary1.io.queue.storage.ByteUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueClient {
    private static final Logger LOGGER = Logger.getLogger(QueueClient.class.getName());

    private static final int BUFFER_SIZE = 100_000_000;
    private static final int TIMEOUT = 2_000;
    private static final int WAIT_ON_EMPTY = 1000;
    private static final Gson GSON = new Gson();
    private static final int THREADS_COUNT = Runtime.getRuntime().availableProcessors() * 2;

    private String hostname;
    private int port;

    private SocketChannel channel;
    private ExecutorService executorService;
    private List<Consumer<Message>> listeners;

    public QueueClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.listeners = new CopyOnWriteArrayList<>();
    }

    public void connect() throws IOException {
        channel = SocketChannel.open();
        if (!channel.connect(new InetSocketAddress(hostname, port))) {
            throw new IOException("Socket is not available!");
        }
        channel.socket().setSoTimeout(TIMEOUT);
        channel.socket().setSendBufferSize(BUFFER_SIZE);
        channel.socket().setReceiveBufferSize(BUFFER_SIZE);
        channel.configureBlocking(false);

        executorService = Executors.newFixedThreadPool(THREADS_COUNT);
        executorService.execute(messageListener());
    }

    public void subscribe(Consumer<Message> consumer) {
        listeners.add(consumer);
    }

    public void close() throws IOException {
        channel.close();
        executorService.shutdown();
    }

    public void send(Message message) throws IOException {
        String msg = GSON.toJson(message);
        ByteUtils.writeMessage(msg.getBytes(), channel);
    }

    private Runnable messageListener() {
        return () -> {
            try {
                List<Message> messages = readOrWait();
                for (Consumer<Message> consumer : listeners) {
                    for (Message m : messages) {
                        executorService.execute(() -> consumer.accept(m));
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Problem while reading messages.", e);
            }
        };
    }

    private List<Message> readOrWait() throws Exception {
        Message o = readMessage();
        while (o == null) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(WAIT_ON_EMPTY));
            o = readMessage();
        }
        List<Message> messages = new ArrayList<>();
        while (o != null) {
            messages.add(o);
            o = readMessage();
        }
        return messages;
    }

    private Message readMessage() throws IOException {
        ByteBuffer result = ByteUtils.readLengthMessage(channel);
        if (result == null) {
            return null;
        }

        String message = new String(result.array(), StandardCharsets.UTF_8);
        Message m = GSON.fromJson(message, Message.class);
        if (m == null) {
            return null;
        }

        if (m.getType() == MessageType.MESSAGE_EVENT) {
            return GSON.fromJson(message, MessageEvent.class);
        }

        throw new IllegalArgumentException("Type of message [" + m.getType() + "] is not supported!");
    }
}
