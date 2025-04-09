import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }
    // original constructor : LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean shouldRemove = size() > capacity;

        if (size() == capacity && !shouldRemove) {
            System.out.println("Cache is full: " + this);
            System.out.println("----------------------------");
        }

        if (shouldRemove) {
            System.out.println("Evicted: " + eldest.getKey() + "=" + eldest.getValue());

            // Create a copy of the cache minus the eldest entry
            LinkedHashMap<K, V> preview = new LinkedHashMap<>(this);
            preview.remove(eldest.getKey());

            System.out.println("New Cache (after eviction): " + preview);
            System.out.println("----------------------------");
        }

        return shouldRemove;
    }


    public synchronized V getValue(K key) {
        return super.get(key);
    }

    public synchronized void putValue(K key, V value) {
        super.put(key, value);
    }
}
