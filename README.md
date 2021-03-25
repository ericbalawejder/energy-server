# Energy-Server

A server that shows the total amount of energy metered by a system for a given timeframe.  
A series of sensor data values (time in seconds, voltage in volts, and current in amps) are
located in the file "sensors.json". The server responds with an approximate value
for kWh measured over the given time interval. The server is built from 
[Dropwizard](https://www.dropwizard.io/en/latest/) and uses the 
[Jackson Streaming API](https://github.com/FasterXML/jackson-core) to read the data from the JSON file.

### Usage
```
$ curl -d "starttime=STARTTIME&endtime=ENDTIME" http://localhost:8080/ | jq .
```
where STARTTIME and ENDTIME are time in seconds.

### How to run:
```
$ gradle clean build
```
```
$ ./gradlew run
```
or 
```
$ java -jar build/libs/energy-server-0.0.1-SNAPSHOT-all.jar server
```
#### To run the tests:
```
$ ./gradlew test
```
