# Simple Spring Boot Load Balancer (Educational Project)

## 📌 Purpose

This project is a **purely educational implementation of a Load Balancer**, built with **Spring Boot**, to demonstrate how a load balancer works *internally*.


Students are expected to:
- Read the code
- Understand the responsibilities of each component
- Use this project as a **reference** when building their own load balancer

---

## 🎓 Learning Goals

By studying this project, students should understand:

- How HTTP request forwarding works
- What responsibilities a load balancer has
- How backend selection strategies work
- Why health checks are important
- How routing, proxying, and backend selection are separated
- Why real-world load balancers (Nginx, Envoy) are architected the way they are

---

## 🧠 What This Load Balancer Does

### Implemented Features

- Reverse proxy for HTTP requests
- Multiple backend instances
- Round Robin load balancing strategy
- Backend health checks
- Backend registry with health awareness
- Transparent request forwarding (method, headers, body, query params)

### Not Implemented (Intentionally)

- TLS termination
- Advanced retries / circuit breakers
- Rate limiting
- Security features
- Service discovery
- Production-grade observability

---

## 🏗 Architecture Overview

```
Client
  |
  v
LoadBalancerController
  |
  v
LoadBalancingStrategy
  |
  v
BackendRegistry  <---- HealthCheckScheduler
  |
  v
ProxyClient
  |
  v
Backend Services
```

---

## 🧩 Core Components

### 1️⃣ LoadBalancerController
- Entry point for **all incoming HTTP requests**
- Delegates backend selection to a strategy
- Forwards the request using the proxy client

---

### 2️⃣ LoadBalancingStrategy
- Decides **which backend should handle the request**
- Example: Round Robin, IP Hash
- Operates **only on healthy backends**

---

### 3️⃣ BackendRegistry
- Stores the list of all backends
- Tracks healthy and unhealthy instances
- Acts as the **source of truth** for backend availability

---

### 4️⃣ HealthCheckScheduler
- Periodically checks backend health
- Marks backends as healthy or unhealthy
- Prevents traffic from being routed to broken instances

---

### 5️⃣ ProxyClient
- Responsible only for HTTP forwarding
- Copies request method, headers, query parameters, and body
- Sends the request to the selected backend
- Returns the backend response as-is

---

## ❤️ Health Checks

Each backend must expose a simple endpoint:

```
GET /health
```

Expected behavior:
- `200 OK` → backend is healthy
- Any error or timeout → backend is unhealthy

---

## 🚀 How to Run

### Start backend services
```bash
java -jar widgets-ms-1.0.0.jar --server.port=8090 --MIRO_TOKEN=your_token --DEFAULT_COLOR=RED
java -jar widgets-ms-1.0.0.jar --server.port=8095 --MIRO_TOKEN=your_token --DEFAULT_COLOR=BLUE
```

### Start the load balancer
```bash
java -jar load-balancer.jar --server.port=8080
```

### Send a request
[Get Sticky Note]
```bash
curl "http://localhost:8080/stickyNote?boardKey=your_borad_key&widgetId=your_widget_id" 
```
[Create Sticky Note]
```bash
curl -X POST "http://localhost:8080/stickyNote" \
  -H "Content-Type: application/json" \
  -d '{
    "boardKey": "your_board_key",
    "text": "<p>Hello from curl</p>",
    "x": 100,
    "y": 100
  }' 
```
[Delete Sticky Note]
```bash
curl -X DELETE "http://localhost:8080/stickyNote?boardKey=your_board_key&widgetId=your_widget_id"  
```

---

## 🧪 Suggested Student Exercises

- Implement sticky sessions (IP Hash)
- Add retries and timeouts
- Add simple metrics
- Implement circuit breaker
- Dynamic backend registration

---

