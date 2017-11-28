# Ssh to the pod container
kubectl exec -it brs -- /bin/bash

# Test run rest container
kubectl run -it rest-station --image=gcr.io/bike-rental-system/station-rest --replicas=1 --port=8080 --env="CASSANDRA_HOST=brs" --labels="name=station-rest,tier=backend,app=web"
