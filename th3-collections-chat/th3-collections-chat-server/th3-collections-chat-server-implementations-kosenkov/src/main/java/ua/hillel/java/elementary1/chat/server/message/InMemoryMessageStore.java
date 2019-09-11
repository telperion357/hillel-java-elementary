package ua.hillel.java.elementary1.chat.server.message;

import java.util.*;

public class InMemoryMessageStore implements MessageStore {
    private static final int CACHE_SIZE = 10;

    // Main application message store "history" map
    private NavigableMap<Long, Message> history;
    // Cache message store to support fast recent history reading
    private Map<Long, Message> cache;

    public InMemoryMessageStore() {
        history = new TreeMap<>(Comparator.<Long>naturalOrder().reversed());
        cache = new LinkedHashMap<Long, Message>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CACHE_SIZE;
            }
        };

    }

    @Override
    public void add(Message message) {
        cache.put(message.getId(), message);
        history.put(message.getId(), message);
    }

    @Override
    public Message get(long id) {
        return history.get(id);
    }

    @Override
    public Message remove(long id) {
        Message tmp = history.remove(id);
        cache.remove(id);
        return tmp;
    }

    @Override
    public List<Message> getHistory(int n, long lastId) {
        List<Message> result = new ArrayList<>();
        // Get the part of history map with id keys strictly less than lastId
        SortedMap<Long, Message> tail = history.tailMap(lastId);
        // Get the iterator over the collection of messages in descending order by id,
        // starting with the next message after lastId. The collection is in desc order,
        // because the map is sorted in descending way due to the reversed comparator.
        Iterator<Message> tailItr = tail.values().iterator();
        // Iterate through the collection and copy first n messages to the list to return
        int count = 0;
        while (count < n && tailItr.hasNext()) {
            result.add(tailItr.next());
            count++;
        }
        return result;
    }

    @Override
    public List<Message> getTop() {
        return Collections.unmodifiableList(new ArrayList<>(cache.values()));
    }
}
