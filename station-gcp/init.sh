# One-time setup for the cluster and persistent volume
# System setup kubectl expose deployment hello-world --type=NodePort --name=example-service

gcloud config set compute/zone us-west1-c
gcloud config set project bike-rental-system

gcloud container clusters create brs-cluster --num-nodes 1

kubectl create -f brs-persistant-volume.yaml
