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
