Spring Boot provides a wide variety of annotations, categorized based on their functionality. Here’s a breakdown:

---

## **1. Core Spring Annotations**
| Annotation | Description |
|------------|------------|
| `@Configuration` | Defines a configuration class for Spring Boot. |
| `@Component` | Marks a generic Spring-managed bean. |
| `@Bean` | Declares a method that returns a bean to be managed by Spring. |
| `@Value` | Injects property values from `application.properties` or `application.yml`. |
| `@Lazy` | Initializes a bean lazily, only when required. |
| `@Primary` | Marks a bean as the preferred choice when multiple beans of the same type exist. |
| `@DependsOn` | Specifies that a bean must be initialized before another. |

---

## **2. Stereotype Annotations (Spring-managed Components)**
| Annotation | Description |
|------------|------------|
| `@Component` | Generic stereotype for Spring components. |
| `@Service` | Marks a service component (business logic layer). |
| `@Repository` | Marks a data access layer component and enables exception translation. |
| `@Controller` | Defines a Spring MVC controller. |
| `@RestController` | Combines `@Controller` and `@ResponseBody` to return JSON/XML responses. |

---

## **3. Dependency Injection Annotations**
| Annotation | Description |
|------------|------------|
| `@Autowired` | Injects dependencies automatically. |
| `@Qualifier` | Specifies which bean to inject when multiple beans of the same type exist. |
| `@Resource` | Similar to `@Autowired`, but part of Java EE. |
| `@Inject` | Alternative to `@Autowired` (from Java CDI). |

---

## **4. Spring Boot Configuration Annotations**
| Annotation | Description |
|------------|------------|
| `@SpringBootApplication` | Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. |
| `@EnableAutoConfiguration` | Enables Spring Boot's auto-configuration feature. |
| `@ComponentScan` | Scans specified packages for Spring components. |
| `@PropertySource` | Loads external properties from a file. |
| `@EnableConfigurationProperties` | Enables a `@ConfigurationProperties` class. |
| `@ConfigurationProperties` | Binds properties from `application.properties` to a POJO. |

---

## **5. Spring MVC & Web Annotations**
| Annotation | Description |
|------------|------------|
| `@RequestMapping` | Maps a URL to a controller method (can be used at the class or method level). |
| `@GetMapping` | Maps HTTP GET requests to a method. |
| `@PostMapping` | Maps HTTP POST requests to a method. |
| `@PutMapping` | Maps HTTP PUT requests to a method. |
| `@DeleteMapping` | Maps HTTP DELETE requests to a method. |
| `@PatchMapping` | Maps HTTP PATCH requests to a method. |
| `@ResponseBody` | Serializes an object to JSON/XML as a response. |
| `@RequestBody` | Binds request body to a method parameter. |
| `@PathVariable` | Extracts values from the URL path. |
| `@RequestParam` | Extracts query parameters. |
| `@ModelAttribute` | Binds form data to a model object. |
| `@RestControllerAdvice` | Provides global exception handling for REST APIs. |
| `@ExceptionHandler` | Defines an exception handler for a controller. |

---

## **6. Spring Data (JPA & MongoDB) Annotations**
| Annotation | Description |
|------------|------------|
| `@Entity` | Marks a class as a JPA entity. |
| `@Table` | Specifies the database table for an entity. |
| `@Id` | Marks a field as the primary key. |
| `@GeneratedValue` | Specifies primary key generation strategy. |
| `@Column` | Defines column properties. |
| `@ManyToOne` | Defines a many-to-one relationship. |
| `@OneToMany` | Defines a one-to-many relationship. |
| `@ManyToMany` | Defines a many-to-many relationship. |
| `@JoinColumn` | Specifies the foreign key column. |
| `@Transactional` | Manages transactions for a method or class. |
| `@Cacheable` | Enables caching for a method. |

---

## **7. Spring Security Annotations**
| Annotation | Description |
|------------|------------|
| `@EnableWebSecurity` | Enables Spring Security for the application. |
| `@PreAuthorize` | Restricts access to methods based on conditions. |
| `@PostAuthorize` | Checks authorization after a method is executed. |
| `@Secured` | Defines security roles required for a method. |
| `@RolesAllowed` | Specifies allowed roles for a method. |

---

## **8. Spring Boot Messaging (RabbitMQ, Kafka)**
| Annotation | Description |
|------------|------------|
| `@EnableRabbit` | Enables RabbitMQ support. |
| `@RabbitListener` | Listens to messages from a RabbitMQ queue. |
| `@KafkaListener` | Listens to messages from a Kafka topic. |

---

## **9. Spring Boot Testing Annotations**
| Annotation | Description |
|------------|------------|
| `@SpringBootTest` | Loads the full Spring Boot application context for testing. |
| `@WebMvcTest` | Tests Spring MVC controllers in isolation. |
| `@MockBean` | Mocks a Spring bean for testing. |
| `@DataJpaTest` | Tests JPA repositories. |

---

## **10. Lifecycle & Initialization Annotations**
| Annotation | Description |
|------------|------------|
| `@PostConstruct` | Executes a method after dependency injection is complete. |
| `@PreDestroy` | Executes a method before the bean is destroyed. |
