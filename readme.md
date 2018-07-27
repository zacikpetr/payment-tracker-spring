# Payment Tracker

This application can track Currency-Value mappings and store it into in-memory database.

>Note: This project is only technological extension demo of original project `payment-tracker`, it is not finished and should not be considered as solution.


## Prerequisities
Java 8 is needed to build and run application. Gradle is not required as it is provided embedded in application as wrapper.

## Build
Application can be built using Gradle wrapper. Run this command under project root directory.

```
./gradlew clean build
```
or
```
gradlew.bat clean build
```

This step is responsible for application, building, testing and packaging.

## Run

### Self-executable JAR
Application is packaged into self-executable JAR file. 
To run application, first navigate to `libs` directory:


```
cd build/libs
```

>Note: Application must be built first in order to see all directories.

Execute application by calling:

```
java -jar payment-tracker-spring-0.0.1-SNAPSHOT.jar 
```

## Usage

```
java -jar payment-tracker-spring-0.0.1-SNAPSHOT.jar 
```

This command starts Spring Boot application at `http://localhost:8080`

- To see all records, open browser to show URL `http://localhost:8080/payment/stream`. Records are represented in form of json stream and updated every second.
- In order to create or modify records, send **POST** request to `http://localhost:8080/payment` in form of simple json map. You can create or modify multiple records at once.


Example of getting records in browser after 5 seconds (five times updated):
```
{"USD":3000}
{"HIT":-2900,"USD":3000}
{"HIT":-2900,"USD":3000,"HIP":1}
{"HIT":-2900,"HAP":2,"USD":3000,"HIP":1}
{"HIT":-2900,"HAP":2,"USD":3000,"HOP":3,"HIP":1}

```

Example of creating and editing multiple items at once in pseudo-code:
```
POST localhost:8080/payment

# Body:
{"HOP":3, "KOP": 5,"MOP": 5}
```