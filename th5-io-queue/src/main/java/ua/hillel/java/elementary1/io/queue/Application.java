package ua.hillel.java.elementary1.io.queue;

import ua.hillel.java.elementary1.io.queue.net.QueueServer;
import ua.hillel.java.elementary1.io.queue.net.SubscriptionsStorage;
import ua.hillel.java.elementary1.io.queue.storage.StorageEngine;

import java.io.IOException;

public class Application {

    private static final String PORT_PARAM = "port";
    private static final String HOST_PARAM = "host";

    private static final int DEFAULT_PORT = 1111;
    private static final String DEFAULT_HOST = "localhost";


    public static void main(String[] args) throws IOException {
        StorageEngine engine = new StorageEngine();
        SubscriptionsStorage ss = new SubscriptionsStorage();
        QueueServer s = new QueueServer(ss, engine,
                Integer.getInteger(PORT_PARAM, DEFAULT_PORT),
                System.getProperty(HOST_PARAM, DEFAULT_HOST));
        s.start();
    }
}
