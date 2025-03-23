### **Lifecycle of Spring Components (Bean, Configuration, Service, Controller, Repository) on Server Startup**  

When a Spring Boot application starts, it follows a structured sequence to initialize and manage the lifecycle of its components. Below is a breakdown of what happens:

---

## **1. Application Startup Process**
### **Step 1: Spring Context Initialization**
- The **Spring container (ApplicationContext)** is created.
- The `@SpringBootApplication` annotation triggers **component scanning** and **autoconfiguration**.

---

## **2. Component Lifecycle in Detail**  
### **A. `@Configuration` Class Processing**
- Spring scans for `@Configuration` classes.
- Methods annotated with `@Bean` are executed, and the returned objects are registered in the **Application Context**.
- Example:
    ```java
    @Configuration
    public class AppConfig {
        @Bean
        public MyBean myBean() {
            return new MyBean();
        }
    }
    ```
- **Lifecycle:**
  1. Spring creates an instance of `AppConfig`.
  2. It calls `myBean()` and registers the returned `MyBean` instance as a singleton bean.

---

### **B. `@Bean` Lifecycle**
- Any method annotated with `@Bean` follows this lifecycle:
  1. **Object Creation**: Spring instantiates the bean.
  2. **Dependency Injection**: Injects dependencies into the bean.
  3. **Post-Initialization (`@PostConstruct`)**: Executes any methods annotated with `@PostConstruct`.
  4. **Ready for Use**: The bean is now available for injection.
  5. **Pre-Destroy (`@PreDestroy`)**: Executes cleanup tasks before shutdown.

---

### **C. `@Component` and Stereotype Annotations (`@Service`, `@Repository`, `@Controller`)**
Spring scans for these annotations and registers them as beans.

#### **1. `@Component` (Generic Bean)**
- Any class marked with `@Component` is detected and managed by Spring.

#### **2. `@Service` (Business Logic)**
- Spring registers the class as a service component.
- It is typically used to write business logic.
- Example:
    ```java
    @Service
    public class UserService {
        public String getUser() {
            return "John Doe";
        }
    }
    ```
- **Lifecycle:**
  1. Spring instantiates `UserService`.
  2. Injects dependencies (if any).
  3. The service is now ready to be used.

#### **3. `@Repository` (Data Layer)**
- Used to interact with the database.
- Spring provides additional functionalities like **exception translation** for persistence operations.
- Example:
    ```java
    @Repository
    public class UserRepository {
        public void save(User user) {
            // save user to DB
        }
    }
    ```
- **Lifecycle:** Same as `@Service`.

#### **4. `@Controller` (Web Layer)**
- Handles HTTP requests.
- Works with `@RequestMapping` and `@GetMapping`, `@PostMapping`, etc.
- Example:
    ```java
    @RestController
    public class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/user")
        public String getUser() {
            return userService.getUser();
        }
    }
    ```
- **Lifecycle:**  
  1. Spring creates an instance of `UserController`.  
  2. Injects dependencies like `UserService`.  
  3. The controller is mapped to routes and is ready to handle requests.

---

### **3. Post-Initialization & Running**
- Spring **fully initializes the context**.
- It starts any necessary components (e.g., embedded Tomcat for web apps).
- Application starts handling requests.

---

### **4. Shutdown Process**
When the application stops:
1. **Spring calls `@PreDestroy` methods** on beans.
2. **The application context is closed**.
3. **Spring gracefully shuts down** and releases resources.

---

## **Summary of Lifecycle Stages**
| Stage | What Happens? |
|--------|-------------|
| **Application Startup** | Spring initializes the context and scans components. |
| **Bean Creation** | Spring instantiates `@Configuration`, `@Bean`, `@Component`, `@Service`, etc. |
| **Dependency Injection** | Injects dependencies into components. |
| **Post-Processing** | Executes `@PostConstruct` methods. |
| **Ready to Serve Requests** | Controllers, Services, Repositories are now available for use. |
| **Shutdown** | Executes `@PreDestroy` and closes the context. |
