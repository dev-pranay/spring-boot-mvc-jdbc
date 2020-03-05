# SpringBootMvcJdbc

This project is the demonstration of how to implement MVC pattern using Spring Boot and how to work with JDBC using Spring Boot.
This project is developed using Spring Boot 2.2.5 with dependencies 'spring-boot-starter-data-jdbc', 'spring-boot-starter-web',
'spring-boot-starter-data-jpa', 'lombok', 'jstl', 'tomcat-embed-jasper' and 'h2'.

## Exceptions

Exception handling is done in 'Global Exception Handler' class using '@ControllerAdvice'.

## Database

In memory database, H2, is used for storing and retrieving the Employee dummy data.
For checking the data in database, '/h2-console' is used where you can login to your database
and can check the data using SQL queries.
The H2 database console can be accessed using 'http://localhost:APP_PORT/h2-console/'.

## Unit Testing

Unit testing is done using JUNIT 5 and MOCKITO. Also tested using postman client.