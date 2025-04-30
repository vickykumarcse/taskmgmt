`kubectl` is the **command-line tool for interacting with Kubernetes clusters**. It's the primary way developers and operators communicate with Kubernetes to deploy, inspect, and manage applications and cluster resources.

---

### 🔧 What You Can Do with `kubectl`:

- **Deploy applications** using YAML files or direct commands
- **View and manage resources** like pods, deployments, services, config maps, secrets, etc.
- **Inspect cluster state** and monitor health/logs
- **Exec into containers** running in pods for debugging
- **Scale or restart deployments**
- **Apply configuration changes** to live environments

---

### 🧠 How It Works:

- `kubectl` uses a configuration file (usually `~/.kube/config`) to **connect to your cluster’s API server**.
- It sends **REST API calls** to the Kubernetes control plane.
- You can specify:
  - **Context** (which cluster to talk to)
  - **Namespace**
  - **Resource type** (e.g., pod, service)
  - **Actions** (e.g., get, apply, delete)

---

### 📌 Example:

```bash
kubectl get pods
```
> Lists all pods in the current namespace.

```bash
kubectl apply -f deployment.yaml
```
> Deploys the configuration defined in the `deployment.yaml` file.

---

## **Commands**

### 📦 **Workloads: Pods, Deployments, etc.**

| Command | Description |
|--------|-------------|
| `kubectl get pods` | List all pods in the current namespace. |
| `kubectl get deployments` | List all deployments. |
| `kubectl describe pod <pod-name>` | Detailed info (events, status, etc.) about a pod. |
| `kubectl logs <pod-name>` | View logs of a pod’s main container. |
| `kubectl exec -it <pod-name> -- bash` | Open a terminal session into a pod. |
| `kubectl delete pod <pod-name>` | Delete a specific pod (it will restart if managed by a deployment). |

---

### 📁 **Apply & Manage YAML Configs**

| Command | Description |
|--------|-------------|
| `kubectl apply -f <file or dir>` | Apply a YAML file or all in a directory. |
| `kubectl delete -f <file or dir>` | Delete all resources defined in a file or dir. |
| `kubectl edit deployment <name>` | Open and edit a live deployment YAML in your editor. |

---

### 📂 **Resource Management**

| Command | Description |
|--------|-------------|
| `kubectl get all` | Show all resources (pods, services, etc.) in namespace. |
| `kubectl get svc` | List all services. |
| `kubectl get configmap` | List all ConfigMaps. |
| `kubectl get secret` | List secrets. |
| `kubectl get namespaces` | List all namespaces. |

---

### 🛠️ **Cluster Info & Debugging**

| Command | Description |
|--------|-------------|
| `kubectl cluster-info` | Show cluster endpoints. |
| `kubectl config view` | View full kubeconfig settings. |
| `kubectl version --short` | Show client/server version info. |
| `kubectl top pod` | Show CPU and memory usage of pods (needs metrics server). |

---

### 📌 **Namespace Handling**

| Command | Description |
|--------|-------------|
| `kubectl get pods -n <namespace>` | List pods in a specific namespace. |
| `kubectl config set-context --current --namespace=<ns>` | Set default namespace for your `kubectl` session. |

---

### 🔄 **Rollouts and Scaling**

| Command | Description |
|--------|-------------|
| `kubectl rollout status deployment/<name>` | Check status of a rollout. |
| `kubectl rollout restart deployment/<name>` | Restart all pods in a deployment. |
| `kubectl scale deployment <name> --replicas=3` | Manually scale a deployment. |

---