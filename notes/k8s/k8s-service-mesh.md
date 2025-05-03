## 🧭 Service Mesh

A **service mesh** is an infrastructure layer built into your system that manages **communication between microservices** in a secure, observable, and reliable way — **without requiring changes to the application code**.

---

## 🧭 What It Does

Think of a service mesh as a **dedicated communication layer** for your services. It handles:

✅ **Traffic routing and load balancing**
✅ **Security (e.g., mTLS)**
✅ **Retries, timeouts, circuit breakers**
✅ **Observability (tracing, metrics, logging)**
✅ **Policy enforcement (e.g., rate limits, access control)**

---

## 🔌 How It Works

The most common pattern is the **sidecar proxy model**:

* Each microservice has a **sidecar proxy** (like Envoy) injected alongside it.
* These proxies handle all **inbound and outbound traffic**.
* The **control plane** (e.g., Istio) manages configurations and coordination.

---

### 🔁 Service Mesh Request Flow:

```
[Service A] ⇄ [Sidecar A] ⇄ [Network] ⇄ [Sidecar B] ⇄ [Service B]
```

This allows observability, retries, encryption, etc., without modifying `Service A` or `Service B` code.

---

## 💡 Key Components

| Component         | Role                                 |
| ----------------- | ------------------------------------ |
| **Data Plane**    | Sidecar proxies (Envoy)              |
| **Control Plane** | Configuration, policy, and telemetry |
| **API Server/UI** | Optional tools for visualization     |

---

## 🛠 Popular Service Meshes

| Name        | Description                      |
| ----------- | -------------------------------- |
| **Istio**   | Most feature-rich, widely used   |
| **Linkerd** | Lightweight, simpler alternative |
| **Consul**  | Focuses on service discovery too |
| **Kuma**    | CNCF-backed, from Kong           |

---

## 🔒 Example Use Case

You want:

* TLS encryption between services
* Retry failed requests automatically
* View metrics on service-to-service latency

💥 Normally, you'd need to **manually code this** into every app.
😌 With a service mesh, it's handled **outside the app**, via configuration.

