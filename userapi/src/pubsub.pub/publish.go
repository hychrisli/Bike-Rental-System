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
	// topics := []string{topicConfirmation, topicCompletion}

	// topics := []string{topicConfirmation}
	// msg := []byte(`{
	// 	"transaction_id": "4989a28e-387a-4c2c-8922-7cfbaeee0bb5",
	// 	"user_id": "1",
	// 	"is_reserved": true,
	// 	"bike_id": "10",
	// 	"station_id": "10"
	//  }`)

	topics := []string{topicCompletion}
	msg := []byte(`{
		"transaction_id": "4989a28e-387a-4c2c-8922-7cfbaeee0bb5",
		"user_id": "1",
		"grand_total": 10
	 }`)
	for _, topic := range topics {
		ctx := context.Background()
		client, err := pubsub.NewClient(ctx, proj)
		if err != nil {
			log.Fatalf("Could not create pubsub Client: %v", err)
		}
		t := createTopicIfNotExists(client, topic)
		if err := publish(t, msg); err != nil {
			log.Fatalf("Failed to publish: %v", err)
		}
	}

}

func publish(topic *pubsub.Topic, msg []byte) error {
	ctx := context.Background()

	result := topic.Publish(ctx, &pubsub.Message{
		Data: msg,
	})

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
