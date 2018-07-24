# Call center project

This project resolves the problem of how dispatch concurrent calls to a different kid of employees.

This project consists of two modules known as core and service.


## Core
The core contains the main logic to solve the problem

### Compile

To compile the core you have to execute the following command:

```
mvn clean install
```

### Solution
The project use a concurrent queue to enqueue the calls and a schedule task consume
calls from the queue, the task which finally dispatch the calls to the employees based on the priority
and the availability of each employee.

The task has an interface for a repository which defines how the employees list is retrieved
and how update the employee information if required.

The solution support more than 10 threads thanks to the queue and in the case of that all
the employees are in other calls, the call will be returned to the queue, but if there is no
employees in the repository the application throws an exception.

## Service
The service has a main method which is a example of how use the dispatcher concurrently,
the service has an implementation of the IEmployeeRepository which use a database,
the queries to the data base are executed by jooq and the scripts to create the tables of
the database are executed by flyway.

To test the main service is necessary to modify the application.properties o inject
one in the command line, the file has the information related to the database connection.

### Compile
To compile the service you have to compile the core first follow the instruction
in the previous section.

when you have the core compiled you need to run this command in order to execute all
the migrations

```
mvn clean initialize flyway:migrate install
```

If the compilation is successful run the main with the following command

```
mvn spring-boot:run
```

## Improvements

Due to the duration to develop this project, the project has some improvements:
1. Logging the exceptions with a proper logger library.
1. Manage when the application stop in the middle of the process.
1. Expand the test cases to complete a 100% coverage.
1. Create integration test.
1. In case on high availability use a external queue like a message broker.
1. Use a cache for the employees to maintain the response time low.