package ua.hillel.java.elementary1.io.queue;

import ua.hillel.java.elementary1.io.queue.messages.PublishRequest;
import ua.hillel.java.elementary1.io.queue.messages.SubscriptionRequest;
import ua.hillel.java.elementary1.io.queue.net.QueueClient;

public class MessageClient {
    public static void main(String[] args) throws Exception {
        QueueClient client = new QueueClient("localhost", 1111);
        client.connect();
        client.subscribe(m -> {
            System.out.println(Thread.currentThread() + ": Received message : " + m);
        });
        client.send(new SubscriptionRequest("t"));
        for (int i = 0; i < 10; i++) {
            client.send(new PublishRequest("t", "msg: " + System.currentTimeMillis()));
        }
        Thread.sleep(1000);
        client.close();
    }
}
