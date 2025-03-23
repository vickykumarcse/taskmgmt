### **What is a Bean in Spring?**  

A **Spring Bean** is an **object that is managed by the Spring container**. Spring **creates, configures, and manages** these objects for you instead of manually instantiating them using `new`.  

---

### **📌 Key Features of a Spring Bean**
✅ **Created & Managed by Spring** – You don’t need `new ClassName()`.  
✅ **Lifecycle Management** – Beans can have initialization (`@PostConstruct`) and destruction (`@PreDestroy`) hooks.  
✅ **Dependency Injection** – Spring **automatically injects** required dependencies.  
✅ **Scope Control** – Beans can be **singleton**, **prototype**, or other scopes.  

---

### **🔹 How Does Spring Create a Bean?**
Spring registers beans in its **ApplicationContext** using **annotations** or **XML configuration**.

### **1️⃣ Using `@Component` (Auto-detection)**
```java
@Component
public class MyService {
    public void doSomething() {
        System.out.println("Bean method executed!");
    }
}
```
🔹 Spring automatically **detects** this class as a **bean** because of `@Component`.  

---

### **2️⃣ Using `@Bean` (Manual Configuration)**
```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```
🔹 This **manually registers** `MyService` as a bean in Spring.

---

### **🔹 How to Use a Bean? (`@Autowired`)**
Once a bean is created, you can **inject** it using `@Autowired`:
```java
@Service
public class MyController {
    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    public void execute() {
        myService.doSomething();
    }
}
```
🔹 Spring **automatically injects** `MyService` without needing `new MyService()`.

---

### **📌 Types of Bean Scopes**
| Scope      | Description |
|------------|------------|
| `singleton` (default) | One instance per Spring container. |
| `prototype` | A new instance is created every time. |
| `request` | One instance per HTTP request (used in web apps). |
| `session` | One instance per HTTP session. |
| `application` | One instance per web application. |

**Example: Prototype Scope**
```java
@Bean
@Scope("prototype")
public MyService myService() {
    return new MyService();
}
```
🔹 Every time you request `MyService`, a **new object** is created.

---

### **🚀 Summary**
✔ A **Bean** is an object managed by **Spring’s IoC container**.  
✔ Beans are created via **`@Component`**, **`@Service`**, **`@Bean`**, or other annotations.  
✔ Spring **injects** beans into other components using **`@Autowired`**.  
✔ Beans can have **different scopes** (singleton, prototype, request, etc.).  

### **Spring Bean Lifecycle Management 🚀**  

Spring manages the lifecycle of a bean from **creation to destruction**. You can control this lifecycle using **annotations** (`@PostConstruct`, `@PreDestroy`) or **interfaces** (`InitializingBean`, `DisposableBean`).  

---

## **📌 Lifecycle Phases of a Spring Bean**
1️⃣ **Bean Instantiation** – Spring **creates** the bean.  
2️⃣ **Dependency Injection** – Spring **injects required dependencies** (`@Autowired`).  
3️⃣ **Initialization Callbacks** – You can run custom logic after the bean is created (`@PostConstruct`).  
4️⃣ **Bean Ready for Use** – The bean is **fully initialized** and can be used.  
5️⃣ **Destruction Callbacks** – Before shutting down, Spring calls cleanup methods (`@PreDestroy`).  

---

## **🔹 Example: Managing Bean Lifecycle with Annotations**
```java
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    public MyBean() {
        System.out.println("🔹 Bean Instantiated!");
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ @PostConstruct: Bean Initialized!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("❌ @PreDestroy: Bean is about to be Destroyed!");
    }
}
```
### **🛠 What Happens?**
- **Spring creates `MyBean`** (`🔹 Bean Instantiated!`).
- **After dependencies are injected, `@PostConstruct` runs** (`✅ Bean Initialized!`).
- **Before application shutdown, `@PreDestroy` runs** (`❌ Bean is about to be Destroyed!`).

---

## **🔹 Example: Managing Lifecycle with Interfaces**
Spring also provides `InitializingBean` and `DisposableBean` interfaces.

```java
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements InitializingBean, DisposableBean {

    public MyBean() {
        System.out.println("🔹 Bean Instantiated!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("✅ afterPropertiesSet(): Bean Initialized!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("❌ destroy(): Bean is about to be Destroyed!");
    }
}
```
### **🛠 What Happens?**
- **Spring creates `MyBean`** (`🔹 Bean Instantiated!`).
- **`afterPropertiesSet()` runs after injection** (`✅ Bean Initialized!`).
- **`destroy()` runs before shutdown** (`❌ Bean is about to be Destroyed!`).

---

## **🔹 Example: Managing Lifecycle with `@Bean` Configuration**
If a bean is registered manually using `@Bean`, you can use `initMethod` and `destroyMethod`.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public MyBean myBean() {
        return new MyBean();
    }
}

class MyBean {
    public void init() {
        System.out.println("✅ init(): Bean Initialized!");
    }

    public void cleanup() {
        System.out.println("❌ cleanup(): Bean is about to be Destroyed!");
    }
}
```

---

## **🔹 Summary of Lifecycle Methods**
| Method | Annotation/Interface | When It Runs? |
|--------|----------------------|---------------|
| Constructor | `new` or Spring instantiation | When the bean is created |
| `@PostConstruct` | Annotation | After dependency injection |
| `afterPropertiesSet()` | `InitializingBean` interface | After properties are set |
| `@PreDestroy` | Annotation | Before Spring destroys the bean |
| `destroy()` | `DisposableBean` interface | Just before bean destruction |
| `destroyMethod` | `@Bean(destroyMethod="cleanup")` | Before Spring shuts down |

---

### **🚀 Conclusion**
✔ `@PostConstruct` and `@PreDestroy` are **recommended for lifecycle management**.  
✔ **Interfaces (`InitializingBean`, `DisposableBean`)** provide a more controlled approach.  
✔ `@Bean(initMethod, destroyMethod)` is useful for **manual bean registration**.  
