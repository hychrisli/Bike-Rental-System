// Sample pubsub-quickstart creates a Google Cloud Pub/Sub topic.
package main

import (
	"fmt"
	"log"

	// Imports the Google Cloud Pub/Sub client package.
	"cloud.google.com/go/pubsub"
	"golang.org/x/net/context"
)

func main() {
	ctx := context.Background()

	// Sets your Google Cloud Platform project ID.
	projectID := "animated-axe-183700"

	// Creates a client.
	client, err := pubsub.NewClient(ctx, projectID)
	if err != nil {
		log.Fatalf("Failed to create client: %v", err)
	}

	// Sets the name for the new topic.
	topicName := "my-new-topic"

	// Creates the new topic.
	topic, err := client.CreateTopic(ctx, topicName)
	if err != nil {
		log.Fatalf("Failed to create topic: %v", err)
	}

	fmt.Printf("Topic %v created.\n", topic)
}
