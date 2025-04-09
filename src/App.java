public class App {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);

        cache.putValue("A", "Apple");
        cache.putValue("B", "Banana");
        cache.putValue("C", "Cherry");

        System.out.println("Initial Cache: " + cache);

        cache.getValue("A"); // Access A
        cache.putValue("D", "Date"); // Add D, B gets evicted

        System.out.println("After adding 'D': " + cache);
    }
}
