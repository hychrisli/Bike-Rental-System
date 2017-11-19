package main

import (
	"fmt"
	"log"
	"os"

	// [START imports]
	"cloud.google.com/go/pubsub"
	"golang.org/x/net/context"
	// [END imports]
)

const topicReservation string = "TOPIC_RESERVATION"
const subReservation string = "SUB_RESERVATION"

const topicConfirmation string = "TOPIC_CONFIRMATION"
const subConfirmation string = "SUB_CONFIRMATION"

const topicCompletion string = "TOPIC_COMPLETION"
const subCompletion string = "SUB_COMPLETION"

func main() {

	proj := os.Getenv("GOOGLE_CLOUD_PROJECT")
	if proj == "" {
		fmt.Fprintf(os.Stderr, "GOOGLE_CLOUD_PROJECT environment variable must be set.\n")
		os.Exit(1)
	}
	topics := []string{topicReservation, topicConfirmation, topicCompletion}

	for _, topic := range topics {
		ctx := context.Background()
		client, err := pubsub.NewClient(ctx, proj)
		if err != nil {
			log.Fatalf("Could not create pubsub Client: %v", err)
		}
		t := createTopicIfNotExists(client, topic)

		if err := publish(t, "hello world from "+topic); err != nil {
			log.Fatalf("Failed to publish: %v", err)
		}
	}

}

func publish(topic *pubsub.Topic, msg string) error {
	ctx := context.Background()
	// [START publish]

	result := topic.Publish(ctx, &pubsub.Message{
		Data: []byte(msg),
	})
	// Block until the result is returned and a server-generated
	// ID is returned for the published message.
	id, err := result.Get(ctx)
	if err != nil {
		return err
	}
	fmt.Printf("Published a message; msg ID: %v\n", id)
	// [END publish]
	return nil
}

func createTopicIfNotExists(c *pubsub.Client, topic string) *pubsub.Topic {
	ctx := context.Background()

	// Create a topic to subscribe to.
	t := c.Topic(topic)
	ok, err := t.Exists(ctx)
	if err != nil {
		log.Fatal(err)
	}
	if ok {
		return t
	}

	t, err = c.CreateTopic(ctx, topic)
	if err != nil {
		log.Fatalf("Failed to create the topic: %v", err)
	}
	return t
}
