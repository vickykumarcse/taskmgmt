#!/bin/bash

ENV_FILE=".env"
KEY="HOST_IP"

# Determine the host IP from Minikube's network interface
HOST_IP=$(minikube ssh "ip route | grep default" | awk '{print $3}')

# Validate IP format
if [[ ! $HOST_IP =~ ^([0-9]{1,3}\.){3}[0-9]{1,3}$ ]]; then
  echo "❌ Failed to detect valid host IP from Minikube. Minikube may not be running!"
  exit 1
fi

# Update or insert the HOST_IP in .env
if grep -q "^$KEY=" "$ENV_FILE" 2>/dev/null; then
  sed -i "s/^$KEY=.*/$KEY=$HOST_IP/" "$ENV_FILE"
else
  echo "$KEY=$HOST_IP" >> "$ENV_FILE"
fi

echo "✅ $KEY set to $HOST_IP in $ENV_FILE"
echo "Starting Services using Docker Compose..."
# Start Docker Compose
docker compose up -d
echo "✅ Services started."