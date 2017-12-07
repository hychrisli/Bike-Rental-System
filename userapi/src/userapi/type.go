package main

// User object
type User struct {
	ID        string  `json:"id" bson:"_id"`
	FirstName string  `json:"first_name" bson:"first_name"`
	LastName  string  `json:"last_name" bson:"last_name"`
	Credit    int     `json:"credit" bson:"credit"`
	Orders    []Order `json:"orders" bson:"orders"`
}

// Order object
type Order struct {
	TransactionID string  `json:"transaction_id" bson:"transaction_id"`
	UserID        string  `json:"user_id" bson:"user_id"`
	IsReserved    bool    `json:"is_reserved" bson:"is_reserved"`
	BikeID        string  `json:"bike_id" bson:"bike_id"`
	StationID     string  `json:"station_id" bson:"station_id"`
	Status        string  `json:"status" bson:"status"`
	GrandTotal    float32 `json:"grand_total" bson:"grand_total"`
}
