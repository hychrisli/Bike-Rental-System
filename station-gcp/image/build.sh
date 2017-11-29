# Build station-rest image
cp ../../station-rest/target/station-rest-0.0.1-SNAPSHOT-exec.jar station-rest/
docker build -t gcr.io/bike-rental-system/station-rest station-rest/
gcloud docker -- push gcr.io/bike-rental-system/station-rest

# Build station-pubsub image
cp ../../station-pubsub/target/station-pubsub-0.0.1-SNAPSHOT-exec.jar station-pubsub/
docker build -t gcr.io/bike-rental-system/station-pubsub station-pubsub/
gcloud docker -- push gcr.io/bike-rental-system/station-pubsub