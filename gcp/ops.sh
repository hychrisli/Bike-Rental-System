# Create cassandra pod
kubectl create -f brs-cassandra-pod.yaml
kubectl expose pod  --type=ClusterIP --name=svc-db

# Create station-rest pod
kubectl create -f brs-station-rest.yaml
kubectl expose pod  --type=ClusterIP --name=svc-rest