package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"sync"

	"cloud.google.com/go/pubsub"
	"github.com/gorilla/mux"
	"github.com/mitchellh/mapstructure"
	"github.com/unrolled/render"
	"github.com/urfave/negroni"
	mgo "gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// MongoDB Config
var mongodbServer = "mongodb://127.0.0.1:27017"
var mongodbDatabase = "cmpe282"
var mongodbCollection = "user"

func NewServer() *negroni.Negroni {
	formatter := render.New(render.Options{
		IndentJSON: true,
	})
	n := negroni.Classic()
	mx := mux.NewRouter()
	initRoutes(mx, formatter)
	n.UseHandler(mx)
	return n
}

// API Routes
func initRoutes(mx *mux.Router, formatter *render.Render) {
	mx.HandleFunc("/ping", pingHandler(formatter)).Methods("GET")
	mx.HandleFunc("/user/{id}", userHandler(formatter)).Methods("GET")
	mx.HandleFunc("/order", orderCreateHandler(formatter)).Methods("POST")
}

// API Ping Handler
func pingHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct{ Test string }{"ping alive!"})
	}
}

// GET user by id API handler
func userHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		params := mux.Vars(req)
		id := params["id"]
		fmt.Println("user id: ", id)
		session, err := mgo.Dial(mongodbServer)
		if err != nil {
			panic(err)
		}
		defer session.Close()
		session.SetMode(mgo.Monotonic, true)
		c := session.DB(mongodbDatabase).C(mongodbCollection)
		var result bson.M
		err = c.FindId(id).One(&result)
		if err != nil {
			fmt.Println(err)
			formatter.JSON(w, http.StatusNotFound, map[string]string{"error": "user " + id + " not found!"})
		}
		fmt.Println("user:", result)
		formatter.JSON(w, http.StatusOK, result)
	}
}

var (
	topic *pubsub.Topic

	// Messages received by this instance.
	messagesMu sync.Mutex
	messages   []string
)

const maxMessages = 10

// POST new order API handler
func orderCreateHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		msg := &pushRequest{}
		if err := json.NewDecoder(r.Body).Decode(msg); err != nil {
			http.Error(w, fmt.Sprintf("Could not decode body: %v", err), http.StatusBadRequest)
			return
		}
		var dat map[string]interface{}

		if err := json.Unmarshal(msg.Message.Data, &dat); err != nil {
			panic(err)
		}
		// fmt.Println(dat)

		// messagesMu.Lock()
		// defer messagesMu.Unlock()
		// message := string(msg.Message.Data)
		// fmt.Println(message)

		var o order
		if err := mapstructure.Decode(dat, &o); err != nil {
			panic(err)
		}

		fmt.Println()

		id := o.BelongTo
		session, err := mgo.Dial(mongodbServer)
		if err != nil {
			panic(err)
		}
		defer session.Close()
		session.SetMode(mgo.Monotonic, true)
		c := session.DB(mongodbDatabase).C(mongodbCollection)
		err = c.Update(bson.M{"_id": id}, bson.M{"$push": bson.M{"orders": o}})
		if err != nil {
			fmt.Println(err)
			formatter.JSON(w, http.StatusNotFound, map[string]string{"error": "updating user " + id + " with error!"})
		}
		// msg := &pushRequest{}
		// b, err := ioutil.ReadAll(req.Body)
		// if err != nil {
		// 	log.Fatal(err)
		// }
		// fmt.Printf("%s\n", b)
		// if err := json.NewDecoder(req.Body).Decode(msg); err != nil {
		// 	http.Error(w, fmt.Sprintf("Could not decode body: %v", err), http.StatusBadRequest)
		// 	return
		// }
		// defer req.Body.Close()
		// messagesMu.Lock()
		// defer messagesMu.Unlock()
		// // Limit to ten.
		// messages = append(messages, string(msg.Message.Data))
		// if len(messages) > maxMessages {
		// 	messages = messages[len(messages)-maxMessages:]
		// }
		// fmt.Println(messages)
		// formatter.JSON(w, http.StatusOK, map[string]string{"status": "ok"})
	}
}
