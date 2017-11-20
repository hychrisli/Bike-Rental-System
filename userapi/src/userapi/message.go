package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"time"

	"cloud.google.com/go/pubsub"
)

const topicReservation string = "TOPIC_RESERVATION"
const subReservation string = "SUB_RESERVATION"

const topicConfirmation string = "TOPIC_CONFIRMATION"
const subConfirmation string = "SUB_CONFIRMATION"

const topicCompletion string = "TOPIC_COMPLETION"
const subCompletion string = "SUB_COMPLETION"

const proj string = "animated-axe-183700"

// SetUpSubscriptions
func SetUpSubscriptions() {
	topics := []string{
		topicConfirmation,
		topicCompletion,
	}
	subscriptions := []string{
		subConfirmation,
		subCompletion,
	}
	for i, topic := range topics {
		fmt.Print(topic, " ")
		fmt.Println(subscriptions[i])
		fmt.Printf("Current index %v\n", i)
		ctx := context.Background()
		client, err := pubsub.NewClient(ctx, proj)
		if err != nil {
			log.Fatalf("Could not create pubsub Client: %v", err)
		}
		subscription := subscriptions[i]
		go setUpSingleSubscription(client, topic, subscription)
	}
}

func setUpSingleSubscription(client *pubsub.Client, topic, subscription string) {

	t := createTopicIfNotExists(client, topic)

	if err := createSubscriptionIfNotExists(client, subscription, t); err != nil {
		log.Fatal(err)
	}

	if err := pullMsgs(client, subscription, t); err != nil {
		log.Fatal(err)
	}
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

func createSubscriptionIfNotExists(client *pubsub.Client, name string, topic *pubsub.Topic) error {
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

	return nil
}

func pullMsgs(client *pubsub.Client, name string, topic *pubsub.Topic) error {
	ctx := context.Background()

	received := 0
	sub := client.Subscription(name)
	cctx, _ := context.WithCancel(ctx)

	fmt.Println("start pulling message number: ", received)
	err := sub.Receive(cctx, func(ctx context.Context, msg *pubsub.Message) {
		// var dat map[string]interface{}
		// json.Unmarshal(msg.Data, &dat)
		var ord Order
		json.Unmarshal(msg.Data, &ord)
		if name == subConfirmation {
			fmt.Printf("executing updateOrderConfirmation %+v", ord)
			updateOrderConfirmation(ord)
		}

		if name == subCompletion {
			fmt.Printf("executing updateOrderCompletetion %+v", ord)
			updateOrderCompletetion(ord)
		}
		msg.Ack()
	})
	if err != nil {
		return err
	}

	return nil
}

func publish(msg Order) error {
	ctx := context.Background()
	client, err := pubsub.NewClient(ctx, proj)

	dat, _ := json.Marshal(msg)
	t := client.Topic(topicReservation)
	result := t.Publish(ctx, &pubsub.Message{
		Data: dat,
	})

	id, err := result.Get(ctx)
	if err != nil {
		return err
	}
	fmt.Printf("Published a message; msg ID: %v\n", id)

	return nil
}
