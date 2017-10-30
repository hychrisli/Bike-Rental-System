# How to Run

* mvn package
* Run publisher example (using Google pubsub libraries)
```bash 
  mvn exec:java@publisher -Dexec.mainclass="cmpe282.pubsub.PublisherExample" -Dexec.args="myTopic"
```
* Run subscriber example (using Google pubsub libraries)
```bash
  mvn exec:java@subscriber -Dexec.mainclass="cmpe282.pubsub.SubscriberExample" -Dexec.args="myTopic2 mySub2"
```
