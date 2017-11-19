package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"sync"

	"cloud.google.com/go/pubsub"
)

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

func publish(client *pubsub.Client, topic string, msg Order) error {
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

func pullMsgs(client *pubsub.Client, name string, topic *pubsub.Topic) error {
	ctx := context.Background()

	var mu sync.Mutex
	received := 0
	sub := client.Subscription(name)
	cctx, cancel := context.WithCancel(ctx)
	err := sub.Receive(cctx, func(ctx context.Context, msg *pubsub.Message) {
		mu.Lock()
		defer mu.Unlock()
		received++
		if received >= 10 {
			cancel()
			msg.Nack()
			return
		}
		fmt.Printf("Got message: %q\n", string(msg.Data))
		msg.Ack()
	})
	if err != nil {
		return err
	}
	// [END pull_messages]
	return nil
}
