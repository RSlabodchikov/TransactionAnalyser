# Transaction Analyser

----

Transaction analyser is simplified financial transaction analysis system.
The goal of the system is to display statistic information about processed financial transactions.

----

## Build
Build jar using command:
```
mvn clean package
```
Open with java created transaction-analyser.jar file with 4 required arguments: 
 1) path to file with data
 2) merchant
 3) date from
 4) date to
### Alternative way of testing
To test analyser you can specify properties in file src/test/resources/test.properties 
and change file with location src/test/resources/data/example.csv or create new file and specify path to it in properties.
After this manipulations you can run test(TransactionAnalyserTest.analyserTest by using this command:
```
 mvn clean test
```