public class FakeBackend {
    private static int backendCalls = 0; // to calculate how many were from backend

    public static String getFromBackend(String key) {
        backendCalls++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Value-for-" + key;
    }

    public static int getBackendCallCount() {
        return backendCalls;
    }
}

