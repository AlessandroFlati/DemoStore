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
- Apache Maven / Gradle

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

### Spring Fundamentals

#### Inversion of Control (IoC) and Dependency Injection (DI)

IoC is a design principle in which the control flow of an application is inverted compared to traditional procedural programming. Rather than your code controlling the creation and lifecycle of objects, the Spring container takes charge, instantiating and managing beans as needed. This shift simplifies the management of dependencies and promotes a cleaner separation of concerns.

DI is a technique where objects receive their dependencies from an external source rather than creating them internally. In Spring, DI can be implemented via:
- **Constructor Injection**: Dependencies are provided through a class constructor.
- **Setter Injection**: Dependencies are assigned through setter methods.
- **Field Injection**: Dependencies are directly assigned to fields (less preferred in modern practices due to testing difficulties).

For example, instead of a controller instantiating its own service, the service is injected by the framework, thus making the code more modular and testable.

#### Spring Container and Component Scanning

The Spring container automatically detects classes annotated with stereotypes (such as `@Component`, `@Service`, `@Repository`, or `@Controller`) and registers them as **beans**. When the container instantiates a bean, it looks for dependencies annotated with `@Autowired` and injects the appropriate beans based on the type or qualifier. This automated process significantly reduces boilerplate code and improves maintainability.

#### Configuring Beans and Understanding Bean Scopes

In Spring, beans are objects managed by the IoC container. You can define beans in several ways:
- **XML Configuration**: Traditionally, beans were declared in XML files. Although less common today, this method remains useful for legacy projects.
- **Java Configuration**: Beans can be defined using Java classes annotated with `@Configuration`. Use `@Bean` annotated methods to create and return bean instances.
- **Annotation-based Configuration**: Beans can be defined using annotations like `@Component`, `@Service`, `@Repository`, and `@Controller` directly on classes.

There are several Bean Scopes available in Spring:
- **Singleton**: The default scope in Spring. Only one instance of the bean is created per Spring IoC container. 
  - Use case: Services and repositories that maintain shared state or require resource efficiency.
- **Prototype**: A new instance is created each time the bean is requested. 
  - Use case: Stateful beans that should not be shared.
- **Web-aware scopes**:
  - **Request**: A new instance is created for each HTTP request.
    - Use case: Controllers that require request-specific instances.
  - **Session**: A new instance is created for each HTTP session.
    - Use case: Beans that need to be session-specific.
  - **Application**: A single instance is created for the entire web application.
    - Use case: Beans that should be shared across the application.
  - **WebSocket**: A new instance is created for each WebSocket connection.
    - Use case: Beans that require WebSocket-specific instances.

### Creating a Spring Boot Application

#### Introduction to Spring Boot

Spring Boot is designed to streamline the process of setting up and running Spring applications by providing sensible defaults and a host of auto-configurations. This reduces the need for extensive manual configuration.

Auto-configuration intelligently configures components based on the dependencies present on the classpath, making it easier to set up services like web servers, data sources, and security.

#### Spring Boot application properties

Spring Boot applications can be configured using `application.properties` or `application.yml` files. These files contain key-value pairs that define properties for the application. For example, you can set the server port, database connection details, logging levels, etc.

Some examples of useful properties you can set in `application.properties`:
- `server.port=8080`: Sets the port on which the embedded server runs.
- `spring.application.name=DemoStore`: Sets the application name.
- `spring.datasource.url=jdbc:mysql://localhost:3306/mydb`: Sets the database URL.
- `spring.datasource.username=myuser`: Sets the database username.
- `spring.datasource.password=mypassword`: Sets the database password.
- `logging.level.org.springframework=DEBUG`: Sets the logging level for Spring classes.
- `spring.jpa.hibernate.ddl-auto=update`: Configures Hibernate to automatically update the database schema.
- `spring.jpa.show-sql=true`: Enables SQL logging. 
- `spring.jpa.properties.hibernate.format_sql=true`: Formats the SQL queries for better readability.
- `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect`: Sets the Hibernate dialect for MySQL.
- `spring.jpa.properties.hibernate.hbm2ddl.auto=update`: Automatically updates the database schema based on the entity classes.
- `spring.jpa.properties.hibernate.show_sql=true`: Shows SQL queries in the console.

Even if you don't totally understand these properties yet, it's important to know where they are defined and how they can be used to configure your application. We'll cover these in more detail in later sections.

#### Spring Boot Starters

Spring Boot starters are a set of convenient dependency descriptors that you can include in your application. You can add starters to your project to get a pre-configured set of dependencies that work well together. For example, the `spring-boot-starter-web` starter includes all the dependencies needed to build a web application.

You can find a list of available starters on the [Spring Initializr](https://start.spring.io/) page when creating a new project.

If you're using Maven, you can add a starter to your `pom.xml` file like this:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

or if you're using Gradle, add it to your `build.gradle` file like this:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```
under the `dependencies` block.

#### Dependency Management with Spring Boot

Spring Boot provides a parent POM that centralizes dependency management. This means you don’t have to specify version numbers for many dependencies as the parent POM handles compatibility.
This approach reduces configuration complexity and prevents version conflicts. If needed, you can override default versions by specifying them explicitly in your project’s build file.

#### Build Tools integration

Spring Boot is compatible with popular build tools like Maven and Gradle. You can use these tools to manage dependencies, build your project, and run tasks like testing and packaging.

- **Maven**: The `pom.xml` file of a Spring Boot project typically extends the Spring Boot parent, which brings in the dependency management. This makes it easy to add starters without worrying about versions.
- **Gradle**: Similarly, with Gradle, using the Spring Boot plugin simplifies dependency management and auto-configuration. You can add starters as dependencies in the `build.gradle` file as described earlier.

