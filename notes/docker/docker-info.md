### 🐳 What is Docker?

**Docker** is a **containerization platform** that lets you package applications and their dependencies into **lightweight, portable containers**. These containers can run consistently across different environments — from development to production — regardless of the underlying OS or system configuration.

---

### 📦 Key Concepts in Docker:

| Term | Description |
|------|-------------|
| **Image** | A read-only template with the app and environment settings. |
| **Container** | A running instance of an image. |
| **Dockerfile** | A script with instructions to build an image. |
| **Docker Hub** | A public registry where you can store and pull images. |
| **Volume** | Persistent storage for containers. |
| **Network** | Allows communication between containers. |

---

### 🧰 Most Important Docker Commands

#### 🔨 **Image Commands**
```bash
docker build -t <name>:<tag> .        # Build image from Dockerfile
docker images                         # List all images
docker rmi <image-id>                 # Remove an image
docker pull <image>                   # Download image from Docker Hub
docker push <name>:<tag>              # Push image to Docker Hub
docker image prune                    # Remove dangling images
```

#### 🚀 **Container Commands**
```bash
docker run -d -p 8080:80 <image>      # Run a container in background
docker run -it <image> bash           # Start a container with interactive shell
docker ps                             # List running containers
docker ps -a                          # List all containers (running + stopped)
docker stop <container-id>            # Stop a running container
docker start <container-id>           # Start a stopped container
docker restart <container-id>         # Restart a container
docker rm <container-id>              # Remove a container
```

#### 📂 **Volumes and Storage**
```bash
docker volume create <volume-name>    # Create a volume
docker volume ls                      # List all volumes
docker run -v <volume-name>:/data ... # Mount volume to container
```

#### 🔍 **Inspecting and Debugging**
```bash
docker logs <container-id>            # View container logs
docker exec -it <container-id> bash   # Enter a running container
docker inspect <container-id>         # Get detailed container info
```

#### 🌐 **Networking**
```bash
docker network ls                     # List all networks
docker network create <name>          # Create a custom network
docker network inspect <name>         # View network details
```

---

### 🔄 Docker Compose (for multi-container apps)
```bash
docker compose up -d                  # Start all services defined in docker-compose.yml
docker compose down                   # Stop and remove all services/containers
```

---

### 🧠 Example Workflow

```bash
docker build -t myapp:latest .
docker run -d -p 8080:8080 myapp:latest
docker ps
docker logs <container-id>
```