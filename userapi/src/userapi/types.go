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
	BelongTo    string
}

type pushRequest struct {
	Message struct {
		Attributes map[string]string
		Data       []byte
		ID         string `json:"message_id"`
	}
	Subscription string
}
