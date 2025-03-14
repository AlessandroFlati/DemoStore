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
