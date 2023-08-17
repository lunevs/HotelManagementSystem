# HotelManagementSystem

Project Description: Backend Development of a Hotel Management Service 
This document provides an overview of an exam project focused on backend development for backend developers. The goal of the project is to develop the backend part of a Hotel Management service, utilizing various technologies and frameworks such as Spring Boot, Spring Security, HSQLDB, Mockito, Swagger, AOP, Lombok, Hibernate, JPA, and SLF4J. 

Objective: 
The main objective of this project is to create a robust backend microservice for hotel operations with (optional) a frontend component. The service will expose a RESTful API to perform various hotel operations. 

Technologies: 
Spring Boot: Used for application development and streamlined setup and deployment. 
HSQLDB: Utilized as the database for storing application data.
Mockito: Utilized for creating mock objects and performing unit testing. 
Swagger: Used for documenting the API and generating interactive documentation.
AOP (Aspect-Oriented Programming): Implemented for cross-cutting concerns such as logging.
Hibernate and JPA: Utilized for database interaction and object-relational mapping.
SLF4J: Employed for logging and event registration within the application. 
Lombok: Simplify using annotations.

Functionality: 
The service will implement CRUD operations for various entities related to hotel operations. This includes: 
Location Management: Creating, updating, and deleting locations (hotels). Bookings: Performing guest bookings. Room specifications: Allows add or remove amenities to rooms.
Calendar Management: Managing and balancing hotels capacity.

 
Security: 
The application will implement security measures to restrict access to authorized users. This will include requiring an "admin" username for accessing sensitive operations like CRUD functionality and performing POST, DELETE, and PUT requests. 

Data Initialization: 
To facilitate testing and development, a class named "InitDataBase" will be created programmatically to add sample data for accounts, transactions, and customer profiles. 
The successful completion of this project will result in a fully functional backend service for hotel management operations. 

