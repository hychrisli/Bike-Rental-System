package main

import (
	"fmt"

	mgo "gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// MongoDB Config
const mongodbServer string = "mongodb://127.0.0.1:27017"
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
