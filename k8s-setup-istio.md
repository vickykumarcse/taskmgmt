## 🚀 Step-by-Step Setup: External Load Balancing via Istio in Minikube


### ✅ Step 1: Enable Ingress in Minikube

```bash
minikube addons enable ingress
```
---

### ✅ Step 2: Install Istio CLI

Download and install Istio:

```bash
curl -L https://istio.io/downloadIstio | sh -
cd istio-*
export PATH=$PWD/bin:$PATH
```

---

### ✅ Step 3: Install Istio in Minikube

Install Istio with the default demo profile:

```bash
istioctl install --set profile=demo -y
```
demo profile Installs everything, including observability tools (Prometheus, Grafana, Jaeger, Kiali). Ideal for learning/testing.

Verify:

```bash
kubectl get pods -n istio-system
```

---

### ✅ Step 4: Label the Namespace for Sidecar Injection

```bash
kubectl create namespace task
kubectl label namespace task istio-injection=enabled
```
**What it does?:**

* `kubectl create namespace task` → Creates a new namespace called `task`.
* `kubectl label namespace task istio-injection=enabled` → Enables automatic Istio sidecar (Envoy) injection for all pods in that namespace.

---

### ✅ Step 5: Deploy Your App with Istio Sidecar

Make sure your app is deployed in the `task` namespace.

Update your manifest if needed (or use the following):

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: taskmgmt-config
  namespace: task
spec:
  ---
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: taskmgmt-deployment
  namespace: task
spec:
  ---
---
apiVersion: v1
kind: Service
metadata:
  name: taskmgmt-service
  namespace: task
spec:
  ---
```
Apply:

```bash
kubectl apply -f ./k8s/k8s-configmap.yml
kubectl apply -f ./k8s/k8s-deployment.yml
kubectl apply -f ./k8s/k8s-service.yml
```
---

### ✅ Step 6: Expose via Istio Gateway

**k8s-gateway.yml**

```yaml
apiVersion: networking.istio.io/v1beta1
kind: Gateway
metadata:
  name: taskmgmt-gateway
  namespace: task
spec:
  selector:
    istio: ingressgateway # Use Istio's built-in ingress
  servers:
    - port:
        number: 80
        name: http
        protocol: HTTP
      hosts:
        - "*"
---
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: taskmgmt-vs
  namespace: task
spec:
  hosts:
    - "*"
  gateways:
    - taskmgmt-gateway
  http:
    - match:
        - uri:
            prefix: /api/
      route:
        - destination:
            host: taskmgmt-service
            port:
              number: 8080
```

Apply:

```bash
kubectl apply -f ./k8s/k8s-gateway.yml
```
---

### ✅ Step 7: Get External IP and Access App

Find the Istio ingress gateway IP:

```bash
minikube tunnel
# In new terminal:
kubectl get svc istio-ingressgateway -n istio-system
```

Access your app at:

```bash
curl http://<EXTERNAL-IP>/api/tasks
```

---

### 🎉 You now have a service mesh running your taskmgmt app with:

* Envoy sidecar proxies
* Automatic traffic management
* Future support for mTLS, metrics, tracing, and more

---

