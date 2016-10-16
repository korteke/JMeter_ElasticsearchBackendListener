# JMeter_ElasticsearchBackendListener
Elasticsearch Backend Listener for Apache JMeter 3.x

## Getting Started

---

### Prerequisities

Java JDK >7   
Jmeter 3.x   
Elasticsearch 2.x   

### Installing

```
mvn clean package
cp $projectHome/target/*.jar --> $JMETER_HOME/lib/ext   
cp $projectHome/target/dependencies/*.jar --> $JMETER_HOME/lib/ext   
```
   
$projectHome == where you cloned this repository

### Configure
* Add new Backend Listener to your Test Suite (Add --> Listener --> Backend Listener)
* Backend Listener Implementation = net.kvak.jmeter.backendlistener.elasticsearch.ElasticsearchBackend
* Configure Elasticsearch parameters

### Remarks
If JMeter returns non numeric value to HTTP ResponseCode field (example: Non HTTP response code: java.net.SocketTimeoutException) then this plugin will subtitute that to the HTTP StatusCode 531.   
This functionality prevents the explosion of Elasticsearch index mapping which states that ResponseCode must be numeric.   
StatusCode can be defined at settings page.
