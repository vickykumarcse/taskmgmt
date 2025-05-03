## 🧭 Ingress Controller

An **Ingress Controller** is a specialized **Kubernetes resource** that acts as a **gateway** for managing external access to services within your cluster, typically via **HTTP and HTTPS**.

---

## 🔍 What It Does

While an **Ingress** is just a set of rules (YAML) that define how traffic should be routed, the **Ingress Controller** is the actual software that **reads those rules and executes them**.

---

## 🧭 How It Works

1. You define an `Ingress` resource with rules (e.g., route `/api` to `service-a`, `/admin` to `service-b`).
2. The Ingress Controller (like **NGINX**, **Traefik**, or **Istio**) watches for those resources.
3. It configures a reverse proxy (internally) to:

   * Terminate SSL/TLS (HTTPS)
   * Route traffic to the right pod/service
   * Optionally do rate limiting, auth, logging, etc.

---

## 🔁 Flow Example (with NGINX)

```
Browser (curl/taskmgmt.local) 
    ↓
[Ingress Controller Pod (NGINX)]
    ↓
[taskmgmt-service (ClusterIP)]
    ↓
[Spring Boot Pod]
```

---

## ✅ Common Ingress Controllers

| Ingress Controller  | Notes                                  |
| ------------------- | -------------------------------------- |
| **NGINX**           | Most widely used, stable, configurable |
| **Traefik**         | Easy for dynamic environments, UI      |
| **Istio**           | Advanced, service mesh                 |
| **HAProxy**         | High-performance option                |
| **AWS/GCP Ingress** | Cloud-native integrations              |

---

## 🧠 Why Use It?

* One entry point for all your services
* Centralized **HTTPS termination**
* Fine-grained **routing rules**
* Works well with DNS and TLS automation