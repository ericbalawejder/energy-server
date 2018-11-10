# Energy Server

A server that tells us the total amount of energy metered by a system for a given timeframe.  
A series of sensor data values (time in seconds, voltage in volts, and current in amps) are
located in the file "sensors.json". The server responds with an approximate value
for kWh measured over the given time interval. The server is built from Dropwizard https://www.dropwizard.io/1.3.5/docs/index.html
and uses the Jackson Streaming API https://github.com/FasterXML/jackson-core to read the data from the JSON file. Packaged with Maven.

### Usage

```shell
$ curl -d "starttime=STARTTIME&endtime=ENDTIME" http://localhost:8080/
```
where STARTTIME and ENDTIME are time in seconds.

### How to run:

Tested on macOS High Sierra and an Ubuntu-14.04 Vagrant box VM using Java 1.8 or later.

```shell
$ java -version
java version "1.8.0_191"
Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
```

In the top level project folder:

```shell
$ java -jar target/energy-server-1.0-SNAPSHOT-jar-with-dependencies.jar server configuration.yml
```
The server should be running and post similar output.

```shell
.
.
.
INFO  [2018-10-24 18:44:12,748] org.eclipse.jetty.server.handler.ContextHandler: Started i.d.j.MutableServletContextHandler@433c6abb{/,null,AVAILABLE}
INFO  [2018-10-24 18:44:12,769] org.eclipse.jetty.server.AbstractConnector: Started application@7d0cc890{HTTP/1.1,[http/1.1]}{0.0.0.0:8080}
INFO  [2018-10-24 18:44:12,770] org.eclipse.jetty.server.AbstractConnector: Started admin@49293b43{HTTP/1.1,[http/1.1]}{0.0.0.0:8081}
INFO  [2018-10-24 18:44:12,770] org.eclipse.jetty.server.Server: Started @2338ms

```

In a separate terminal window:

```shell
$ curl -d "starttime=1234&endtime=5678" http://localhost:8080/
```

Output

```shell
{"results":{"energy":307.714,"units":"kWh"}}
```

Valid JSON

```shell
$ curl -d "starttime=1234&endtime=5678" http://localhost:8080/ | jq .
```

Output

```shell
% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    71  100    44  100    27    450    276 --:--:-- --:--:-- --:--:--   448
{
  "results": {
    "units": "kWh",
    "energy": 307.714
  }
}
```
