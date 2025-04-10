import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    static AtomicInteger totalRequests = new AtomicInteger(0);
    static AtomicInteger cacheHits = new AtomicInteger(0);
    static AtomicInteger cacheMisses = new AtomicInteger(0);

    public static void main(String[] args) {
        int totalUniqueKeys = 100;
        int totalRequestsToSimulate = 10_000;

        // Step 1: Bigger Cache
        LRUCache<String, String> cache = new LRUCache<>(100);
        ExecutorService executor = Executors.newFixedThreadPool(20); // more threads for speed

        // Step 2: Create 100 reusable keys
        String[] keys = new String[totalUniqueKeys];
        for (int i = 0; i < totalUniqueKeys; i++) {
            keys[i] = "key" + i;
        }

        // Step 3: Start timer before requests
        long globalStart = System.nanoTime();

        // Step 4: Simulate 10,000 random requests
        for (int i = 0; i < totalRequestsToSimulate; i++) {
            final String key = keys[(int) (Math.random() * totalUniqueKeys)];

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
                } else {
                    cacheHits.incrementAndGet();
                }

                totalRequests.incrementAndGet();

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1_000_000; // (Optional: time per request)
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to complete
        }

        // Step 5: End timer and print report
        long globalEnd = System.nanoTime();
        long totalDurationMs = (globalEnd - globalStart) / 1_000_000;
        double throughput = (totalRequests.get() * 1000.0) / totalDurationMs;

        System.out.println("\n------ Final Benchmark Report ------");
        System.out.println("Total requests: " + totalRequests.get());
        System.out.println("Cache hits: " + cacheHits.get());
        System.out.println("Cache misses: " + cacheMisses.get());
        System.out.println("Backend calls: " + FakeBackend.getBackendCallCount());

        double hitRate = (cacheHits.get() * 100.0) / totalRequests.get();
        System.out.printf("Cache hit rate: %.2f%%\n", hitRate);
        System.out.printf("Backend load rate: %.2f%%\n", 100.0 - hitRate);
        System.out.printf("Total time taken: %d ms\n", totalDurationMs);
        System.out.printf("Throughput: %.2f requests/sec\n", throughput);
    }
}
