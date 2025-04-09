import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);

        //Create a threadpool to create 5 threads, Each thread can run a task (insert or get from the cache).
        ExecutorService executor = Executors.newFixedThreadPool(5);


        String[] keys = {"A", "B", "C", "D", "E"};
        String[] values = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};

        for (int i = 0; i < keys.length; i++) {
            final int index = i;
            executor.submit(() -> {  //Submits a task to the thread pool
                cache.putValue(keys[index], values[index]);
                

                String fetched = cache.getValue(keys[index]);
                

                // Synchronized printing of cache state
                
            });
            
        }
        //Because tasks run in different threads, the order of insertions and retrievals will be non-deterministic
        executor.shutdown();
    }
}
