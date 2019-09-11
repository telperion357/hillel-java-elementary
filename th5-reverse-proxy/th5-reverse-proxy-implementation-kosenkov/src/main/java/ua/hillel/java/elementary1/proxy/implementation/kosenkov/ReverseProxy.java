package ua.hillel.java.elementary1.proxy.implementation.kosenkov;

import ua.hillel.java.elementary1.proxy.AbstractReverseProxy;
import ua.hillel.java.elementary1.proxy.Configuration;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ReverseProxy extends AbstractReverseProxy {

    final static int BUFFER_SIZE = 1024;

    private List<Server> servers;
    /**
     * The list of selectors, corresponding to the servers.
     */
    private Selector selector;
    /**
     * variable to control the loop of processing channels.
     */
    private boolean start = false;

    /**
     * Instantiates a new reverse proxy with predefined configuration.
     * @param configuration the configuration.
     */
    public ReverseProxy(Configuration configuration) {
        super(configuration);
        servers = initServersWithConfig();
    }

    @Override
    public void start() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        registerServersWithSelector();
        start = true;
        while (start) {
            selectAndProcessChannels();
        }
    }

    @Override
    public void stop() {
        start = false;
        try {
            selector.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Server> initServersWithConfig() {
        // TODO initialize servers with configuration builder
        return new ArrayList<>();
    }

    /**
     * Opens a ServerSocketChannel for each Server item.
     * Registers it with a Selector for APPEND.
     * Attaches Server item to the SelectionKey.
     * Adds selector to the collection of selectors of this application.
     */
    private void registerServersWithSelector() {
        for (Server server : servers) {
            try {
                ServerSocketChannel serverChannel = ServerSocketChannel.open();
                serverChannel.bind(server.getServerAddress());
                serverChannel.configureBlocking(false);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT, server);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void selectAndProcessChannels() {
        try {
            // How many available channels since last call?
            int n = selector.select();
            if (n <= 0) {
                // Nothing changed.
                return;
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
                    copyChannels(key);
                }
                // Must remove after.
                i.remove();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accepts an incoming connection to the server.
     * Chooses by random weighted selection one of the endpoints for this server.
     * Opens a connection to the endpoint.
     * Registers the incoming connection channel socket with the selector for READ and WRITE,
     * and attaches the endpoint connection channel socket to the selection key.
     * Registers the endpoint connection channel socket with the selector for READ and WRITE,
     * and attaches the incoming connection channel socket to the selection key.
     * In this way the incoming connection and the connection to the endpoint are mutually attached.
     *
     * @param key
     * @throws IOException
     */
    private static void accept(SelectionKey key) throws IOException {
        // Accept incoming connection, get incoming channel.
        SocketChannel incomingChannel = ((ServerSocketChannel) key.channel()).accept();
        incomingChannel.configureBlocking(false);
        // Get the corresponding server object from the selection key attachment.
        // Connect to one of the endpoints, corresponding to the server.
        Server server = (Server) key.attachment();
        SocketChannel endpointChannel =
                SocketChannel.open(server.selectEndpoint());
        endpointChannel.configureBlocking(false);
        // Register incoming channel and endpoint channel with the selector,
        // and mutually attach them to each other.
        incomingChannel.register(
                key.selector(),
                SelectionKey.OP_READ | SelectionKey.OP_WRITE,
                endpointChannel
        );
        endpointChannel.register(
                key.selector(),
                SelectionKey.OP_READ | SelectionKey.OP_WRITE,
                incomingChannel
        );
    }

    /**
     * Copies all available data from the selected channel to the attached channel.
     * @param key
     * @throws IOException
     */
    private static void copyChannels(SelectionKey key) throws IOException {
        SocketChannel in =  (SocketChannel) key.channel();
        SocketChannel out = (SocketChannel) key.attachment();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (in.read(buffer) > 0 || buffer.position() != 0) {
            // Reset read position to 0
            buffer.flip();
            out.write(buffer);
            buffer.compact();
        }
    }
}
