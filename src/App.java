import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    static AtomicInteger totalRequests = new AtomicInteger(0);
    static AtomicInteger cacheHits = new AtomicInteger(0);
    static AtomicInteger cacheMisses = new AtomicInteger(0);

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        String[] keys = {"A", "B", "C", "D", "A", "E", "C"};

        for (String key : keys) {
            executor.submit(() -> {
                long startTime = System.nanoTime();

                String value;

                synchronized (cache) {
                    value = cache.getValue(key);
                }

                if (value == null) {
                    cacheMisses.incrementAndGet();
                    value = FakeBackend.getFromBackend(key);
                    synchronized (cache) {
                        cache.putValue(key, value);
                    }
                    System.out.println("Cache MISS → Backend: " + key + " = " + value);
                } else {
                    cacheHits.incrementAndGet();
                    System.out.println("Cache HIT → Memory: " + key + " = " + value);
                }

                totalRequests.incrementAndGet();

                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime) / 1_000_000;
                System.out.println("Request: " + key + " | Time: " + durationMs + "ms");
            });
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }

        System.out.println("\n------ Benchmark Report ------");
        System.out.println("Total requests: " + totalRequests.get());
        System.out.println("Cache hits: " + cacheHits.get());
        System.out.println("Cache misses: " + cacheMisses.get());
        System.out.println("Backend calls: " + FakeBackend.getBackendCallCount());

        double hitRate = (cacheHits.get() * 100.0) / totalRequests.get();
        System.out.printf("Cache hit rate: %.2f%%\n", hitRate);
        System.out.printf("Backend load reduction: %.2f%%\n", 100.0 - hitRate);
    }
}
