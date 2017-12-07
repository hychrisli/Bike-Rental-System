package main

import (
	"fmt"

	mgo "gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// MongoDB Config
// const mongodbServer string = "mongodb://mongo-0.mongo,mongo-1.mongo,mongo-2.mongo:27017"
const mongodbServer string = "mongodb://mongo-0.mongo:27017"

// const mongodbServer string = "mongodb://127.0.0.1:27017"
const mongodbDatabase string = "cmpe282"
const mongodbCollection string = "user"

func createUser(u User) error {
	session, err := mgo.Dial(mongodbServer)
	if err != nil {
		return err
	}
	defer session.Close()
	session.SetMode(mgo.Monotonic, true)
	c := session.DB(mongodbDatabase).C(mongodbCollection)
	err = c.Insert(u)
	fmt.Println(err)
	return err
}

func getUser(id string, result *bson.M) error {
	session, err := mgo.Dial(mongodbServer)
	if err != nil {
		return err
	}
	defer session.Close()
	session.SetMode(mgo.Monotonic, true)
	c := session.DB(mongodbDatabase).C(mongodbCollection)
	fmt.Printf("%T\n", id)
	err = c.FindId(id).One(&result)
	fmt.Println(err)
	return err
}

func createOrder(ord Order) error {
	session, err := mgo.Dial(mongodbServer)
	defer session.Close()
	if err != nil {
		return err
	}
	session.SetMode(mgo.Monotonic, true)
	c := session.DB(mongodbDatabase).C(mongodbCollection)
	id := ord.UserID
	err = c.Update(bson.M{"_id": id}, bson.M{"$push": bson.M{"orders": ord}})
	if err != nil {
		fmt.Println(err)
		return err
	}
	err = publish(ord)
	fmt.Println(err)
	return err
}

// func updateOrder(dat map[string]interface{}) error {

// 	session, err := mgo.Dial(mongodbServer)
// 	defer session.Close()
// 	if err != nil {
// 		return err
// 	}
// 	session.SetMode(mgo.Monotonic, true)
// 	c := session.DB(mongodbDatabase).C(mongodbCollection)
// 	id := dat["user_id"].(string)
// 	transactionID := dat["transaction_id"].(string)

// 	if dat["is_reserved"] != nil {
// 		isReserved := dat["is_reserved"].(bool)
// 		bikeID := dat["bike_id"].(string)
// 		stationID := dat["station_id"].(string)
// 		err = c.Update(
// 			bson.M{"_id": id, "orders.transaction_id": transactionID},
// 			bson.M{"$set": bson.M{
// 				"orders.$.is_reserved": isReserved,
// 				"orders.$.bike_id":     bikeID,
// 				"orders.$.station_id":  stationID,
// 			}},
// 		)
// 	}

// 	if dat["grand_total"] != nil {
// 		grandTotal := dat["grand_total"].(int)
// 		err = c.Update(
// 			bson.M{"_id": id, "orders.transaction_id": transactionID},
// 			bson.M{"$set": bson.M{
// 				"orders.$.grand_total": grandTotal,
// 			}},
// 		)
// 	}
// 	fmt.Println(err)
// 	return err
// }

func updateOrderConfirmation(ord Order) error {

	session, err := mgo.Dial(mongodbServer)
	defer session.Close()
	if err != nil {
		return err
	}
	session.SetMode(mgo.Monotonic, true)
	c := session.DB(mongodbDatabase).C(mongodbCollection)
	id := ord.UserID
	transactionID := ord.TransactionID
	isReserved := ord.IsReserved
	bikeID := ord.BikeID
	stationID := ord.StationID
	var status string
	if isReserved {
		status = "Order is successful"
	} else {
		status = "Order is cancelled"
	}
	err = c.Update(
		bson.M{"_id": id, "orders.transaction_id": transactionID},
		bson.M{"$set": bson.M{
			"orders.$.is_reserved": isReserved,
			"orders.$.bike_id":     bikeID,
			"orders.$.station_id":  stationID,
			"orders.$.status":      status,
		}},
	)
	fmt.Println(err)
	return err
}

func updateOrderCompletetion(ord Order) error {

	session, err := mgo.Dial(mongodbServer)
	defer session.Close()
	if err != nil {
		return err
	}
	session.SetMode(mgo.Monotonic, true)
	c := session.DB(mongodbDatabase).C(mongodbCollection)
	id := ord.UserID
	transactionID := ord.TransactionID
	grandTotal := ord.GrandTotal
	fmt.Println("GrandTotal is", grandTotal)
	status := "Order closed"
	err = c.Update(
		bson.M{"_id": id},
		bson.M{"$inc": bson.M{
			"credit": -grandTotal,
		}},
	)
	if err != nil {
		return err
	}
	err = c.Update(
		bson.M{"_id": id, "orders.transaction_id": transactionID},
		bson.M{"$set": bson.M{
			"orders.$.grand_total": grandTotal,
			"orders.$.status":      status,
		}},
	)
	fmt.Println(err)
	return err
}

// func updateOrder(id string, ord order) error {
// 	session, err := mgo.Dial(mongodbServer)
// 	defer session.Close()
// 	if err != nil {
// 		return err
// 	}
// 	session.SetMode(mgo.Monotonic, true)
// 	c := session.DB(mongodbDatabase).C(mongodbCollection)
// 	err = c.Update(bson.M{"_id": id}, bson.M{"$push": bson.M{"orders": ord}})
// 	return err
// }
// db.user.update({"_id": "1",
// 	"orders.transaction_id": "a00d2850-0153-4140-ba6a-f7ad7d60bf43"},
// 	{$set: {"orders.$.bike_id": "5"}})
