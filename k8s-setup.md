Complete guide to **install Minikube**, **build and use your local Docker image**, and **deploy your `taskmgmt` app using Kubernetes YAMLs**.

---

## ✅ Step-by-Step Guide to Run Taskmgmt App on Minikube

---

### 🔧 1. **Install Minikube (and kubectl)**

#### 🐧 For Linux (Mint):
```bash
# Install kubectl
sudo apt update
sudo apt install -y curl
curl -LO "https://dl.k8s.io/release/$(curl -Ls https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo mv kubectl /usr/local/bin/

# Install Minikube
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
```

---

### 🚀 2. **Start Minikube with Docker Driver**
```bash
minikube start --driver=docker --memory=2048 --cpus=2

```
Minikube performs **several setup steps** to initialize a local Kubernetes cluster using Docker as the runtime.

---

### 🔍 Step-by-Step Breakdown:

1. **Checks Docker Availability**  
   - Ensures Docker is installed and the daemon is running.
   - Verifies the `docker` driver is usable.

2. **Creates a Minikube Profile (default)**  
   - A **profile** is like a named Kubernetes cluster. It creates one called `minikube` unless you specify otherwise.

3. **Downloads Kubernetes Components**  
   - Downloads the Kubernetes version binaries (API server, controller manager, etc.).
   - Downloads and prepares a **container-based VM image** (called a "node image") that acts as a Kubernetes node.

4. **Starts a Kubernetes Node Inside a Docker Container**  
   - Spins up a container that mimics a real VM, acting as your cluster node.
   - Allocates **2 GB RAM** and **2 CPU cores** to this container as per your flags.

5. **Bootstraps the Kubernetes Control Plane**  
   - Initializes:
     - `kube-apiserver`
     - `kube-scheduler`
     - `kube-controller-manager`
     - `etcd`
     - `kubelet`
     - `kube-proxy`
   - Sets up networking within the container.

6. **Configures `kubectl` Automatically**  
   - Updates your `~/.kube/config` to point to the new cluster.
   - You can immediately run commands like:
     ```bash
     kubectl get nodes
     ```

7. **Installs Addons (optional)**  
   - Some addons (like DNS, storage-provisioner, dashboard) are installed automatically.
---

### 🐳 3. **Build Your Local Image for Minikube**
```
docker build -t taskmgmt:latest .
```
This command tells Docker to build an image from a Dockerfile in the current directory (.), and tag it as taskmgmt:latest.
<br>
Verify the image in docker with
```
docker images
```
Load image in minikube
```
minikube image load taskmgmt:latest
```
Loads the built image into Minikube’s internal Docker registry, so Kubernetes pods running inside Minikube can use it.

✅ Now the image is available inside Minikube.

Verify the taskmgmt image with below command
```
minikube image list
```
---

### 📁 4. **Apply All YAML Files**
Apply them in order:

```bash
kubectl apply -f ./k8s/k8s-configmap.yml
kubectl apply -f ./k8s/k8s-deployment.yml
kubectl apply -f ./k8s/k8s-service.yml
```
These `kubectl apply` commands will apply three different Kubernetes resource files — each handling a different part of your Spring Boot app deployment. Here's what each one does:

---

#### 1. ✅ `kubectl apply -f ./k8s/k8s-configmap.yml`

- **Applies the ConfigMap** defined in `k8s-configmap.yml`.
- A `ConfigMap` is used to inject configuration (like environment variables or host settings) into your app **without hardcoding** them in your `Deployment`.
- Example: setting `SERVICE_HOST`, database URIs, or API keys.

🧠 This must be created **before** the deployment, since the app might depend on it during pod startup.

---

#### 2. ✅ `kubectl apply -f ./k8s/k8s-deployment.yml`

- **Creates or updates a Deployment**, which defines **how your app runs** in Kubernetes:
  - The number of replicas (pods)
  - The container image (`taskmgmt:latest`)
  - Environment variables (possibly from the ConfigMap)
  - Ports to expose (e.g., 8080)

🚀 This is what launches and manages your Spring Boot application pods.

---

#### 3. ✅ `kubectl apply -f ./k8s/k8s-service.yml`

- **Creates a Kubernetes Service**, which exposes your app internally or externally.
- Most likely of type `NodePort`, allowing access from your local machine via `localhost:<nodePort>`.
- It maps traffic to your pods based on `app: taskmgmt` label.

🌐 This is how you access your running Spring Boot app from outside the cluster (e.g., browser or Postman).

---

### 🧩 Summary Flow:

1. `ConfigMap` → loads external config like hostnames.
2. `Deployment` → starts the application using the config.
3. `Service` → exposes the app for access.

---

### 🌐 5. **Access Your App**

If you used a `NodePort` service:
```bash
minikube service taskmgmt-service
```

This opens the app in your default browser. Alternatively:
```bash
minikube service taskmgmt-service --url
```
---

### 🧪 6. **Check Logs and Status**

```bash
kubectl get all
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs deployment/taskmgmt-deployment
```
Restart the deployment
```
kubectl rollout restart deployment taskmgmt-deployment
```

### 🧪 7. **Stop the minikube**
If you just want to free memory but reuse your cluster later, use:

```bash
minikube stop
```
If you just want to free memory and delete everything the Minikube container/VM, volumes, profile, and all Kubernetes data. This is like a full reset — you’ll have to start fresh if you want to use Minikube again.

```bash
minikube delete
```