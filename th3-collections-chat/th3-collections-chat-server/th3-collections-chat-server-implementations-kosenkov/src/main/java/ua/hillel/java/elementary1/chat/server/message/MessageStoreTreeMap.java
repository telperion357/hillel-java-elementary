package ua.hillel.java.elementary1.chat.server.message;

import java.util.*;

// Message store implementation based on TreeMap,
// ordered by message id in ascending order.
// Adding, getting amd removing message takes logarithmic time
// of the size of the store.
// getHistory method takes linear time of the number of messages to get.
//
public class MessageStoreTreeMap implements MessageStore{

    // TreeMap backed message store
    private NavigableMap<Long, Message> store;
    // Number of messages for getTop method.
    private final int TOP = 10;

    public MessageStoreTreeMap() {
        store = new TreeMap<>();
    }

    @Override
    public void add(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message to add should not be null");
        }
        store.put(message.getId(), message);
    }

    @Override
    public Message get(long id) {
        return store.get(id);
    }

    @Override
    public Message remove(long id) {
        return store.remove(id);
    }

    @Override
    public List<Message> getHistory(int n, long lastId) {
        List<Message> list = new ArrayList<>();
        // Find the message with equal or lower id than lastId
        Map.Entry<Long, Message> entry = store.floorEntry(lastId);
        if (entry == null) {
            return list;
        }
        long itrId = entry.getValue().getId();
        list.add(entry.getValue());
        for (int i = 0; i < n; i++) {
            // Find the message with strictly lower id than previous id
            entry = store.lowerEntry(itrId);
            if (entry == null) {
                return list;
            }
            itrId = entry.getValue().getId();
            list.add(entry.getValue());
        }
        return list;
    }

    // Returns the list of TOP number of last messages in reverse order.
    @Override
    public List<Message> getTop() {
        List<Message> result = new ArrayList<>();
        // Get the map with the reversed iteration order of elements
        // and the iterable collection of its elements.
        Collection<Message> messages = store.descendingMap().values();
        // Iterate through the collection and copy
        // TOP number of messages into a new list.
        int counter = 0;
        for (Message msg : messages) {
            result.add(msg);
            if (counter++ >= TOP ) {
                break;
            }
        }
        return result;
    }
}
