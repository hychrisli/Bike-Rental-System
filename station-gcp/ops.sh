# Create cassandra pod
kubectl create -f brs-cassandra.yaml
kubectl expose pod cassandra --type=ClusterIP --name=svc-db

# Create station-rest pod
kubectl create -f brs-station-rest.yaml
kubectl expose pod station-rest --type=ClusterIP --name=svc-rest

# Create station-pubsub pod
kubectl create -f brs-station-pubsub.yaml


# ssh into pods
kubectl exec -it cassandra -- /bin/bash
kubectl exec -it station-rest -- /bin/bash
kubectl exec -it station-pubsub -- /bin/bash

# cqlsh cassadnra
kubectl exec -it cassandra -- cqlsh