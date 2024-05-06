The Book Service is a pivotal component tailored to streamline CRUD (Create, Read, Update, Delete) operations concerning books within an expansive system. It synergizes with the Borrow Service to meticulously manage the availability statuses of books.

Components:
Book Service: This segment orchestrates CRUD operations pertaining to books.
Borrow Service: It meticulously oversees the borrowing status of books and establishes communication channels with the Book Service to synchronize updates.
Technologies Employed:
Java
Spring Boot
OpenFeign: Facilitates seamless communication between services.
Lombok
H2 Database
Netflix Eureka Server: Primarily for service registration and monitoring.
Microservices Architecture
Functionality:
CRUD Operations:

Create: Allows the seamless addition of new books to the system.
Read: Facilitates the retrieval of comprehensive book details, including their availability status.
Update: Empowers users to modify both book details and availability status.
Delete: Grants the authority to remove books from the system.
Communication with Borrow Service:

It leverages the OpenFeign client to ensure smooth communication with the Borrow Service.
The availability status of books undergoes real-time updates in response to borrow requests and returns.
How It Works:
Book Service:

It exposes REST endpoints catering to CRUD operations on books.
Maintains a robust database or storage mechanism to persistently store book records.
Upon receiving requests, it internally processes CRUD operations, thereby updating the status of books in the database.
Utilizes the OpenFeign client to establish seamless communication with the Borrow Service, ensuring timely updates regarding book availability.
Borrow Service:

Primarily responsible for managing borrowing transactions, including both checkouts and returns.
Establishes communication with the Book Service to diligently update the book availability status upon book borrowings or returns.
Usage:
Book Service Endpoints:

/books:
GET: Retrieve all books.
POST: Add a new book.
/books/{id}:
GET: Retrieve details of a specific book.
PUT: Update details of a specific book.
DELETE: Remove a book from the system.
Borrow Service Endpoints:

/borrow/books:
GET: Retrieve all borrowed books.
/borrow/{bookId}/{status}:
PUT: Update the availability status of a book. For example: http://192.168.2.78:56973/borrow/5/Borrowed.
Interacting with Borrow Service:
The Borrow Service meticulously communicates with the Book Service to ensure accurate updates regarding the availability status of books.
Dynamic updates concerning book availability status are promptly executed in response to borrow requests.
Setup:
Clone the repository housing the Book Service code.
Ensure the presence of Java and Maven.
Configure the application properties to define the connection specifics for both the database and the Borrow Service.
Proceed to build and execute the Book Service application.
Ensure the seamless operation and accessibility of the Borrow Service.
Validate the endpoints and functionality to ensure seamless operations.















***********how I creted this project*************

Create a new maven project in intellij.


File -> New -> Project -> (Select New Project) and choose maven build system.

Part 1: Setting Up Eureka Service

Create a EurekaService Module that will act as a service registry for your microservices.

Create Eureka Service Project:
Right click on project  New -> Module.
Choose Spring Initializr and set the Project SDK to your JDK version. (Choose Java as language and Maven as type)
Click Next, enter EurekaService as the project name, and choose a suitable location for your project.
Click Next, and from the dependencies section, choose:
Spring Boot Actuator
Spring Cloud Eureka Server
Click Finish to create the project.
Configure application.properties:
Navigate to src/main/resources/application.properties.
Add the following properties:

server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false



Run EurekaService:

Create eureka server by using
@EnableEurekaServer in EurekaServiceApplication Class

Right-click on the project and choose Run 'EurekaService'.
Ensure the service is up and running on http://localhost:8761.








Part 2: Setting Up Book Service Module

Create a MovieService module that will register with Eureka and provide movie-related operations.

Create Book Service Project:
Follow the same steps as in Part 1 to create a new project named BookService.
Choose the following dependencies:
Spring Boot Actuator
Spring Data JPA
Spring Web
Spring Cloud Eureka Client
H2 Database
Lombok
Eureka discovery Client
Spring Boot DevTools


Configure application.properties:
Navigate to src/main/resources/application.properties.
Add the following properties:


server.port=0
spring.application.name=movie-service
info.app.name="movie-service"
info.app.description="Movie Service Application"
info.app.version="1.0.0"
eureka.instance.prefer-ip-address=true
eureka.client.fetch-registry=true
eureka.client.register-with-eureka=true
eureka.client.region=default
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.instance-id=movie-service:${spring.application.instance_id:${random.value}}
eureka.client.registry-fetch-interval-seconds=5
management.endpoints.web.exposure.include=info,health ,shutdown
management.endpoint.shutdown.enabled=true
management.info.env.enabled=true

#create a new folder h2db
spring.datasource.url=jdbc:h2:file:C:/h2db/my_data_file
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
# Enable H2 Console - http://localhost:8080/h2-console
# Console path - http://localhost:8080/h2
spring.h2.console.path=/h2
spring.h2.console.settings.web-allow-others=true




Create Book Model and Repository:
Create a new Java class Book in the model package and annotate it with @Entity.
Define properties for the Movie entity (like id, title, etc.) and annotate them with JPA annotations.
Create an interface BookRepository in the repository package.

[//]: # (Annotate BookRepository with @RepositoryRestResource and extend JpaRepository.)
[//]: # (Create Seeder to Populate Book:)
[//]: # (Create a new class DbSeeder and use it to populate the BookRepository with some initial movies.)
Run BookService:
Right-click on the project and choose Run 'BookService'.
Ensure it registers with Eureka and is accessible.
Add a rest endpoint (controller) to fetch movies.


Part 3: Setting Up Borrowing Service Module

Create a UserService module that will register with Eureka and consume MovieService to fetch movies

Create User Service Project:
Follow the same steps as in Part 1 to create a new project named UserService.
Choose the following dependencies:
Spring Boot Actuator
Spring Web
Spring Cloud Eureka Client
OpenFeign
Spring Boot DevTools


Configure application.properties similar to MovieService.
Implement Fetching movie functionality using OpenFeign
Create Feign client to communicate with MovieService.
Configure Feign client by adding @EnableFeignClients to the application
Implement endpoints to fetch all movies
Implement a rest service to fetch all movies (just to test that the feign client is working. In reality this is unnecessary.)
Run ReviewService:


Borrowing Service: Handles the borrowing operations (borrow and return books).
Open Feign Client:
Use Open Feign in the Borrowing Service for communicating with the Book Service to update the status of the books.

Create a Borrowing Service module that will register with Eureka and consume BookService to fetch books

Configure application.properties similar to BookService.
Implement Fetching book functionality using OpenFeign
Create Feign client to communicate with BookService.
Configure Feign client by adding @EnableFeignClients to the application
Implement endpoints to fetch all books
Implement a rest service to fetch all books(just to test that the feign client is working. In reality this is unnecessary.)





Right-click on the project and choose Run UserService.
Ensure it registers with Eureka and is accessible.
Testing Services:
Use a REST client like Postman or use your browser to interact with your services.
Ensure MovieService and ReviewService are communicating properly and Eureka dashboard shows all services.