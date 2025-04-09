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
        return size() > capacity;
    }

    public V getValue(K key) {
        return super.get(key);
    }

    public void putValue(K key, V value) {
        super.put(key, value);
    }
}
