# Build station-rest image
docker build -t gcr.io/bike-rental-system/station-rest station-rest/
gcloud docker -- push gcr.io/bike-rental-system/station-rest

# Build station-pubsub image
