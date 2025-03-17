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

### Introduction to Hibernate and JPA

#### Object-Relational Mapping (ORM)

ORM bridges the gap between object-oriented programming and relational databases. Instead of writing SQL statements for each operation, you can work with Java objects and let the framework handle the translation to SQL.

#### JPA (Java Persistence API) basics

JPA is a Java specification for ORM that defines a set of interfaces and annotations for mapping Java objects to database tables. It provides a standard way to interact with databases, making it easier to switch between different ORM frameworks.

The main components of JPA are:
- **Entity**: Marking a class with `@Entity` tells Spring Boot and Hibernate that the class should be persisted to the database. The `@Table` annotation allows you to specify the corresponding table name.
- **Primary Key**: Use the `@Id` annotation to mark a field as the primary key. You can also specify the generation strategy using `@GeneratedValue`.
- **Columns**: Use the `@Column` annotation to map fields to database columns. You can specify column names, lengths, and other properties.

That said, other annotations and configurations are available to customize the mapping between entities and database tables:
- **Relationships**: JPA supports various types of relationships between entities, such as `@OneToOne`, `@OneToMany`, `@ManyToOne`, and `@ManyToMany`.
- **Repositories**: JPA repositories provide a set of CRUD operations for working with entities. You can create custom queries using method names or JPQL (Java Persistence Query Language).
- **Transactions**: JPA transactions ensure that database operations are atomic and consistent. Use the `@Transactional` annotation to mark methods that should run within a transaction.
- **JPQL**: JPQL is a query language similar to SQL but operates on entities rather than tables. It allows you to write queries using entity names and fields.
- **Criteria API**: The Criteria API provides a type-safe way to build queries programmatically. It is useful for dynamic queries and complex conditions.
- **Auditing**: JPA supports auditing features like `@CreatedDate`, `@LastModifiedDate`, and `@CreatedBy` to track entity creation and modification.
- **Caching**: JPA supports caching to improve performance. You can configure first-level and second-level caches to reduce database access.
- **Validation**: JPA entities can be validated using Bean Validation annotations like `@NotNull`, `@Size`, and `@Pattern`.
- **Inheritance**: JPA supports inheritance strategies like `@Inheritance`, `@DiscriminatorColumn`, and `@DiscriminatorValue` for mapping inheritance hierarchies.
- **Locking**: JPA provides optimistic and pessimistic locking mechanisms to handle concurrent access to entities.
- **Listeners**: JPA entity listeners allow you to execute custom logic before or after entity lifecycle events like persisting, updating, or deleting.
- **Embeddables**: JPA supports embeddable objects that can be reused across multiple entities. Use the `@Embeddable` and `@Embedded` annotations to map embeddable objects.
- **Projections**: JPA projections allow you to fetch only a subset of entity attributes in a query result. This can improve performance by reducing data transfer.
- **Specifications**: JPA specifications provide a way to define complex query predicates that can be reused across queries. Specifications can be combined to build dynamic queries.
- **Batch Processing**: JPA supports batch processing for bulk inserts, updates, and deletes. Use the `@BatchSize` annotation to optimize database interactions.
- **Custom Types**: JPA allows you to define custom types for mapping complex data structures to database columns. Use the `@TypeDef` annotation to register custom types.
- **Schema Generation**: JPA can automatically generate database schemas based on entity mappings. You can configure schema generation options using properties in `application.properties`.
- **Native Queries**: JPA supports native SQL queries for executing database-specific operations. Use the `@Query` annotation with the `nativeQuery` attribute set to `true`.
- **Named Queries**: JPA allows you to define named queries using the `@NamedQuery` annotation. Named queries can be referenced by name in repository methods.
- **Entity Graphs**: JPA entity graphs allow you to define fetch plans for entity associations. Use entity graphs to optimize data retrieval and avoid N+1 query issues.

Most of these features may seem overwhelming at first, but you'll gradually become familiar with them as you work on real-world projects. For now, focus on understanding the basic concepts and building a solid foundation.

#### Hibernate as a JPA provider

Hibernate is a popular JPA provider that implements the JPA specification. It provides additional features and optimizations beyond the standard JPA API. Spring Boot integrates seamlessly with Hibernate, allowing you to leverage its capabilities without additional configuration.

For example, Spring Boot autoconfigures JPA when the `spring-boot-starter-data-jpa` dependency is added. By default, if you include an H2 dependency, Spring Boot will set up an in-memory database for you, making it easy to test and develop without requiring an external database. Hibernate manages the persistence context, handling tasks such as caching and lazy loading.

#### Configuring the DataSource

In `application.properties` (or `application.yml`), you can configure the database connection details using properties like `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password`. Spring Boot automatically creates a DataSource bean based on these properties.

For example, to configure a MySQL database, you might set the following properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=myuser
spring.datasource.password=mypassword
spring.jpa.hibernate.ddl-auto=update
```

The `spring.jpa.hibernate.ddl-auto` property is used to define how Hibernate handles schema creation and updates. Using update ensures that your schema evolves with your entity changes during development.

#### Creating JPA Entities

To create JPA entities, you need to define classes annotated with `@Entity`. These classes represent database tables, and their fields map to table columns. You can also define relationships between entities using annotations like `@OneToOne`, `@OneToMany`, `@ManyToOne`, and `@ManyToMany`.

#### Relationships between Entities

JPA supports various types of relationships between entities:
- **One-to-One**: A `@OneToOne` mapping links two entities, each containing a single reference to the other. For example, a User entity might have a `@OneToOne` relationship with a `UserProfile` entity.
  ```java
  @OneToOne
  @JoinColumn(name = "profile_id")  // The foreign key column
  private UserProfile profile;
  ```
- **One-to-Many and Many-to-One**: In a one-to-many relationship, one entity is the parent, and it has multiple children. For instance, `Category` (parent) and `Product` (child). A product belongs to exactly one category, but a category can contain many products.
  ```java
  @Entity
  public class Category {
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
  }
  
  @Entity
  public class Product {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
  }
  ```
  `mappedBy = "category"` indicates that `Product` owns the relationship to `Category` (because it contains the `category_id` foreign key column).
- **Many-to-Many**: In a many-to-many scenario, both entities can have multiple references to each other. For instance, a `Product` can be linked to many `Tag` entities, and each `Tag` can apply to many products.
  ```java
  @ManyToMany
  @JoinTable(
    name = "product_tag",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<Tag> tags = new HashSet<>();
  ```
  The `@JoinTable` annotation specifies the join table name and the foreign key columns for each entity.

#### Cascade and Orphan Removal

The `cascade` attribute in JPA relationships defines how operations like persist, merge, remove, and refresh should propagate from parent entities to child entities. For example, if you set `cascade = CascadeType.ALL`, operations on the parent entity will cascade to the child entity.

The `orphanRemoval` attribute is used to specify whether child entities should be removed when they are no longer referenced by the parent entity. Setting `orphanRemoval = true` ensures that orphaned child entities are deleted from the database.

#### Lazy vs Eager Loading

JPA supports two types of loading strategies for relationships: lazy loading and eager loading.
- **Lazy Loading** (`FetchType.LAZY`): By default, JPA uses lazy loading for relationships. This means that related entities are loaded only when accessed. Lazy loading can help improve performance by fetching data on demand.
- **Eager Loading** (`FetchType.EAGER`): In contrast, eager loading fetches related entities immediately when the parent entity is loaded. While this can reduce the number of queries, it may lead to performance issues if the fetched data is not always needed.

You can specify the loading strategy using the `fetch` attribute in the relationship annotations. For example:
```java
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Product> products = new ArrayList<>();
```

### API Rest with Spring MVC

#### Defining a REST Controller

- Mark the class with `@RestController` to indicate that it serves JSON (or other media) rather than rendering views.
- Use `@RequestMapping` at the class level to define a common path (e.g., `/api/products`).
- For each endpoint, use the appropriate annotation based on the HTTP method:
  - `@GetMapping` for read operations
  - `@PostMapping` for create
  - `@PutMapping` or `@PatchMapping` for update
  - `@DeleteMapping` for delete

Typically, you’ll inject a service (e.g., `ProductService`) that encapsulates business logic and interacts with the repository layer.

#### Request and Response Bodies

- In case of Path Variables, use `@PathVariable` to extract values from the URI (e.g., `/api/products/{id}`).
- For Query Parameters, use `@RequestParam` to extract values from the query string (e.g., `/api/products?category=electronics`). You can specify default values and required parameters.
- Use `@RequestBody` to bind the request body to a method parameter. This is useful for POST and PUT requests where the client sends data in the request body. Spring automatically converts JSON to Java objects using Jackson.
- If needed, use `ResponseEntity` to customize the response status, headers, and body. This allows you to return different HTTP status codes and headers based on the outcome of the operation.
- For complex responses, consider using DTOs (Data Transfer Objects) to represent the data sent to and from the client. This helps decouple the internal domain model from the external API.

#### Exception Handling

- You can simply throw custom exceptions or use Spring's built-in exceptions (e.g., `ResponseEntityException`) to handle errors and return appropriate HTTP status codes.
- Use `@ControllerAdvice` to define global exception handling for all controllers. This allows you to centralize exception handling logic and avoid duplicating code. More on this later

#### Handling Updates, Deletions, and Basic Error Handling & Validation

###### PUT vs PATCH
Use `PUT` to update an entire resource and `PATCH` to update only specific fields.
When implementing updates, ensure that:
- The endpoint receives the resource ID (tipically as a path variable).
- The JSON payload is converted into an entity or DTO via `@RequestBody`.
- Appropriate service methods are called to update the resource.

###### DELETE
Use `DELETE` to remove a resource. The endpoint should receive the resource ID as a path variable and call the corresponding service method to delete the resource.
Ensure the service layer safely removes the entity, handling any potential issues (such as dependent records or integrity constraints).

Return a status like `204 No Content` to indicate a successful deletion.

#### Data validation

Apply constraint on entity fields, or, preferably, on DTOs to ensure that the data is valid before processing it. Use annotations like `@NotNull`, `@Size`, `@Pattern`, etc., to enforce constraints.

In particular, use `@Valid` in the controller method to trigger validation on the request body. If validation fails, Spring will automatically return a `400 Bad Request` response with details about the validation errors.
```java
@PostMapping
public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    // Service call and return response
}
```

#### Basic Error Handling

For cases where a resource is not found, you might throw a custom exception (e.g., `ResourceNotFoundException`) which can then be translated into an appropriate HTTP status code (e.g., `404 Not Found`).

Use Spring's `ResponseStatusException` for quick error responses. 

Optionally, introduce a global exception handler using `@ControllerAdvice` to centralize exception handling and customize error messages.

#### Controller Advice

`@ControllerAdvice` is an annotation used to define global exception handlers in Spring MVC. It allows you to centralize exception handling logic and apply it across multiple controllers.

- **Centralized Exception Handling**: With `@ControllerAdvice`, you can create methods annotated with @ExceptionHandler that catch specific exceptions (or a group of exceptions) from all controllers, returning custom responses (e.g., specific HTTP status codes, error messages).
- **Global Data Binding**: It also supports global model attributes and data binding settings, which can be useful for pre-populating common data needed by multiple controllers.
- **Cleaner Controllers**: By moving exception handling logic to a separate class, you can keep your controllers clean and focused on request handling, improving code readability and maintainability.

Imagine a scenario where any `ResourceNotFoundException` thrown anywhere in your controllers should result in a 404 Not Found response. You could define a global exception handler like this:
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

You could even create a custom `ErrorResponse` class to standardize error responses across your application:
```java
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters omitted for brevity
}
```
and a custom `ResourceNotFoundException` class:
```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```
so that you can define your `@ControllerAdvice` class like this:
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException globally
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors or other exceptions as needed
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```
This way, you can define custom error responses for different types of exceptions and ensure a consistent error handling approach across your application. In the controllers, you can simply throw a `ResourceNotFoundException` when needed, and the global exception handler will take care of returning the appropriate response.

### Repository and Service Layers Integration

#### Repository Layer

The repository layer is responsible for interacting with the database. It typically consists of interfaces that extend `JpaRepository` or `CrudRepository`.

Spring Data JPA provides built-in methods for common CRUD operations, eliminating the need to write boilerplate code. For example:
- `save(entity)`: Saves an entity to the database.
- `findById(id)`: Retrieves an entity by its ID.
- `findAll()`: Retrieves all entities.
- `delete(entity)`: Deletes an entity.
- `deleteById(id)`: Deletes an entity by its ID.
- `existsById(id)`: Checks if an entity exists by its ID.
- `count()`: Returns the total number of entities.

The implementation is straightforward:
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional query methods can be defined here if needed.
}
```

#### Service Layer

The service layer contains business logic and orchestrates interactions between the controller and repository layers. It encapsulates complex operations and ensures that the business rules are enforced.

We already saw how to create a simple service class that returns a greeting message. In a real-world application, the service layer would interact with the repository layer to perform CRUD operations on entities, like:
```java
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
```

#### Transaction Management

Although basic CRUD operations are handled by Spring Data, you might need to annotate service methods with `@Transactional` if the operations involve multiple steps that must either complete successfully or roll back together.

The `@Transactional` annotation ensures that the annotated method runs within a transaction. If an exception occurs, the transaction is rolled back, and any changes made during the transaction are discarded.

Basically, the service layer abstracts the data access logic from the controller, allowing you to focus on business logic and ensuring that the application follows the separation of concerns principle.

#### Propagation and Isolation Levels

- **Propagation**: Defines how transactions should propagate when a method is called within an existing transaction. Common propagation levels include:
  - `REQUIRED`: If a transaction exists, use it; otherwise, create a new one.
  - `REQUIRES_NEW`: Always create a new transaction.
- **Isolation**: Defines the degree to which a transaction is isolated from the effects of other transactions. Common isolation levels include:
  - `READ_UNCOMMITTED`: Allows dirty reads, non-repeatable reads, and phantom reads.
  - `READ_COMMITTED`: Prevents dirty reads but allows non-repeatable reads and phantom reads.
  - `REPEATABLE_READ`: Prevents dirty reads and non-repeatable reads but allows phantom reads.
  - `SERIALIZABLE`: Prevents dirty reads, non-repeatable reads, and phantom reads.
  - `DEFAULT`: Uses the default isolation level of the underlying database.

If you're not familiar with dirty read, non-repeatable read, or phantom read, here's a brief explanation:
- **Dirty Read**: Occurs when one transaction reads data that has been modified by another transaction but not yet committed. If the modifying transaction rolls back, the reading transaction has read invalid data.
- **Non-Repeatable Read**: Occurs when a transaction reads the same row multiple times and gets different results due to another transaction updating the row in between reads.
- **Phantom Read**: Occurs when a transaction reads a set of rows multiple times and gets different results due to another transaction inserting or deleting rows in between reads.

Please note that accessing lazy-loaded associations outside of a transactional context may result in a `LazyInitializationException`. To avoid this, you can:
- Eagerly fetch the association using `FetchType.EAGER`.
- Use `JOIN FETCH` in JPQL queries to fetch the association eagerly.
- Use DTO projections to fetch only the required data.
- Use `@Transactional` to ensure that the association is fetched within a transactional context.

### Spring Security

#### Introduction to Spring Security

Spring Security is a powerful and customizable authentication and access control framework for Java applications. It provides comprehensive security services for Java EE-based enterprise software applications.

Spring Security can be used to:
- Secure web applications using various authentication mechanisms (e.g., form-based, HTTP Basic, OAuth).
- Implement access control for different parts of the application.
- Protect against common security vulnerabilities like cross-site scripting (XSS), cross-site request forgery (CSRF), and session fixation.
- Integrate with external identity providers like LDAP, Active Directory, and OAuth providers.
- Implement single sign-on (SSO) and federated authentication.
- Secure REST APIs using token-based authentication (e.g., JWT).
- Customize security configurations based on roles, permissions, and user attributes.
- Implement password hashing, encryption, and other security best practices.
- Monitor and audit security events using logging and monitoring tools.

#### Setting up Spring Security

To add Spring Security to your project, you can include the `spring-boot-starter-security` dependency in your `pom.xml` or `build.gradle` file. Spring Boot autoconfigures security settings based on the presence of this dependency.

#### Basic Authentication

Spring Security provides several authentication mechanisms out of the box. One of the simplest is basic authentication, where the user provides a username and password in the request headers.

To enable basic authentication, you can configure it in the `application.properties` file:
```properties
spring.security.user.name=user
spring.security.user.password=password
```
and annotate your main application class with `@EnableWebSecurity`:
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Security configuration goes here
}
```

#### Customizing Security Configuration through SecurityFilterChain and WebSecurityConfigurerAdapter

To customize security settings, you can extend `WebSecurityConfigurerAdapter` and override its methods. This allows you to define custom security rules, configure authentication providers, and set up access control.

For example, you can define security rules based on URL patterns:
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
}
```

In this example:
- Requests to `/public/**` are permitted to all users.
- Requests to `/admin/**` require the user to have the `ADMIN` role.
- All other requests require authentication.
- A custom login page is configured at `/login`.
- The logout functionality is permitted to all users.

This is using the `WebSecurityConfigurerAdapter` to define security rules based on URL patterns. You can also configure authentication providers, password encoding, and other security settings. Another approach (available from Spring Boot 3.x+) is to use the `SecurityFilterChain` interface to define security rules programmatically. The following example shows how to configure basic authentication using `SecurityFilterChain`:
```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .logout(withDefaults());
        return http.build();
    }
}
```
Note how the `SecurityFilterChain` approach is simpler, more flexible and allows for fine-grained control over security configurations. It's recommended for new projects and provides a more modern way to define security rules.

#### User definitions and password encoding

In a real-world application, you would typically define users and roles in a database or LDAP server. Spring Security provides several ways to define users and roles, including:
- In-memory user details service
- JDBC-based user details service
- Custom user details service
- LDAP-based user details service
- OAuth-based user details service
- External identity providers

When defining users, it's important to hash passwords using a secure algorithm like BCrypt. Spring Security provides password encoding support through the `PasswordEncoder` interface. You can use `BCryptPasswordEncoder` to securely hash passwords:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```
and then encode passwords before storing them in the database:
```java
String encodedPassword = passwordEncoder.encode("password");
```
When authenticating users, Spring Security automatically decodes the stored password and compares it with the provided password.

This bean is usually part of the `WebSecurityConfigurerAdapter` configuration class, but it can also be defined separately if needed.


#### Advanced Authorization and JWT integration
Spring Security provides advanced authorization features, including method-level security and role-based access control. You can use annotations like `@PreAuthorize`, `@PostAuthorize`, `@Secured`, and `@RolesAllowed` to secure methods based on user roles and permissions.
```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/api/products/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
}
```
In this example, the `deleteProduct` method is secured with the `@PreAuthorize` annotation, which checks if the user has the `ADMIN` role before allowing access to the method.

Of course, you can always define these restrictions in your security configuration class like we did before, if you aim for centralization.

#### JWT (JSON Web Token) Authentication

JWT is a compact, URL-safe means of representing claims to be transferred between two parties. It allows you to securely transmit information as a JSON object, which can be verified and trusted because it is digitally signed.

A JWT token typically consists of three parts:
- **Header**: Contains metadata about the token, such as the algorithm used for signing (e.g. HS256).
- **Payload**: Contains the claims or data being transmitted. This can include user information, roles, and expiration time.
- **Signature**: The header and payload are base64-encoded and concatenated with a period (.) separator. The signature is created by signing the encoded header and payload with a secret key.

The main advantage of using JWT is that it allows stateless authentication, meaning the server does not need to store session information. The client can send the token with each request, and the server can verify its validity without needing to maintain session state.
To implement JWT authentication in a Spring Boot application, you typically follow these steps:
1. **Generate JWT Token**: When a user successfully logs in, generate a JWT token containing user information and roles. Sign the token with a secret key.
2. **Send JWT Token to Client**: Return the generated token to the client in the response body or as a cookie.
3. **Client Stores JWT Token**: The client stores the token (e.g., in local storage or a cookie) and includes it in the Authorization header of subsequent requests.
4. **Validate JWT Token**: On each request, the server validates the token by checking its signature and expiration time. If valid, extract user information and roles from the token.
5. **Authorize Access**: Based on the user information and roles extracted from the token, authorize access to protected resources.
6. **Refresh Token (optional)**: Implement a refresh token mechanism to allow users to obtain a new JWT token without re-authenticating. This is useful for long-lived sessions.
7. **Logout (optional)**: Implement a logout mechanism to invalidate the JWT token on the server side. This can be done by maintaining a blacklist of revoked tokens or using short-lived tokens with refresh tokens.

As a conceptual overview, here's how you might implement JWT authentication in a Spring Boot application:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests(authorizeRequests ->
                    authorizeRequests.requestMatchers("/authenticate").permitAll()
                    .anyRequest().authenticated()
            )
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

or through a dedicated class extending one of the base Spring Security classes (e.g. `OncePerRequestFilter`): 
```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtil.validateToken(token)) {
                Authentication auth = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```
This filter checks for the presence of a JWT token in the Authorization header, validates it, and sets the authentication context if valid.

If you follow the latter approach, the configuration class would change like this:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

One does not preclude the other, and you can use both approaches together if needed.

Remember that JWT and form-based authentication are not mutually exclusive. You can use JWT for stateless authentication in REST APIs while still using form-based authentication for web applications. Spring Security allows you to configure both methods side by side, depending on your application's requirements. 

That said, you can't use JWT for form-based authentication, as the latter relies on server-side sessions to maintain user state. JWT is designed for stateless authentication, where the server does not store session information. Instead, the client sends the JWT token with each request, and the server validates it without maintaining session state. However, you can use JWT for stateless authentication in REST APIs while still using form-based authentication for web applications. Spring Security allows you to configure both methods side by side, depending on your application's requirements.

### Aspect Oriented Programming (AOP) for Logging and Cross-cutting Concerns

#### Introduction to AOP
Aspect Oriented Programming (AOP) is a programming paradigm that allows you to separate cross-cutting concerns from the main business logic of your application. Cross-cutting concerns are aspects of a program that affect multiple modules, such as logging, security, transaction management, and error handling.
AOP allows you to define these concerns in a modular way, making your code cleaner and easier to maintain. In Spring, AOP is implemented using proxies and aspects.

#### Key Concepts of AOP
- **Aspect**: A module that encapsulates a cross-cutting concern. In Spring, an aspect is typically defined using the `@Aspect` annotation.
- **Join Point**: A point in the execution of the program where an aspect can be applied. This can be a method call, object instantiation, or field access.
- **Advice**: The action taken by an aspect at a join point. There are different types of advice:
  - **Before**: Executed before the join point.
  - **After**: Executed after the join point, regardless of its outcome.
  - **After Returning**: Executed after the join point if it completes successfully.
  - **After Throwing**: Executed if the join point throws an exception.
  - **Around**: Surrounds the join point, allowing you to control its execution.
- **Pointcut**: An expression that defines a set of join points where advice should be applied. Pointcuts can be defined using annotations, method names, or regular expressions.

#### Enabling AOP in Spring
To enable AOP in a Spring Boot application, you need to add the `spring-boot-starter-aop` dependency to your project. This starter includes the necessary dependencies for using AOP with Spring.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
or
```groovy
implementation 'org.springframework.boot:spring-boot-starter-aop'
```

Then, you have to annotate your main application class with `@EnableAspectJAutoProxy` to enable AOP support:
```java
@SpringBootApplication
@EnableAspectJAutoProxy
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

#### Creating an Aspect
To create an aspect, you need to define a class annotated with `@Aspect`. Inside this class, you can define pointcuts and advice methods.
For example, to log method execution times, you can create an aspect like this:
```java
@Aspect
@Component
public class LoggingAspect {

  // Log before execution of any method in the service package
  @Before("execution(* com.demostore.service.*.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("Entering method: " + joinPoint.getSignature().getName());
  }

  // Log after the method returns
  @AfterReturning(pointcut = "execution(* com.demostore.service.*.*(..))", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    System.out.println("Method " + joinPoint.getSignature().getName() + " returned: " + result);
  }
}
```

In this example:
- The `@Aspect` annotation marks the class as an aspect.
- The `@Before` annotation defines a pointcut that matches all methods in the `com.demostore.service` package. The advice method `logBefore` is executed before the matched methods.
- The `@AfterReturning` annotation defines a pointcut that matches all methods in the `com.demostore.service` package. The advice method `logAfterReturning` is executed after the matched methods return successfully, and it receives the result of the method as a parameter.
- The `JoinPoint` parameter provides information about the method being executed, such as its name and arguments.
- The `execution` expression specifies the join points where the advice should be applied. You can use wildcards and other expressions to match specific methods or packages.
- The `..` in the expression indicates that any number of arguments can be passed to the method.
- The `@Around` advice can be used to control the execution of the join point, allowing you to modify the input or output, handle exceptions, or even skip the execution altogether.

### Caching and Scheduling

#### Caching
Caching is a technique used to store frequently accessed data in memory to improve performance and reduce the load on the underlying data source (e.g., database, API). Spring provides a powerful caching abstraction that allows you to easily integrate caching into your application.
To enable caching in a Spring Boot application, you need to add the `spring-boot-starter-cache` dependency to your project:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
or
```groovy
implementation 'org.springframework.boot:spring-boot-starter-cache'
```
Then, you can enable caching by annotating your main application class with `@EnableCaching`:
```java
@SpringBootApplication
@EnableCaching
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

#### Caching Annotations
Spring provides several annotations for caching:
- `@Cacheable`: Indicates that the result of a method should be cached. The next time the method is called with the same arguments, the cached result will be returned instead of executing the method again.
- `@CachePut`: Indicates that the result of a method should be cached, but the method is always executed. This is useful for updating the cache with new data.
- `@CacheEvict`: Indicates that a cache entry should be removed. This is useful for invalidating cached data when it becomes stale or when the underlying data changes.
- `@CacheConfig`: Used to define common cache settings for a class, such as the cache name and default expiration time.

A typical example of using caching annotations would be:
```java
@Cacheable("products")
public List<Product> getAllProducts() {
    // Expensive database call or complex business logic
    return productRepository.findAll();
}

@CachePut(value = "products", key = "#product.id")
public Product updateProduct(Product product) {
    return productRepository.save(product);
}

@CacheEvict(value = "products", key = "#id")
public void deleteProduct(Long id) {
    productRepository.deleteById(id);
}
```

In this example:
- The `getAllProducts` method is annotated with `@Cacheable`, which means that the result will be cached. The next time this method is called with the same arguments, the cached result will be returned.
- The `updateProduct` method is annotated with `@CachePut`, which means that the method will be executed, and the result will be cached. This is useful for updating the cache with new data.
- The `deleteProduct` method is annotated with `@CacheEvict`, which means that the cache entry for the specified product ID will be removed when this method is called. This is useful for invalidating cached data when it becomes stale or when the underlying data changes.

#### Caching Providers
Spring supports various caching providers, including:
- **Ehcache**: A popular open-source caching library that provides in-memory and disk-based caching.
- **Caffeine**: A high-performance caching library that provides in-memory caching with advanced features like automatic eviction and expiration.
- **Hazelcast**: An in-memory data grid that provides distributed caching and data storage.
- **Redis**: An in-memory data structure store that can be used as a caching provider.
- **Infinispan**: A distributed in-memory key/value data store and cache.
- **JCache (JSR-107)**: A standard caching API for Java that provides a common interface for different caching providers.

To use a specific caching provider, you need to add the corresponding dependency to your project and configure it in your `application.properties` file. For example, to use Caffeine, you would add the following dependency:
```xml
<dependency>
  <groupId>com.github.ben-manes.caffeine</groupId>
  <artifactId>caffeine</artifactId>
  <version>3.9.4</version>
</dependency>
```
or
```groovy
implementation 'com.github.ben-manes.caffeine:caffeine:3.1.8'
```
and configure it in your `application.properties` file:
```properties
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=100,expireAfterAccess=10m
```

This configuration sets the cache type to Caffeine and specifies a maximum size of 100 entries and an expiration time of 10 minutes after the last access.

#### Scheduling

Scheduling is a technique used to execute tasks at specific intervals or at specific times. Spring provides a powerful scheduling abstraction that allows you to easily schedule tasks in your application.
Scheduling is already provided with the `spring-boot-starter` dependency, so you don't need to add any additional dependencies.

To enable scheduling in a Spring Boot application, you need to annotate your main application class with `@EnableScheduling`:
```java
@SpringBootApplication
@EnableScheduling
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

#### Scheduling Annotations
Spring provides several annotations for scheduling:
- `@Scheduled`: Indicates that a method should be executed at a fixed interval or at a specific time. You can specify the interval using cron expressions, fixed rates, or fixed delays.
  - `@Scheduled(fixedRate = 5000)`: Executes the method every 5 seconds.
  - `@Scheduled(fixedDelay = 5000)`: Executes the method every 5 seconds after the previous execution
  - `@Scheduled(cron = "0 0/5 * * * ?")`: Executes the method every 5 minutes using a cron expression.


### Testing and Quality Code

#### Unit Testing with JUnit and Mockito
JUnit is a popular testing framework for Java applications. It provides annotations and assertions to write unit tests for your code. Mockito is a mocking framework that allows you to create mock objects for testing purposes.
Mockito is often used in conjunction with JUnit to create unit tests for your service and repository layers. It allows you to mock dependencies and verify interactions between objects.

To use JUnit and Mockito in your Spring Boot application, you need to add the following dependencies to your `pom.xml` or `build.gradle` file:
```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-params</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```
or
```groovy
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.mockito:mockito-core'
testImplementation 'org.mockito:mockito-junit-jupiter'
testImplementation 'org.junit.jupiter:junit-jupiter-engine'
testImplementation 'org.junit.jupiter:junit-jupiter-api'
testImplementation 'org.junit.jupiter:junit-jupiter-params'
```

#### Writing Unit Tests
Unit tests are typically written in the `src/test/java` directory of your project. You can create test classes that mirror the structure of your main application classes.
For example, if you have a `ProductService` class, you can create a corresponding test class named `ProductServiceTest`. In this test class, you can use JUnit and Mockito to write unit tests for the service methods.

You would typically use the `@SpringBootTest` annotation to load the application context and the `@MockBean` annotation to create mock objects for dependencies. You can also use the `@InjectMocks` annotation to inject the mock objects into the class under test.
```java
@SpringBootTest
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  public void testFindAll() {
    List<Product> products = Arrays.asList(
            new Product(1L, "Product 1", "Apple", 100.),
            new Product(2L, "Product 2", "Banana", 200.)
    );
    Mockito.when(productRepository.findAll()).thenReturn(products);

    List<Product> result = productService.findAll();

    assertEquals(2, result.size());
    Mockito.verify(productRepository, Mockito.times(1)).findAll();
  }
}
```

In this example:
- The `@SpringBootTest` annotation loads the application context for the test.
- The `@Mock` annotation creates a mock object for the `ProductRepository` dependency.
- The `@InjectMocks` annotation injects the mock object into the `ProductService` instance.
- The `@Test` annotation marks the method as a test case.
- The `Mockito.when` method is used to define the behavior of the mock object when the `findAll` method is called.
- The `assertEquals` method is used to assert that the result of the `findAll` method is as expected.
- The `Mockito.verify` method is used to verify that the `findAll` method of the `productRepository` was called exactly once.
- You can also use parameterized tests to run the same test with different inputs. JUnit 5 provides the `@ParameterizedTest` annotation for this purpose. You can use various sources for parameters, such as `@ValueSource`, `@MethodSource`, or `@CsvSource`.
```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3})
public void testFindById(int id) {
    Product product = new Product(id, "Product " + id, "Apple", 100.);
    Mockito.when(productRepository.findById((long) id)).thenReturn(Optional.of(product));

    Product result = productService.findById((long) id);

    assertEquals(product, result);
    Mockito.verify(productRepository, Mockito.times(1)).findById((long) id);
}
```
In this example, the `@ParameterizedTest` annotation is used to run the `testFindById` method with different values for the `id` parameter. The `@ValueSource` annotation provides the values for the parameterized test.
You can also use `@MethodSource` to provide a method that returns a stream of arguments for the test:
```java
@ParameterizedTest
@MethodSource("productProvider")
public void testFindById(Product product) {
    Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

    Product result = productService.findById(product.getId());

    assertEquals(product, result);
    Mockito.verify(productRepository, Mockito.times(1)).findById(product.getId());
}

private static Stream<Arguments> productProvider() {
    return Stream.of(
            Arguments.of(new Product(1L, "Product 1", "Apple", 100.)),
            Arguments.of(new Product(2L, "Product 2", "Banana", 200.))
    );
}
```
In this example, the `productProvider` method returns a stream of `Arguments` objects, each containing a `Product` instance. The `testFindById` method is executed for each product in the stream.

Please note the advantage that Mockito provides over JUnit: it allows you to create mock objects for dependencies, which makes it easier to isolate the class under test and verify interactions between objects. This is particularly useful when testing service and repository layers, where you may need to mock database calls or external API calls. Mockito also provides features like argument matchers, verification, and stubbing, which make it a powerful tool for writing unit tests. You can use Mockito to create mock objects, define their behavior, and verify interactions with them.

#### Integration Testing with Spring Boot
Integration testing is the process of testing the interaction between different components of your application. In a Spring Boot application, integration tests typically involve loading the application context and testing the behavior of multiple components together.
Integration tests are typically written in the `src/test/java` directory of your project. You can create test classes that mirror the structure of your main application classes.
For example, if you have a `ProductController` class, you can create a corresponding test class named `ProductControllerTest`. In this test class, you can use JUnit and Spring's testing support to write integration tests for the controller methods.
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Test
    @WithMockUser(username = "user")
    public void testGetAllProducts() throws Exception {
      List<Product> products = Arrays.asList(
              new Product(1L, "Product 1", "Apple", 100.),
              new Product(2L, "Product 2", "Banana", 200.)
      );
      Mockito.when(productService.findAll()).thenReturn(products);
  
      mockMvc.perform(
                      get("/api/products")
                              .contentType("application/json")
                              .accept("application/json")
              )
              .andExpect(status().isOk())
              .andDo(
                      result -> System.out.println(result.getResponse().getContentAsString())
              )
              .andExpect(jsonPath("$[0].name").value("Product 1"))
              .andExpect(jsonPath("$[1].name").value("Product 2"));
    }
}
```
In this example:
- The `@SpringBootTest` annotation loads the application context for the test. The `webEnvironment` attribute is set to `RANDOM_PORT`, which means that a random port will be used for the embedded web server.
- The `@AutoConfigureMockMvc` annotation configures the `MockMvc` instance for testing the controller.
- The `@Autowired` annotation injects the `MockMvc` instance into the test class.
- The `@Mock` annotation creates a mock object for the `ProductService` dependency.
- The `@WithMockUser` annotation creates a mock user with the specified username and roles for testing purposes. This is useful for testing secured endpoints that require authentication.
- The `mockMvc.perform` method is used to perform a GET request to the `/api/products` endpoint. The request is configured with the content type and accept headers.
- The `andExpect` method is used to assert the expected status code and response content. The `jsonPath` method is used to assert the values of specific fields in the JSON response.
- The `andDo` method is used to print the response content for debugging purposes.

Please note that since we're using an in-memory database for testing, you don't need to set up a separate database for integration tests, otherwise the test will fail.

#### Code Quality and Static Analysis
Code quality is an important aspect of software development. It refers to the overall quality of the codebase, including readability, maintainability, and adherence to coding standards. High-quality code is easier to understand, modify, and extend, which leads to fewer bugs and better collaboration among developers.
To ensure code quality in your Spring Boot application, you can use various tools and techniques, among which:
- **Static Code Analysis**: Use static code analysis tools to analyze your code for potential issues, such as code smells, security vulnerabilities, and performance problems. Tools like SonarQube, Checkstyle, PMD, and FindBugs can help identify these issues and provide recommendations for improvement.
- **Code Formatting**: Use code formatting tools to ensure consistent code style across your project. Tools like Prettier, Spotless, and Checkstyle can help enforce coding standards and automatically format your code.
- **Linting**: Use linting tools to identify and fix potential issues in your code. Linting tools can help catch common mistakes, such as unused variables, incorrect imports, and inconsistent naming conventions.
- **Unit Testing**: Write unit tests for your code to ensure that it behaves as expected. Unit tests help catch bugs early in the development process and provide documentation for your code.
- **Integration Testing**: Write integration tests to ensure that different components of your application work together as expected. Integration tests help catch issues that may arise from interactions between components.

We already covered the latter two points, so let's focus on the first three.

#### Static Code Analysis with SonarQube
SonarQube is an open-source platform for continuous inspection of code quality. It provides static code analysis, code coverage, and code quality metrics. SonarQube can be integrated into your build process to automatically analyze your code and provide feedback on code quality.
To use SonarQube in your Spring Boot application, you need to add the SonarQube plugin to your build tool (e.g., Maven or Gradle) and configure it in your `pom.xml` or `build.gradle` file.
For Maven, you can add the following plugin to your `pom.xml` file:
```xml
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>4.0.0.2929</version>
</plugin>
```
For Gradle, you can add the following plugin to your `build.gradle` file:
```groovy
plugins {
    id "org.sonarqube" version "4.0.0.2929"
}
```
Of course, you need to install [SonarQube](https://www.sonarsource.com/products/sonarqube/downloads/) and run it locally or on a server. You can download SonarQube from the official website and follow the installation instructions. Once SonarQube is running, you can access the web interface at `http://localhost:9000` (or the configured URL) to view the analysis results.

Then, you can run the SonarQube analysis using the following command:
```bash
mvn sonar:sonar
```
or
```bash
./gradlew sonar
```

This will analyze your code and send the results to the SonarQube server. You can then view the analysis results in the SonarQube web interface.

#### Code Formatting with Checkstyle

Checkstyle is a static code analysis tool that helps enforce coding standards and best practices in Java code. It checks your code against a set of predefined rules and provides feedback on code style, formatting, and potential issues.
To use Checkstyle in your Spring Boot application, you need to add the Checkstyle plugin to your build tool (e.g., Maven or Gradle) and configure it in your `pom.xml` or `build.gradle` file.
For Maven, you can add the following plugin to your `pom.xml` file:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
        <failOnViolation>true</failOnViolation>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
For Gradle, you can add the following plugin to your `build.gradle` file:
```groovy
plugins {
    id "checkstyle"
}

checkstyle {
  toolVersion = '10.21.4'
  configFile = file("${rootDir}/checkstyle.xml")
  ignoreFailures = false
  showViolations = true
}

checkstyleMain {
  reports {
    xml.required = true
    html.required = false
  }
}
```

You can create a `checkstyle.xml` file in the root of your project or in a separate directory (e.g., `config/checkstyle`) to define the Checkstyle rules you want to enforce. You can use the default Checkstyle configuration or customize it according to your project's coding standards. You can use the [Google Java Style Guide](https://checkstyle.sourceforge.io/).
To run Checkstyle, you can use the following command:
```bash
mvn checkstyle:check
```
or
```bash
./gradlew checkstyleMain
```

This will analyze your code and report any violations against the defined Checkstyle rules. You can view the results in the console output or in the generated reports.

Other tools for static code analysis include PMD, FindBugs, and SpotBugs. These tools can be used in a similar way to Checkstyle to analyze your code for potential issues and provide feedback on code quality.
- **PMD**: A static code analysis tool that checks Java code for potential issues, such as unused variables, empty catch blocks, and unnecessary object creation. PMD provides a set of predefined rules and allows you to create custom rules.
- **FindBugs**: A static code analysis tool that detects potential bugs in Java code. FindBugs analyzes bytecode to identify common programming mistakes, such as null pointer dereferences, infinite recursive loops, and thread synchronization issues.
- **SpotBugs**: A fork of FindBugs that provides additional features and improvements. SpotBugs is actively maintained and supports the latest Java versions. It can be used as a standalone tool or integrated into your build process.
- **SonarLint**: A static code analysis tool that provides real-time feedback on code quality in your IDE. SonarLint integrates with popular IDEs like IntelliJ IDEA, Eclipse, and Visual Studio Code. It helps you identify and fix code quality issues as you write code, ensuring that your code adheres to coding standards and best practices.
- **Spotless**: A code formatting tool that integrates with various build tools (e.g., Maven, Gradle) and IDEs. Spotless can automatically format your code according to predefined rules and coding standards. It supports multiple languages, including Java, Kotlin, and Groovy.
- **Prettier**: A code formatter that supports multiple languages and integrates with various IDEs and build tools. Prettier automatically formats your code according to a set of rules, ensuring consistent code style across your project. It is particularly popular in the JavaScript and TypeScript communities but can also be used for Java projects.

### Better logging and monitoring

#### Logging (Part 2)
Logging is an essential aspect of any application, as it helps you monitor the application's behavior, troubleshoot issues, and gather insights into its performance. Spring Boot provides built-in support for logging through the SLF4J (Simple Logging Facade for Java) API and Logback as the default logging implementation.
You can configure logging in your Spring Boot application using the `application.properties` or `application.yml` file. You can set the logging level, specify the logging format, and configure log file locations.
```properties
# Set the logging level for the entire application
logging.level.root=INFO
# Set the logging level for a specific package
logging.level.com.demostore=DEBUG
# Set the logging pattern for the console output
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
# Set the logging pattern for the file output
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
# Set the log file name and location
logging.file.name=logs/application.log
# Set the log file size and retention policy
logging.file.size=10MB
logging.file.max-history=30
```
You can also configure logging using YAML:
```yaml
logging:
  level:
    root: INFO
    com.demostore: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: logs/application.log
    size: 10MB
    max-history: 30
```

Apart from the native Java logging, Spring Boot also supports other logging frameworks like Log4j2 and Log4j. You can use these frameworks by adding the corresponding dependencies to your project and configuring them in the `application.properties` or `application.yml` file.
For example, to use Log4j2, you need to add the following dependencies to your `pom.xml` or `build.gradle` file:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.17.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.17.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-web</artifactId>
    <version>2.17.1</version>
</dependency>
```
or
```groovy
implementation 'org.springframework.boot:spring-boot-starter-log4j2'
implementation 'org.apache.logging.log4j:log4j-core:2.17.1'
implementation 'org.apache.logging.log4j:log4j-slf4j-impl:2.17.1'
implementation 'org.apache.logging.log4j:log4j-api:2.17.1'
implementation 'org.apache.logging.log4j:log4j-web:2.17.1'
```

Then, you can create a `log4j2.xml` file in the `src/main/resources` directory to configure Log4j2:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} - %msg%n"/>
        </Console>
        <File name="File" fileName="logs/application.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```
This configuration defines two appenders: one for console output and one for file output. The logging level is set to `INFO` for the root logger, and both appenders are attached to it.
You can also configure Log4j2 using YAML or JSON formats. For example, to use YAML, you can create a `log4j2.yml` file in the `src/main/resources` directory:
```yaml
Configuration:
  status: WARN
  Appenders:
    Console:
      name: Console
      target: SYSTEM_OUT
      PatternLayout:
        pattern: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    File:
      name: File
      fileName: logs/application.log
      PatternLayout:
        pattern: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  Loggers:
    Root:
      level: info
      AppenderRef:
        - ref: Console
        - ref: File
```
or JSON:
```json
{
  "Configuration": {
    "status": "WARN",
    "Appenders": {
      "Console": {
        "name": "Console",
        "target": "SYSTEM_OUT",
        "PatternLayout": {
          "pattern": "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
        }
      },
      "File": {
        "name": "File",
        "fileName": "logs/application.log",
        "PatternLayout": {
          "pattern": "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
        }
      }
    },
    "Loggers": {
      "Root": {
        "level": "info",
        "AppenderRef": [
          {"ref": "Console"},
          {"ref": "File"}
        ]
      }
    }
  }
}
```

#### Monitoring
Monitoring is the process of collecting and analyzing data about your application's performance, resource usage, and behavior. Monitoring helps you identify issues, optimize performance, and ensure the reliability of your application.
Spring Boot provides built-in support for monitoring through the Actuator module. The Actuator module exposes various endpoints that provide information about the application's health, metrics, and environment.
To use the Actuator module in your Spring Boot application, you need to add the `spring-boot-starter-actuator` dependency to your project:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
or
```groovy
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```

Then, you can configure the Actuator endpoints in your `application.properties` or `application.yml` file:
```properties
# Enable all Actuator endpoints
# management.endpoints.web.exposure.include=*
# Or just enable specific Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics
# Enable Actuator endpoints over HTTP
management.endpoints.web.base-path=/actuator
# Enable Actuator endpoints over JMX
management.endpoints.jmx.exposure.include=*
# Enable Actuator health check
management.health.diskspace.enabled=true
# Set the disk space threshold for the health check
management.health.diskspace.threshold=10MB
# Set the health check status
management.endpoint.health.status.http-mapping.UP=200
management.endpoint.health.status.http-mapping.DOWN=503
```
or
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
      base-path: /actuator
    jmx:
      exposure:
        include: "*"
  health:
    diskspace:
      enabled: true
      threshold: 10MB
  endpoint:
    health:
      status:
        http-mapping:
          UP: 200
          DOWN: 503
```
You can access the Actuator endpoints by sending HTTP requests to the `/actuator` base path. For example, to check the application's health, you can send a GET request to `/actuator/health`. The response will include information about the application's health status and any additional health indicators that are enabled.
```bash
curl -X GET http://localhost:8080/actuator/health
```
The response will look like this:
```json
{
  "status": "UP",
  "components": {
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 1000000000,
        "free": 500000000,
        "threshold": 10000000
      }
    }
  }
}
```
In this example, the `status` field indicates that the application is healthy (`UP`), and the `components` field provides additional information about the disk space health indicator.
You can also access other Actuator endpoints, such as `/actuator/info`, which provides information about the application, and `/actuator/metrics`, which provides various metrics about the application's performance and resource usage.
```bash
curl -X GET http://localhost:8080/actuator/info
```
The response will look like this:
```json
{
  "app": {
    "name": "My Application",
    "version": "1.0.0"
  }
}
```
```bash
curl -X GET http://localhost:8080/actuator/metrics
```
The response will look like this:
```json
{
  "names": [
    "jvm.memory.used",
    "jvm.gc.pause",
    "system.cpu.usage",
    "http.server.requests"
  ]
}
```
You can also use the `/actuator/metrics/{metricName}` endpoint to get detailed information about a specific metric. For example, to get information about the `jvm.memory.used` metric, you can send a GET request to `/actuator/metrics/jvm.memory.used`.
```bash
curl -X GET http://localhost:8080/actuator/metrics/jvm.memory.used
```
The response will look like this:
```json
{
  "name": "jvm.memory.used",
  "description": "Memory used",
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 123456789
    }
  ],
  "availableTags": []
}
```
In this example, the `measurements` field provides the value of the `jvm.memory.used` metric, which indicates the amount of memory used by the JVM.
You can also use the `/actuator/heapdump` endpoint to get a heap dump of the application. A heap dump is a snapshot of the memory used by the application, which can be useful for analyzing memory leaks and performance issues.
```bash
curl -X GET http://localhost:8080/actuator/heapdump
```
The response will be a binary file containing the heap dump. You can save this file and analyze it using tools like Eclipse Memory Analyzer (MAT) or VisualVM.
You can also use the `/actuator/threaddump` endpoint to get a thread dump of the application. A thread dump is a snapshot of the threads running in the JVM, which can be useful for analyzing performance issues and deadlocks.
```bash
curl -X GET http://localhost:8080/actuator/threaddump
```
The response will be a plain text file containing the thread dump. You can save this file and analyze it using tools like VisualVM or JConsole.
```bash
curl -X GET http://localhost:8080/actuator/loggers
```
The response will look like this:
```json
{
  "loggers": {
    "com.demostore": {
      "configuredLevel": "DEBUG",
      "effectiveLevel": "DEBUG"
    },
    "org.springframework": {
      "configuredLevel": "INFO",
      "effectiveLevel": "INFO"
    }
  }
}
```

And of course many, many more endpoints are available. You can find the complete list of Actuator endpoints in the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator).

#### Monitoring with Micrometer
Micrometer is a metrics collection and monitoring library that integrates with Spring Boot. It provides a simple and consistent API for collecting application metrics and supports various monitoring systems, such as Prometheus, Grafana, InfluxDB, and more.
To use Micrometer in your Spring Boot application, you need to add the `micrometer-core` dependency to your project:
```xml
<dependency>
  <groupId>io.micronaut.micrometer</groupId>
  <artifactId>micronaut-micrometer-core</artifactId>
  <version>5.8.0</version>
</dependency>
```
or
```groovy
implementation 'io.micronaut.micrometer:micronaut-micrometer-core:5.8.0'
```

You can also add the dependencies for the specific monitoring system you want to use. For example, to use Prometheus, you need to add the following dependency:
```xml
<dependency>
  <groupId>io.micronaut.micrometer</groupId>
  <artifactId>micronaut-micrometer-registry-prometheus</artifactId>
  <version>5.8.0</version>
</dependency>
```
or
```groovy
implementation 'io.micronaut.micrometer:micronaut-micrometer-registry-prometheus:5.8.0'
```

Then, you can configure Micrometer in your `application.properties` or `application.yml` file:
```properties
# Enable Micrometer metrics
management.prometheus.metrics.export.pushgateway.base-url=http://localhost:9091
management.prometheus.metrics.export.pushgateway.job=store
management.prometheus.metrics.export.pushgateway.push-rate=1m
management.prometheus.metrics.export.pushgateway.shutdown-operation=delete
management.prometheus.metrics.export.pushgateway.username=admin
management.prometheus.metrics.export.pushgateway.password=admin
```
