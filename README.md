# Random String Generator
This project generates files with random unique strings in a given specification.
The user communicates with the app by REST endpoints. Users define how long the strings will be (min and max length),
specify possible chars of string (from what characters the string is going to created) and how many unique strings he wants.
The application saves valid requests sent by POST method to MySQL database.
When the user performs GET request to the same address application runs asynchronously for each task and generates files.

## Technologies used for this project
- Java 17
- Maven
- Spring Boot 2.7.5
- Spring Data JPA (Hibernate)
- MySQL 8 
- H2 Database (test scope In-Memory database)
- JUnit 5
- Mockito
- AssertJ

## How to run
### Requirements and configuration
- MySQL 8 database
- Postman 

In MySQL create the database with the name "string_generator", default name and password are set to "root" and "password" if you would like to change it, go to:
*src/main/resources/application.properties* and change these two fields: *spring.datasource.username* and *spring.datasource.password*. 

Now using Postman you could send POST or GET request to controll the application.

### Usage example
Request:
Method: *POST* url: *http://localhost:8080//api/v1/generator*

Request Body:
```json
{
    "minLength": 10,
    "maxLength": 10,
    "numberOfStrings":10,
    "characters": ["z","o","l","a","o","x"]
}
```

Response Header: Status 201 Created

Response Body:
```json
{
    "characters": [
        "a",
        "x",
        "z",
        "l",
        "o",
        "p",
        "r"
    ],
    "minLength": 10,
    "maxLength": 100,
    "numberOfStrings": 1000
}
```
___
Request:
Method: *GET* url: *http://localhost:8080//api/v1/generator*

Response Header: Status 200 OK

Response Body:
*Files created in 'src/main/resources/generatedfiles*

___
Request:
Method: *GET* url: *http://localhost:8080//api/v1/generator/thread*

Response Header: Status 200 OK

Response Body:
*Currently running jobs: 2*

When you already have multiple tasks added to your database you could perform a GET request to generate files and then during the program execution,
you could perform another GET request to see how many threads are currently running.
