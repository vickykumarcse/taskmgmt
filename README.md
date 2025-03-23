# Task Management System

## Overview
Task Management System is a project built using Spring Boot with Redis and MongoDB Atlas. It integrates RabbitMQ as a message broker to handle asynchronous messaging efficiently.

## Prerequisites
Ensure you have the following installed before running the application:
- [Docker](https://www.docker.com/)
- [RabbitMQ](https://www.rabbitmq.com/)
- [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
- [Redis](https://redis.io/)

## Starting RabbitMQ
Run the following command to start RabbitMQ using Docker:

```sh
sudo docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management
```

### RabbitMQ Access
- **Message Broker (AMQP):** [http://localhost:5672](http://localhost:5672)
- **Web Management UI:** [http://localhost:15672](http://localhost:15672)
  
#### Default Credentials:
- **Username:** `guest`
- **Password:** `guest`

## Running the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/vickykumarcse/taskmgmt.git
   cd taskmgmt
   ```
2. Configure your environment settings for MongoDB Atlas and Redis.
3. Build and run the application using:
   ```sh
   mvn spring-boot:run
   ```

## License
This project is licensed under the MIT License.