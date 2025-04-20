# Task Management System

## 📌 Overview

**TaskMgmt** is a Spring Boot–based task management system designed for scalability, performance, and real-time analytics. It integrates core microservice patterns and observability tools to streamline task processing, queuing, caching, and monitoring.

The system uses:

- **MongoDB** as the primary NoSQL database for storing task data.
- **Redis** for high-speed caching of frequently accessed task information.
- **RabbitMQ** for asynchronous message-driven task processing (e.g., email notifications).
- **Kafka** for real-time task event streaming and analytics.
- **Prometheus** to collect application and infrastructure metrics.
- **Grafana** to visualize those metrics in a clean, customizable dashboard.
- **Kafka UI** for managing and monitoring Kafka topics.
- **Spring Boot Actuator** and **Micrometer** for exposing metrics and integrating with observability tools.

All components are containerized using Docker Compose to ensure easy local development and deployment.

## ✅ Prerequisites
Ensure you have the following installed **before running the application**:
- [Docker](https://www.docker.com/) (for running dependencies)
- [Java 17+](https://adoptium.net/) (for running the Spring Boot application)

### **Verify Docker Installation**
Check if Docker is installed by running:
```sh
docker --version
```
If installed, it will display the version number.

---

## 🚀 Getting Started

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/vickykumarcse/taskmgmt.git
cd taskmgmt
```

### **2️⃣ Start Dependencies using Docker**
Run the following command to start **MongoDB, Redis, and RabbitMQ**:
```sh
docker compose up -d
```
To stop and remove the running containers:
```sh
docker compose down
```

### **3️⃣ Build and Run the Application**
Compile and start the Spring Boot application:
```sh
./mvnw spring-boot:run
```

---

## 📨 RabbitMQ Management
RabbitMQ provides a web-based UI to monitor message queues.

- **AMQP Broker:** [http://localhost:5672](http://localhost:5672)
- **Web Management UI:** [http://localhost:15672](http://localhost:15672)

#### **Default Login Credentials:**
- **Username:** `guest`
- **Password:** `guest`

---

## Kafka Management
Kafka UI Portal
- **Web Management UI:** [http://localhost:8081](http://localhost:8081)


## 📊 Monitoring & Metrics
### **🔍 Actuator Endpoints**
Spring Boot Actuator provides health and monitoring endpoints.

- **Check live threads:**  
  👉 [http://localhost:8080/actuator/metrics/jvm.threads.live](http://localhost:8080/actuator/metrics/jvm.threads.live)
- **Thread dump:**  
  👉 [http://localhost:8080/actuator/threaddump](http://localhost:8080/actuator/threaddump)
- **Prometheus:**  
  👉 [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
- **All available metrics:**  
  👉 [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- **System health status:**  
  👉 [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

### 🔗 Monitoring URLs

- **Prometheus**: [http://localhost:9090](http://localhost:9090)
- **Grafana**: [http://localhost:3000](http://localhost:3000)  
  &nbsp;&nbsp;&nbsp;&nbsp;👤 **Username**: `admin`  
  &nbsp;&nbsp;&nbsp;&nbsp;🔒 **Password**: `admin`

---

## 📊 Connect Grafana to Prometheus

1. Open the Grafana UI: [http://localhost:3000](http://localhost:3000)
2. Go to **⚙️ → Data Sources**
3. Click **"Add data source"**
4. Select **Prometheus**
5. In the URL field, enter:

   ```
   http://prometheus:9090
   ```

6. Click **"Save & Test"** to verify the connection

---

## 📝 License
This project is licensed under the **MIT License**. Feel free to use and modify it as needed.