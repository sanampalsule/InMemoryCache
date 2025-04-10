# High Performance In-Memory LRU Cache (Java + Multi-threading)

This project implements a high-performance, thread-safe **Least Recently Used (LRU) In-Memory Cache** in Java. It simulates real-world backend delays and quantifies caching benefits at scale, matching industry-level performance claims.

---

## 🚀 Key Features

- ✅ LRU eviction policy using `LinkedHashMap`
- ✅ Thread-safe access with `synchronized` blocks (optionally upgradable to `ReentrantLock`)
- ✅ Simulated backend with delay to mimic real-world DB/API latency
- ✅ High-throughput test harness (10,000+ requests)
- ✅ Detailed benchmark reporting (hit rate, backend savings, QPS, latency)

---

## 🛠 Technologies Used

- Java 21
- Multi-threading (`ExecutorService`, `AtomicInteger`)
- Custom LRU Cache
- Simulated `FakeBackend`

---

## 📦 Project Structure

```
├── src/
│   ├── App.java              # Main benchmarking application
│   ├── LRUCache.java         # LRU cache implementation
│   └── FakeBackend.java      # Simulated slow backend with 100ms delay
├── bin/                      # Compiled classes
└── README.md
```

---

## 🧪 Benchmark Setup (Module 5)

- **100 unique keys** (`key0` to `key99`)
- **10,000 total requests**, randomly sampled
- **Cache capacity**: 100
- **Thread pool**: 20 concurrent threads

---

## 📊 Final Benchmark Report

```
------ Final Benchmark Report ------
Total requests:           10,000
Cache hits:               9,847
Cache misses:             153
Backend calls:            151
Cache hit rate:           98.47%
Backend load rate:        1.53%
Total time taken:         834 ms
Throughput:               11,990.41 requests/sec
```

---

## 📈 Quantified Ready Results

-  **98.47% cache hit rate** → proves efficient reuse of in-memory data
-  **Backend load reduced by 98%** → only ~1.5% requests hit backend
-  **11,990 QPS throughput** → exceeds 10K/sec target
-  **<1 second latency** for 10,000 requests

---

## 💡 How It Works

- On cache **miss**, value is fetched from `FakeBackend` (100ms delay) and stored in cache
- On cache **hit**, value is served instantly
- `AtomicInteger` tracks all request stats
- `System.nanoTime()` measures per-request and total timing

---





