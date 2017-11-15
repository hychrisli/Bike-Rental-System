gcloud init
gcloud components install beta

gcloud beta pubsub topics create TOPIC_RESERVATION_REQUEST
gcloud beta pubsub subscriptions create --topic TOPIC_RESERVATION_REQUEST SUB_RESERVATION_REQUEST

gcloud beta pubsub topics create TOPIC_RESERVATION_RESPONSE
gcloud beta pubsub subscriptions create --topic TOPIC_RESERVATION_RESPONSE SUB_RESERVATION_RESPONSE
