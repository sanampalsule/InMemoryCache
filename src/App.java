import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        String[] keys = {"A", "B", "C", "D", "A", "E", "C"};

        for (String key : keys) {
            executor.submit(() -> {
                String value;

                synchronized (cache) {
                    value = cache.getValue(key);
                }

                if (value == null) {
                    value = FakeBackend.getFromBackend(key);

                    synchronized (cache) {
                        cache.putValue(key, value);
                    }

                    System.out.println("Cache MISS → Backend: " + key + " = " + value);
                } else {
                    System.out.println("Cache HIT → Memory: " + key + " = " + value);
                }
            });
        }

        executor.shutdown();

        // Wait for all threads to finish before printing total backend calls
        while (!executor.isTerminated()) {
            // Spin wait
        }

        System.out.println("Total backend calls: " + FakeBackend.getBackendCallCount());
    }
}
