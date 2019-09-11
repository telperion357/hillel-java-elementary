package ua.hillel.java.elementary1.chat.server.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InMemoryMessageDatabase implements MessageDatabase {
    private Map<String, MessageStore> chatStores;
    private Map<String, MessageStore> directStores;

    public InMemoryMessageDatabase() {
        this.chatStores   = new HashMap<>();
        this.directStores = new HashMap<>();
    }

    @Override
    public MessageStore getChatStore(String chat) {
        return getOrCreate(chatStores, chat);
    }

    @Override
    public MessageStore getDirectStore(String u1, String u2) {
        // Sort and concatenate user names to obtain a uniform name for direct chat
        //List<String> users = Arrays.asList(u1, u2);
        //users.sort(Comparator.naturalOrder());
        String[] users = {u1, u2};
        Arrays.sort(users);
        // a, b -> "a:b"
        // ab:c
        // a:bc
        String key = String.join(":", users);
        return getOrCreate(directStores, key);
    }

    private MessageStore getOrCreate(Map<String, MessageStore> map,
                                     String key) {
        MessageStore store = map.get(key);
        if (store == null) {
            store = new InMemoryMessageStore();
        }
        map.put(key, store);
        return store;
    }
}
