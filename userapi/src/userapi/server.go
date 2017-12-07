package main

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/gorilla/mux"
	"github.com/rs/cors"
	uuid "github.com/satori/go.uuid"
	"github.com/unrolled/render"
	"github.com/urfave/negroni"
	"gopkg.in/mgo.v2/bson"
)

type Response struct {
	Msg string `json:"message"`
}

// NewServer for rest
func NewServer() *negroni.Negroni {
	formatter := render.New(render.Options{
		IndentJSON: true,
	})
	n := negroni.Classic()
	mx := mux.NewRouter()
	initRoutes(mx, formatter)
	c := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
	})
	n.Use(c)
	n.UseHandler(mx)
	return n
}

// API Routes
func initRoutes(mx *mux.Router, formatter *render.Render) {
	mx.HandleFunc("/ping", pingHandler(formatter)).Methods("GET")
	mx.HandleFunc("/user", createUserHandler(formatter)).Methods("POST")
	mx.HandleFunc("/user/{id}", getUserHandler(formatter)).Methods("GET")
	mx.HandleFunc("/order", createOrderHandler(formatter)).Methods("POST")
}

// API Ping Handler
func pingHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, Response{Msg: "ping alive!"})
	}
}

// POST user API handler
func createUserHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		var usr User
		if err := json.NewDecoder(req.Body).Decode(&usr); err != nil {
			fmt.Println(err)
			formatter.JSON(w, http.StatusBadRequest, Response{Msg: "Could not decode body"})
			return
		}

		if err := createUser(usr); err != nil {
			fmt.Println(err)
			formatter.JSON(w, http.StatusBadRequest, Response{Msg: "Could not create user"})
			return
		}

		formatter.JSON(w, http.StatusOK, Response{Msg: "user created"})
	}
}

// GET user by id API handler
func getUserHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		id := mux.Vars(req)["id"]
		fmt.Println("user id: ", id)
		var result bson.M
		if err := getUser(id, &result); err != nil {
			formatter.JSON(w, http.StatusNotFound, Response{Msg: "user " + id + " not found!"})
			return
		}
		// fmt.Println("user:", result)
		formatter.JSON(w, http.StatusOK, result)
	}
}

// POST new order API handler
func createOrderHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		var ord Order

		if err := json.NewDecoder(req.Body).Decode(&ord); err != nil {
			fmt.Println(err)
			formatter.JSON(w, http.StatusBadRequest, Response{Msg: "Could not decode body"})
			return
		}

		ord.TransactionID = uuid.NewV4().String()
		ord.Status = "New Order Processing"

		if err := createOrder(ord); err != nil {
			formatter.JSON(w, http.StatusBadRequest, Response{Msg: "Creating order to mongodb"})
			return
		}

		out, _ := json.Marshal(ord)
		fmt.Println(string(out))
		formatter.JSON(w, http.StatusOK, Response{Msg: "Order created"})

	}
}
