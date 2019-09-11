package ua.hillel.java.elementary1.io.queue.net;

import ua.hillel.java.elementary1.io.queue.storage.ByteUtils;
import ua.hillel.java.elementary1.io.queue.storage.StorageEngine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class QueueServer {
    private static final Logger logger = Logger.getLogger(QueueServer.class.getName());

    private static final int BUFFER_SIZE = 100_000_000;
    private static final int SO_TIMEOUT = 2000;

    private SubscriptionsStorage subscriptionsStorage;
    private StorageEngine storageEngine;
    private int port;
    private String hostname;

    public QueueServer(SubscriptionsStorage subscriptionsStorage,
                       StorageEngine storageEngine,
                       int port,
                       String hostname) {
        this.subscriptionsStorage = subscriptionsStorage;
        this.storageEngine = storageEngine;
        this.port = port;
        this.hostname = hostname;
    }

    public void start() throws IOException {
        ServerSocketChannel sc = ServerSocketChannel.open();
        sc.bind(new InetSocketAddress(hostname, port));
        sc.socket().setReuseAddress(true);
        sc.socket().setReceiveBufferSize(BUFFER_SIZE);
        sc.socket().setSoTimeout(SO_TIMEOUT);

        logger.info(String.format("Listening to %s:%s", hostname, port));

        // Say it is not blocking!
        sc.configureBlocking(false);
        // Ask about descriptors
        Selector selector = Selector.open();
        // Register selector only for connected and accepted.
        sc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // How many available channels since last call?
            int n = selector.select();
            if (n <= 0) {
                // Nothing changed.
                continue;
            }
            // Changed keys
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext(); ) {
                SelectionKey key = i.next();
                // Channel in accepted state, we ask system
                // to get read-write descriptor.
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    // read the byte.
                    try {
                        read(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Must remove after.
                i.remove();
            }
        }
    }

    // Register channel for read and write
    private void accept(SelectionKey key) throws IOException {
        logger.info(String.format("Accepted connection from [%s]", key));

        SocketChannel newChannel = ((ServerSocketChannel) key.channel()).accept();
        newChannel.configureBlocking(false);
        newChannel.socket().setSendBufferSize(BUFFER_SIZE);
        // Add attachment to the session
        Session s = new Session(subscriptionsStorage, storageEngine, newChannel);
        newChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE, s);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // Get attachment
        Session attachment = (Session) key.attachment();
        List<ByteBuffer> messages = ByteUtils.readLengthMessages(channel);
        if (messages == null) {
            attachment.close();
            return;
        }
        if (messages.isEmpty()) {
            return;
        }
        for (ByteBuffer b : messages) {
            b.flip();
            attachment.onMessage(b);
        }
    }
}
