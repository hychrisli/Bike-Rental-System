package main

import (
	"fmt"
	"log"
	"os"
	"sync"
	"time"

	"cloud.google.com/go/pubsub"
	"golang.org/x/net/context"
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

	// topics := []string{topicReservation, topicConfirmation, topicCompletion}
	// subscriptions := []string{subReservation, subConfirmation, subCompletion}

	topics := []string{topicReservation}
	subscriptions := []string{subReservation}

	for i, topic := range topics {
		fmt.Printf("Current index %v\n", i)
		ctx := context.Background()
		client, err := pubsub.NewClient(ctx, proj)
		if err != nil {
			log.Fatalf("Could not create pubsub Client: %v", err)
		}
		subscription := subscriptions[i]
		go setUpSubscription(client, topic, subscription)
	}
	var input string
	fmt.Scanln(&input)
}

func setUpSubscription(client *pubsub.Client, topic, subscription string) {

	t := createTopicIfNotExists(client, topic)

	if err := create(client, subscription, t); err != nil {
		log.Fatal(err)
	}

	// Pull messages via the subscription.
	if err := pullMsgs(client, subscription, t); err != nil {
		log.Fatal(err)
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

func create(client *pubsub.Client, name string, topic *pubsub.Topic) error {
	ctx := context.Background()
	// [START create_subscription]
	ok, err := client.Subscription(name).Exists(ctx)
	if err != nil {
		return err
	}
	if ok {
		return nil
	}
	sub, err := client.CreateSubscription(ctx, name, pubsub.SubscriptionConfig{
		Topic:       topic,
		AckDeadline: 20 * time.Second,
	})
	if err != nil {
		return err
	}
	fmt.Printf("Created subscription: %v\n", sub)
	// [END create_subscription]
	return nil
}

func pullMsgs(client *pubsub.Client, name string, topic *pubsub.Topic) error {
	ctx := context.Background()

	// [START pull_messages]
	// Consume 10 messages.
	var mu sync.Mutex
	received := 0
	sub := client.Subscription(name)
	cctx, _ := context.WithCancel(ctx)

	fmt.Println("start pulling message number: ", received)
	err := sub.Receive(cctx, func(ctx context.Context, msg *pubsub.Message) {
		mu.Lock()
		defer mu.Unlock()
		received++
		fmt.Printf("Got message: %q from %+v number %v\n", string(msg.Data), name, received)
		msg.Ack()
	})
	if err != nil {
		return err
	}

	// [END pull_messages]
	return nil
}
