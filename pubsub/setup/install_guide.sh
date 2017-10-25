#############
#
# Installation
#
#############
SDK=google-cloud-sdk-176.0.0-linux-x86_64
SDk_DIR=google-cloud-sdk

wget https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/${SDK}.tar.gz

gunzip ${SDK}.tar.gz

tar -xvf ${SDK}.tar

# ${SDk_DIR}/install.sh

# move google-cloud-sdk to a desiganated directory
# add google-cloud-sdk/bin to PATH

# Once configured, install beta component
gcloud components update && gcloud components install beta

# Install pub/sub emulator at local machine
gcloud components install pubsub-emulator

#############
#
# Init project
#
#############
gcloud init

#############
#
# start emulator
#
#############

gcloud beta emulators pubsub start

# auto-config env variables
$(gcloud beta emulators pubsub env-init)









