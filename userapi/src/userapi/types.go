package main

type person struct {
	Id        string
	FirstName string
	LastName  string
	Orders    []order
}

type order struct {
	Id          string
	StationID   string
	OrderStatus string
}
