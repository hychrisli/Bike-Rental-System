package main

import (
	"encoding/json"
	"fmt"
	"log"
	"os"
	"time"

	// [START imports]
	"cloud.google.com/go/pubsub"
	"golang.org/x/net/context"
	// [END imports]
)

type order struct {
	Id          string
	StationID   string
	OrderStatus string
	BelongTo    string
}

func main() {
	ctx := context.Background()
	// [START auth]
	proj := os.Getenv("GOOGLE_CLOUD_PROJECT")
	if proj == "" {
		fmt.Fprintf(os.Stderr, "GOOGLE_CLOUD_PROJECT environment variable must be set.\n")
		os.Exit(1)
	}
	client, err := pubsub.NewClient(ctx, proj)
	if err != nil {
		log.Fatalf("Could not create pubsub Client: %v", err)
	}
	// [END auth]

	const topicName = "example-topic"
	// Create a new topic called example-topic.
	topic := createTopicIfNotExists(client, topicName)

	const sub = "example-subscription"
	const end = "http://localhost:3000/order"
	// Create a new subscription.
	if err := createWithEndpointIfNotExists(client, sub, topic, end); err != nil {
		log.Fatal(err)
	}

	msg := order{Id: "abc", StationID: "123", OrderStatus: "init", BelongTo: "1"}

	// Publish a text message on the created topic.
	if err := publish(client, topicName, msg); err != nil {
		log.Fatalf("Failed to publish: %v", err)
	}
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

func publish(client *pubsub.Client, topic string, msg order) error {
	ctx := context.Background()
	// [START publish]

	dat, _ := json.Marshal(msg)
	t := client.Topic(topic)
	result := t.Publish(ctx, &pubsub.Message{
		Data: dat,
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

func createWithEndpointIfNotExists(client *pubsub.Client, subName string, topic *pubsub.Topic, endpoint string) error {
	ctx := context.Background()
	// [START create_push_subscription]

	// For example, endpoint is "https://my-test-project.appspot.com/push".
	sub := client.Subscription(subName)
	ok, err := sub.Exists(ctx)
	if err != nil {
		return err
	}
	if ok {
		return nil
	}
	sub, err = client.CreateSubscription(ctx, subName, pubsub.SubscriptionConfig{
		Topic:       topic,
		AckDeadline: 10 * time.Second,
		PushConfig:  pubsub.PushConfig{Endpoint: endpoint},
	})
	if err != nil {
		return err
	}
	fmt.Printf("Created subscription: %v\n", sub)
	// [END create_push_subscription]
	return nil
}
