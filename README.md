Publisher/Subscriber EIP pattern using Spring Integration and Gemfire
=====================================================================

Sample flow 1: Just test the subscriber architecture
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

Refer this [blog](http://krishnasblog.com/2012/09/29/claimcheck-pattern-using-spring-integration-and-gemfire/) for more details on the above flow.

Sample flow 2:
In this project, I will demo the complete end to end Publisher/Subscriber model using Spring Integration and Gemfire. This assumes that you have already installed activemq, created a topic MyTopic, started the Gemfire server as above and finally maven imported claimcheck-subscriber project into STS IDE 

To quickly see how it works,

* run the hsqldb server as in, hsqldb\runServer.bat
* copy hsqldb\sqltool.rc into c:\Users\<you loginid> folder
* run the hsqldb client as in, hsqldb\runSqlTools.bat
* call "\j goSmarter" on the sql prompt
* execute the script db.sql, it just contains one table notification
* insert records with status= 2 like "insert into notification (id, name, status) values (1, 'order', 2)
* Maven import application-data-publisher
* Add both the applications application-data-publisher, claimcheck-subscriber on the server and start the TC server
* if you notice, end to end, the application-data-publisher picks up the message from the db table and pushes to Gemfire/Topic and the claimcheck-subscriber will pickup from the Gemfire/Topic and prints the message on the console


Refer this [blog](http://krishnasblog.com/) for more details on the above flow.

Feel free to modify this code and use it for your need. 
