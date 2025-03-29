### **What Happens Internally When You Run:**
```sh
./mvnw spring-boot:run
```

#### **1️⃣ `./mvnw` → Uses Maven Wrapper**  
- `mvnw` (Maven Wrapper) ensures that Maven runs even if it's **not installed globally**.  
- It **downloads** the correct version of Maven (if not already present) into `~/.m2/wrapper/`.  
- Equivalent to running `mvn` but uses the **project-specific Maven version**.

---

#### **2️⃣ `spring-boot:run` → Executes the Spring Boot Plugin**  
- `spring-boot:run` is a goal of **`spring-boot-maven-plugin`**, which does the following:  
  1. **Compiles the Project** → Runs `mvn compile` to build your source code (`src/main/java`).  
  2. **Starts an Embedded Server** → Uses Tomcat (default) or any configured server.  
  3. **Runs Your `main` Method** → Finds and executes the class with `@SpringBootApplication`.  
  4. **Live Reload (if DevTools is enabled)** → Watches for changes and restarts automatically.  

---

### **Breaking It Down**
| Step | What Happens |
|------|-------------|
| 1️⃣ `./mvnw` | Runs the Maven Wrapper (`mvnw`) instead of system-wide `mvn`. |
| 2️⃣ **Maven Loads `pom.xml`** | Reads `pom.xml` and finds the required dependencies and plugins. |
| 3️⃣ **Executes `spring-boot:run`** | Runs `spring-boot-maven-plugin`, which compiles and starts the app. |
| 4️⃣ **Spring Boot Starts** | Finds the `@SpringBootApplication` class and launches the app. |
| 5️⃣ **Embedded Server Runs** | Tomcat (or another embedded server) starts, listening on the default port (usually `8080`). |

---

### **How It Differs from `java -jar`**
| Command | What It Does |
|---------|-------------|
| `./mvnw spring-boot:run` | Runs the app **without building a JAR** (faster for development). |
| `./mvnw clean package && java -jar target/*.jar` | Builds a JAR first, then runs it (better for production). |

---

### **When to Use It?**
✔️ **For quick development and testing** (auto-restarts on changes).  
✔️ **When you don’t want to manually build a JAR**.  
✔️ **When using Maven Wrapper** instead of globally installed Maven.  

---

### **What If It’s Not Working?**
If you get `"No plugin found for prefix 'spring-boot'"`, check that `spring-boot-maven-plugin` is in `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

