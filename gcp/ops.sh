# System setup kubectl expose deployment hello-world --type=NodePort --name=example-service

gcloud config set compute/zone us-west1-c
gcloud config set project bike-rental-system

gcloud container clusters create brs-cluster --num-nodes 1


# Create pod
kubectl create -f brs-cassandra-pod.yaml

# Ssh to the pod container
kubectl exec -it brs -- /bin/bash


# Build image
docker build -t gcr.io/bike-rental-system/station-rest .

# Push image
gcloud docker -- push gcr.io/bike-rental-system/station-rest

# Test run rest container
kubectl run -it rest-station --image=gcr.io/bike-rental-system/station-rest --replicas=1 --port=8080 --env="CASSANDRA_HOST=brs" --labels="name=station-rest,tier=backend,app=web"


kubectl expose pod station_rest --type=ClusterIP --name=rest-out

kubectl cp $GOOGLE_APPLICATION_CREDENTIALS station-rest

kubectl port-forward station-rest 8080:8080
