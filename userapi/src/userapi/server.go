package main

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
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

// POST new order API handler
func orderCreateHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		var o order
		_ = json.NewDecoder(req.Body).Decode(&o)
		defer req.Body.Close()
		fmt.Println(o)
		formatter.JSON(w, http.StatusOK, map[string]string{"status": "ok"})
	}
}
