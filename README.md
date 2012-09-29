Chaimcheck Pattern using Spring Integration and Gemfire
=======================================================

In this project, I will demo the use of [Claimcheck-Enterprise Integration Pattern](http://eaipatterns.com/StoreInLibrary.html) using Spring Integration and Gemfire. To quickly see how it works,

* Download [activemq](http://activemq.apache.org/activemq-560-release.html)
* Start the activemq by executing <Activemq home>/bin/activemq.bat
* Create a Topic called "MyTopic"
* Get the source code
* Start the Gemfire replicated-region based server as follows
* cd gemfire-server
* mvn package
* mvn exec:java -Dexec.mainClass="org.springframework.data.gemfire.examples.Server"
* Maven import claimcheck-subscriber project into STS IDE and "Run on Server" this project
* run the below command and notice the queue called consumed and message got printed on the STS IDE console
* cd claimcheck-publisher
* mvn test

Feel free to modify this code and use it for your need. Refer this [blog](http://krishnasblog.com/) for more details.
