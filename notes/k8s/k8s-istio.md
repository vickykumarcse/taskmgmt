### 🔧 Istio

**Istio** is an **open-source service mesh** that helps you manage, secure, and observe microservices communication in a distributed application. It sits **between services** in your Kubernetes cluster (or other environments) and **transparently handles networking concerns** like traffic routing, service discovery, load balancing, security, and observability.

---

### 🔧 How Istio Works – Core Concepts

1. **Sidecar Proxy (Envoy)**

   * Istio uses **Envoy** proxies, which are injected as **sidecars** into each service pod.
   * These proxies intercept all inbound and outbound traffic, enabling Istio to control and observe service-to-service communication **without changing your app code**.

2. **Control Plane (Istiod)**

   * Istio’s brain is `istiod`, the control plane, which:

     * Manages configuration (like routing rules, policies).
     * Distributes them to the Envoy proxies.
     * Handles certificate management for mTLS.

3. **Data Plane**

   * The actual traffic between services flows through the Envoy proxies. This is called the **data plane**.
   * Proxies enforce the policies and perform the telemetry and routing defined by the control plane.

---

### 🚀 Key Features of Istio

| Feature                | Description                                                                                                  |
| ---------------------- | ------------------------------------------------------------------------------------------------------------ |
| **Traffic Management** | Fine-grained control over traffic: routing, retries, timeouts, A/B testing, canary deployments, etc.         |
| **Security**           | mTLS (mutual TLS), policy enforcement, authentication, and authorization.                                    |
| **Observability**      | Tracing, metrics, and logs for all service communications. Integrates with Prometheus, Grafana, Jaeger, etc. |
| **Resilience**         | Circuit breakers, retries, failover, and fault injection.                                                    |

---

### 📦 Example Flow

Let’s say **Service A calls Service B**:

1. Request from Service A goes to its **sidecar proxy (Envoy)**.
2. Envoy applies **Istio rules** (e.g., mTLS encryption, routing rules).
3. Traffic is sent to Service B’s Envoy proxy.
4. Service B receives the traffic through its proxy.
5. Metrics, logs, and tracing are collected along the way.

---

### 📌 Typical Use Case in Kubernetes

1. Deploy Istio in your cluster.
2. Label the namespace: `istio-injection=enabled` → Istio automatically injects sidecars.
3. Define `VirtualService`, `DestinationRule`, and other Istio resources to manage traffic and policies.

---

Ah, got it — you're asking **what Istio sets up automatically** in your Kubernetes cluster when you install it (e.g., with `istioctl install`) — including components like **IngressGateway**, **LoadBalancer**, and other core components that power the **service mesh infrastructure**.

Here’s a breakdown of the **main Kubernetes resources automatically created** by Istio, including their **types (`kind`) and names**, along with short YAML-style examples to help understand their structure.

---

## ✅ Istio Components Automatically Created on Installation

### 🔹 1. `Namespace`: **istio-system**

All core Istio components are deployed here.

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: istio-system
```

---

### 🔹 2. `Deployment`: **istiod**

Istio control plane (handles configuration, mTLS, telemetry, etc.)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: istiod
  namespace: istio-system
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: istiod
```

---

### 🔹 3. `Service`: **istiod**

Exposes control plane to other mesh components.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: istiod
  namespace: istio-system
spec:
  ports:
    - name: grpc-xds
      port: 15010
```

---

### 🔹 4. `Deployment`: **istio-ingressgateway**

The default **Ingress Gateway** to receive external traffic into the mesh.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: istio-ingressgateway
  namespace: istio-system
spec:
  replicas: 1
  selector:
    matchLabels:
      istio: ingressgateway
```

---

### 🔹 5. `Service`: **istio-ingressgateway**

Often of type `LoadBalancer` (for cloud), or `NodePort` (on Minikube).

```yaml
apiVersion: v1
kind: Service
metadata:
  name: istio-ingressgateway
  namespace: istio-system
spec:
  type: LoadBalancer  # or NodePort on Minikube
  ports:
    - name: http
      port: 80
    - name: https
      port: 443
```

---

### 🔹 6. `ServiceAccount`: **istiod-service-account**, **istio-ingressgateway-service-account**

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: istiod-service-account
  namespace: istio-system
```

---

### 🔹 7. `MutatingWebhookConfiguration`: **istio-sidecar-injector**

Automatically injects Envoy sidecars into your pods when the namespace is labeled.

```yaml
apiVersion: admissionregistration.k8s.io/v1
kind: MutatingWebhookConfiguration
metadata:
  name: istio-sidecar-injector
```

---

### 🔹 8. `ValidatingWebhookConfiguration`: **istiod-istio-system**

Validates Istio CRDs before applying them.

```yaml
apiVersion: admissionregistration.k8s.io/v1
kind: ValidatingWebhookConfiguration
metadata:
  name: istiod-istio-system
```

---

### 🔹 9. `ConfigMap`: **istio-ca-root-cert**, **istio**

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: istio-ca-root-cert
  namespace: istio-system
```

---

### 🔹 10. `Pod`s

Created from the Deployments like `istiod`, `istio-ingressgateway`, etc.

---

### 🔹 11. `ClusterRole` / `ClusterRoleBinding`

Istio creates many of these to allow `istiod` and gateways to watch and manage resources cluster-wide.

---

## 🧪 Optional: `istio-egressgateway`

Another gateway component used to **route outbound traffic** (not installed by default in minimal profiles).

```yaml
kind: Deployment
metadata:
  name: istio-egressgateway
```

---

## 🔍 How to See Them

```bash
# See all Istio components
kubectl get all -n istio-system

# Check Gateway service type (LoadBalancer or NodePort)
kubectl get svc istio-ingressgateway -n istio-system

# List webhooks
kubectl get mutatingwebhookconfigurations
kubectl get validatingwebhookconfigurations
```
---
