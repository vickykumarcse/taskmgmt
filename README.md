# Task Management System

## 📌 Overview
The **Task Management System** is a backend project built using **Spring Boot**, **Redis**, and **MongoDB Atlas**. It integrates **RabbitMQ** as a message broker to handle asynchronous messaging efficiently. The application is containerized using **Docker** to ensure a smooth setup and deployment process.

---

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
- **All available metrics:**  
  👉 [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- **System health status:**  
  👉 [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

### **📈 Redis Metrics**
Monitor Redis performance using Actuator metrics:

- **Number of keys:**  
  👉 [http://localhost:8080/actuator/metrics/redis.keys](http://localhost:8080/actuator/metrics/redis.keys)
- **Memory usage:**  
  👉 [http://localhost:8080/actuator/metrics/redis.memory.used](http://localhost:8080/actuator/metrics/redis.memory.used)

---

## 📝 License
This project is licensed under the **MIT License**. Feel free to use and modify it as needed.

---