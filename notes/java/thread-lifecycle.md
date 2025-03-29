In Java, a thread can be in one of **six states** during its lifecycle. These states are defined in the `java.lang.Thread.State` enum.

---

## **🔹 Thread States in Java**
| State | Description |
|-------|------------|
| **NEW** | The thread is created but not yet started. |
| **RUNNABLE** | The thread is ready to run but waiting for CPU time. |
| **BLOCKED** | The thread is waiting to acquire a lock (synchronized resource). |
| **WAITING** | The thread is waiting indefinitely for another thread’s signal. |
| **TIMED_WAITING** | The thread is waiting for a specified time. |
| **TERMINATED** | The thread has completed execution or was stopped. |

---

## **🔹 Detailed Explanation with Examples**
### **1️⃣ NEW**
- The thread is created but not started yet (`start()` not called).
```java
Thread t = new Thread(() -> System.out.println("Running"));
System.out.println(t.getState()); // Output: NEW
```

---

### **2️⃣ RUNNABLE**
- The thread is ready to run but waiting for CPU time.
```java
Thread t = new Thread(() -> {
    while (true) {} // Infinite loop
});
t.start();
System.out.println(t.getState()); // Output: RUNNABLE (if CPU assigned)
```

---

### **3️⃣ BLOCKED**
- The thread is waiting for a **lock** (synchronized resource).
```java
class SharedResource {
    synchronized void access() {
        while (true) {} // Infinite lock
    }
}
SharedResource resource = new SharedResource();
Thread t1 = new Thread(resource::access);
Thread t2 = new Thread(resource::access);
t1.start();
t2.start();
Thread.sleep(100);
System.out.println(t2.getState()); // Output: BLOCKED
```

---

### **4️⃣ WAITING**
- The thread is waiting indefinitely (`wait()`, `join()` without timeout).
```java
Thread t1 = new Thread(() -> {
    synchronized (Thread.currentThread()) {
        try {
            Thread.currentThread().wait(); // Indefinite wait
        } catch (InterruptedException e) {}
    }
});
t1.start();
Thread.sleep(100);
System.out.println(t1.getState()); // Output: WAITING
```

---

### **5️⃣ TIMED_WAITING**
- The thread is waiting for a **specific time** (`sleep()`, `wait(timeout)`, `join(timeout)`).
```java
Thread t1 = new Thread(() -> {
    try {
        Thread.sleep(5000); // Sleep for 5 sec
    } catch (InterruptedException e) {}
});
t1.start();
Thread.sleep(100);
System.out.println(t1.getState()); // Output: TIMED_WAITING
```

---

### **6️⃣ TERMINATED**
- The thread has finished execution or was stopped.
```java
Thread t = new Thread(() -> System.out.println("Done"));
t.start();
Thread.sleep(100);
System.out.println(t.getState()); // Output: TERMINATED
```

---

## **🔹 How to Get Thread State in Java**
You can check the state of a thread using:
```java
System.out.println(thread.getState());
```

---

## **🔹 Summary**
| State | Cause |
|-------|-------|
| **NEW** | Thread created but `start()` not called. |
| **RUNNABLE** | Ready to run, waiting for CPU. |
| **BLOCKED** | Waiting for a synchronized lock. |
| **WAITING** | Waiting indefinitely for another thread’s signal. |
| **TIMED_WAITING** | Waiting for a specified time (e.g., `sleep`, `wait(1000)`). |
| **TERMINATED** | Thread execution completed or stopped. |