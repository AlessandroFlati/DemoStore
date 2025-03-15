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
