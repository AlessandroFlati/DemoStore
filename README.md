# DemoStore

This project is meant to provide a _hands-on_ experience with the classical frameworks Spring, Spring Boot and Hibernate.

This README.md file will guide you through the project explaining in detail the main concepts and features of the frameworks used.

## Table of Contents

- Introduction and Setup
- Spring fundamentals
- Spring Boot
- Hibernate and JPA introduction
- API Rest with Spring MVC
- Hibernate integration with Spring
- Spring Security
- Spring advanced aspects
- Test & Quality
- Monitoring, Logging and Deployment
- Microservices integration
- Final considerations

## Introduction and Setup

### Environment Setup and Initial Project Structure

#### Development Tools Setup

- Java Development Kit (JDK) 8
- Integrated Development Environment (IDE) - Eclipse, IntelliJ IDEA, NetBeans
- Apache Maven
- Gradle

#### Use Spring Initializer to create a new project

- Go to [Spring Initializr](https://start.spring.io/)
- Choose the appropriate project settings (e.g., Maven Project, Java, Spring Boot version).
- Add a couple of dependencies (for now, “Spring Web” is sufficient).
- Generate and download the project, then import it into your IDE.

At the end of this step, you should have a project structure similar to the first commit.

#### Project Structure

- `src/main/java` - Java source files
  - `com.demo.store` - Main package
    - `DemoStoreApplication.java` - Main class
- `src/main/resources` - Resources such as static files and configuration files
  - `application.properties` - Application properties

#### Creating the Package Structure

- `com.demo.store.controller` - Controllers
- `com.demo.store.model` - Models
- `com.demo.store.repository` - Repositories
- `com.demo.store.service` - Services

This organization is a common practice in Spring projects, but it is not mandatory. You can organize your project as you see fit.

That said, it's important to keep the project organized and easy to understand - and this is a common practice to help you logically separate functionalities and make the project more maintainable as new features are added.

#### Refactoring the Main Application Class

Ensure that your main application class (e.g., `DemoStoreApplication.java`) is placed in a root package (like com.demostore) so that component scanning picks up all the sub-packages automatically.
```
src/main/java/
└── demo
    └── store
        ├── StoreApplication.java
        ├── controller
        ├── model
        ├── repository
        └── service
```

#### Implementing a basic Service and Controller

- Create a simple service class in the service package. This service should have a method that returns a greeting message.
- Create a controller class in the controller package. This controller should have a method that calls the service method and returns the greeting message.

#### Running the Application

- Run the main application class (`DemoStoreApplication.java`) as a Java application.
- Access the URL `http://localhost:8080/api/hello` in your browser.