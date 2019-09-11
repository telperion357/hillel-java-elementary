package ua.hillel.java.elementary1.chat.server.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Message store implementation based on ArrayList, ordered by message id in ascending order.
// Our model of chat suggests that under normal operation
// messages arrive to the store ordered by the id in ascending order.
// If we keep the ArrayList of messages in the same order,
// it will take no or little cost to add the new message to the store.
// It also will take linear time by n to get the history of n consecutive messages,
// starting at some arbitrary position
//
// As a downside, it will take a linear time of the store size to add or delete a message
// at some arbitrary position in the store.
//
public class MessageStoreArrayList implements MessageStore {

    // Array-list backed message store
    private ArrayList<Message> store;
    // Number of messages for getTop method.
    private final int TOP = 10;

    public MessageStoreArrayList() {
        store = new ArrayList<>();
    }

    // Comparator for binary search by message id
    private static class ComparatorMessageId implements Comparator<Message> {
        @Override
        public int compare(Message message1, Message message2) {
            return Long.compare(message1.getId(), message2.getId());
        }
    }

    // Adds the given message to the store,
    // keeping the order of the list
    @Override
    public void add(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message to add should not be null");
        }

        if (store.isEmpty()) {
            store.add(message);
            return;
        }

        // If the new message id is greater than the id of any stored message,
        // add the new message to the end of the list
        // (Don't forget, the list is ordered)
        int last = store.size() - 1;
        if(message.getId() > store.get(last).getId()) {
            store.add(message);
            return;
        }

        // Found the place to add the new message to keep order of the list
        int position = Collections.binarySearch(store, message, new ComparatorMessageId());
        // If there is no message with such id, add it
        if(position < 0) {
            int insertionPosition = Math.abs(position + 1);
            store.add(insertionPosition, message);
        } else { // If there is a message with such id, rewrite it.
            store.set(position, message);
        }
    }

    // Returns the message with the given id,
    // or null if there is no message with the given id in the store
    @Override
    public Message get(long id) {
        // Create the message with the given id for the search purpose.
        Message messageId = new Message(id, null, null, 0);
        // Found the message with the given id in the list.
        int position = Collections.binarySearch(store, messageId, new ComparatorMessageId());
        // If there is a message with such id, return it
        if(position >= 0) {
            return store.get(position);
        }
        // If there is no message with such id, return null.
        return null;
    }

    // Returns and removes from the store the message with the given id,
    // or null if there is no message with the given id in the store
    @Override
    public Message remove(long id) {
        // Create the message with the given id for the search purpose.
        Message messageId = new Message(id, null, null, 0);
        // Found the message with the given id in the list.
        int position = Collections.binarySearch(store, messageId, new ComparatorMessageId());
        // If there is a message with such id, remove and return it
        if(position >= 0) {
            Message tmp = store.get(position);
            store.remove(position);
            return tmp;
        }
        // If there is no message with such id, return null.
        return null;
    }

    // Returns the list of n consecutive messages (if any are present)
    // from specified id back in the direction of descending id.
    // Empty list, if no messages are present.
    @Override
    public List<Message> getHistory(int n, long lastId) {
        // Create the message with the given id for the search purpose.
        Message messageId = new Message(lastId, null, null, 0);
        // Found the starting position for iteration,
        // corresponding to the index of the message with the given id
        int startPosition = Collections.binarySearch(store, messageId, new ComparatorMessageId());
        if(startPosition < 0) {
            startPosition = Math.abs(startPosition + 1);
        }

        // Iterate the store back from startPosition to (startPosition - n)
        // Save elements to the new list and return it
        List<Message> list = new ArrayList<>();
        int itr = startPosition;
        while (itr >= 0 && itr > startPosition - n) {
            list.add(store.get(itr--));
        }
        return list;
    }

    @Override
    public List<Message> getTop() {
        // Iterate the store back from the end and get last TOP messages.
        // Save messages to the new list and return it.
        List<Message> list = new ArrayList<>();
        int startPosition = store.size() - 1;
        int itr = startPosition;
        while (itr >= 0 && itr > startPosition - TOP) {
            list.add(store.get(itr--));
        }
        return list;
    }
}
