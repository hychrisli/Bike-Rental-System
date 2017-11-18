gcloud init
gcloud components install beta

gcloud beta pubsub topics create TOPIC_RESERVATION
gcloud beta pubsub subscriptions create --topic TOPIC_RESERVATION SUB_RESERVATION

gcloud beta pubsub topics create TOPIC_CONFIRMATION
gcloud beta pubsub subscriptions create --topic TOPIC_CONFIRMATION SUB_CONFIRMATION

gcloud beta pubsub topics create TOPIC_COMPLETION
gcloud beta pubsub subscriptions create --topic TOPIC_COMPLETION SUB_COMPLETION
